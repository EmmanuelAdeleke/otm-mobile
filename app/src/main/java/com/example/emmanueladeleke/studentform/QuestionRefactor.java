package com.example.emmanueladeleke.studentform;

/**
 * Created by EmmanuelAdeleke on 16/01/2016.
 */
public class QuestionRefactor {

    public int lecturerId;
    public String firstName;
    public String lastName;
    public String questionId;
    public String topic;
    public String question;


    public QuestionRefactor(int lecturerId, String firstName, String lastName,
                            String questionId, String topic, String question) {
        this.lecturerId = lecturerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.questionId = questionId;
        this.topic = topic;
        this.question = question;
    }

    @Override
    public String toString() {
        return lecturerId + " " + firstName + " " + lastName +
                questionId + " " + topic + " " + question;
    }
}
