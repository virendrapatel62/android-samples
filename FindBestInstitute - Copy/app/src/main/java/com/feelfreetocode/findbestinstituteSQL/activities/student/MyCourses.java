package com.feelfreetocode.findbestinstituteSQL.activities.student;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.feelfreetocode.findbestinstituteSQL.activities.institute.AllStudents;
import com.feelfreetocode.findbestinstituteSQL.constants.API;
import com.feelfreetocode.findbestinstituteSQL.models.Admission;
import com.feelfreetocode.findbestinstituteSQL.models.Course;
import com.feelfreetocode.findbestinstituteSQL.models.Rating;
import com.feelfreetocode.findbestinstituteSQL.models.Student;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyCourses extends AppCompatActivity {

    RecyclerView myCourseList;
    List<Admission> admissions;
    CourseAdapter adapter;
    Student student;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);
        initAll();

        getCourses();
    }

    public  void initAll(){
        myCourseList = findViewById(R.id.mycourselist);
        admissions = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.INVISIBLE);
        adapter = new CourseAdapter();
        myCourseList.setAdapter(adapter);
        myCourseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        student = (Student) getIntent().getSerializableExtra("student");
//        myCourseList.setAdapter(adapter);
//        progressBar.setVisibility(View.INVISIBLE);

    }

    private void getCourses(){
        Uri.Builder builder = Uri.parse(API.GET_COURSES_OF_STUDNET).buildUpon();
        builder.appendQueryParameter("studentId" , student.getStudentId()+"");

        StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("---------" , response);

                Admission[] ss = (Admission[]) new Gson().fromJson(response , Admission[].class);
                for(Admission ob: ss){
                    admissions.add(ob);
                }
                Log.e("----------listtt" , admissions.toString());
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyCourses.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }



    class CourseAdapter extends  RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.mycourse_item_layout , viewGroup , false);
            return new CourseHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final  int i) {
            final CourseHolder h = (CourseHolder)viewHolder;
            final Admission admission = admissions.get(i);

            h.courseName.setText(admission.getCourse().getCourseName());
            h.courseFee.setText("Rs. "+admission.getCourse().getCourseFee()+"");
            h.icon.setText(admission.getCourse().getCourseName().toUpperCase().charAt(0)+"");
            h.courseRating.setRating(0.0f);
            h.instituteName.setText(admission.getCourse().getInstitute().getInstituteName());
            h.myRating.setVisibility(View.VISIBLE);
            h.gettingYoutRating.setVisibility(View.INVISIBLE);

            StringRequest getRating = new StringRequest(Request.Method.GET, API.GET_COURSE_RATE + "?courseId=" + admission.getCourse().getCourseId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        float f = Float.valueOf(response);
                        h.courseRating.setRating(f);
                    }catch (Exception ex){
                        ex.printStackTrace();
                        h.courseRating.setRating(0.0f);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            } );
            Volley.newRequestQueue(getApplicationContext()).add(getRating);


            // getting my rating

            StringRequest studentRating = new StringRequest(Request.Method.GET, API.GET_STUDENT_RATING+ "?courseId=" + admission.getCourse().getCourseId()+"&studentId="+student.getStudentId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        if(response.equalsIgnoreCase("null")){
                            Log.e("------------" , null);
                            h.myRating.setRating(0.0f);
                        }else{
                            Rating rating = (Rating)new Gson().fromJson(response , Rating.class);
                            float f = Float.valueOf(rating.getRating());
                            h.myRating.setRating(f);
                        }

                    }catch (Exception ex){
                        ex.printStackTrace();
                        h.myRating.setRating(0.0f);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            } );
            Volley.newRequestQueue(getApplicationContext()).add(studentRating);



            h.myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser){
                        Uri.Builder builder = Uri.parse(API.RATE).buildUpon();
                        builder.appendQueryParameter("studentId" , student.getStudentId()+"");
                        builder.appendQueryParameter("courseId" , admission.getCourse().getCourseId()+"");
                        builder.appendQueryParameter("rating" , rating+"");
                        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equalsIgnoreCase("\"true\"")){
                                    Toast.makeText(MyCourses.this, "Cant Rate at this time", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return admissions.size();
        }
        class CourseHolder extends RecyclerView.ViewHolder{

            TextView courseName , instituteName , courseFee , icon;
            RatingBar myRating , courseRating;
            ProgressBar gettingYoutRating;

            public CourseHolder(@NonNull View itemView) {
                super(itemView);
                courseFee = itemView.findViewById(R.id.courseFee);
                courseName = itemView.findViewById(R.id.courseName);
                instituteName = itemView.findViewById(R.id.instituteName);
                myRating = itemView.findViewById(R.id.yourRate);
                courseRating = itemView.findViewById(R.id.courseRate);
                gettingYoutRating = itemView.findViewById(R.id.progressRaiting);
                icon = itemView.findViewById(R.id.icon);
            }
        }
    }


}
