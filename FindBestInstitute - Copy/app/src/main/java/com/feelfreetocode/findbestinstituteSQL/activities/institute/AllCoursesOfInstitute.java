package com.feelfreetocode.findbestinstituteSQL.activities.institute;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import com.feelfreetocode.findbestinstituteSQL.models.Rating;
import com.feelfreetocode.findbestinstituteSQL.models.Student;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AllCoursesOfInstitute extends AppCompatActivity {

    private Student student;
    private Institute institute;
    private GridView courseGridView;
    TextView heading , fullName;
    private List<Course> courseArrayList;
    private CourseGridAdapter adapter ;
    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses_of_institute);
        getValuesFromIntent();
        initAll();
        collectAll();
    }
    private void initAll(){
        courseArrayList = new ArrayList<>();
        courseGridView = findViewById(R.id.courseGrid);
        heading = findViewById(R.id.heading);
        fullName = findViewById(R.id.fullName);
        adapter = new CourseGridAdapter();

        courseGridView.setAdapter(adapter);
        fullName.setText(institute.getInstituteName());
        heading.setText("");
        String[] s = institute.getInstituteName().trim().split(" ");
        for(int i = 0 ; i < s.length ; i++){
            heading.setText(heading.getText().toString()+s[i].charAt(0));
        }
        heading.setText(heading.getText().toString().toUpperCase());


        int color = R.color.later1;
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
        getWindow().setStatusBarColor(color);
        findViewById(R.id.header).setBackgroundColor(color);

        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.setMessage("Getting All Courses");
    }
    private void getValuesFromIntent(){
        student = (Student)getIntent().getSerializableExtra("student");
        institute = (Institute) getIntent().getSerializableExtra("institute");
    }
    class  CourseGridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return courseArrayList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.all_courses_of_institute_grid_layout , null);
            TextView title = view.findViewById(R.id.title);
            TextView name= view.findViewById(R.id.name);
            TextView fee = view.findViewById(R.id.fee);

            name.setText(courseArrayList.get(position).getCourseName());
            fee.setText("Rs. "+courseArrayList.get(position).getCourseFee()+"");

            String[] s = courseArrayList.get(position).getCourseName().trim().split(" ");
            for(int i = 0 ; i < s.length ; i++){
                title.setText(title.getText().toString()+s[i].charAt(0));
            }
            title.setText(title.getText().toString().toUpperCase());

            int color = R.color.later1;
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

            title.setBackgroundColor(color);

            return view;
        }
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


                AllCoursesOfInstitute.this.courseArrayList.clear();
                AllCoursesOfInstitute.this.courseArrayList.addAll(cc);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AllCoursesOfInstitute.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

}
