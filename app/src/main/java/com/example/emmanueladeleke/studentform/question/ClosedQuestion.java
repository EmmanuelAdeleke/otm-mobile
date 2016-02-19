package com.example.emmanueladeleke.studentform.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EmmanuelAdeleke on 11/02/2016.
 */
public class ClosedQuestion {
    public String _id;
    public String topic;
    public String lecturerId;
    public List<SingleClosedQuestion> questionList;

    public String toString() {
        return _id + " " + topic + " " + lecturerId + " " + questionList;
    }
}