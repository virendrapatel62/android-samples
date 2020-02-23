package com.feelfreetocode.findbestinstituteSQL.activities.institute;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.constants.API;
import com.feelfreetocode.findbestinstituteSQL.models.Course;
import com.feelfreetocode.findbestinstituteSQL.models.Institute;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {
    private Institute institute;
    EditText courseName  , courseFee;
    Button save;
    ArrayList<Course> courses;
    ListView courseList ;
    ProgressBar progressBar;
    CourseListAdapter adapter ;
    Handler handler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        institute  = (Institute) getIntent().getSerializableExtra("institute");
        Toast.makeText(getApplicationContext() , institute.toString() , Toast.LENGTH_LONG ).show();
        initAll();
        setClickListener();
        collectAll();
    }

    private void collectAll(){
        Uri.Builder builder = Uri.parse(API.GET_COURSES_BY_INSTITUTE).buildUpon();
        builder.appendQueryParameter("instituteId" , institute.getInstituteId().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Course[] courses = (Course[])new Gson().fromJson(response , Course[].class);
                Log.e("----------" , courses.toString());
                List<Course> cc = new ArrayList<>();
                for(int i = 0  ; i < courses.length ; i++){
                    cc.add(courses[i]);
                }


                AddCourseActivity.this.courses.clear();
                AddCourseActivity.this.courses.addAll(cc);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddCourseActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setClickListener(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cname = courseName.getText().toString().trim();
                final String cfee = courseFee.getText().toString().trim();

                if(cname.isEmpty()){
                    courseName.setError("Enter Course Name");
                    return;
                }

                if(cfee.isEmpty()){
                    courseFee.setError("Enter Course Fees");
                    return;
                }


                Uri.Builder builder = Uri.parse(API.SAVE_COURSE).buildUpon();
                builder.appendQueryParameter("courseName" , cname);
                builder.appendQueryParameter("courseFee" , cfee);
                builder.appendQueryParameter("instituteId" , institute.getInstituteId().toString());

                StringRequest saveCourseStringRequest = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if(response.equalsIgnoreCase("false")){

                            Toast.makeText(AddCourseActivity.this, "Cant save", Toast.LENGTH_SHORT).show();
                        }else {
                            Course course = new Course();
                            course.setCourseFee(Integer.parseInt(cfee));
                            course.setCourseName(cname );
                            courses.add(course);
                            adapter.notifyDataSetChanged();

                            courseName.setText("");
                            courseFee.setText("");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(AddCourseActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("------" , "sending ");

                Volley.newRequestQueue(getApplicationContext()).add(saveCourseStringRequest);



            }
        });
    }

    private void initAll(){
        save = findViewById(R.id.save);
        courseName = findViewById(R.id.courseName);
        courseFee = findViewById(R.id.courseFee);
        courseList = findViewById(R.id.courseList);
        progressBar = findViewById(R.id.progressBar2);
        courses = new ArrayList<>();
        adapter = new CourseListAdapter(courses);
        courseList.setAdapter(adapter);

    }

    class CourseListAdapter extends BaseAdapter{
        List<Course> courses ;
        CourseListAdapter(List<Course> courses){
            this.courses = courses;
        }
        @Override
        public int getCount() {
            return courses.size();
        }

        public void add(Course course){
            courses.add(course);
            this.notifyDataSetChanged();
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
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.institute_add_course_course_list , null);
            ((TextView)view.findViewById(R.id.name)).setText(courses.get(position).getCourseName());
            if(courses.get(position).getCourseFee()  == -1)
                ((TextView)view.findViewById(R.id.fee)).setText("Course Fee");
            else
                ((TextView)view.findViewById(R.id.fee)).setText("Rs. "+courses.get(position).getCourseFee()+"");
            return  view;

        }
    }
}
