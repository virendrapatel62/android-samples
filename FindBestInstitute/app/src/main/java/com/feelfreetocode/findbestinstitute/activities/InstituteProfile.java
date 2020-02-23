package com.feelfreetocode.findbestinstitute.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.activities.institute.AllCoursesOfInstitute;
import com.feelfreetocode.findbestinstitute.models.Course;
import com.feelfreetocode.findbestinstitute.models.Enquiry;
import com.feelfreetocode.findbestinstitute.models.Student;

import java.util.ArrayList;
import java.util.Random;

public class InstituteProfile extends AppCompatActivity {

    private Course course ;
    private Student student ;
    private ListView dataList;
    ArrayList<String> keys ;
    ArrayList<String> values;
    PropertiesAdapter adapter;
    private TextView heading;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_profile);
        getValuesFromIntent();
        initAll();
        setOnClickListener();
    }

    private void initAll(){
        dataList = findViewById(R.id.datalist);
        heading = findViewById(R.id.heading);
        int random = new Random().nextInt(5);
        switch (random){
            case 0:
                color = getResources().getColor(R.color.later1);
                break;
            case 1:
                color = getResources().getColor(R.color.later5);
                break;
            case 2:
                color = getResources().getColor(R.color.later2);
                break;
            case 3:
                color = getResources().getColor(R.color.later3);
                break;
            case 4:
                color = getResources().getColor(R.color.later4);
                break;
        }

        heading.setBackgroundColor(color);
        getWindow().setStatusBarColor(color);
        adapter = new PropertiesAdapter();

        String courseName = course.getInstitute().getInstituteName();
        heading.setText("");
        String[] s = courseName.split(" ");
        try{
            for(int n = 0 ; n<s.length ; n++){
                String m = s[n].trim().charAt(0)+"";
                heading.setText(heading.getText().toString()+m);
            }
            heading.setText(heading.getText().toString().trim().toUpperCase());
        }catch(Exception ex){
            ex.printStackTrace();
            heading.setText(course.getInstitute().getInstituteName().charAt(0)+"");
        }



        keys = new ArrayList<>();
        values = new ArrayList<>();

        keys.add("Institute"); values.add(course.getInstitute().getInstituteName());
        keys.add("Institute Address (Click to open on Map)"); values.add(course.getInstitute().getAddress());
        keys.add("Email"); values.add(course.getInstitute().getEmail());
        keys.add("Contact"); values.add(course.getInstitute().getContact());
        keys.add("Owner"); values.add(course.getInstitute().getAdminName());
        keys.add(""); values.add("Cheak Out All Courses ->");

        dataList.setAdapter(adapter);
    }

    private void setOnClickListener(){
        dataList.setOnItemClickListener((AdapterView.OnItemClickListener) new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == keys.size()-1){
                    Intent intent = new Intent(InstituteProfile.this , AllCoursesOfInstitute.class);
                    intent.putExtra("institute" , course.getInstitute());
                    intent.putExtra("student" , student);
                    startActivity(intent);
                }
                if(position == 1){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://maps.google.com/maps?q=loc:"+course.getInstitute().getLat()+","+course.getInstitute().getLang()));
                    startActivity(intent);
                }
                if(position == 3){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+course.getInstitute().getContact()));
                    startActivity(intent);
                }
                if(position == 2){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, course.getInstitute().getEmail());
                    startActivity(Intent.createChooser(intent , "Email using "));
                }
            }
        });
    }

    private void getValuesFromIntent(){
        student = (Student)getIntent().getSerializableExtra("student");
        course = (Course)getIntent().getSerializableExtra("course");

        AsyncTask<Integer , Integer , Boolean> saveEnquiry = new AsyncTask<Integer, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Integer... integers) {
                Enquiry enquiry = new Enquiry();
                enquiry.setCourse(course);
                enquiry.setStudent(student);
                boolean flag = enquiry.save();
                Log.e("infooEnquiry" , flag+"");
                return true;
            }
        } ;

        saveEnquiry.execute();

//        Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, course.toString(), Toast.LENGTH_SHORT).show();
    }

    // adapter for list

    class PropertiesAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return keys.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.institute_profile_layout , null);
            TextView key = view.findViewById(R.id.key);
            TextView value= view.findViewById(R.id.value);

            key.setText(keys.get(position));
            value.setText(values.get(position));

            if(position == keys.size()-1){
                CardView c = view.findViewById(R.id.card);
                c.setCardBackgroundColor(color);
                value.setTextColor(getResources().getColor(R.color.white));
            }


            return view;
        }
    }
}
