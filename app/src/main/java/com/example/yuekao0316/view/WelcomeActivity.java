package com.example.yuekao0316.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yuekao0316.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    private TextView showtime;
    private int index = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                index--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showtime.setText(index+"");
                    }
                });
                if (index==0){
                    timer.cancel();
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        },0,1000);
    }

    private void initView() {
        showtime = findViewById(R.id.showtime);
    }
}
