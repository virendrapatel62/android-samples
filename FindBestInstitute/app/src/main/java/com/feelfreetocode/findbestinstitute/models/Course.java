package com.feelfreetocode.findbestinstitute.models;

import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.feelfreetocode.findbestinstitute.constants.API;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Course implements Serializable {

    private static AmazonSimpleDBClient simpleDBClient;

    private String courseId;
    private String courseName;
    private Institute institute;
    private Integer courseFee;
    Rating rating ;

    public Course(Boolean d){

    }

    public Course(String courseName, Integer courseFee) {
        this();
        this.courseName = courseName;
        this.courseFee = courseFee;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Course(String courseId, String courseName, Integer courseFee) {
        this();
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseFee = courseFee;
    }

    public Course(boolean s){

    }

    public Course() {
        rating = new Rating();
        rating.setRating(0.0f);
        simpleDBClient = new AmazonSimpleDBClient(new BasicAWSCredentials(API.ACCESS_KEY_ID, API.SECRET_KEY));
        CreateDomainRequest cr = new CreateDomainRequest(API.COURSE_DOMAIN);
        simpleDBClient.createDomain(cr);
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getCourseFee() {
        return courseFee;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseFee(Integer courseFee) {
        this.courseFee = courseFee;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseFee=" + courseFee +
                '}';
    }

    public boolean save(String InstituteID){
        this.institute = new Institute();
        institute.setInstituteId(InstituteID);
        boolean flag = false;
        try{
            ReplaceableAttribute cName = new ReplaceableAttribute(API.CourseColumns.COURSE_NAME , getCourseName() , true);
            ReplaceableAttribute cFee = new ReplaceableAttribute(API.CourseColumns.COURSE_FEE , getCourseFee().toString() , true);
            ReplaceableAttribute instituteId = new ReplaceableAttribute(API.CourseColumns.INSTITUTE_ID , InstituteID , true);

            List<ReplaceableAttribute> attributeList = new ArrayList<>();
            attributeList.add(cName);
            attributeList.add(cFee);
            attributeList.add(instituteId);
            PutAttributesRequest putAttributesRequest = new PutAttributesRequest(API.COURSE_DOMAIN , generateCode() , attributeList);
            simpleDBClient.putAttributes(putAttributesRequest);
            this.courseId = generateCode();
            flag = true;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  flag;

    }

    private String generateCode(){
        return new StrongPasswordEncryptor().encryptPassword(courseName+courseFee);
    }


    public List<Course> getCourseOfIntitute(String instituteId){
        List<Course> courses = new ArrayList<>();
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.COURSE_DOMAIN + " where "+API.CourseColumns.INSTITUTE_ID+" = '" + instituteId + "'");

            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                courses = getCoursesFromSelectResult(selectResult);
            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return courses ;
    }
    public List<Course> getCourseByCourseName(String name){
        List<Course> courses = new ArrayList<>();
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.COURSE_DOMAIN + " where "+API.CourseColumns.COURSE_NAME+" like '%" + name + "%'");

            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                courses = getCoursesFromSelectResult(selectResult);
            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return courses ;
    }

    public List<Course> getCourses(){
        List<Course> courses = new ArrayList<>();
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.COURSE_DOMAIN +" limit 6" );



            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                courses = getCoursesFromSelectResult(selectResult);
            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return courses ;
    }

    public Course getCourse(String id){
        List<Course> courses = new ArrayList<>();
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.COURSE_DOMAIN +" where itemName()='"+id+"'" );



            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                courses = getCoursesFromSelectResult(selectResult);
            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return courses.get(0) ;
    }

    private static ArrayList<Course> getCoursesFromSelectResult(SelectResult selectResult){
        ArrayList<Course> courses= new ArrayList<Course>();

        List<Item> rows = selectResult.getItems();
        //Log.e("info list:" , rows.toString());
        //Log.e("info SelectResult:" , selectResult.toString());
        Iterator<Item> rowIterator = rows.iterator();
        while(rowIterator.hasNext()){
            Item row = rowIterator.next();
            Course course = new Course();
            List<Attribute> columns = row.getAttributes();
            course.setCourseId(row.getName());
            Iterator<Attribute> columnIterator = columns.iterator();
            while(columnIterator.hasNext()){
                Attribute attribute = columnIterator.next();
                String name = attribute.getName();
                String value = attribute.getValue();
                Log.e("info Column name Value:" , name + "  -- > "+ value);
                //creating object
                switch (name){
                    case API.CourseColumns.COURSE_NAME:
                        course.setCourseName(value);
                        break;
                    case API.CourseColumns.COURSE_FEE:
                        course.setCourseFee(Integer.parseInt(value));
                        break;
                    case API.CourseColumns.INSTITUTE_ID:
                        Institute institute = new Institute();
                        institute.setInstituteId(value);
                        institute = institute.getInstituteById(value);
                        course.setInstitute(institute);
                        break;
                }
            }
            courses.add(course);
        }
        return courses;
    }
}
