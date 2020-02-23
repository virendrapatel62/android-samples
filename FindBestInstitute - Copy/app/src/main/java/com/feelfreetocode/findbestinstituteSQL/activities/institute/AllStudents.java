package com.feelfreetocode.findbestinstituteSQL.activities.institute;

import android.content.Intent;
import android.net.Uri;
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
import com.feelfreetocode.findbestinstituteSQL.models.Course;
import com.feelfreetocode.findbestinstituteSQL.models.Institute;
import com.feelfreetocode.findbestinstituteSQL.models.Student;
import com.google.gson.Gson;

import java.io.ObjectInputStream;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        allstudentsList = findViewById(R.id.allstudents);

        institute = (Institute)getIntent().getSerializableExtra("institute");
        adapter = new StudentAdapter();
        allstudentsList.setAdapter(adapter);

        allstudentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AllStudents.this , StudentProfile.class);
                i.putExtra("student" , (Student)adapter.getItem(position));
                startActivity(i);
            }
        });
        collectStudents();
    }

    private void collectStudents(){
        Uri.Builder builder = Uri.parse(API.GET_ADMITTED_STUDNET).buildUpon();
        builder.appendQueryParameter("instituteId" , institute.getInstituteId()+"");

        StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("---------" , response);
                keys.clear();
                values.clear();
                Object[][] ss = (Object[][]) new Gson().fromJson(response , Object[][].class);
                for(Object[] ob: ss){
                    String s = new Gson().toJson(ob[0]);
                    Admission admission = (Admission)new Gson().fromJson(s , Admission.class);
                    students.add(admission.getStudent());
                    keys.add(admission.getStudent().getName());
                    values.add("Contact :"+ admission.getStudent().getContact()+
                            " \n Email :" + admission.getStudent().getEmail()+
                            "\n Course : "+ admission.getCourse().getCourseName());
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllStudents.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);
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
