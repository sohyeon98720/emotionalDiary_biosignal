package com.example.sohyeon.emotionaldiary;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Activity act = this;
    Button b1, choiceDialog, b3;
    ImageView imageView;
    String imagename;
    String textname;
    public static String emotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });//사진찍기 버튼 클릭하면 카메라 실행

        choiceDialog = (Button) findViewById(R.id.button2);

        choiceDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"날짜형", "감성형"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("사진 분류를 선택해 주세요").setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex[0] = which;
                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[selectedIndex[0]].equals("감성형")) {
                            Intent intent = new Intent(act, EmotionActivity.class);
                            startActivity(intent);
                        } else if (items[selectedIndex[0]].equals("날짜형")) {
                            Intent intent = new Intent(act, ShowCalenderActivity.class);
                            startActivity(intent);
                        }
                        //Toast.makeText(NewSubActivity.this, items[selectedIndex[0]], Toast.LENGTH_SHORT).show();

                    }
                }).create().show();
            }
        });

        imageView = (ImageView) findViewById(R.id.image_view);

        b3 = (Button) findViewById(R.id.button00);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act,HomeActivity.class);
                startActivity(intent);
            }
        });//뒤로가기버튼
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(resultCode == RESULT_OK) {
            Log.d("TEST", "ImageURI : " + data.getData());
            imageView.setImageURI(data.getData());
            Toast.makeText(MainActivity.this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
            String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
            imagename = timeStamp + ".txt";
            Log.d("TEST", "imagename=" + imagename);

            try {
                FileOutputStream os = openFileOutput(imagename, MODE_APPEND);
                os.write(data.getData().toString().getBytes());
                os.write(new String("\n").getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            emotion = HomeActivity.emotion;
            Log.d("TEST", "isget : " + emotion);
            imagename = emotion + ".txt";
            Log.d("TEST", "emotion=" + imagename);
            try {
                FileOutputStream os = openFileOutput(imagename, MODE_APPEND);
                os.write(data.getData().toString().getBytes());
                os.write(new String("\n").getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
