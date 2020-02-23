package com.feelfreetocode.findbestinstituteSQL.activities.student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.models.Student;

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {

    ListView studentDetailList;
    ArrayList<String> keys = new ArrayList();
    ArrayList<String> values = new ArrayList();
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        studentDetailList  = findViewById(R.id.studentDetailsList);

        student = (Student)getIntent().getSerializableExtra("student");

        keys.add("Name"); values.add(student.getName());
        keys.add("Email"); values.add(student.getEmail());
        keys.add("Address"); values.add(student.getAddress());
        keys.add("Contact"); values.add(student.getContact());

        StudentAdapter adapter = new StudentAdapter();
        studentDetailList.setAdapter(adapter);

        TextView icon = findViewById(R.id.icon);
        icon.setText((student.getName().charAt(0)+"").toUpperCase());
        icon.setBackgroundColor(getResources().getColor(R.color.lightSeegreen));
        getWindow().setStatusBarColor(getResources().getColor(R.color.lightSeegreen));
    }

    class StudentAdapter extends BaseAdapter{

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

            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.student_profile_item_layout , null);
            TextView key = v.findViewById(R.id.key);
            TextView value = v.findViewById(R.id.value);

            key.setText(keys.get(position));
            value.setText(values.get(position));
            return v;
        }
    }
}
