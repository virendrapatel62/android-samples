package com.nvmicrosoftwares.openurl;

public class Designation{

    private Integer designationId;
    private String designation;

    public Designation(Integer id , String designation){
        this.designationId = designationId;
        this.designation = designation;
    }

    public Designation(String designation){
        this.designation = designation;
    }

    public Designation(){
    }

    public Designation(Integer designationId ){
        this.designationId = designationId;
    }

    public Integer getDesignationId(){
        return designationId;
    }

    public void setDesignationId(Integer id){
        this.designationId = id;
    }
    public String getDesignation(){
        return designation;
    }

    public void setDesignation(String s){
        this.designation = s;
    }

}