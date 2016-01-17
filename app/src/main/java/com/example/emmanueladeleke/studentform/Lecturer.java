package com.example.emmanueladeleke.studentform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmmanuelAdeleke on 15/01/2016.
 */
public class Lecturer {
    int _id;
    String firstName;
    String lastName;
    String username;
    String password;
    String emailAddress;

    ArrayList<OpenQuestion> questions;

    public String toString() {
        return "ID: " + _id + "\nfirstName: " +  firstName + "\nlastName: " + lastName + "\nusername: " + username + "\npassword: " + password + "\nemailAddress: " + emailAddress + "\n" +
                "QuestionList " + questions;
    }
}

