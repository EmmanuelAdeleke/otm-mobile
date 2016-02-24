package com.example.emmanueladeleke.studentform.entity;

import com.example.emmanueladeleke.studentform.question.OpenQuestion;

import java.util.List;

/**
 * Created by EmmanuelAdeleke on 15/01/2016.
 */
public class Lecturer {
    public String _id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String emailAddress;
    public List<OpenQuestion> questions;

    public String toString() {
        return "ID: " + _id + "\nfirstName: " +  firstName + "\nlastName: " + lastName + "\nusername: " + username + "\npassword: " + password + "\nemailAddress: " + emailAddress + "\n" +
                "questions: " + questions;
    }
}

