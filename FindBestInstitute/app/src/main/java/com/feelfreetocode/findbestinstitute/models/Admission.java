package com.feelfreetocode.findbestinstitute.models;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.feelfreetocode.findbestinstitute.constants.API;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Admission implements Serializable {
    private Student student;
    private Course course;
    private String id;
    private float rateByMe = 0.0f;
    public static Context context;
    private static  AmazonSimpleDBClient simpleDBClient;

    public Admission(Student student , Course course, String id) {
        this();
        this.student = student;
        this.course = course;
        this.id = id;
    }

    public float getRateByMe() {
        return rateByMe;
    }

    public void setRateByMe(float rateByMe) {
        this.rateByMe = rateByMe;
    }

    public Admission(Student student, Course course) {
        this();
        this.student = student;
        this.course = course;
    }

    public Admission() {
        simpleDBClient = new AmazonSimpleDBClient(new BasicAWSCredentials(API.ACCESS_KEY_ID, API.SECRET_KEY));
        CreateDomainRequest cr = new CreateDomainRequest(API.ADMISSSION_DOMAIN);
        simpleDBClient.createDomain(cr);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean save(){
        boolean flag = false;
        try{
            ReplaceableAttribute studentId= new ReplaceableAttribute(API.AdmissionColumns.STUDENT_ID ,getStudent().getStudentId()  , true);
            ReplaceableAttribute courseID = new ReplaceableAttribute(API.AdmissionColumns.COURSE_ID, getCourse().getCourseId(), true);
            ReplaceableAttribute instituteId = new ReplaceableAttribute(API.AdmissionColumns.INSTITUTE_ID, getCourse().getInstitute().getInstituteId(), true);


            List<ReplaceableAttribute> attributeList = new ArrayList<>();
            attributeList.add(studentId);
            attributeList.add(courseID);
            attributeList.add(instituteId);
            PutAttributesRequest putAttributesRequest = new PutAttributesRequest(API.ADMISSSION_DOMAIN, generateCode() , attributeList);
            simpleDBClient.putAttributes(putAttributesRequest);
            this.id = generateCode();
            Log.e("infooE" , id);
            flag = true;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  flag;

    }
    public List<Admission> getCoursesOfStudent(String studentid , Context context){
        this.context= context;
        List<Admission> courses = new ArrayList<>();
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.ADMISSSION_DOMAIN +" where "+API.AdmissionColumns.STUDENT_ID +"='"+studentid+"'" );



            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("Admission" , list.toString());
            Log.e("Admission" , list.toString());
            Log.e("Admission" , studentid);

            if(list!=null && !list.isEmpty()){
                courses = getAdmissionFromSelectResult(selectResult);
            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return courses;
    }

    public List<Admission> getStudentIdsOfInstitute(String instituteid ){
        List<Admission> admissions = new ArrayList<>();
        try {
            SelectRequest selectRequest = new SelectRequest("select "+API.AdmissionColumns.STUDENT_ID +","+API.AdmissionColumns.COURSE_ID+" from " +
                    API.ADMISSSION_DOMAIN +" where "+API.AdmissionColumns.INSTITUTE_ID+"='"+instituteid+"'" );



            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            for(int i = 0 ; i<list.size() ; i++){
                Admission admission = new Admission();
                List<Attribute> ats = list.get(i).getAttributes();
                for(int k = 0 ; k < ats.size() ; k++){
                    if(ats.get(k).getName().equals(API.AdmissionColumns.STUDENT_ID)){
                        Student s = new Student(true);
                        s.setStudentId(ats.get(k).getValue());
                        admission.setStudent(s);
                    }
                    if(ats.get(k).getName().equals(API.AdmissionColumns.COURSE_ID)){
                        Course c = new Course(true);
                        c.setCourseId(ats.get(k).getValue());
                        admission.setCourse(c);
                    }

                }
                admissions.add(admission);
            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return admissions;
    }
    private static ArrayList<Admission> getAdmissionFromSelectResult(SelectResult selectResult){
        ArrayList<Admission> courses= new ArrayList<Admission>();

        List<Item> rows = selectResult.getItems();
        //Log.e("info list:" , rows.toString());
        //Log.e("info SelectResult:" , selectResult.toString());
        Iterator<Item> rowIterator = rows.iterator();
        while(rowIterator.hasNext()){
            Item row = rowIterator.next();

            Admission course = new Admission();

            List<Attribute> columns = row.getAttributes();

            Iterator<Attribute> columnIterator = columns.iterator();
            while(columnIterator.hasNext()){
                Attribute attribute = columnIterator.next();
                String name = attribute.getName();
                String value = attribute.getValue();
                Log.e("info Column name Value:" , name + "  -- > "+ value);


                //creating object

                switch (name){
                    case API.AdmissionColumns.COURSE_ID:
                        Log.e("Admission" , "Switch Case ");
                        course.setCourse(getCourse(value));
                        break;
                }
            }

            courses.add(course);
        }

        return courses;
    }

    private static Course getCourse(String id){
        Log.e("Admission" , "GetCourse()");
        Course course = new Course();
        course.setCourseId(id);
        try {
            FileInputStream fis = context.openFileInput("courses");
            ObjectInputStream oos = new ObjectInputStream(fis);
            List<Course> courses = (List<Course>)oos.readObject();
            Comparator<Course> comparator = new Comparator<Course>() {
                @Override
                public int compare(Course o1, Course o2) {
                    return o1.getCourseId().compareTo(o2.getCourseId());
                }
            };

            Collections.sort(courses , comparator);
            int i = Collections.binarySearch(courses , course , comparator);
            if(i !=-1){
                Log.e("Admission" , "Found");
                Log.e("found" , i+"");
                return courses.get(i);
            }
            Log.e("Admission" , "Not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

    private String generateCode(){
        return student.getEmail()+course.getCourseId();
    }
}
