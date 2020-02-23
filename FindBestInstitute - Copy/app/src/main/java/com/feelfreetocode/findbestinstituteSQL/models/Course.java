package com.feelfreetocode.findbestinstituteSQL.models;

import android.content.Intent;
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
import com.feelfreetocode.findbestinstituteSQL.constants.API;

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Course implements Serializable {

    private Integer courseId;
    private String courseName;
    private Institute institute;
    private Integer courseFee;

    public Course(Integer courseId, String courseName, Institute institute, Integer courseFee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.institute = institute;
        this.courseFee = courseFee;
    }
    @Override
    public String toString() {
        return "Course{" + "courseId=" + courseId + ", courseName=" + courseName + ", institute=" + institute + ", courseFee=" + courseFee + '}';
    }
    public Course() {
    }

    public Course(Integer courseId, String courseName, Institute institute) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.institute = institute;
    }

    public Course(String courseName, Institute institute, Integer courseFee) {
        this.courseName = courseName;
        this.institute = institute;
        this.courseFee = courseFee;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Integer getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(Integer courseFee) {
        this.courseFee = courseFee;
    }
}
