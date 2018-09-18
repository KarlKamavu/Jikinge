package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roonit.jikinge.R;
import com.roonit.jikinge.utils.BasicActivity;

public class SplashActivity extends BasicActivity {
    LottieAnimationView animationView;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animationView=(LottieAnimationView)findViewById(R.id.animation_view);
        user= FirebaseAuth.getInstance().getCurrentUser();

       // animator.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user!=null){
            GotoMainActivity();
        }
    }

    public void GotoMainActivity(){

        Intent settingIntent=new Intent(SplashActivity.this,MainActivity.class);
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingIntent);
        finish();

    }

    public void getStarted(View view) {
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }
}
