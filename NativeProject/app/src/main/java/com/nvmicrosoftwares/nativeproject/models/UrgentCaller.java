package com.nvmicrosoftwares.nativeproject.models;

public class UrgentCaller {
    private String name;
    private  String number;
    private  Integer id;


    public UrgentCaller(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public UrgentCaller(Integer id , String name, String number) {

        this.name = name;
        this.number = number;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public Integer getId() {
        return id;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UrgentCaller{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", id=" + id +
                '}';
    }



}
