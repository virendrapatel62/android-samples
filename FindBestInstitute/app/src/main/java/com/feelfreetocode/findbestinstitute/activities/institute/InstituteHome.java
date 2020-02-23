package com.feelfreetocode.findbestinstitute.activities.institute;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.abangfadli.shotwatch.ScreenshotData;
import com.abangfadli.shotwatch.ShotWatch;
import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.activities.LoginByScrenn;
import com.feelfreetocode.findbestinstitute.adapters.InstituteHomeOptionsAdapter;
import com.feelfreetocode.findbestinstitute.models.Enquiry;
import com.feelfreetocode.findbestinstitute.models.Institute;

import java.util.ArrayList;
import java.util.List;


public class InstituteHome extends AppCompatActivity {

    GridView gridView;
    InstituteHomeOptionsAdapter optionsAdapter ;
    private Institute institute;
    String[] options = new String[]{ "All Students"  , "Add Course" , "About Your Institute" , "Show Enquery Students"};
    ShotWatch shotWatch;
    int[] images = new int[]{R.drawable.students_group, R.drawable.courses , R.drawable.about , R.drawable.enquiry};
    ArrayList<Enquiry> enquiries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_home);

        institute  = (Institute) getIntent().getSerializableExtra("institute");

        Toast.makeText(getApplicationContext() , institute.toString(), Toast.LENGTH_LONG ).show();

        initAll();
        setClickListener();

         shotWatch = new ShotWatch(getContentResolver(), new ShotWatch.Listener() {
            @Override
            public void onScreenShotTaken(ScreenshotData screenshotData) {
                Toast.makeText(InstituteHome.this, "Screen Shot taken", Toast.LENGTH_SHORT).show();
            }
        });
        shotWatch.register();
        collectEnquries();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shotWatch.register();
    }

    private void collectEnquries(){
        AsyncTask<Integer , Integer , List<Enquiry>> enTask = new AsyncTask<Integer, Integer, List<Enquiry>>() {
            @Override
            protected List<Enquiry> doInBackground(Integer... integers) {
                Log.e("infoo" , "starting");
                List<Enquiry> enquiries = new Enquiry().getEnquiries(institute.getInstituteId());
                return enquiries;
            }

            @Override
            protected void onPostExecute(List<Enquiry> enquiries) {
                super.onPostExecute(enquiries);

                optionsAdapter.getNotifications()[3] = enquiries.size()+"";
                optionsAdapter.getShowProgress()[3] = 4;
                optionsAdapter.notifyDataSetChanged();
                InstituteHome.this.enquiries.addAll(enquiries);
                Log.e("infoEnquiries" , enquiries.toString());
            }
        };
        enTask.execute();
    }



    private void initAll(){
        gridView = findViewById(R.id.grid);
        optionsAdapter = new InstituteHomeOptionsAdapter(getApplicationContext() , options , images);
        gridView.setAdapter(optionsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getApplicationContext().deleteFile("instituteUser");
        getApplicationContext().deleteFile("login");
        startActivity(new Intent(this , LoginByScrenn.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logout , menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void setClickListener(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(InstituteHome.this, ""+gridView.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();

                if(position == 1){
                    Intent it = new Intent(getApplicationContext() , AddCourseActivity.class);
                    it.putExtra("institute" , institute.getInstituteId());
                    startActivity(it);
                }
                if(position == 3){
                    Intent it = new Intent(getApplicationContext() , EnquiryStudents.class);
                    it.putExtra("students" , enquiries);
                    startActivity(it);
                }
                if(position == 0){
                    Intent it = new Intent(getApplicationContext() , AllStudents.class);
                    it.putExtra("institute" , institute);
                    startActivity(it);
                }
            }
        });
    }


}
