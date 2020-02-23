package com.nvmicrosoftwares.loginregistration.databasehandlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nvmicrosoftwares.loginregistration.models.Student;

public class StudentDatabaseHandler {
    public static final String DBNAME  ="STUDENTDATABASE";
    private final String TABLE = "students";
    private final String NAME = "name";
    private final String EMAIL= "email";
    private final String PASSWORD = "password";
    private final String ADDRESS = "address";
    private final String COLLEGE = "college";

    SQLiteDatabase db;

    public StudentDatabaseHandler(SQLiteDatabase db){
        this.db = db;
    }
    public void createTable(){
        db.execSQL("create table if not exists "+TABLE+" ("+
                NAME+" varchar not null, "+
                EMAIL+" varchar not null , "+
                PASSWORD+" varchar not null , "+
                ADDRESS+" varchar not null , "+
                COLLEGE+" varchar not null) ");
    }

    public boolean save(Student s){
        boolean flag = false;
        try{
            db.execSQL("insert into "+TABLE+" values (? , ? , ? , ? , ? )" ,
                    new String[]{s.getName() , s.getEmail() , s.getPassword() , s.getAddress() , s.getCollege()});
            flag = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            db.close();
        }
        return flag;
    }

    public boolean login(Student s){
        boolean flag = false;
        try{
            Cursor c = db.rawQuery("select "+NAME+","+EMAIL+","+PASSWORD+","+ADDRESS+","+COLLEGE+"" +
                            " from "+TABLE+ "" +
                            " where email = ? and password = ?" ,
                    new String[]{s.getEmail() , s.getPassword()});
            Log.e("emmail :" , s.getEmail());
            Log.e("Password :" , s.getPassword());
            if(c.moveToNext()){
                s.setName(c.getString(0));
                s.setCollege(c.getString(4));
                s.setAddress(c.getString(3));
                flag = true;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            db.close();
        }
        return  flag;
    }

    public Student get(String email){
        Student s = null;
        try{
            Cursor c = db.rawQuery("select "+NAME+","+EMAIL+","+PASSWORD+","+ADDRESS+","+COLLEGE+"" +
                            " from "+TABLE+ "" +
                            " where email = ? " ,
                    new String[]{email});
            Log.e("emmail :" , email);
            if(c.moveToNext()){
                s = new Student();
                s.setName(c.getString(0));
                s.setCollege(c.getString(4));
                s.setAddress(c.getString(3));
                s.setPassword(c.getString(2));
                s.setEmail(email);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            db.close();
        }
        return  s ;
    }

    public  boolean updateProfile(Student student){
        boolean flag = false;
        try {
            db.execSQL("update "+TABLE+"" +
                    " set "+NAME+" = ? ,"
                    +ADDRESS+" = ? ,"
                    +PASSWORD+" = ? ,"
                    +COLLEGE+" = ? "  , new String[]{student.getName() , student.getAddress() , student.getPassword() , student.getCollege()});
            flag =  true;

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            db.close();
        }
        return flag;
    }
}
