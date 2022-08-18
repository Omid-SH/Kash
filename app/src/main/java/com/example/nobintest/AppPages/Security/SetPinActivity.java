package com.example.nobintest.AppPages.Security;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.nobintest.AppPages.AppActivity;
import com.example.nobintest.R;

public class SetPinActivity extends AppCompatActivity {

    private TextView header;
    private ImageView done;
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private String userPin1 = null;
    private String userPin2 = null;
    private boolean isCheckAgain;
    private boolean pinConfirmed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);
        isCheckAgain = false;
        pinConfirmed = false;
        setPinLockView();
    }

    private void setPinLockView() {
        header = findViewById(R.id.set_pin_text);
        done = findViewById(R.id.set_pin_done);
        done.setOnClickListener(mDoneOnClickListener);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        mPinLockView.setPinLength(10);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.roman_silver));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }

    private ImageView.OnClickListener mDoneOnClickListener = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isCheckAgain) {
                if (userPin1.equals(userPin2)) {
                    pinConfirmed = true;
                    setUserPinCode();
                    finish();
                } else {
                    mPinLockView.resetPinLockView();
                }
            } else {
                done.setVisibility(View.GONE);
                header.setText(getString(R.string.set_pin_header_confirm));
                mPinLockView.resetPinLockView();
                isCheckAgain = true;
            }
        }
    };

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            header.setText(getString(R.string.set_pin_reach_max_length));
        }

        @Override
        public void onEmpty() {
            if (isCheckAgain) {
                header.setText(getString(R.string.set_pin_header_confirm));
                done.setImageDrawable(getDrawable(R.drawable.ic_done_all));
            } else {
                header.setText(getString(R.string.set_pin_header));
                done.setImageDrawable(getDrawable(R.drawable.ic_done));
            }
            done.setVisibility(View.GONE);
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            if (isCheckAgain) {
                userPin2 = intermediatePin;
                header.setText(getString(R.string.set_pin_header_confirm));
                done.setImageDrawable(getDrawable(R.drawable.ic_done_all));
            } else {
                userPin1 = intermediatePin;
                header.setText(getString(R.string.set_pin_header));
                done.setImageDrawable(getDrawable(R.drawable.ic_done));
            }
            if (pinLength > 0) {
                done.setVisibility(View.VISIBLE);
            } else {
                done.setVisibility(View.GONE);
            }
        }
    };

    private void setUserPinCode() {
        SharedPreferences sharedPreferences = getApplication().
            getSharedPreferences(getString(R.string.user_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.preference_user_pin_key), userPin1);
        editor.apply();
    }

    @Override
    protected void onStop() {
        if (!pinConfirmed) {
            setUserSecurityState(getString(R.string.security_state_none));
        }
        super.onStop();
    }

    private void setUserSecurityState(String state) {
        SharedPreferences sharedPreferences = getApplication().
            getSharedPreferences(getString(R.string.user_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.preference_security_key), state);
        editor.apply();
    }
}
