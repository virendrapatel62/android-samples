package com.nvmicrosoftwares.nativeproject.models;

public enum MessageTypes {
    Driving(1) , UrgentKey(2) , PlayMusicOnly(3) , VibrateOnly(4) , EmailMe(5) , YourEmail(6);
    private Integer id;

    MessageTypes(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
