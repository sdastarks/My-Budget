package com.example.mybudget.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.User;
import com.example.mybudget.R;
import com.example.mybudget.myDbHelper;


/**
 * The activity is used to register user
 *
 * @author Benish
 */

public class RegisterActivity extends AvatarChangeActivity implements View.OnClickListener {

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
    private Button btn_exitRegisterActivity;

    private TextView appCompatTextViewLoginLink;
    private TextView chooseAvatarTextView;
    private TextView register_headline;

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

    public static final String USER_PREFS_NAME = "userPreferenceFile";
    public static final String USER_ID = "userId";
    public static final String USER_FISRT_NAME = "userName";
    SharedPreferences sharedPreferences;
    int userGlobalId;
    String userGlobalName;
    private Drawable d;
    private WelcomeFragmentDialog welcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getApplicationContext().getSharedPreferences(USER_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences settings = getSharedPreferences(USER_PREFS_NAME, 0);
        userGlobalId = getUserId();
        userGlobalName = getUserName();

        Intent intent = getIntent();
        switchValue = intent.getStringExtra("editProfile");
        if (intent != null) { // to avoid the NullPointerException

            initializeViews();
            initializeObjects();

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
                register_headline.setText("Register");

            } else if (switchValue.equals("update")) {
                setContentView(R.layout.activity_register);
                setValues();
                appCompatButtonRegister.setVisibility(View.GONE);
                appCompatTextViewLoginLink.setVisibility(View.GONE);
                appCompatButtonUpdateUser.setVisibility(View.VISIBLE);
                chooseAvatarTextView.setVisibility(View.GONE);
                register_headline.setText("Edit Profile");
                avatar_image = (ImageView)findViewById(R.id.avatarImage);
                if(imageResId != -1){
                    d = getDrawable(imageResId);
                    avatar_image.setImageDrawable(d);
                }
            }
        }
        exitRegisterUpdateActivity();
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
        register_headline = (TextView)findViewById(R.id.register_activity_title);
        btn_exitRegisterActivity = (Button) findViewById(R.id.btn_cancel_register_user);

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
        welcomeFragment = new WelcomeFragmentDialog();
    }

    /**
     * @param view This method will execute onClick method depending on which button has been clicked
     */
    public void onClick(View view) {
        //initializeListeners();
        try {
            switch (view.getId()) {
                case R.id.appCompatButtonRegister: {
                    if (inputValidation()) {
                        addUser(user);
                        Log.v(TAG, "user added");
                        showWelcomeDialog();
                    }
                }
                break;

                case R.id.appCompatButtonUpdateUser: {
                    //deleteUser();
                    if (inputValidation()) {
                        updateUser(user);
                        Log.v(TAG, "user updated");
                        Intent intentLoadMainPage = new Intent (RegisterActivity.this, MainActivity.class);
                        startActivity(intentLoadMainPage);
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

        String valueAgeNotNull = valueAge.trim();
        int value1 = 0;
        try {
            value1 = valueAgeNotNull != null && !valueAgeNotNull.isEmpty() && !valueAgeNotNull.equals("") ? Integer.parseInt(valueAgeNotNull) : 0;
        }catch (NumberFormatException e){
            e.printStackTrace();
            textInputEditTextAge.setError("Enter a valid number");
            valid = false;
        }
        try {
            if (valueAge.isEmpty()) {
                textInputEditTextAge.setError("Field cannot be empty");
                valid = false;
            } else if (valueAge.length() > 2) {
                textInputEditTextAge.setError("Invalid value");
                valid = false;
            } else if (value1 == 0) {
                textInputEditTextAge.setError("Age cannot be 0");
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
        user = databaseHelper.getUser(userGlobalId);
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
        SharedPreferences sharedPrefs = getSharedPreferences(USER_PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(USER_FISRT_NAME, userName);
        editor.putInt(USER_ID, userId);
        editor.putBoolean("isLogged", true);
        editor.apply();
    }

    //get user name and id from shared preference
    public String getUserName() {
        SharedPreferences sharedPrefs = getSharedPreferences(USER_PREFS_NAME, 0);
        return sharedPrefs.getString(USER_FISRT_NAME, null);
    }

    private int getUserId() {
        SharedPreferences sharedPrefs = getSharedPreferences(USER_PREFS_NAME, 0);
        return sharedPrefs.getInt(USER_ID, 0);
    }
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

    private void showWelcomeDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        welcomeFragment = new WelcomeFragmentDialog();
        welcomeFragment.show(fragmentManager, "welcomeFragment");
    }

    private void exitRegisterUpdateActivity() {
        btn_exitRegisterActivity.setOnClickListener(new View.OnClickListener() {
            /*
             * Method sends the user back to the main menu
             * when the cancel button is initialised
             */
            @Override
            public void onClick(View v) {
                Log.v(TAG, "cancel button initialised");
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void deleteUser(){
        databaseHelper.deleteUser();
        SharedPreferences sharedPrefs = getSharedPreferences(USER_PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear().commit();
    }

}
