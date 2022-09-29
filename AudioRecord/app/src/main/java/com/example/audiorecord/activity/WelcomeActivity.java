package com.example.audiorecord.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.audiorecord.R;
import com.example.audiorecord.constants.Constants;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getPermission();
    }

    private void getPermission() {
        //获取到权限后执行跳转
        goMainActivity();
    }

    private void goMainActivity() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, Constants.DELAY_TIME);
    }
}
