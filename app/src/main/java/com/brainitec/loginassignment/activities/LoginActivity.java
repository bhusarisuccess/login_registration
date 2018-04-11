package com.brainitec.loginassignment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brainitec.loginassignment.R;
import com.brainitec.loginassignment.sql.DatabaseHelper;
import com.brainitec.loginassignment.utils.InputValidation;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;


    private EditText edtEmail;
    private EditText editPassword;
    private Button btnLogin;
    private TextView tvRegisterNewUser;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait!");

        edtEmail = findViewById(R.id.editText_login_userName);
        editPassword = findViewById(R.id.editText_login_passowrd);
        btnLogin = findViewById(R.id.btn_login);
        tvRegisterNewUser = findViewById(R.id.tv_newUser);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btnLogin.setOnClickListener(this);
        tvRegisterNewUser.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                verifyFromSQLite();
                break;
            case R.id.tv_newUser:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(edtEmail, getString(R.string.error_message_userName_email))) {
            return;
        }
        /*if (!inputValidation.isInputEditTextEmail(edtEmail, getString(R.string.error_message_email))) {
                return;
        }*/
        if (!inputValidation.isInputEditTextFilled(editPassword, getString(R.string.error_message_password))) {
            return;
        }

        if (databaseHelper.checkUser(edtEmail.getText().toString().trim()
                , editPassword.getText().toString().trim())) {


            progressDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent accountsIntent = new Intent(activity, HomeActivity.class);
                    accountsIntent.putExtra("EMAIL", edtEmail.getText().toString().trim());
                    emptyInputEditText();
                    startActivity(accountsIntent);
                    progressDialog.dismiss();
                    finish();
                }
            }, 2000);

        } else {
            // Toast to show success message that record is wrong

            Toast.makeText(this, getString(R.string.error_valid_email_password), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        edtEmail.setText(null);
        editPassword.setText(null);
    }
}