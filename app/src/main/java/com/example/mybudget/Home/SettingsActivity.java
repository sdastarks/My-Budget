package com.example.mybudget.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.R;

public class SettingsActivity extends AvatarChangeActivity {

    private Button btn_save;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_save=findViewById(R.id.btn_save_settings);
        btn_exit=findViewById(R.id.btn_exit_settings);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
