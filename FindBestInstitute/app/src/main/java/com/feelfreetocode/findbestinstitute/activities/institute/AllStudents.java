package com.feelfreetocode.findbestinstitute.activities.institute;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.activities.student.StudentProfile;
import com.feelfreetocode.findbestinstitute.models.Admission;
import com.feelfreetocode.findbestinstitute.models.Course;
import com.feelfreetocode.findbestinstitute.models.Institute;
import com.feelfreetocode.findbestinstitute.models.Student;

import java.util.ArrayList;
import java.util.List;

public class AllStudents extends AppCompatActivity {

    ListView allstudentsList;
    Institute institute;
    StudentAdapter adapter;
    public static Student student;
    public static Course course;

    ArrayList<Student> students = new ArrayList<>();
    ArrayList<String> keys = new ArrayList();
    ArrayList<String> values = new ArrayList();
    ArrayList<String> courses = new ArrayList();
    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        allstudentsList = findViewById(R.id.allstudents);

        institute = (Institute)getIntent().getSerializableExtra("institute");
        adapter = new StudentAdapter();
        allstudentsList.setAdapter(adapter);
        new GetStudentIdTask().execute();

        allstudentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AllStudents.this , StudentProfile.class);
                i.putExtra("student" , (Student)adapter.getItem(position));
                startActivity(i);
            }
        });


    }

    class GetStudentIdTask extends AsyncTask<String , Integer , List<Admission>>{

        @Override
        protected List<Admission> doInBackground(String... strings) {
            student = new Student(false);
            course = new Course(false);
            Log.e("allstudents" , "collecting");
            List<Admission> ids = new Admission().getStudentIdsOfInstitute(institute.getInstituteId());
            size = ids.size();
            return ids;
        }

        @Override
        protected void onPostExecute(List<Admission> strings) {
            for(int i = 0 ; i <strings.size() ; i++){
                Log.e("allstudents" ,strings.get(i).getStudent()+"" );
                new GetStudent().execute(strings.get(i).getStudent().getStudentId() , strings.get(i).getCourse().getCourseId() );
            }
            adapter.notifyDataSetChanged();
            super.onPostExecute(strings);
        }
    }
    class GetStudent extends AsyncTask<String , Integer , Object[]>{


        @Override
        protected Object[] doInBackground(String... strings) {
            Log.e("allstudents" , "collecting");
            Student s = student.getStudentByStudentId(strings[0]);
            students.add(s);
            Course c = course.getCourse(strings[1]);
            Object[] o = new Object[]{s , c};
            return  o;
        }

        @Override
        protected void onPostExecute(Object[] o) {
            Student s = (Student) o[0];
            Course c = (Course) o[1];
            keys.add(s.getName());
            values.add("Contact : " + s.getContact()+
                    "\nCourse : "+c.getCourseName());
            adapter.notifyDataSetChanged();
            super.onPostExecute(o);
        }
    }



    class StudentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return keys.size();
        }

        @Override
        public Object getItem(int position) {
            return students.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.student_profile_item_layout , null);
            TextView key = v.findViewById(R.id.key);
            TextView value = v.findViewById(R.id.value);

            key.setText(keys.get(position));
            value.setText(values.get(position));
            return v;
        }
    }
}
