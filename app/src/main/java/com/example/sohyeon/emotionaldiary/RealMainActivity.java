package com.example.sohyeon.emotionaldiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class RealMainActivity extends AppCompatActivity {
    ImageView imageView1;
//    TextView title;
    Activity act = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
/*        title = (TextView)findViewById(R.id.title);
        title.setText("Emotion Diary");*/


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            //Do Something
            @Override
            public void run() {
//                 TODO Auto-generated method stub
                Intent i = new Intent(act, HomeActivity.class); // xxx가 현재 activity,
                //yyy가 이동할 activity
                startActivity(i);
                finish();
            }
        }, 3000); // 1000ms
    }
}
