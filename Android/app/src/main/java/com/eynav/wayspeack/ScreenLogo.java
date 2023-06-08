package com.eynav.wayspeack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenLogo extends AppCompatActivity {

    ImageView app_logo;
    Animation top,bottom;

    TextView tvNameLogo;
    View bottomView1, bottomView2, bottomView3;
    View topView1, topView2, topView3;
    //slogen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_logo);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        app_logo = findViewById(R.id.app_logo);
        tvNameLogo = findViewById(R.id.tvNameLogo);
        bottomView1 = findViewById(R.id.bottomView1);
        bottomView2 = findViewById(R.id.bottomView2);
        bottomView3 = findViewById(R.id.bottomView3);
        topView1 = findViewById(R.id.topView1);
        topView2 = findViewById(R.id.topView2);
        topView3 = findViewById(R.id.topView3);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_IMMERSIVE);



//        top = AnimationUtils.loadAnimation(this,R.anim.top);
        Animation logo_animation = AnimationUtils.loadAnimation(this,R.anim.zoom_animation);
        Animation name_animation = AnimationUtils.loadAnimation(this,R.anim.zoom_animation);
        Animation top_view1_animation= AnimationUtils.loadAnimation(this,R.anim.top_view);
        Animation top_view2_animation= AnimationUtils.loadAnimation(this,R.anim.top_view);
        Animation top_view3_animation= AnimationUtils.loadAnimation(this,R.anim.top_view);

        Animation bottom_view1_animation= AnimationUtils.loadAnimation(this,R.anim.bottom_view);
        Animation bottom_view2_animation= AnimationUtils.loadAnimation(this,R.anim.bottom_view);
        Animation bottom_view3_animation= AnimationUtils.loadAnimation(this,R.anim.bottom_view);
        topView1.startAnimation(top_view1_animation);
        bottomView1.startAnimation(bottom_view1_animation);
//        app_logo.setAnimation(top);

        top_view1_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("1");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("2");

                topView2.setVisibility(View.VISIBLE);
                bottomView2.setVisibility(View.VISIBLE);
                topView2.startAnimation(top_view2_animation);
                bottomView2.startAnimation(bottom_view2_animation);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                System.out.println("3");

            }
        });

        top_view2_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                topView3.setVisibility(View.VISIBLE);
                bottomView3.setVisibility(View.VISIBLE);
                topView3.startAnimation(top_view3_animation);
                bottomView3.startAnimation(bottom_view3_animation);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        top_view3_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                app_logo.setVisibility(View.VISIBLE);
                app_logo.startAnimation(logo_animation);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvNameLogo.setVisibility(View.VISIBLE);
                tvNameLogo.startAnimation(name_animation);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        name_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(ScreenLogo.this, MainActivity2.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        int SPLASH_SCREEN = 3000;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(ScreenLogo.this, MainActivity2.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_SCREEN);
    }
}