package com.example.nobintest.AppPages.Security;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.nobintest.AppPages.AppActivity;
import com.example.nobintest.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mattprecious.swirl.SwirlView;

public class FingerPrintActivity extends AppCompatActivity {

    private SwirlView swirlView;
    private TextView autFooter, backUpPin;
    private FingerprintManager fingerprintManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        backUpPin = findViewById(R.id.aut_help_backup_pin);
        autFooter = findViewById(R.id.aut_footer);
        swirlView = findViewById(R.id.swirl_view);
        swirlView.setState(SwirlView.State.ON, true);

        backUpPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(FingerPrintActivity.this, PinActivity.class));
            }
        });

        if (checkState()) {
            fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            Dexter.withActivity(this).withPermission(Manifest.permission.USE_BIOMETRIC).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    setStatus("Waiting for authentication...", true);
                    auth();
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    setStatus("Need permission", false);
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }
    }

    private boolean checkState() {
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
                return true;
            }
        } else {
            Toast.makeText(this, "Low API Level for this Feature", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void auth() {
        if (fingerprintManager.isHardwareDetected()) {
            if (fingerprintManager.hasEnrolledFingerprints()) {
                fingerprintManager.authenticate(null, null, 0, new FingerprintManager.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        setStatus(errString.toString(), false);
                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                        super.onAuthenticationHelp(helpCode, helpString);
                        setStatus(helpString.toString(), false);
                    }

                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        setStatus("Success !", true);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        setStatus("not recognized", false);
                    }
                }, null);
            } else {
                setStatus("No fingerprint saved", false);
            }
        } else {
            setStatus("No fingerprint reader detected", false);
        }
    }

    private void setStatus(final String message, boolean State) {
        if (State == false) {
            swirlView.setState(SwirlView.State.ERROR, true);
        } else {
            swirlView.setState(SwirlView.State.ON, true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                autFooter.setText(message);
            }
        });

        if (message.equals("Success !")) {
            finish();
            startActivity(new Intent(FingerPrintActivity.this, AppActivity.class));
        }
    }

}
