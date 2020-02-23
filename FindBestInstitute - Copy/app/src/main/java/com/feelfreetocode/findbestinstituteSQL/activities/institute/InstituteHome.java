package com.feelfreetocode.findbestinstituteSQL.activities.institute;

import android.content.Intent;
import android.net.Uri;
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
import com.amazonaws.javax.xml.stream.b;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.activities.LoginByScrenn;
import com.feelfreetocode.findbestinstituteSQL.adapters.InstituteHomeOptionsAdapter;
import com.feelfreetocode.findbestinstituteSQL.constants.API;
import com.feelfreetocode.findbestinstituteSQL.models.Enquiry;
import com.feelfreetocode.findbestinstituteSQL.models.Institute;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class InstituteHome extends AppCompatActivity {

    GridView gridView;
    InstituteHomeOptionsAdapter optionsAdapter ;
    private Institute institute;
    String[] options = new String[]{ "All Students"  , "Add Course" , "About Your Institute" , "Show Enquery Students"};
    int[] images = new int[]{R.drawable.students_group, R.drawable.courses , R.drawable.about , R.drawable.enquiry};
    ArrayList<Enquiry> enquiries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_home);

        institute = (Institute) getIntent().getSerializableExtra("institute");

//        Toast.makeText(getApplicationContext(), institute.toString(), Toast.LENGTH_LONG).show();

        initAll();
        setClickListener();
        collectEnquiries();
    }

    private void collectEnquiries(){
        Uri.Builder builder = Uri.parse(API.GET_ENQUIRY).buildUpon();
        builder.appendQueryParameter("instituteId" , institute.getInstituteId()+"");
        StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                Toast.makeText(InstituteHome.this, "Admitted", Toast.LENGTH_SHORT).show();
                Enquiry[] en = (Enquiry[])new Gson().fromJson(response , Enquiry[].class);
                enquiries.clear();
                for(Enquiry e : en){
                    enquiries.add(e);
                }
                optionsAdapter.getNotifications()[3] = enquiries.size()+"";
                optionsAdapter.getShowProgress()[3] = 4;
                optionsAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(InstituteHome.this, "Cheack Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private void initAll(){
        gridView = findViewById(R.id.grid);
        optionsAdapter = new InstituteHomeOptionsAdapter(getApplicationContext() , options , images);
        gridView.setAdapter(optionsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(InstituteHome.this , LoginByScrenn.class));
        finish();

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
                    it.putExtra("institute" , institute);
                    startActivity(it);
                }
                if(position == 3){
                    Intent it = new Intent(getApplicationContext() , EnquiryStudents.class);
                    it.putExtra("enquiries" , enquiries);
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
