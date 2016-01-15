package com.example.emmanueladeleke.studentform;

/**
 * Created by EmmanuelAdeleke on 15/01/2016.
 */
public class OpenQuestion {
    String _id;
    String topic;
    String question;

    @Override
    public String toString() {
        return "ID: " +  _id + "\n" + "topic: " + topic + "\n" + "question: " + question;
    }
}
