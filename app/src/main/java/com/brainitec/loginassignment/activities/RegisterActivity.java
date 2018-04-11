package com.brainitec.loginassignment.activities;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brainitec.loginassignment.R;
import com.brainitec.loginassignment.model.User;
import com.brainitec.loginassignment.sql.DatabaseHelper;
import com.brainitec.loginassignment.utils.InputValidation;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;
    private EditText edtUserName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfermPassword;
    private Button btnSignUp;
    private TextView tvRegisterNewUser;
    private TextView tvAlreadyUser;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please wait!");
        edtUserName = findViewById(R.id.editText_signup_userName);
        edtEmail = findViewById(R.id.editText_signup_email);
        edtPassword = findViewById(R.id.editText_signup_passowrd1);
        edtConfermPassword = findViewById(R.id.editText_signup_passowrd2);
        btnSignUp = findViewById(R.id.btn_signUp);
        tvAlreadyUser = findViewById(R.id.tv_alreadyUser);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btnSignUp.setOnClickListener(this);
        tvAlreadyUser.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_signUp:
                postDataToSQLite();
                break;

            case R.id.tv_alreadyUser:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(edtUserName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(edtEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(edtEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(edtPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(edtPassword, edtConfermPassword,
                getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(edtEmail.getText().toString().trim())) {

            user.setName(edtUserName.getText().toString().trim());
            user.setEmail(edtEmail.getText().toString().trim());
            user.setPassword(edtPassword.getText().toString().trim());

            databaseHelper.addUser(user);
            progressDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    progressDialog.dismiss();
                    //Toast msg  to show success message that record saved successfully
                    Toast.makeText(RegisterActivity.this, getString(R.string.success_message), Toast.LENGTH_LONG).show();
                    finish();

                }
            }, 2000);


        } else {
            // Toast msg to show error message that record already exists
            Toast.makeText(this, getString(R.string.error_email_exists), Toast.LENGTH_LONG).show();

        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        edtUserName.setText(null);
        edtEmail.setText(null);
        edtPassword.setText(null);
        edtConfermPassword.setText(null);
    }
}
