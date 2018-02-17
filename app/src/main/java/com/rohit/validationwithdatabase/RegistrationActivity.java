package com.rohit.validationwithdatabase;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegistrationActivity.this;

    private ScrollView scrollView;
    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextLastName;
    private TextInputEditText textInputEditTextDateOfBirth;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextGender;

    private Button appCompatButtonRegister;
    private TextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private RegistrationDatabaseHelper registrationDatabaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        scrollView = (ScrollView) findViewById(R.id.register_scroll_view);
        textInputEditTextFirstName = (TextInputEditText) findViewById(R.id.textInputEditTextFirstName);
        textInputEditTextLastName = (TextInputEditText) findViewById(R.id.textInputEditTextLastName);
        textInputEditTextDateOfBirth = (TextInputEditText) findViewById(R.id.textInputEditTextDateOfBirth);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        textInputEditTextGender = (TextInputEditText)findViewById(R.id.textInputEditTextGender);

        appCompatButtonRegister = (Button) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (TextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        registrationDatabaseHelper = new RegistrationDatabaseHelper(activity);
        user = new User();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }

    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextFirstName, getString(R.string.error_message_first_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextLastName, getString(R.string.error_message_last_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextDateOfBirth,  getString(R.string.error_message_date_of_birth))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(textInputEditTextGender,  getString(R.string.error_message_date_of_birth))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                 getString(R.string.error_password_match))) {
            return;
        }

        if (!registrationDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setFirstname(textInputEditTextFirstName.getText().toString().trim());
            user.setLastname(textInputEditTextLastName.getText().toString().trim());
            user.setDateofbirth(textInputEditTextDateOfBirth.getText().toString().trim());
            user.setGender(textInputEditTextGender.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            registrationDatabaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextFirstName.setText(null);
        textInputEditTextLastName.setText(null);
        textInputEditTextDateOfBirth.setText(null);
        textInputEditTextGender.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
