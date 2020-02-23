
package com.feelfreetocode.findbestinstitute.models;


import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Institute implements Serializable {

    private static  AmazonSimpleDBClient simpleDBClient;
    
    private String instituteId;
    private String instituteName;
    private String address ;
    private String city;
    private String state;
    private String lat ;
    private String lang;
    private String adminName;
    private String contact ;
    private String email ;
    private String password ;

    public Institute(String instituteName, String address, String city, String state, String lat, String lang, String adminName, String contact, String email, String password) {
        this();
        this.instituteName = instituteName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.lat = lat;
        this.lang = lang;
        this.adminName = adminName;
        this.contact = contact;
        this.email = email;
        this.password = password;
    }

    public Institute() {
        AWSCredentials credentials = new BasicAWSCredentials(API.ACCESS_KEY_ID, API.SECRET_KEY);
        simpleDBClient = new AmazonSimpleDBClient(credentials);
        createDomain();
    }

    public void createDomain() {
        // TODO Auto-generated method stub
        CreateDomainRequest cr = new CreateDomainRequest(API.INSTITUTE_DOMAIN);
        simpleDBClient.createDomain(cr);
    }

    public Institute(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }
    
    

    public String getInstituteId() {
        return instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getLat() {
        return lat;
    }

    public String getLang() {
        return lang;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean registration(){
        // TODO Auto-generated method stub
        // creating attributes and assigning string values
        ReplaceableAttribute instituteName = new ReplaceableAttribute(API.InstituteTableColumns.INSTITUTE_NAME , getInstituteName() , true);
        ReplaceableAttribute addres = new ReplaceableAttribute(API.InstituteTableColumns.ADDRESS , getAddress() , true);
        ReplaceableAttribute adminName= new ReplaceableAttribute(API.InstituteTableColumns.ADMIN_NAME, getAdminName() , true);
        ReplaceableAttribute city = new ReplaceableAttribute(API.InstituteTableColumns.CITY, getCity() , true);
        ReplaceableAttribute contact= new ReplaceableAttribute(API.InstituteTableColumns.CONTACT, getContact() , true);
        ReplaceableAttribute email = new ReplaceableAttribute(API.InstituteTableColumns.EMAIL , getEmail() , false);
        ReplaceableAttribute lat = new ReplaceableAttribute(API.InstituteTableColumns.LAT , getLat() , true);
        ReplaceableAttribute lang= new ReplaceableAttribute(API.InstituteTableColumns.LANG, getLang() , true);
        ReplaceableAttribute password= new ReplaceableAttribute(API.InstituteTableColumns.PASSWORD , getPassword() , true);
        ReplaceableAttribute state= new ReplaceableAttribute(API.InstituteTableColumns.STATE, getState() , true);


        ArrayList<ReplaceableAttribute> attributes = new ArrayList<>();
        attributes.add(instituteName);
        attributes.add(addres);
        attributes.add(adminName);
        attributes.add(city);
        attributes.add(contact);
        attributes.add(email);
        attributes.add(lat);
        attributes.add(lang);
        attributes.add(password);
        attributes.add(state);

        PutAttributesRequest par = new PutAttributesRequest(API.INSTITUTE_DOMAIN , generateId() , attributes );
        try {
            simpleDBClient.putAttributes(par);
        } catch (Exception exception) {
            exception.printStackTrace();

            System.out.println("EXCEPTION = " + exception);
            return false;
        }

        return true;

    }

    public Institute login(){
        Institute i = null;
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.INSTITUTE_DOMAIN + " where email = '" + getEmail() + "' and password = '" + getPassword() + "'");

            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
            Log.e("infoooo" , list.toString());

            if(list!=null && !list.isEmpty()){
                ArrayList<Institute> in = getInstituteFromSelectResult(selectResult);
                if(in.size()!=0){
                    i = in.get(0);
                }

            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return i ;
    }

    public Institute getInstituteById(String id){
        Institute i = null;
        try {
            SelectRequest selectRequest = new SelectRequest("select * from " +
                    API.INSTITUTE_DOMAIN + " where itemName()= '"+id+"'");


            selectRequest.withConsistentRead(true);
            selectRequest.setNextToken(null);
            SelectResult selectResult = simpleDBClient.select(selectRequest);

            List<Item> list = selectResult.getItems();
//            Log.e("infoooo By ID" , list.toString());

            if(list!=null && !list.isEmpty()){
                ArrayList<Institute> in = getInstituteFromSelectResult(selectResult);
//                Log.e("info SingleResult: " , in.toString());
                if(in.size()>0){
                    i = in.get(0);
                }

            }
        }catch (Exception ec){
            ec.printStackTrace();
        }
        return i ;
    }


    private ArrayList<Institute> getInstituteFromSelectResult(SelectResult selectResult){
        ArrayList<Institute> institutes = new ArrayList<Institute>();

        List<Item> rows = selectResult.getItems();
        Log.e("info list:" , rows.toString());
        Log.e("info SelectResult:" , selectResult.toString());
        Iterator<Item> rowIterator = rows.iterator();
        while(rowIterator.hasNext()){
            Item row = rowIterator.next();

            Institute institute = new Institute();

            List<Attribute> columns = row.getAttributes();

            institute.setInstituteId(row.getName());
            Iterator<Attribute> columnIterator = columns.iterator();
            while(columnIterator.hasNext()){
                Attribute attribute = columnIterator.next();
                String name = attribute.getName();
                String value = attribute.getValue();
                Log.e("info Column name Value:" , name + "  -- > "+ value);


                //creating object

                switch (name){
                    case API.InstituteTableColumns.EMAIL:
                        institute.setEmail(value);
                        break;
                    case API.InstituteTableColumns.PASSWORD:
                        institute.setPassword(value);
                        break;
                    case API.InstituteTableColumns.LANG:
                        institute.setLang(value);
                        break;
                    case API.InstituteTableColumns.LAT:
                        institute.setLat(value);
                        break;
                    case API.InstituteTableColumns.ADDRESS:
                        institute.setAddress(value);
                        break;
                    case API.InstituteTableColumns.ADMIN_NAME:
                        institute.setAdminName(value);
                        break;
                    case API.InstituteTableColumns.INSTITUTE_NAME:
                        institute.setInstituteName(value);
                        break;
                    case API.InstituteTableColumns.CITY:
                        institute.setCity(value);
                        break;
                    case API.InstituteTableColumns.STATE:
                        institute.setState(value);
                        break;
                    case API.InstituteTableColumns.CONTACT:
                        institute.setContact(value);
                        break;
                }
            }

            institutes.add(institute);
        }

        return institutes;
    }

    private String generateId(){
        return new StrongPasswordEncryptor().encryptPassword(getInstituteName()+getContact());
    }




}
