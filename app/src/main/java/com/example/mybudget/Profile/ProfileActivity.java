package com.example.mybudget.Profile;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.StyleRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.Models.User;
import com.example.mybudget.R;
import com.example.mybudget.myDbHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * The activity is used to create user Profile
 *
 * @author Benish
 */
public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivityLog";
    User user = new User();
    private myDbHelper databaseHelper;


    private TextView textFirstName;
    private TextView textLastName;
    private TextView textEmail;
    private TextView textAge;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private int userAge;
    private boolean switchValue;

    /*
    private static final int CAMERA_TAKE_REQUEST = 200;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private ImageView imageviewCamera;
    File file;
    Uri uri;
    private Context context;
    private Activity activity;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeViews();
        initializeObjects();

        user = databaseHelper.getUser();
        if (user != null) {
            setValues();
        } else Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();



        /*imageviewCamera = (ImageView)findViewById(R.id.imageviewCamera);
        context = this;
        activity = ProfileActivity.this;
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);*/

    }

    private void initializeViews() {
        textFirstName = (TextView) findViewById(R.id.textFirstName);
        textLastName = (TextView) findViewById(R.id.textLastName);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textAge = (TextView) findViewById(R.id.textAge);
    }

    private void initializeObjects() {
        databaseHelper = new myDbHelper(this, "userdb.db", null, 1);
    }

    private void setValues() {
        initializeViews();
        user = databaseHelper.getUser();
        userFirstName = user.getUserFirstName();
        userLastName = user.getUserLastName();
        userEmail = user.getUserMail();
        userAge = user.getUserAge();

        textFirstName.setText(userFirstName);
        textLastName.setText(userLastName);
        textEmail.setText(userEmail);
        textAge.setText("" + userAge);
    }




/*
    //Get the image from camera
    @TargetApi(Build.VERSION_CODES.M)
    public void take(View v) {
        if(checkCameraExists()) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                        ALL_PERMISSIONS_RESULT);
            } else {
                Toast.makeText(context,"Permissions already granted.", Toast.LENGTH_LONG).show();
                launchCamera();
            }
        } else {
            Toast.makeText(activity, "Camera not available.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkCameraExists() {
        return activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void launchCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        file = new File(Environment.getExternalStorageDirectory(), String.valueOf(System.currentTimeMillis()) + ".jpg");
        uri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMERA_TAKE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case CAMERA_TAKE_REQUEST:
                imageviewCamera.setImageURI(android.net.Uri.parse(file.toURI().toString()));
                break;
        }
    }

    // Permissions
    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            String msg = "These permissions are mandatory for the application. Please allow access.";
                            showMessageOKCancel(msg,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Toast.makeText(context, "Permissions garanted.", Toast.LENGTH_LONG).show();
                    launchCamera();
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/
}
