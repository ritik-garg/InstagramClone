package com.ritik.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnSignUpLogin, btnLogInLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("LogIn");

        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);
        btnLogInLogin = findViewById(R.id.btnLogInLogin);

        edtPasswordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnLogInLogin);
                }
                return false;
            }
        });

        btnSignUpLogin.setOnClickListener(this);
        btnLogInLogin.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null) {
//            ParseUser.logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUpLogin:
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnLogInLogin:
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Logging in...");
                progressDialog.show();

                ParseUser.logInInBackground(edtEmailLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null && e == null) {
                            FancyToast.makeText(Login.this, user.get("username") + " is Logged In Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            transitionToSocialMediaActivity();
                        }
                        else {
                            FancyToast.makeText(Login.this,  e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
                break;
        }
    }

    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(Login.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
