
package com.example.nobintest.AppPages.SignInUp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nobintest.R;


public class RegistrationActivity extends AppCompatActivity {

    private ImageView logo, joinus;
    private AutoCompleteTextView username, email, password;
    private Button signup;
    private TextView signin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeGUI();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String inputName = username.getText().toString().trim();
                final String inputPw = password.getText().toString().trim();
                final String inputEmail = email.getText().toString().trim();

                if (validateInput(inputName, inputPw, inputEmail))
                    registerUser(inputName, inputPw, inputEmail);

            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }


    private void initializeGUI() {

        logo = findViewById(R.id.ivRegLogo);
        joinus = findViewById(R.id.ivJoinUs);
        username = findViewById(R.id.atvUsernameReg);
        email = findViewById(R.id.atvEmailReg);
        password = findViewById(R.id.atvPasswordReg);
        signin = findViewById(R.id.tvSignIn);
        signup = findViewById(R.id.btnSignUp);
        progressDialog = new ProgressDialog(this);

    }

    private void registerUser(final String inputName, final String inputPw, String inputEmail) {

        Toast.makeText(RegistrationActivity.this, getString(R.string.register_not_available), Toast.LENGTH_LONG).show();

    }


    private void sendUserData(String username, String password) {

    }

    private boolean validateInput(String inName, String inPw, String inEmail) {

        if (inName.isEmpty()) {
            username.setError("Username is empty.");
            return false;
        }
        if (inPw.isEmpty()) {
            password.setError("Password is empty.");
            return false;
        }
        if (inEmail.isEmpty()) {
            email.setError("Email is empty.");
            return false;
        }

        return true;
    }


}
