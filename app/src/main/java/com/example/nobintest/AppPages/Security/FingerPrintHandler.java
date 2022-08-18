package com.example.nobintest.AppPages.Security;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.nobintest.R;
import com.mattprecious.swirl.SwirlView;

import org.w3c.dom.Text;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerPrintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        this.update("There was an Auth Error" + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        this.update("Auth Failed", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        this.update("Error: " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        this.update("You can now access", true);
    }

    private void update(String s, boolean b) {
        TextView autFooter = ((Activity) context).findViewById(R.id.aut_footer);
        SwirlView swirlView = ((Activity) context).findViewById(R.id.swirl_view);

        autFooter.setText(s);
        if (b == false) {
            swirlView.setState(SwirlView.State.ERROR, true);
        }
    }
}
