package com.example.nobintest.AppPages.Security;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.nobintest.R;

public class SecurityActivity extends AppCompatActivity {

    String userSecurityState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        getUserSecuritySate();
        setRadioGroupSetting();
    }


    private void getUserSecuritySate() {
        SharedPreferences sharedPreferences = getApplication().
            getSharedPreferences(getString(R.string.user_file_key), Context.MODE_PRIVATE);

        userSecurityState = sharedPreferences.getString(getString(R.string.preference_security_key),
            getString(R.string.security_state_none));
    }

    private void setRadioGroupSetting() {
        RadioGroup radioGroup = findViewById(R.id.security_radio_group);
        if (userSecurityState.equals(getString(R.string.security_state_pin))) {
            radioGroup.check(R.id.radio_button_pin);
        } else if (userSecurityState.equals(getString(R.string.security_state_fingerprint))) {
            radioGroup.check(R.id.radio_button_fingerprint);
        } else {
            radioGroup.check(R.id.radio_button_none);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_none:
                        finish();
                        setUserSecurityState(getString(R.string.security_state_none));
                        break;
                    case R.id.radio_button_pin:
                        setUserSecurityState(getString(R.string.security_state_pin));
                        startActivity(new Intent(SecurityActivity.this, SetPinActivity.class));
                        break;
                    case R.id.radio_button_fingerprint:
                        handelFingerPrint();
                }
            }
        });
    }

    private void setUserSecurityState(String state) {
        SharedPreferences sharedPreferences = getApplication().
            getSharedPreferences(getString(R.string.user_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.preference_security_key), state);
        editor.apply();
    }

    private void handelFingerPrint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(this, "Fingerprint Scanner not detected in Device", Toast.LENGTH_LONG).show();
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC)
                != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission not granted to use Fingerprint Scanner", Toast.LENGTH_LONG).show();
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(this, "Add Lock to your Phone in Settings", Toast.LENGTH_LONG).show();
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "Add at least one Fingerprint to your Phone in Settings", Toast.LENGTH_LONG).show();
            } else {
                setUserSecurityState(getString(R.string.security_state_fingerprint));
                startActivity(new Intent(SecurityActivity.this, SetPinActivity.class));
            }
        } else {
            Toast.makeText(this, "Low API Level for this Feature", Toast.LENGTH_LONG).show();
        }
    }
}
