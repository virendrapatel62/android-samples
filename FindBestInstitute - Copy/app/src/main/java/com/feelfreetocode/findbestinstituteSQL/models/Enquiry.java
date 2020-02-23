package com.feelfreetocode.findbestinstituteSQL.models;

import android.util.Log;

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
import com.feelfreetocode.findbestinstituteSQL.constants.API;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Enquiry implements Serializable {
    String enquiryId;
    Course course;
    Student student;


    public Enquiry() {

    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public String getEnquiryId() {
        return enquiryId;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }





    private String generateCode(){
        return student.getEmail()+course.getCourseId();
    }
}
