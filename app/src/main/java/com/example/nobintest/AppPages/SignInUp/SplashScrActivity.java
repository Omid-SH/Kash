package com.example.nobintest.AppPages.SignInUp;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nobintest.AppPages.AppActivity;
import com.example.nobintest.AppPages.AppIntroActivity;
import com.example.nobintest.AppPages.Security.FingerPrintActivity;
import com.example.nobintest.AppPages.Security.PinActivity;
import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.R;

import java.lang.ref.WeakReference;

public class SplashScrActivity extends AppCompatActivity {

    private ImageView logoSplash, text;
    private Animation anim1, anim2, anim3;
    private String userState;
    private String userSecurityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_scr);

        init();

        logoSplash.startAnimation(anim1);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logoSplash.startAnimation(anim2);
                anim2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (userState.equals(AppDataManager.USER_STATE_NEW)) {
                            startNextActivity(true);
                        } else if (userState.equals(AppDataManager.USER_STATE_GUEST)) {
                            startNextActivity(false);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                text.startAnimation(anim3);
                anim3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        checkUserState();
        checkUserSecurityState();
    }

    private void checkUserState() {
        AppDataManager appDataManager = AppDataManager.getInstance(getApplication());
        String string = appDataManager.getProfileState();

        if (string.equals(AppDataManager.USER_STATE_NEW)) {
            userState = AppDataManager.USER_STATE_NEW;
            appDataManager.setProfileState(AppDataManager.USER_STATE_GUEST);
            appDataManager.setUserName("Guest");
        } else if (string.equals(AppDataManager.USER_STATE_GUEST)) {
            userState = AppDataManager.USER_STATE_GUEST;
        } else {
            userState = AppDataManager.USER_STATE_SIGNED_IN;

            DataManager dataManager = DataManager.getInstance();
            DataManager.SignInHandler signInHandler = DataManager.getSignInHandlerInstance();
            signInHandler.setSplashScrActivityWeakReference
                (new WeakReference<SplashScrActivity>(SplashScrActivity.this));

            if (!dataManager.isNetworkConnected(getApplication())) {
                Toast.makeText(this, "NO NETWORK CONNECTION!", Toast.LENGTH_SHORT).show();
            } else {
                String username = appDataManager.getUserName();
                String password = appDataManager.getPassWord();
                dataManager.getUserToken(username, password);
            }
        }
    }

    private void checkUserSecurityState() {
        AppDataManager appDataManager = AppDataManager.getInstance(getApplication());
        userSecurityState = appDataManager.getSecurityState();
    }

    private void init() {

        logoSplash = findViewById(R.id.ivLogoSplash);
        text = findViewById(R.id.logo_under_text);

        anim1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        anim2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout);
        anim3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadein);
    }

    public void startNextActivity(boolean isIntro) {
        logoSplash.setVisibility(View.GONE);
        finish();
        if (isIntro) {
            startActivity(new Intent(SplashScrActivity.this, AppIntroActivity.class));
        } else {
            if (userSecurityState.equals(AppDataManager.SECURITY_STATE_FINGERPRINT)) {
                startActivity(new Intent(SplashScrActivity.this, FingerPrintActivity.class));
            } else if (userSecurityState.equals(AppDataManager.SECURITY_STATE_PIN)) {
                startActivity(new Intent(SplashScrActivity.this, PinActivity.class));
            } else {
                startActivity(new Intent(SplashScrActivity.this, AppActivity.class));
            }
        }
    }

}
