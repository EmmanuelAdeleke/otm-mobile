package com.example.emmanueladeleke.studentform.question;

import java.util.Arrays;

/**
 * Created by EmmanuelAdeleke on 18/02/2016.
 */
public class SingleClosedQuestion {
    public String question;
    public String[] ansOption;
    public int A;
    public int B;
    public int C;
    public int D;

    public String toString() {
        return question.toString() + " " + Arrays.toString(ansOption);
    }
}
