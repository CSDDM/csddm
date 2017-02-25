package com.example.csddm.entity;

import java.io.Serializable;

/**
 * Created by dell on 2017/2/14.
 */

public class User implements Serializable{
    private String account;
    private String name;
    private String password;

    public User() {
    }

    public String getAccount(){
        return account;
    }

    public String getPassword(){
        return password;
    }

    public void setAccount(String account){
        this.account=account;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
