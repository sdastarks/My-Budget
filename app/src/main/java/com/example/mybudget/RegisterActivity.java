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
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.mybudget.Models.User;

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
    private ImageView avatar_image;
    private myDbHelper databaseHelper;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        initializeObjects();
        initializeListeners();
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
              ft.replace(R.id.fragment_avatar_layout, new NewWishFragment());
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
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initializeObjects() {
        databaseHelper = new myDbHelper(this, "userdb.db", null, 1);
    }

    /**
     * This method is to initialize listeners
     */
    private void initializeListeners() {
        appCompatButtonRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        inputValidation();
        addUser(user);
    }
    /**
     * Validate the input entered by user
     */
    private boolean inputValidation() {
        initializeViews();
        boolean valid = true;

        String valueFirstName = textInputEditTextFirstName.getText().toString().trim();
        String valueLastName = textInputEditTextLastName.getText().toString().trim();
        String valueAge = textInputEditTextAge.getText().toString().trim();
        String valueEmail = textInputEditTextEmail.getText().toString();
        Boolean valueInteger;

        try{
            Integer.parseInt(valueFirstName);
            valueInteger=true;

        }
        catch (Exception e){
            valueInteger=false;
        }

        Log.v(TAG, "valueInteger: "+valueInteger);

        if (valueFirstName.isEmpty() || valueFirstName.length() < 3) {
            textInputEditTextFirstName.setError("at least 3 characters");
            valid = false;

        }
        else {
            textInputEditTextFirstName.setError(null);
        }

        if (valueLastName.isEmpty() || valueLastName.length() < 3) {
            textInputEditTextLastName.setError("at least 3 characters");
            valid = false;
        } else {
            textInputEditTextLastName.setError(null);
        }

        if (valueAge.isEmpty()) {
            textInputEditTextAge.setError("enter your age");
            valid = false;
        } else {
            textInputEditTextAge.setError(null);
        }

        if (!valueEmail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(valueEmail).matches()) {
            textInputEditTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            textInputEditTextEmail.setError(null);
        }

        return valid;
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

        // if(inputValidation()){ databaseHelper.updateUser();}
        //else  { Toast.makeText(this, " All fields must be filled", Toast.LENGTH_SHORT).show(); }


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
