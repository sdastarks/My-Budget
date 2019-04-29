package com.example.mybudget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

@SuppressWarnings("rawtypes")
public class SendMailTask extends AsyncTask {



    public SendMailTask() {

    }

    @Override
    //Params... params
    protected Object doInBackground(Object... args) {
        try {
            Log.i("SendMailTask", "About to instantiate GMail...");
            publishProgress("Processing input....");
            GmailSender androidEmail = new GmailSender(args[0].toString(),
                    args[1].toString());
            androidEmail.createEmailMessage();
            publishProgress("Sending approval email to your parents....");
            androidEmail.sendEmail();
            publishProgress("Email Sent.");
            Log.i("SendMailTask", "Mail Sent.");

        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

}
