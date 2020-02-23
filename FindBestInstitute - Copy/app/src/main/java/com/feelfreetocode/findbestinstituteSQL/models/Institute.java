
package com.feelfreetocode.findbestinstituteSQL.models;


import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
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

public class Institute implements Serializable {

    private Integer instituteId;
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



    public Institute() {
    }


    @Override
    public String toString() {
        return "Institute{" + "instituteId=" + instituteId + ", instituteName=" + instituteName + ", address=" + address + ", city=" + city + ", state=" + state + ", lat=" + lat + ", lang=" + lang + ", adminName=" + adminName + ", contact=" + contact + ", email=" + email + ", password=" + password + '}';
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Institute(Integer instituteId, String instituteName, String address, String city, String state, String lat, String lang, String adminName, String contact, String email, String password) {
        this.instituteId = instituteId;
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
}
