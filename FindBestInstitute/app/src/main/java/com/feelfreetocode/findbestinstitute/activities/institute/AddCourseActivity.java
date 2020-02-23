package com.feelfreetocode.findbestinstitute.activities.institute;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.models.Course;
import com.feelfreetocode.findbestinstitute.models.Institute;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class AddCourseActivity extends AppCompatActivity {
    private String  INSTITUTE_ID;
    EditText courseName  , courseFee;
    Button save;
    ListView courseList ;
    ProgressBar progressBar;
    Handler handler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        INSTITUTE_ID  = getIntent().getStringExtra("institute");
        Toast.makeText(getApplicationContext() , INSTITUTE_ID , Toast.LENGTH_LONG ).show();
        initAll();
        setClickListener();
        getDataFromLocal();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startProgress(){
        progressBar.setVisibility(View.VISIBLE);
        save.setEnabled(false);
    }
    private void stopProgress(){
        progressBar.setVisibility(View.INVISIBLE);
        save.setEnabled(true);
    }

    private void getDataFromLocal(){
        try {
            FileInputStream fis = getApplicationContext().openFileInput(INSTITUTE_ID);
            ObjectInputStream ois = new ObjectInputStream(fis);
             List<Course> courses = (List<Course>)ois.readObject();
             if(courses!=null && !courses.isEmpty()){
                 courseList.setAdapter(new CourseListAdapter(courses));
             }else{
                 startProgress();
                 collectCourse();
             }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("infooread" , e.toString());
            startProgress();
            collectCourse();
        }
        collectCourse();
    }

    @Override
    protected void onStart() {
        super.onStart();

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

                if(INSTITUTE_ID==null){
                    return;
                }
                AsyncTask<Integer , Integer , Boolean> saveTask = new AsyncTask<Integer, Integer, Boolean>() {
                    Course course = null;
                    @Override
                    protected Boolean doInBackground(Integer... integers) {
                        course = new Course(cname , Integer.parseInt(cfee));
                        return course.save(INSTITUTE_ID);
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if(aBoolean){
                            ((CourseListAdapter)(courseList.getAdapter())).add(course);
                        }else{
                            Toast.makeText(getApplicationContext() , "Can't Added ! Check Internet Connection" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onCancelled() {
                        super.onCancelled();
                        Toast.makeText(getApplicationContext() , "Can't Added ! Check Internet Connection" , Toast.LENGTH_SHORT).show();
                    }
                };

                saveTask.execute();
                courseName.setText("");
                courseFee.setText("");


            }
        });
    }
    private void collectCourse(){

        AsyncTask<String  , Integer , List<Course>> collect = new AsyncTask<String, Integer, List<Course>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<Course> doInBackground(String... strings) {

                List<Course> courses = new Course().getCourseOfIntitute(strings[0]);
                courses.add(0 , new Course("Course Name" , -1));
                return courses;
            }

            @Override
            protected void onPostExecute(List<Course> courses) {
                super.onPostExecute(courses);
                courseList.setAdapter(new CourseListAdapter(courses));
                stopProgress();
                try {
                    FileOutputStream os = getApplicationContext().openFileOutput(INSTITUTE_ID , MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(courses);
                    Log.e("infooException: ", "Saved");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("infooException: ", e.getLocalizedMessage());
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                stopProgress();
            }
        };

//        try {
            //List<Course> courses = collect.execute(INSTITUTE_ID).get();
            collect.execute(INSTITUTE_ID);
//            courseList.setAdapter(new CourseListAdapter(courses));

    }

    private void initAll(){
        save = findViewById(R.id.save);
        courseName = findViewById(R.id.courseName);
        courseFee = findViewById(R.id.courseFee);
        courseList = findViewById(R.id.courseList);
        progressBar = findViewById(R.id.progressBar2);

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
