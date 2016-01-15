package com.example.emmanueladeleke.studentform;

/**
 * Created by EmmanuelAdeleke on 18/12/2015.
 */
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public String toString() {
        return "ID: " + id + "\n" +
                "Name :" + firstName + " " + lastName + "\n" +
                "Email: " + emailAddress + "\n";
    }

}
