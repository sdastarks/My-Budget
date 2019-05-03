package com.example.mybudget.Models;

public class User {
    private int userId;
    private String userFirstName;
    private String userLastName;
    private int userAge;
    private String userMail;
    private String userPhone;

    public User(){}

    public User( String userFirstName, String userLastName, String userMail, int userAge){
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userMail = userMail;
        this.userAge = userAge;
    }

    //Getter and Setters
    public void setUserId(int userId) { this.userId = userId; }
    public int getUserId() { return userId; }

    public void setUserFirstName(String userFirstName){
        this.userFirstName = userFirstName;
    }
    public String getUserFirstName(){
        return userFirstName;
    }

    public void setUserLastName(String userLastName){
        this.userLastName = userLastName;
    }
    public String getUserLastName(){
        return userLastName;
    }

    public void setUserMail(String userMail){
        this.userMail = userMail;
    }
    public String getUserMail(){ return userMail; }

    public void setUserAge(int userAge){
        this.userAge = userAge;
    }
    public int getUserAge(){
        return userAge;
    }

    public void setUserPhone(String userPhone){ this.userPhone = userPhone; }
    public String getUserPhone(){ return userPhone; }
}
