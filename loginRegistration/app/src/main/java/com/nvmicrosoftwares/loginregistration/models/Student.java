package com.nvmicrosoftwares.loginregistration.models;

import android.database.sqlite.SQLiteDatabase;

import com.nvmicrosoftwares.loginregistration.databasehandlers.StudentDatabaseHandler;

public class Student {
    private String name;
    private String email;
    private  String password;
    private String address;
    private String college;

    public Student(String name, String email, String password, String address, String college) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.college = college;
    }

    public Student() {
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

    public String getCollege() {
        return college;
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

    public void setCollege(String college) {
        this.college = college;
    }

    public  boolean save(SQLiteDatabase sqLiteDatabase){
        return new StudentDatabaseHandler(sqLiteDatabase).save(this);
    }

    public static void createTable(SQLiteDatabase db){
        new StudentDatabaseHandler(db).createTable();
    }

    public boolean login(SQLiteDatabase sb){
        return new StudentDatabaseHandler(sb).login(this);
    }

    public boolean updateProfile(SQLiteDatabase sb){
        return new StudentDatabaseHandler(sb).updateProfile(this);
    }

    public static Student get(String email , SQLiteDatabase sb){
        return new StudentDatabaseHandler(sb).get(email);
    }


}
