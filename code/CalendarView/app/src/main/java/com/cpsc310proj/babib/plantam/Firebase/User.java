package com.cpsc310proj.babib.plantam.Firebase;

/**
 * Created by Binh Vo on 12/13/17.
 */

public class User {

    private String name;
    private String classYear;
    private String userName;
    private String email;
    private String id;


    public User() {}

    public User(String name, String classYear, String userName, String email) {
        this.name = name;
        this.classYear = classYear;
        this.userName = userName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", classYear='" + classYear + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
