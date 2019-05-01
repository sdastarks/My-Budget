package com.example.mybudget.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.User;
import com.example.mybudget.R;
import com.example.mybudget.myDbHelper;

import static com.example.mybudget.Profile.RegisterActivity.USER_ID;
import static com.example.mybudget.Profile.RegisterActivity.USER_PREFS_NAME;

/**
 * The activity is used to create user Profile
 *And pick an avatar
 * @author Benish
 */
public class ProfileActivity extends AvatarChangeActivity {
    private static final String TAG = "ProfileActivityLog";
    User userData = new User();
    private myDbHelper databaseHelper;

    private TextView textFullName;
    private TextView textEmail;
    private TextView textAge;

    private TextView user_profile_balance;
    private TextView user_profile_savings;
    private TextView user_profile_spendings;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private int userAge;
    private Button btn_exitProfileActivity;
    private boolean switchValue;
    SharedPreferences sharedPreferences;
    int userGlobalId;
    private Drawable d;
    private ImageView hero_profile;


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

        databaseHelper = new myDbHelper(this, "userdb.db", null, 1);
        userData = databaseHelper.getUser(userGlobalId);

        hero_profile = (ImageView)findViewById(R.id.hero_image_profile);
        if(imageResId != -1){
            d = getDrawable(imageResId);
            hero_profile.setImageDrawable(d);
        }

        sharedPreferences = getApplicationContext().getSharedPreferences(USER_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        userGlobalId = sharedPreferences.getInt(USER_ID, 0);;
        if (userGlobalId != 0)
            {
            setUserPersonalData(userGlobalId);
            setUserBalanceValues();
            updateBalance();
            savingsOfUser();
            spendingsOfUser();

            } else Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();

        activateOnExitProfileActiviy();
        /*imageviewCamera = (ImageView)findViewById(R.id.imageviewCamera);
        context = this;
        activity = ProfileActivity.this;
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);*/

    }

    private void setUserPersonalData(int user_id) {

        textFullName = (TextView)findViewById(R.id.full_name_user);
        textAge = (TextView)findViewById(R.id.age_user);
        textEmail = (TextView)findViewById(R.id.email_user);

        userData = databaseHelper.getUser(user_id);
        userFirstName = userData.getUserFirstName();
        userLastName = userData.getUserLastName();
        userEmail = userData.getUserMail();
        userAge = userData.getUserAge();

        textFullName.setText(userFirstName + " "+ userLastName);
        textEmail.setText(userEmail);
        textAge.setText("" + userAge);
    }

    private void setUserBalanceValues(){
        user_profile_balance = (TextView)findViewById(R.id.profile_balance);
        user_profile_savings = (TextView)findViewById(R.id.profile_savings);
        user_profile_spendings = (TextView)findViewById(R.id.profile_spending);

    }

    public int updateBalance(){
        user_profile_balance = findViewById(R.id.profile_balance);
        int income = databaseHelper.calcIncome();
        int expense = databaseHelper.calcExpenses();
        int wishes = databaseHelper.calcWish();
        int earning = databaseHelper.calcEarning();
        int balance = (income + earning) - (expense + wishes);
        user_profile_balance.setText(String.valueOf(balance) + " SEK");
        return balance;
    }

    public int savingsOfUser(){
        user_profile_savings = (TextView)findViewById(R.id.profile_savings);
        int wishes = databaseHelper.calcWish();
        int savings = wishes;
        user_profile_savings.setText(String.valueOf(savings) + " SEK");
        return savings;
    }

    public int spendingsOfUser(){
        user_profile_spendings = (TextView)findViewById(R.id.profile_spending);
        int expense = databaseHelper.calcExpenses();
        int wishes = databaseHelper.calcWish();
        int spendings = (expense + wishes);
        user_profile_spendings.setText(String.valueOf(spendings) + " SEK");
        return spendings;
    }

    private void activateOnExitProfileActiviy() {
        btn_exitProfileActivity = (Button)findViewById(R.id.btn_cancel_profile_user);
        btn_exitProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExitActivity = new Intent (ProfileActivity.this, MainActivity.class);
                startActivity(intentExitActivity);
            }
        });

    }

}
