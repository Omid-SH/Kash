package com.example.nobintest.AppPages.About;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nobintest.R;

public class AboutActivity extends AppCompatActivity {

    private ImageView logoSplash;
    private Animation anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        logoSplash = findViewById(R.id.ivLogoSplash);
        anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_slow);

        logoSplash.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logoSplash.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                logoSplash.startAnimation(anim);
            }
        });
    }
}
