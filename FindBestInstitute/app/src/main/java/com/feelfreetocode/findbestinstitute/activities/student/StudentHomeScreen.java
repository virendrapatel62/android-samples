package com.feelfreetocode.findbestinstitute.activities.student;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.activities.InstituteProfile;
import com.feelfreetocode.findbestinstitute.activities.LoginByScrenn;
import com.feelfreetocode.findbestinstitute.models.Course;
import com.feelfreetocode.findbestinstitute.models.Distance;
import com.feelfreetocode.findbestinstitute.models.Institute;
import com.feelfreetocode.findbestinstitute.models.Rating;
import com.feelfreetocode.findbestinstitute.models.Student;

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
    EditText searchText;
    ProgressBar progressBar ;
    ImageView search ;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_screen);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initAll();
        setSearchListener();
        getStudent();
        collectData();
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
        search = findViewById(R.id.search);
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
                    getStudent();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String text = searchText.getText().toString();
                if(text.trim().isEmpty()){
                    return ;
                }
                AsyncTask<String , Integer , List<Course>> searchTask = new AsyncTask<String, Integer, List<Course>>() {
                    @Override
                    protected List<Course> doInBackground(String... strings) {

                        return new Course().getCourseByCourseName(text);
                    }

                    @Override
                    protected void onPreExecute() {
                        search.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        super.onPreExecute();
                    }

                    @Override
                    protected void onPostExecute(List<Course> courses) {
                        progressBar.setVisibility(View.INVISIBLE);
                        veiwData.clear();
                        veiwData.addAll(courses);
                        search.setEnabled(true);
                        adapter.notifyDataSetChanged();
                        super.onPostExecute(courses);
                        recyclerView.scheduleLayoutAnimation();
                    }

                    @Override
                    protected void onCancelled(List<Course> courses) {
                        progressBar.setVisibility(View.INVISIBLE);
                        super.onCancelled(courses);
                        search.setEnabled(true);
                    }
                };


                searchTask.execute();
            }
        });
    }

    private void getStudent() {
        try {
            student = (Student)getIntent().getSerializableExtra("student");
            pd.show();
            FileInputStream fis = getApplicationContext().openFileInput("courses");
            ObjectInputStream ois = new ObjectInputStream(fis);
            veiwData = (ArrayList<Course>)ois.readObject();
            Log.e("infooread" , veiwData.toString());
            pd.dismiss();
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("infooread" , e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("infooread" , e.toString());
            e.printStackTrace();
        }
    }


    private void collectData() {
        AsyncTask<Integer, Integer, List<Course>> asyncTask = new AsyncTask<Integer, Integer, List<Course>>() {
            @Override
            protected void onPreExecute() {
                if(veiwData.isEmpty()){
                    pd.show();
                }
                super.onPreExecute();
            }

            @Override
            protected List<Course> doInBackground(Integer... objects) {
                List<Course> courseList = new Course().getCourses();

                Collections.sort(courseList, new Comparator<Course>() {
                    @Override
                    public int compare(Course o1, Course o2) {
                        Double dis1 = new Distance().
                                distance(new Double(o1.getInstitute().getLat()), new Double(o1.getInstitute().getLang())
                                        , new Double(student.getLat()), new Double(student.getLang()), "k");
                        Double dis2 = new Distance().
                                distance(new Double(o2.getInstitute().getLat()), new Double(o2.getInstitute().getLang())
                                        , new Double(student.getLat()), new Double(student.getLang()), "k");
                        return dis1.compareTo(dis2);
                    }
                });
                return courseList;
            }

            @Override
            protected void onPostExecute(List<Course> courses) {
                super.onPostExecute(courses);
                Log.e("infoo  Data: ", courses.toString());
                veiwData = courses;
                adapter.notifyDataSetChanged();
                pd.dismiss();

                try {
                    FileOutputStream os = getApplicationContext().openFileOutput("courses" , MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(courses);
                    Log.e("infooException: ", "Saved");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("infooException: ", e.getLocalizedMessage());
                }
            }
        };
        asyncTask.execute(new Integer(12));
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
            holder.insName.setText("   "+veiwData.get(i).getInstitute().getInstituteName());
            holder.course.setText("   "+veiwData.get(i).getCourseName());
            holder.letter.setText(veiwData.get(i).getInstitute().getInstituteName().charAt(0) + "");


            Double dis2 = new Distance().
                    distance(new Double(veiwData.get(i).getInstitute().getLat()), new Double(veiwData.get(i).getInstitute().getLang())
                            , new Double(student.getLat()), new Double(student.getLang()), "k");

            holder.fee.setText("   "+"Rs. " + veiwData.get(i).getCourseFee());
            holder.distance.setText("   "+dis2.toString().substring(0, 4) + " Km");

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


            // login code

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(StudentHomeScreen.this, "Item : " + veiwData.get(i), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext() , InstituteProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("student" ,student );
                    intent.putExtra("course" ,veiwData.get(i));
                    intent.putExtra("rating" ,holder.ratingBar.getRating());
                    startActivity(intent);
                }
            });

            new AsyncTask<Integer , Integer , Float>(){
                @Override
                protected void onPostExecute(Float aFloat) {
                    holder.progressBar.setVisibility(View.INVISIBLE);
                    holder.ratingBar.setRating(aFloat);
                    super.onPostExecute(aFloat);
                }

                @Override
                protected Float doInBackground(Integer... integers) {
                    try {
                        float f = new Rating().getAverageRatingsOfCourse(veiwData.get(i).getCourseId());
                        return f;
                    }catch(Exception ec){
                        ec.printStackTrace();
                        return 0.0f;
                    }
                }
            }.execute();
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
//                Toast.makeText(StudentHomeScreen.this, "costrcuter", Toast.LENGTH_SHORT).show();
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
