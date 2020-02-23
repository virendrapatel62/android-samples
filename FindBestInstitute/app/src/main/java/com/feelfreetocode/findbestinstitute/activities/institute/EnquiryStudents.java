package com.feelfreetocode.findbestinstitute.activities.institute;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.activities.student.StudentProfile;
import com.feelfreetocode.findbestinstitute.models.Admission;
import com.feelfreetocode.findbestinstitute.models.Enquiry;
import com.feelfreetocode.findbestinstitute.models.Student;

import java.util.ArrayList;

public class EnquiryStudents extends AppCompatActivity {

    RecyclerView enquiryStudentList;
    ArrayList<Enquiry> enquiries ;
    EnquiryStudentAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_students);

        enquiryStudentList = findViewById(R.id.enquiryStudentlist);

        enquiries = (ArrayList)getIntent().getSerializableExtra("students");

        adapter = new EnquiryStudentAdapter();
        enquiryStudentList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        enquiryStudentList.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.enquiry_nofitfication_menu , menu);
        return super.onCreateOptionsMenu(menu);

    }

    class EnquiryStudentAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.enquirystudentlayout , viewGroup , false);

            return new DataHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            DataHolder data = (DataHolder)viewHolder;
            data.alphabate.setText(enquiries.get(i).getStudent().getName().charAt(0)+"");
            data.name.setText(enquiries.get(i).getStudent().getName());
            data.number.setText(enquiries.get(i).getStudent().getContact());
            data.courseName.setText(enquiries.get(i).getCourse().getCourseName());
            data.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+enquiries.get(i).getStudent().getContact()));
                    startActivity(callIntent);
                }
            });

            data.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(EnquiryStudents.this , StudentProfile.class);
                    in.putExtra("student" , enquiries.get(i).getStudent());
                    startActivity(in);
                }
            });

            data.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add("Remove");
                    menu.add("Mark Admitted");
                    MenuItem m = menu.getItem(0);
                    MenuItem admitted = menu.getItem(1);
                    Log.e("clicked on " , m.toString());

                    m.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Log.e("Clicked On ", enquiries.get(i).toString() );
                            new DeleteTask().execute(enquiries.get(i));
                            enquiries.remove(enquiries.get(i));
                            return false;
                        }
                    });
                    admitted.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Log.e("Clicked On ", enquiries.get(i).toString() );
                            new MarkAdmittedTask().execute(enquiries.get(i));
                            enquiries.remove(enquiries.get(i));
                            return false;
                        }
                    });
                }
            });
        }


        @Override
        public int getItemCount() {
            return enquiries.size();
        }


        class DataHolder extends  RecyclerView.ViewHolder{
            TextView name , number , alphabate  , courseName  ;
            ImageView call;
            public DataHolder(@NonNull View itemView) {

                super(itemView);
                name = itemView.findViewById(R.id.nameofstudent);
                alphabate = itemView.findViewById(R.id.alphbate);
                number = itemView.findViewById(R.id.mobilofstudent);
                courseName = itemView.findViewById(R.id.courseName);
                call = itemView.findViewById(R.id.call);

            }
        }
    }
    class  DeleteTask extends AsyncTask<Enquiry, Integer, Enquiry> {
        @Override

        protected Enquiry doInBackground(Enquiry... integers) {
            Log.e("infooDelete" , "Deleting");
            integers[0].delete();
            return integers[0];
        }

        @Override
        protected void onPostExecute(Enquiry aBoolean) {
            super.onPostExecute(aBoolean);
            Log.e("infooDelete" , "Dleeted");
            adapter.notifyDataSetChanged();
        }
    }
    class  MarkAdmittedTask extends AsyncTask<Enquiry, Integer, Enquiry> {
        @Override

        protected Enquiry doInBackground(Enquiry... integers) {
            Log.e("infooSaving" , "Saving");
            new Admission(integers[0].getStudent() , integers[0].getCourse()).save();
            return integers[0];
        }

        @Override
        protected void onPostExecute(Enquiry aBoolean) {
            super.onPostExecute(aBoolean);
            Log.e("infooSave" , "Dleeted");
            new DeleteTask().execute(aBoolean);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.removeAllMenuItem){
            Log.e("infooCLicked" , "Deleting");

            for(int i = 0 ; i< enquiries.size() ; i++){
                 Enquiry e = enquiries.get(i);
                enquiries.remove(e);
                new DeleteTask().execute(e);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.enquiry_context_menu  , menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
}
