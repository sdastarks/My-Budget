package com.example.mybudget;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.Models.User;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.example.mybudget.myDbHelper.USER_PROFILE;


/**
 * The activity is used to register user
 *
 * @author Benish
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivityLog";
    private final AppCompatActivity activity = RegisterActivity.this;
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutAge;

    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextLastName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextAge;
    private AppCompatButton appCompatButtonRegister;
    private AppCompatButton appCompatButtonUpdateUser;

    private TextView appCompatTextViewLoginLink;
    private ImageView avatar_image;
    private myDbHelper databaseHelper;
    User user;
    private boolean switchValue;
    boolean valid = true;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private int userAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


            Bundle extra = getIntent().getExtras();
            if(extra != null) { // to avoid the NullPointerException
                switchValue = extra.getBoolean("editProfile");
                Log.v(TAG, "switch activity use" + switchValue);

                if (!switchValue) {
                    initializeViews();
                    initializeObjects();
                   // initializeListeners();
                    appCompatButtonUpdateUser.setVisibility(View.GONE);

                }
            }
                else if (switchValue) {
                setContentView(R.layout.activity_register);
                initializeViews();
                initializeObjects();
                //initializeListeners();
                setValues();
                appCompatButtonRegister.setVisibility(View.GONE);
                appCompatTextViewLoginLink.setVisibility(View.GONE);
                appCompatButtonUpdateUser.setVisibility(View.VISIBLE);
                }
    }

    /**
     * This method is to initialize Image onClick
     */
    public void selectAvatar(){
        avatar_image = (ImageView) findViewById(R.id.avatarImage);
        avatar_image.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Log.d(TAG, "Select Avatar: activated");
              FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
              ft.replace(R.id.fragment_avatar_layout, new AvatarFragment());
              ft.commit();
          }
      });
    }

    /**
     * This method is to initialize views
     */
    private void initializeViews() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textInputLayoutFirstName = (TextInputLayout) findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutLastName = (TextInputLayout) findViewById(R.id.textInputLayoutLastName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutAge = (TextInputLayout) findViewById(R.id.textInputLayoutAge);

        textInputEditTextFirstName = (TextInputEditText) findViewById(R.id.textInputEditTextFirstName);
        textInputEditTextLastName = (TextInputEditText) findViewById(R.id.textInputEditTextLastName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextAge = (TextInputEditText) findViewById(R.id.textInputEditTextAge);

        avatar_image = (ImageView) findViewById(R.id.avatarImage);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        appCompatButtonUpdateUser = (AppCompatButton) findViewById(R.id.appCompatButtonUpdateUser);
        appCompatTextViewLoginLink = (TextView)findViewById(R.id.appCompatTextViewLoginLink);

        appCompatButtonRegister.setOnClickListener(this);
        appCompatButtonUpdateUser.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initializeObjects() {
        databaseHelper = new myDbHelper(this, "userdb.db", null, 1);
        User user = new User();
    }

    /**
     * This method is to initialize listeners

    private void initializeListeners() {
        initializeViews();
        appCompatButtonRegister.setOnClickListener(this);
        appCompatButtonUpdateUser.setOnClickListener(this);
    }*/

    /**
     *
     * @param view This method will execute onClick method depending on which button has been clicked
     */
    public void onClick(View view) {
        //initializeListeners();
        try {
            switch (view.getId()) {
                case R.id.appCompatButtonRegister: {
                    if (inputValidation()) {
                        addUser(user);
                        Log.v(TAG, "whatup");
                    }
                }
                break;

                case R.id.appCompatButtonUpdateUser: {
                    if (inputValidation()) {
                        updateUser(user);
                        Log.v(TAG, "on no");
                    }
                }
                break;
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }
    /**
     * Validate the input entered by user
     */
    private boolean inputValidation() {

        if(textInputEditTextEmail.getText().length() > 0){
            String valueEmail = textInputEditTextEmail.getText().toString();
            if (validateEmail() && validateFirstname() && validateLastname() && validateAge()) { return true; }
            return true;
        }
        else if(textInputEditTextEmail.getText().length() == 0) {
            if (validateFirstname() && validateLastname() && validateAge()) { return true; }
            return true;
        }
        else return false;
    }

    private boolean validateFirstname(){
        initializeViews();
        String valueFirstName = textInputEditTextFirstName.getText().toString().trim();
        if (valueFirstName.isEmpty()){ textInputEditTextFirstName.setError("Field cannot be empty");  valid = false;}
        else if(valueFirstName.length() < 3 ) { textInputEditTextFirstName.setError("Name cannot be less then 3 characters"); valid = false; }
        else if(valueFirstName.length() > 15 ) { textInputEditTextFirstName.setError("Name cannot be more then 15 characters");  valid = false;}
        else {
            textInputEditTextFirstName.setError(null);
            valid = true;
        }
        return valid;
    }

    private boolean validateLastname(){
        initializeViews();
        String valueLastName = textInputEditTextLastName.getText().toString().trim();
        if (valueLastName.isEmpty()){ textInputEditTextLastName.setError("Field cannot be empty");  valid = false;}
        else if(valueLastName.length() < 3 ) { textInputEditTextLastName.setError("Name cannot be less then 3 characters"); valid = false; }
        else if(valueLastName.length() > 15 ) { textInputEditTextLastName.setError("Name cannot be more then 15 characters");  valid = false;}
        else {
            textInputEditTextLastName.setError(null);
            valid = true;
        }
        return valid;
    }

    private boolean validateEmail(){
        initializeViews();
        String valueEmail = textInputEditTextEmail.getText().toString();
        if (valueEmail.isEmpty()){ textInputEditTextEmail.setError("Field cannot be empty");  valid = false;}
        else if(!Patterns.EMAIL_ADDRESS.matcher(valueEmail).matches() ) { textInputEditTextEmail.setError("Invalid email address"); valid = false; }
        else {
            textInputEditTextLastName.setError(null);
            valid = true;
        }
        return valid;
    }

    private boolean validateAge(){
        initializeViews();
        String valueAge = textInputEditTextAge.getText().toString();
        int valueAge1 = Integer.parseInt(valueAge);
        try {
            if (valueAge.isEmpty()) {
                textInputEditTextLastName.setError("Field cannot be empty");
                valid = false;}

             else if(valueAge.length() > 2){
                textInputEditTextLastName.setError("Invalid value");
                valid = false;
            }
            else if(valueAge1 == 0){
                textInputEditTextLastName.setError("Invalid value");
                valid = false;
            }
            else{
                 valid = true;
            }

            return valid;
        } catch (Exception e){
            e.printStackTrace();
            return valid = false;
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextFirstName.setText(null);
        textInputEditTextLastName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextAge.setText(null);
    }

    //CRUD operations for user
    public void addUser(User user) {
        initializeViews();
        String valueFirstName = textInputEditTextFirstName.getText().toString();
        String valueLastName = textInputEditTextLastName.getText().toString();
        String valueAge = textInputEditTextAge.getText().toString();
        int valueAge1 = Integer.parseInt(valueAge);
        String valueEmail = textInputEditTextEmail.getText().toString();
        User userObj = new User(valueFirstName, valueLastName, valueEmail, valueAge1);

        databaseHelper.addUser(userObj);
    }


    public void updateUser(User user) {
        initializeViews();
        String valueFirstName = textInputEditTextFirstName.getText().toString();
        String valueLastName = textInputEditTextLastName.getText().toString();
        String valueAge = textInputEditTextAge.getText().toString();
        int valueAge1 = Integer.parseInt(valueAge);
        String valueEmail = textInputEditTextEmail.getText().toString();
        User userObj = new User(valueFirstName, valueLastName, valueEmail, valueAge1);
        databaseHelper.updateUser(userObj);

    }
    private void setValues() {
        initializeViews();
        user = databaseHelper.getUser();
        userFirstName = user.getUserFirstName();
        userLastName = user.getUserLastName();
        userEmail = user.getUserMail();
        userAge = user.getUserAge();

        textInputEditTextFirstName.setText(userFirstName);
        textInputEditTextLastName.setText(userLastName);
        textInputEditTextEmail.setText(userEmail);
        textInputEditTextAge.setText("" + userAge);

    }


    /**
     * This method is to delete user record
     *
     * @param user

    public void deleteUser(){
    initializeViews();
    databaseHelper.open_db();

    db.delete(USER_PROFILE, values, USERID  + " = ?",
    new String[]{String.valueOf(user.getId())});
    databaseHelper.close();
    }*/

}
