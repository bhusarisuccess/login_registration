package com.brainitec.loginassignment.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.brainitec.loginassignment.R;

public class HomeActivity extends AppCompatActivity {
    private TextView tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String user = getIntent().getStringExtra("EMAIL");
        tvUser = findViewById(R.id.tvUser);
        tvUser.setText(user);

    }
}
