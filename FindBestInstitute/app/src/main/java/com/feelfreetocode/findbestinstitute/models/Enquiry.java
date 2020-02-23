package com.feelfreetocode.findbestinstitute.models;

import android.graphics.CornerPathEffect;
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
import com.feelfreetocode.findbestinstitute.constants.API;

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Enquiry implements Serializable {
    String enquiryId;
    Course course;
    Student student;

    private static AmazonSimpleDBClient simpleDBClient;

    public Enquiry() {
        simpleDBClient = new AmazonSimpleDBClient(new BasicAWSCredentials(API.ACCESS_KEY_ID, API.SECRET_KEY));
        CreateDomainRequest cr = new CreateDomainRequest(API.ENQUIRY_DOMAIN);
        simpleDBClient.createDomain(cr);
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

    public static void setSimpleDBClient(AmazonSimpleDBClient simpleDBClient) {
        Enquiry.simpleDBClient = simpleDBClient;
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

    public static AmazonSimpleDBClient getSimpleDBClient() {
        return simpleDBClient;
    }

    public boolean save(){
        boolean flag = false;
        try{
            ReplaceableAttribute studentId= new ReplaceableAttribute(API.EnquiryColumns.STUDENT_ID ,getStudent().getStudentId()  , true);
            ReplaceableAttribute courseID = new ReplaceableAttribute(API.EnquiryColumns.COURSE_ID, getCourse().getCourseId(), true);
            ReplaceableAttribute instituteId = new ReplaceableAttribute(API.EnquiryColumns.INSTITUTE_ID, getCourse().getInstitute().getInstituteId(), true);


            List<ReplaceableAttribute> attributeList = new ArrayList<>();
            attributeList.add(studentId);
            attributeList.add(courseID);
            attributeList.add(instituteId);
            PutAttributesRequest putAttributesRequest = new PutAttributesRequest(API.ENQUIRY_DOMAIN, generateCode() , attributeList);
            simpleDBClient.putAttributes(putAttributesRequest);
            this.enquiryId = generateCode();
            Log.e("infooE" , enquiryId);
            flag = true;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  flag;

    }


    public boolean delete(){

        try{
            DeleteAttributesRequest deleteAttributesRequest = new DeleteAttributesRequest(API.ENQUIRY_DOMAIN , enquiryId);
            simpleDBClient.deleteAttributes(deleteAttributesRequest);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }



    public List<Enquiry> getEnquiries(String insid){

            ArrayList<Enquiry> enquiries = new ArrayList<>();
        try{
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.ENQUIRY_DOMAIN +" where "+ API.EnquiryColumns.INSTITUTE_ID +"= '"+insid+"'");
            Log.e("infooInsideGetEnquery" , insid);
            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                enquiries = getEnquiryFromSelectResult(selectResult);
            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return enquiries ;
    }
    private static ArrayList<Enquiry> getEnquiryFromSelectResult(SelectResult selectResult){
        ArrayList<Enquiry> enquiries= new ArrayList<Enquiry>();

        List<Item> rows = selectResult.getItems();
        //Log.e("info list:" , rows.toString());
        //Log.e("info SelectResult:" , selectResult.toString());
        Iterator<Item> rowIterator = rows.iterator();
        while(rowIterator.hasNext()){
            Item row = rowIterator.next();

            Enquiry enquiry = new Enquiry();

            List<Attribute> columns = row.getAttributes();

            enquiry.setEnquiryId(row.getName());
            Iterator<Attribute> columnIterator = columns.iterator();
            while(columnIterator.hasNext()){
                Attribute attribute = columnIterator.next();
                String name = attribute.getName();
                String value = attribute.getValue();
                Log.e("info Column name Value:" , name + "  -- > "+ value);

                //creating object

                switch (name){
                    case API.EnquiryColumns.COURSE_ID:
                        Course c = new Course();
                        c = c.getCourse(value);
                        enquiry.setCourse(c);

                        break;
                    case API.EnquiryColumns.STUDENT_ID:
                        Student s = new Student();
                        s = s.getStudentByStudentId(value);
                        enquiry.setStudent(s);
                        break;
                }
            }

            enquiries.add(enquiry);
        }

        return enquiries;
    }

    private String generateCode(){
        return student.getEmail()+course.getCourseId();
    }
}
