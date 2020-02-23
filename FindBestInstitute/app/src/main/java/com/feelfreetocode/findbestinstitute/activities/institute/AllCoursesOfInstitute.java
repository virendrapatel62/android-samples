package com.feelfreetocode.findbestinstitute.activities.institute;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.models.Course;
import com.feelfreetocode.findbestinstitute.models.Institute;
import com.feelfreetocode.findbestinstitute.models.Rating;
import com.feelfreetocode.findbestinstitute.models.Student;

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
        getFromLocal();
        //collectAllCourses();
    }

    private void collectAllCourses(){
        AsyncTask<Integer , Integer , List<Course>> asyncTask = new AsyncTask<Integer, Integer, List<Course>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("infoo_"+getClass() , "going to execute");
            }

            @Override
            protected void onPostExecute(List<Course> courses) {
                super.onPostExecute(courses);
                courseArrayList = courses;
                Log.e("infoo_"+getClass() , "okk :"+courses);
                adapter.notifyDataSetChanged();
                if(pd.isShowing())
                    pd.dismiss();
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                if(pd.isShowing())
                    pd.dismiss();
            }

            @Override
            protected List<Course> doInBackground(Integer... integers) {
                List<Course> courses = new Course().getCourseOfIntitute(institute.getInstituteId());
                try {
                    FileOutputStream os = getApplicationContext().openFileOutput(institute.getInstituteId() , MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(courses);
                    Log.e("infooException: ", "Saved");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("infooException: ", e.getLocalizedMessage());
                }
                return courses;
            }
        };

        asyncTask.execute();
    }

    private void getFromLocal(){
        try {
            FileInputStream fis = getApplicationContext().openFileInput(institute.getInstituteId());
            ObjectInputStream ois = new ObjectInputStream(fis);
            courseArrayList = (List<Course>)ois.readObject();
            adapter.notifyDataSetChanged();
            Log.e("infooread" , courseArrayList.toString());
            collectAllCourses();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("infooread" , e.toString());
            pd.show();
            collectAllCourses();
        }
    }

    private void initAll(){
        courseArrayList = new ArrayList<>();
        courseGridView = findViewById(R.id.courseGrid);
        heading = findViewById(R.id.heading);
        fullName = findViewById(R.id.fullName);
        adapter = new CourseGridAdapter();


        courseArrayList.add(new Course("Java Script" , 4500));

        courseGridView.setAdapter(adapter);
        fullName.setText(institute.getInstituteName());
        heading.setText("");
        String[] s = institute.getInstituteName().trim().split(" ");
        for(int i = 0 ; i < s.length ; i++){
            heading.setText(heading.getText().toString()+s[i].charAt(0));
        }


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

            final ProgressBar progressBar = view.findViewById(R.id.courseratingprogress);
            final RatingBar ratingBar = view.findViewById(R.id.courserate);
            new AsyncTask<Integer , Integer , Float>(){
                @Override
                protected void onPostExecute(Float aFloat) {
                    progressBar.setVisibility(View.INVISIBLE);
                    ratingBar.setRating(aFloat);
                    super.onPostExecute(aFloat);
                }

                @Override
                protected Float doInBackground(Integer... integers) {
                    try {
                        float f = new Rating().getAverageRatingsOfCourse(courseArrayList.get(position).getCourseId());
                        return f;
                    }catch(Exception ec){
                        ec.printStackTrace();
                        return 0.0f;
                    }
                }
            }.execute();
            return view;
        }
    }
}
