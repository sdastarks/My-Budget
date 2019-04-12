package com.example.mybudget;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ScrollView;

import static com.example.mybudget.myDbHelper.USER_PROFILE;


/**
 * Fragment allows the user to enter
 * an income, that will affect the balance
 *
 * @author Benish
 *
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private AppCompatButton appCompatButtonRegister;

    private myDbHelper databaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        initializeObjects();
        initializeListeners();
    }

    /**
     * This method is to initialize views
     */
    private void initializeViews() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
    }

    /**
     * This method is to initialize listeners
     */
    private void initializeListeners() {
        appCompatButtonRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initializeObjects() {
        databaseHelper = new myDbHelper(this,"userdb.db",null,1);
    }

    public void onClick(View view){
        inputValidation();
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        //if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
         //   return;
        }


    /**Validate the input entered by user
     *
     */
    private boolean inputValidation(){
        String value =  textInputEditTextName.getText().toString().trim();
        if(value.isEmpty()){
            //textInputLayoutName.setError();
            return false;
        }
        return true;
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
    }

    //CRUD operations for user
    public void addUser(){
        initializeViews();
        databaseHelper.open_db();
        ContentValues values = new ContentValues();
        String name, email;
        name = textInputEditTextName.getText().toString();
        email = textInputEditTextEmail.getText().toString();
        if(!(name.isEmpty()) && !(email.isEmpty())){
            //values.put(USER_NAME,name);
            //values.put(USER_EMAIL,email);
            //values.put(USER_AVATAR,avatar);
        }
        databaseHelper.close_db();
    }

    /**
     * This method to update user record
     *
     * @param

    public void updateUser(){
        initializeViews();
        databaseHelper.open_db();
        ContentValues values = new ContentValues();
        String name, email;

        db.update(USER_PROFILE, values, USERID  + " = ?",
                new String[]{String.valueOf(user.getId())});
        databaseHelper.close();
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
