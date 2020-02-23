package com.feelfreetocode.findbestinstituteSQL.models;

import java.io.Serializable;

public class LoginInfo implements Serializable {
    String email ;
    String password ;
    String type;

    public LoginInfo(String email, String password, String type) {

        this.email = email;
        this.password = password;
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }
}
