package com.feelfreetocode.findbestinstituteSQL.activities.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.feelfreetocode.findbestinstituteSQL.activities.InstituteProfile;
import com.feelfreetocode.findbestinstituteSQL.activities.LoginByScrenn;
import com.feelfreetocode.findbestinstituteSQL.constants.API;
import com.feelfreetocode.findbestinstituteSQL.models.Course;
import com.feelfreetocode.findbestinstituteSQL.models.Distance;
import com.feelfreetocode.findbestinstituteSQL.models.Rating;
import com.feelfreetocode.findbestinstituteSQL.models.Student;
import com.feelfreetocode.findbestinstituteSQL.models.Util;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentHomeScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Course> veiwData = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private ProgressDialog pd;
    private Student student;
    private ArrayList<Float> ratings;
    EditText searchText;
    ProgressBar progressBar ;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_screen);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initAll();
        student = (Student)getIntent().getSerializableExtra("student");
        setSearchListener();
        collectCourses();
    }
    private void collectCourses(){
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_COURSES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Course[] courses = (Course[])new Gson().fromJson(response, Course[].class);
                veiwData.clear();
                ratings = new ArrayList<>();
                for(Course c: courses){
                    ratings.add(0.0f);

                    veiwData.add(c);
                }
                Collections.sort(veiwData, new Comparator<Course>() {
                    @Override
                    public int compare(Course o1, Course o2) {
                        Double d1 = (new Distance().distance(Double.valueOf(o1.getInstitute().getLat()) ,
                                Double.valueOf(o1.getInstitute().getLang()) ,
                                Double.valueOf(student.getLat()) ,
                                Double.valueOf( student.getLang() ),
                                "k"));

                        Double d2 = (new Distance().distance(Double.valueOf(o2.getInstitute().getLat()) ,
                                Double.valueOf(o2.getInstitute().getLang()) ,
                                Double.valueOf(student.getLat()) ,
                                Double.valueOf( student.getLang() ),
                                "k"));
                        return d1.compareTo(d2);
                    }
                });

                adapter.notifyDataSetChanged();
                getRatings();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(StudentHomeScreen.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

    private void searchCourses(String search){
        StringRequest request = new StringRequest(Request.Method.GET, API.SEARCH_COURSE+"?search="+search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Course[] courses = (Course[])new Gson().fromJson(response, Course[].class);
                veiwData.clear();
                ratings = new ArrayList<>();
                for(Course c: courses){
                    ratings.add(0.0f);
                    veiwData.add(c);
                }
                Collections.sort(veiwData, new Comparator<Course>() {
                    @Override
                    public int compare(Course o1, Course o2) {
                        Double d1 = (new Distance().distance(Double.valueOf(o1.getInstitute().getLat()) ,
                                Double.valueOf(o1.getInstitute().getLang()) ,
                                Double.valueOf(student.getLat()) ,
                                Double.valueOf( student.getLang() ),
                                "k"));

                        Double d2 = (new Distance().distance(Double.valueOf(o2.getInstitute().getLat()) ,
                                Double.valueOf(o2.getInstitute().getLang()) ,
                                Double.valueOf(student.getLat()) ,
                                Double.valueOf( student.getLang() ),
                                "k"));
                        return d1.compareTo(d2);
                    }
                });

                adapter.notifyDataSetChanged();
                getRatings();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(StudentHomeScreen.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);

    }


    private void getRatings(){

        for(int i = 0 ; i < veiwData.size() ; i++){
            Course course = veiwData.get(i);
            final int n = i;
            StringRequest getRating = new StringRequest(Request.Method.GET, API.GET_COURSE_RATE + "?courseId=" + course.getCourseId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        float f = Float.valueOf(response);
                        ratings.set(n , f);
                        adapter.notifyDataSetChanged();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            } );
            Volley.newRequestQueue(getApplicationContext()).add(getRating);

        }
    }



    private void initAll(){
        pd = new ProgressDialog(StudentHomeScreen.this);
        pd.setMessage("Loading Courses For You");
        pd.setTitle("Please Wait");
        pd.setIndeterminate(true);
        pd.setCancelable(false);

        recyclerView = findViewById(R.id.courseList);

        Course c = new Course();
        adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar4);
        searchText  = findViewById(R.id.searchText);
        searchText.clearFocus();
    }

    private void setSearchListener(){

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchText.getText().toString().trim().isEmpty()){
                    collectCourses();
                }else{
                    searchCourses(searchText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }





    public class RecyclerViewAdapter extends RecyclerView.Adapter {


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
            View v = (View) LayoutInflater.from(getApplicationContext()).inflate(R.layout.student_home_screen_course_layout, viewGroup, false);

            return new RecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            final RecyclerViewHolder holder = (RecyclerViewHolder) viewHolder;
            final Course course = veiwData.get(i);
            holder.course.setText(course.getCourseName());
            holder.distance.setText((new Distance().distance(Double.valueOf(course.getInstitute().getLat()) ,
                    Double.valueOf(course.getInstitute().getLang()) ,
                    Double.valueOf(student.getLat()) ,
                    Double.valueOf( student.getLang() ),
                    "k")+"").substring(0 , 5)+" Km");

            holder.fee.setText("Rs. "+course.getCourseFee()+"");
            holder.insName.setText(course.getInstitute().getInstituteName());
            holder.letter.setText((course.getInstitute().getInstituteName().toUpperCase()).charAt(0)+"");

            holder.ratingBar.setRating(ratings.get(i));
            if (i % 5 == 0)
                holder.letter.setBackgroundColor(getResources().getColor(R.color.later1));
            if (i % 5 == 2)
                holder.letter.setBackgroundColor(getResources().getColor(R.color.later2));
            if (i % 5 == 1)
                holder.letter.setBackgroundColor(getResources().getColor(R.color.later3));
            if (i % 5 == 3)
                holder.letter.setBackgroundColor(getResources().getColor(R.color.later4));
            if (i % 5 == 4)
                holder.letter.setBackgroundColor(getResources().getColor(R.color.later5));


            holder.progressBar.setVisibility(View.INVISIBLE);


            // login code

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri.Builder builder = Uri.parse(API.SAVE_ENQUIRY).buildUpon();
                    builder.appendQueryParameter("student" , student.getStudentId()+"");
                    builder.appendQueryParameter("course" , course.getCourseId()+"");
                    StringRequest saveEnquiry = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("true")){
                                Toast.makeText(StudentHomeScreen.this, "Saved Enquiry", Toast.LENGTH_SHORT).show();
                            }else
                                {
                                    Toast.makeText(StudentHomeScreen.this, "Already in enquiry", Toast.LENGTH_SHORT).show();
                                }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(StudentHomeScreen.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Volley.newRequestQueue(getApplicationContext()).add(saveEnquiry);

                    Intent intent = new Intent(getApplicationContext(), InstituteProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("student", student);
                    intent.putExtra("course", veiwData.get(i));
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return veiwData.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {

            TextView insName, course, fee, distance, letter;
            ProgressBar progressBar;
            View itemView;
            RatingBar ratingBar;


            public RecyclerViewHolder(@NonNull View itemView ) {
                super(itemView);
                this.itemView = itemView;
                insName = itemView.findViewById(R.id.instituteName);
                course = itemView.findViewById(R.id.course);
                fee = itemView.findViewById(R.id.courseFee);
                fee = itemView.findViewById(R.id.courseFee);
                distance = itemView.findViewById(R.id.distance);
                ratingBar = itemView.findViewById(R.id.rating);
                letter = itemView.findViewById(R.id.letter);
                progressBar = itemView.findViewById(R.id.ratingloading);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.studentlogout){
            getApplicationContext().deleteFile("studentUser");
            getApplicationContext().deleteFile("login");
            startActivity(new Intent(this , LoginByScrenn.class));
        }else if(item.getItemId() == R.id.mycourses){
            Intent i = new Intent(getApplicationContext() , MyCourses.class);
            i.putExtra("student" , student);
            startActivity(i);
        }
        else if(item.getItemId() == R.id.myprofile){
            Intent i = new Intent(getApplicationContext() , StudentProfile.class);
            i.putExtra("student" , student);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            recyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.studenthomemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
