package com.example.nobintest.AppPages.SignInUp;

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


public class PwResetActivity extends AppCompatActivity {

    private ImageView ivLogo, ivPWreset;
    private TextView tvInfo, tvSignin;
    private AutoCompleteTextView atvEmail;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwreset);
        initializeGUI();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = atvEmail.getText().toString();

                if (email.isEmpty()) {
                    atvEmail.setError("Please, fill the email field.", null);
                } else {
                    Toast.makeText(PwResetActivity.this, getString(R.string.password_recovery_not_available),
                        Toast.LENGTH_LONG).show();
                }

            }
        });


        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(PwResetActivity.this, LoginActivity.class));
            }
        });


    }


    private void initializeGUI() {

        ivLogo = findViewById(R.id.ivLogLogo);
        ivPWreset = findViewById(R.id.ivPassReset);
        tvInfo = findViewById(R.id.tvPWinfo);
        tvSignin = findViewById(R.id.tvGoBack);
        atvEmail = findViewById(R.id.atvEmailRes);
        btnReset = findViewById(R.id.btnReset);

    }
}
