package com.example.mybudget.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mybudget.Models.User;
import com.example.mybudget.R;
import com.example.mybudget.SettingsActivity;
import com.example.mybudget.myDbHelper;


/**
 * The activity is used to register user
 *
 * @author Benish
 */

public class RegisterActivity extends SettingsActivity implements View.OnClickListener {

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
    private TextView chooseAvatarTextView;

    private ImageView avatar_image;
    private FrameLayout  frameLayoutNewAvatar;

    private myDbHelper databaseHelper;
    User user;
    private String switchValue;
    boolean valid = true;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private int userAge;

    public static final String PREFS_NAME = "userPreferenceFile";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    SharedPreferences sharedPreferences;
    int userGlobalId;
    String userGlobalName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        userGlobalId = getUserId();
        userGlobalName = getUserName();

        Intent intent = getIntent();
        switchValue = intent.getStringExtra("editProfile");
        if (intent != null) { // to avoid the NullPointerException

            initializeViews();
            initializeObjects();
            // initializeListeners();

            if(frameLayoutNewAvatar != null)
                frameLayoutNewAvatar.setVisibility(View.GONE);

            if(imageResId != -1)
            {
                Drawable drawable = getDrawable(imageResId);
                avatar_image.setImageDrawable(drawable);
            }

            Log.v(TAG, "switch activity use" + switchValue);

            if (switchValue.equals("add")) {

                selectAvatar();
                appCompatButtonUpdateUser.setVisibility(View.GONE);


            } else if (switchValue.equals("update")) {
                setContentView(R.layout.activity_register);
                setValues();
                appCompatButtonRegister.setVisibility(View.GONE);
                appCompatTextViewLoginLink.setVisibility(View.GONE);
                appCompatButtonUpdateUser.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * This method is to initialize Image onClick
     */
    public void selectAvatar() {
        avatar_image = (ImageView) findViewById(R.id.avatarImage);
        avatar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Select Avatar: activated");
                AllItemsVisibilitySwitch();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_avatar, new AvatarFragment());
                ft.commit();
                //gridView.setAdapter(new AvatarGridView(this));

                //gridView.setVisibility(View.VISIBLE);

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
        appCompatTextViewLoginLink = (TextView) findViewById(R.id.appCompatTextViewLoginLink);
        chooseAvatarTextView = (TextView) findViewById(R.id.textView4);

        appCompatButtonRegister.setOnClickListener(this);
        appCompatButtonUpdateUser.setOnClickListener(this);

        frameLayoutNewAvatar = (FrameLayout)findViewById(R.id.framelayout_newavatar);
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
     * @param view This method will execute onClick method depending on which button has been clicked
     */
    public void onClick(View view) {
        //initializeListeners();
        try {
            switch (view.getId()) {
                case R.id.appCompatButtonRegister: {
                    //databaseHelper.deleteUser();
                    if (inputValidation()) {
                        addUser(user);
                        Log.v(TAG, "user added");
                    }
                }
                break;

                case R.id.appCompatButtonUpdateUser: {
                    if (inputValidation()) {
                        updateUser(user);
                        Log.v(TAG, "user updated");
                    }
                }
                break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validate the input entered by user
     */
    private boolean inputValidation() {
        try {
            String valueEmail = textInputEditTextEmail.getText().toString();
            if (valueEmail.isEmpty()) {
                throw new NullPointerException();
            }
            if (validateEmail() && validateFirstname() && validateLastname() && validateAge()) {
                System.out.println("all fields validation");
                return true;
            }

        } catch (NullPointerException e) {
            System.out.println("Email field not validated");
            if (validateFirstname() && validateLastname() && validateAge()) {
                return true;
            }
        }
        return false;
    }

    private boolean validateFirstname() {
        initializeViews();
        String valueFirstName = textInputEditTextFirstName.getText().toString().trim();
        if (valueFirstName.isEmpty()) {
            textInputEditTextFirstName.setError("Field cannot be empty");
            valid = false;
        } else if (valueFirstName.length() < 3) {
            textInputEditTextFirstName.setError("Name cannot be less then 3 characters");
            valid = false;
        } else if (valueFirstName.length() > 15) {
            textInputEditTextFirstName.setError("Name cannot be more then 15 characters");
            valid = false;
        } else {
            textInputEditTextFirstName.setError(null);
            valid = true;
        }
        return valid;
    }

    private boolean validateLastname() {
        initializeViews();
        String valueLastName = textInputEditTextLastName.getText().toString().trim();
        if (valueLastName.isEmpty()) {
            textInputEditTextLastName.setError("Field cannot be empty");
            valid = false;
        } else if (valueLastName.length() < 3) {
            textInputEditTextLastName.setError("Name cannot be less then 3 characters");
            valid = false;
        } else if (valueLastName.length() > 15) {
            textInputEditTextLastName.setError("Name cannot be more then 15 characters");
            valid = false;
        } else {
            textInputEditTextLastName.setError(null);
            valid = true;
        }
        return valid;
    }

    private boolean validateEmail() {
        initializeViews();
        String valueEmail = textInputEditTextEmail.getText().toString();
        if (valueEmail.isEmpty()) {
            textInputEditTextEmail.setError("Field cannot be empty");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(valueEmail).matches()) {
            textInputEditTextEmail.setError("Invalid email address");
            valid = false;
        } else {
            textInputEditTextLastName.setError(null);
            valid = true;
        }
        return valid;
    }

    private boolean validateAge() {
        initializeViews();
        String valueAge = textInputEditTextAge.getText().toString();
        int valueAge1 = Integer.parseInt(valueAge);
        try {
            if (valueAge.isEmpty()) {
                textInputEditTextAge.setError("Field cannot be empty");
                valid = false;
            } else if (valueAge.length() > 2) {
                textInputEditTextAge.setError("Invalid value");
                valid = false;
            } else if (valueAge1 == 0) {
                textInputEditTextAge.setError("Invalid value");
                valid = false;
            } else {
                valid = true;
            }

            return valid;
        } catch (Exception e) {
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

        int userId = databaseHelper.addUser(userObj);
        saveUser(valueFirstName, userId);
    }


    public void updateUser(User user) {
        initializeViews();
        //String keyValue = getUserName();
        //int keyValue = getUserId();
        String valueFirstName = textInputEditTextFirstName.getText().toString();
        String valueLastName = textInputEditTextLastName.getText().toString();
        String valueAge = textInputEditTextAge.getText().toString();
        int valueAge1 = Integer.parseInt(valueAge);
        String valueEmail = textInputEditTextEmail.getText().toString();
        User userObj = new User(valueFirstName, valueLastName, valueEmail, valueAge1);
        databaseHelper.updateUser(userObj, userGlobalId);

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

    //save user name and id in shared preference
    private void saveUser(String userName, int userId) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(USER_NAME, userName);
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    //get user name and id from shared preference
    private String getUserName() {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, 0);
        return sharedPrefs.getString(USER_NAME, null);
    }

    private int getUserId() {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, 0);
        return sharedPrefs.getInt(USER_ID, 0);
    }

    /**
     * This method is to delete user record
     *
     * @param user public void deleteUser(){
     *             initializeViews();
     *             databaseHelper.open_db();
     *             <p>
     *             db.delete(USER_PROFILE, values, USERID  + " = ?",
     *             new String[]{String.valueOf(user.getId())});
     *             databaseHelper.close();
     *             }
     */

    private void AllItemsVisibilitySwitch() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textInputLayoutFirstName.setVisibility(View.GONE);
        textInputLayoutLastName.setVisibility(View.GONE);
        textInputLayoutEmail.setVisibility(View.GONE);
        textInputLayoutAge.setVisibility(View.GONE);

        textInputEditTextFirstName.setVisibility(View.GONE);
        textInputEditTextLastName.setVisibility(View.GONE);
        textInputEditTextEmail.setVisibility(View.GONE);
        textInputEditTextAge.setVisibility(View.GONE);
        chooseAvatarTextView.setVisibility(View.GONE);

        avatar_image.setVisibility(View.GONE);

        appCompatButtonRegister.setVisibility(View.GONE);
        appCompatButtonUpdateUser.setVisibility(View.GONE);
        appCompatTextViewLoginLink.setVisibility(View.GONE);

    }

}
