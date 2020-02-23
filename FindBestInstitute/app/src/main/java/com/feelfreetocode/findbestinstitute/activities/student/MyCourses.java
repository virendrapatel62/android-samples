package com.feelfreetocode.findbestinstitute.activities.student;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.models.Admission;
import com.feelfreetocode.findbestinstitute.models.Course;
import com.feelfreetocode.findbestinstitute.models.Rating;
import com.feelfreetocode.findbestinstitute.models.Student;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        student = (Student)getIntent().getSerializableExtra("student");
        readFromLocal();
    }

    private void readFromLocal(){
        String file = student.getEmail()+"_courses";
        try{
            ObjectInputStream oos = new ObjectInputStream(openFileInput(file));
            admissions = (List)oos.readObject();
            Log.e("admissions" , admissions+"");
            adapter.notifyDataSetChanged();
            if(admissions==null){
                progressBar.setVisibility(View.VISIBLE);
            }else if(admissions.isEmpty()){
                progressBar.setVisibility(View.VISIBLE);
            }
            getAdmissions();
        }catch (Exception ex){
            progressBar.setVisibility(View.VISIBLE);
            getAdmissions();
        }
    }

    public  void initAll(){
        myCourseList = findViewById(R.id.mycourselist);
        admissions = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar3);
//        admissions.add(null);
        adapter = new CourseAdapter();
        myCourseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myCourseList.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void getAdmissions(){
        AsyncTask<Integer , Integer , List<Admission>> asyncTask = new AsyncTask<Integer, Integer, List<Admission>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("infoo_"+getClass() , "going to execute");
            }

            @Override
            protected void onPostExecute(List<Admission> courses) {
                super.onPostExecute(courses);
                admissions = courses;

                try{
                    ArrayList<Admission> as = new ArrayList<>(courses);
                    FileOutputStream fos = openFileOutput(student.getEmail()+"_courses" , MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(as);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            protected List<Admission> doInBackground(Integer... integers) {
                return new Admission().getCoursesOfStudent(student.getStudentId() , getApplicationContext());
            }
        };

        asyncTask.execute();
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
            final CourseHolder ch = (CourseHolder)viewHolder;
            final Admission admission = admissions.get(i);

            ch.courseRating.setRating(0.0f);
            ch.myRating.setRating(0.0f);

            ch.myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser){
                        new SaveRating().execute(new Rating(student , admission.getCourse() , rating));
                        Toast.makeText(MyCourses.this, "Rating Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            new AsyncTask<Integer , Integer , Float>(){
                @Override
                protected void onPostExecute(Float aFloat) {
                    ch.myRating.setRating(aFloat);
                    ch.myRating.setVisibility(View.VISIBLE);
                    ch.gettingYoutRating.setVisibility(View.INVISIBLE);
                    super.onPostExecute(aFloat);
                }

                @Override
                protected Float doInBackground(Integer... integers) {
                    float f = new Rating().getRatingOfCourse(student.getStudentId() , admissions.get(i).getCourse().getCourseId());
                    return f;
                }
            }.execute();

            new AsyncTask<Integer , Integer , Float>(){
                @Override
                protected void onPostExecute(Float aFloat) {
                    ch.courseRating.setRating(aFloat);
                    super.onPostExecute(aFloat);
                }

                @Override
                protected Float doInBackground(Integer... integers) {
                    float f = new Rating().getAverageRatingsOfCourse(admissions.get(i).getCourse().getCourseId());
                    return f;
                }
            }.execute();

            ch.courseName.setText(admission.getCourse().getCourseName());
            ch.courseFee.setText("Rs."+admission.getCourse().getCourseFee());
            ch.instituteName.setText(admission.getCourse().getInstitute().getInstituteName());
            ch.icon.setText((admission.getCourse().getCourseName().charAt(0)+"").toUpperCase());

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


    class SaveRating extends  AsyncTask<Rating , Integer , Boolean> {

        @Override
        protected Boolean doInBackground(Rating... floats) {
            return floats[0].save();
        }
    }

}
