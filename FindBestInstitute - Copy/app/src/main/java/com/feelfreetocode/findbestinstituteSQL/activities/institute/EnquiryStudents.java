package com.feelfreetocode.findbestinstituteSQL.activities.institute;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.activities.student.StudentProfile;
import com.feelfreetocode.findbestinstituteSQL.constants.API;
import com.feelfreetocode.findbestinstituteSQL.models.Admission;
import com.feelfreetocode.findbestinstituteSQL.models.Enquiry;

import java.lang.reflect.Method;
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

        enquiries = (ArrayList)getIntent().getSerializableExtra("enquiries");

        adapter = new EnquiryStudentAdapter();
        enquiryStudentList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        enquiryStudentList.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.enquiry_nofitfication_menu , menu);



        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("-------------" , "oprionmenu");
        for(final Enquiry e: enquiries){
            Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.GET, API.REMOVE_ENQUIRY + "?enquiryId=" + e.getEnquiryId() + "", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    enquiries.remove(e);
                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EnquiryStudents.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }));
        }
        return super.onOptionsItemSelected(item);
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
                            Log.e("----------", enquiries.get(i).toString() );
                            Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.GET, API.REMOVE_ENQUIRY + "?enquiryId=" + enquiries.get(i).getEnquiryId() + "", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    enquiries.remove(enquiries.get(i));
                                    adapter.notifyDataSetChanged();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(EnquiryStudents.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            }));
                            return false;
                        }
                    });
                    admitted.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Log.e("-------", enquiries.get(i).toString() );
                            Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.GET, API.MARK_ADMITTED + "?enquiryId=" + enquiries.get(i).getEnquiryId() + "", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("----------" , response);
                                    if(response.equalsIgnoreCase("\"true\"")){
                                        enquiries.remove(enquiries.get(i));
                                        Toast.makeText(EnquiryStudents.this, "Student Admitted", Toast.LENGTH_SHORT).show();
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(EnquiryStudents.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            }));
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.enquiry_context_menu  , menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Log.e("------------" , item.toString());
        return super.onContextItemSelected(item);
    }


}
