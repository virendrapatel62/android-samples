package com.feelfreetocode.findbestinstitute.models;

import android.support.annotation.NonNull;
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

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Student implements Serializable {
    private static  AmazonSimpleDBClient simpleDBClient;
    String studentId;
    String name;
    String email , password , contact ;
    String address , city , state;
    String lat ;
    String lang;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Student(String studentId, String name, String email, String password, String address, String lat, String lang) {
        this();
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.lat = lat;
        this.lang = lang;
    }

    public Student(String name, String email, String password, String contact, String address, String city, String state, String lat, String lang) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.city = city;
        this.state = state;
        this.lat = lat;
        this.lang = lang;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Student(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }
    public Student(Boolean f) {
    }

    public Student() {
        simpleDBClient = new AmazonSimpleDBClient(new BasicAWSCredentials(API.ACCESS_KEY_ID, API.SECRET_KEY));
        CreateDomainRequest cr = new CreateDomainRequest(API.STUDENT_DOMAIN);
        simpleDBClient.createDomain(cr);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLang() {
        return lang;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", lat='" + lat + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }

    public boolean save(){
        boolean flag = false;
        try{
            ReplaceableAttribute SName = new ReplaceableAttribute(API.StudentColumns.STUDENT_NAME , getName() , true);
            ReplaceableAttribute Semail = new ReplaceableAttribute(API.StudentColumns.STUDENT_EMAIL, getEmail().toString() , true);
            ReplaceableAttribute SPassword= new ReplaceableAttribute(API.StudentColumns.STUDENT_PASSOWRD , getPassword(), true);
            ReplaceableAttribute SAddress= new ReplaceableAttribute(API.StudentColumns.STUDENT_ADDRESS , getAddress() , true);
            ReplaceableAttribute SContact= new ReplaceableAttribute(API.StudentColumns.STUDENT_CONTACT, getContact() , true);
            ReplaceableAttribute SLat= new ReplaceableAttribute(API.StudentColumns.STUDENT_LAT , getLat() , true);
            ReplaceableAttribute SState= new ReplaceableAttribute(API.StudentColumns.STUDENT_STATE , getState() , true);
            ReplaceableAttribute SCity= new ReplaceableAttribute(API.StudentColumns.STUDENT_CITY , getCity() , true);
            ReplaceableAttribute SLang= new ReplaceableAttribute(API.StudentColumns.STUDENT_LANG , getLang() , true);

            List<ReplaceableAttribute> attributeList = new ArrayList<>();
            attributeList.add(SName);
            attributeList.add(Semail);
            attributeList.add(SPassword);
            attributeList.add(SContact);
            attributeList.add(SAddress);
            attributeList.add(SLang);
            attributeList.add(SState);
            attributeList.add(SCity);
            attributeList.add(SLat);
            PutAttributesRequest putAttributesRequest = new PutAttributesRequest(API.STUDENT_DOMAIN , generateCode() , attributeList);
            simpleDBClient.putAttributes(putAttributesRequest);
            this.studentId = generateCode();
            flag = true;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  flag;

    }
    private String generateCode(){
        return new StrongPasswordEncryptor().encryptPassword(name+email);
    }

    public Student login(){
        Student i = null;
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.STUDENT_DOMAIN + " where email = '" + getEmail() + "' and password = '" + getPassword() + "'");

            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                ArrayList<Student> in = getStudentFromSelectResult(selectResult);
                if(in.size()!=0){
                    i = in.get(0);
                }

            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return i ;
    }

    public Student getStudentByStudentId(String id){
        Student i = null;
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.STUDENT_DOMAIN + " where itemName()='"+id+"'");

            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                ArrayList<Student> in = getStudentFromSelectResult(selectResult);
                if(!in.isEmpty()){
                    i = in.get(0);
                }

            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return i ;
    }


    private static ArrayList<Student> getStudentFromSelectResult(SelectResult selectResult){
        ArrayList<Student> students= new ArrayList<Student>();

        List<Item> rows = selectResult.getItems();
        Iterator<Item> rowIterator = rows.iterator();
        while(rowIterator.hasNext()){
            Item row = rowIterator.next();

            Student student = new Student();

            List<Attribute> columns = row.getAttributes();

            student.setStudentId(row.getName());
            Iterator<Attribute> columnIterator = columns.iterator();
            while(columnIterator.hasNext()){
                Attribute attribute = columnIterator.next();
                String name = attribute.getName();
                String value = attribute.getValue();
                Log.e("info Column name Value:" , name + "  -- > "+ value);


                //creating object

                switch (name){
                    case API.StudentColumns.STUDENT_NAME:
                        student.setName(value);
                        break;
                    case API.StudentColumns.STUDENT_ADDRESS:
                        student.setAddress(value);
                        break;
                    case API.StudentColumns.STUDENT_CITY:
                        student.setCity(value);
                        break;
                    case API.StudentColumns.STUDENT_STATE:
                        student.setState(value);
                        break;
                    case API.StudentColumns.STUDENT_CONTACT:
                        student.setContact(value);
                        break;
                    case API.StudentColumns.STUDENT_EMAIL:
                        student.setEmail(value);
                        break;
                    case API.StudentColumns.STUDENT_LANG:
                        student.setLang(value);
                        break;
                    case API.StudentColumns.STUDENT_LAT:
                        student.setLat(value);
                        break;
                    case API.StudentColumns.STUDENT_PASSOWRD:
                        student.setName(value);
                        break;

                }
            }

            students.add(student);
        }

        return students;
    }
}
