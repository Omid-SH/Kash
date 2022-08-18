package com.example.nobintest.AppPages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.nobintest.AppPages.SignInUp.LoginActivity;
import com.example.nobintest.AppPages.SignInUp.SplashScrActivity;
import com.example.nobintest.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

import org.jetbrains.annotations.Nullable;

public class AppIntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance(
            "Welcome...",
            "You can get latest crypto prices of global market and Nobitex trading market",
            R.drawable.app_intro_1
        ));
        addSlide(AppIntroFragment.newInstance(
            "Tools",
            "By using charts and tools inside the app you can monitor prices better than ever",
            R.drawable.app_intro_2
        ));
        addSlide(AppIntroFragment.newInstance(
            "Trade",
            "You can not only monitor prices, but also trade and manage your crypto wallet",
            R.drawable.app_intro_3
        ));
        addSlide(AppIntroFragment.newInstance(
            "... KaSh",
            "Sign in now to get the most app features ...",
            R.drawable.logo
        ));
    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        startActivity(new Intent(AppIntroActivity.this, LoginActivity.class));
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        startActivity(new Intent(AppIntroActivity.this, LoginActivity.class));
    }
}
