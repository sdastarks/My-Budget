package com.example.mybudget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

@SuppressWarnings("rawtypes")
public class SendMailTask extends AsyncTask {

//    private ProgressDialog statusDialog;
//    private Activity sendMailActivity;

    public SendMailTask(/*Activity activity*/) {
//        sendMailActivity = activity;
    }


//    protected void onPreExecute() {
//        statusDialog = new ProgressDialog(sendMailActivity);
//        statusDialog.setMessage("Getting ready...");
//        statusDialog.setIndeterminate(false);
//        statusDialog.setCancelable(false);
//        statusDialog.show();
//    }

    @SuppressWarnings("unchecked")
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

            //Config.mailSuccess="1";


        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }


//    @Override
//    public void onProgressUpdate(Object... values) {
//        statusDialog.setMessage(values[0].toString());
//
//    }

//    @Override
//    public void onPostExecute(Object result) {
//        statusDialog.dismiss();
//    }

}
