package com.nvmicrosoftwares.openurl;

import java.sql.Date;

public class Teacher {

    private Integer teacherId;
    private String name;
    private Date dob;
    private String address;
    private String contact;
    private String email;
    private String password;
    private Integer experience;
    private String qualification;
    private Designation designation;

    public Teacher() {
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

    public Integer getExperience() {
        return experience;
    }

    public String getQualification() {
        return qualification;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setDesignation(Designation dasignation) {
        this.designation = dasignation;
    }

}
