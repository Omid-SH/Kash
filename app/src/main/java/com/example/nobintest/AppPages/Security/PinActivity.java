package com.example.nobintest.AppPages.Security;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.nobintest.AppPages.AppActivity;
import com.example.nobintest.AppPages.SignInUp.SplashScrActivity;
import com.example.nobintest.R;


public class PinActivity extends AppCompatActivity {

    private TextView help;
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private String userPin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        getUserPin();
        setPinLockView();
    }

    private void setPinLockView() {
        help = findViewById(R.id.pin_help);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        mPinLockView.setPinLength(userPin.length());
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.roman_silver));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }

    private void getUserPin() {
        SharedPreferences sharedPreferences = getApplication().
            getSharedPreferences(getString(R.string.user_file_key), Context.MODE_PRIVATE);

        userPin = sharedPreferences.getString(getString(R.string.preference_user_pin_key),
            getString(R.string.default_pin));
    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (pin.equals(userPin)) {
                finish();
                startActivity(new Intent(PinActivity.this, AppActivity.class));
            } else {
                mPinLockView.resetPinLockView();
                help.setText(getString(R.string.wrong_pin));
            }
        }

        @Override
        public void onEmpty() {
            Log.d("TAG", "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d("TAG", "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };
}
