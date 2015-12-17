package com.example.emmanueladeleke.studentform;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by EmmanuelAdeleke on 16/12/2015.
 */
public class UserDialog {

    public static void showMessageToUser(Context context, String message) {

        new AlertDialog.Builder(context)
                .setTitle(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }


}
