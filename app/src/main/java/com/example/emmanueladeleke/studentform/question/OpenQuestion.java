package com.example.emmanueladeleke.studentform.question;

/**
 * Created by EmmanuelAdeleke on 15/01/2016.
 */
public class OpenQuestion {
    public String _id;
    public String topic;
    public String question;

    @Override
    public String toString() {
        return "ID: " +  _id + "\n" + "topic: " + topic + "\n" + "question: " + question;
    }
}
