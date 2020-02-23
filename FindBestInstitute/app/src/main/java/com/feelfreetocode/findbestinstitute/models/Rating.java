package com.feelfreetocode.findbestinstitute.models;

import android.app.Service;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rating implements Serializable {
    private static AmazonSimpleDBClient simpleDBClient;
    Student student ;
    Course course;
    float rating;

    public Rating(Student student, Course course, float rating) {
        this();
        this.student = student;
        this.course = course;
        this.rating = rating;
    }
    public Rating() {
        simpleDBClient = new AmazonSimpleDBClient(new BasicAWSCredentials(API.ACCESS_KEY_ID, API.SECRET_KEY));
        CreateDomainRequest cr = new CreateDomainRequest(API.RATING_DOMAIN);
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean save(){
        Log.e("Rating" , "saving");
        boolean flag = false;
        try{
            ReplaceableAttribute student = new ReplaceableAttribute(API.RatingColumns.STUDENT_ID , getStudent().getStudentId() , true);
            ReplaceableAttribute rating = new ReplaceableAttribute(API.RatingColumns.RATING , getRating()+"" , true);
            ReplaceableAttribute course = new ReplaceableAttribute(API.RatingColumns.COURSE_ID, getCourse().getCourseId(), true);

            List<ReplaceableAttribute> attributeList = new ArrayList<>();
            attributeList.add(student);
            attributeList.add(rating);
            attributeList.add(course);
            PutAttributesRequest putAttributesRequest = new PutAttributesRequest(API.RATING_DOMAIN , getCourse().getCourseId()+getStudent().getStudentId() , attributeList);
            simpleDBClient.putAttributes(putAttributesRequest);
            flag = true;
        }catch(Exception ex){
            Log.e("Rating" , "Exception" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return  flag;

    }

    public float getRatingOfCourse(String studentID , String CoursId){
        Log.e("raiting: " , studentID);
        float rating = 0.0f;
        try {
            SelectRequest selectRequest = new SelectRequest("select "+API.RatingColumns.RATING+" from " +
                    API.RATING_DOMAIN + " where "+API.RatingColumns.COURSE_ID+" = '" + CoursId+ "' and "+API.RatingColumns.STUDENT_ID+" ='"+studentID+"'");

            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            if(list!=null){
                if(list.size()>0){
                    List<Attribute> ats = list.get(0).getAttributes();
                    String name = ats.get(0).getName();
                    String value= ats.get(0).getValue();

                    Log.e("raiting: " , name);
                    Log.e("raiting: " , value);
                    rating = Float.valueOf(value);
                }
            }
            Log.e("rating" , list.toString());

        }catch (Exception ec){
            ec.printStackTrace();
        }
        return rating ;
    }


    public float getAverageRatingsOfCourse(String CoursId){
        float rating = 0.0f;
        int num = 1;
        try {
            SelectRequest selectRequest = new SelectRequest("select "+API.RatingColumns.RATING+" from " +
                    API.RATING_DOMAIN + " where "+API.RatingColumns.COURSE_ID+" = '" + CoursId+ "'");

            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();

            if(list!=null){
                if(list.size()>0){
                    for(int n = 0 ; n < list.size() ; n++){
                        List<Attribute> ats = list.get(n).getAttributes();
                        num = list.size();
                        String name = ats.get(0).getName();
                        String value= ats.get(0).getValue();
                        Log.e("raiting: " , name);
                        Log.e("for : "+CoursId , value );
                        Log.e("raiting: " , value);
                        rating = rating + Float.valueOf(value);
                    }
                }
            }
            Log.e("forAVG : "+CoursId , rating+"");
            Log.e("forAVG : "+CoursId , rating/num +"");
//            Log.e("rating" , list.toString());
//            Log.e("rating avg " , (rating/list.size())+"");

        }catch (Exception ec){
            ec.printStackTrace();
        }
        return rating/num ;
    }


}
