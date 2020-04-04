package com.example.sohyeon.emotionaldiary;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {
    Activity act = this;
    Button b9, bPrev, bNext;
    ImageView imageView;
    String[] filenames;
    int fileindex = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        b9 = (Button) findViewById(R.id.button9);
        b9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act.finish();
            }
        });//뒤로가기 버튼 클릭하면 activity_main으로 이동


        bPrev = (Button) findViewById(R.id.button_prev);
        bPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(fileindex > 0) {
                    fileindex -= 1;
                    DisplayImage(filenames[fileindex]);
                } else {
                    // Toast
                }
            }
        });//이전 사진 보여주기

        bNext = (Button) findViewById(R.id.button_next);
        bNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(fileindex < filenames.length - 1) {
                    fileindex += 1;
                    DisplayImage(filenames[fileindex]);
                } else {
                    // Toast
                }
            }
        });//다음 사진 보여주기

        imageView = (ImageView) findViewById(R.id.image_view);

        String filename = "20180830.txt";

        String ret = "";

        InputStream inputStream = null;
        try {
            inputStream = openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int size = inputStream.available();
                char[] buffer = new char[size];

                inputStreamReader.read(buffer);

                inputStream.close();
                ret = new String(buffer);
                Log.d("TEST", "CONTENTS=" + ret);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filenames = ret.split("\n");
        if(filenames.length > 0) {
            DisplayImage(filenames[0]);
        }
    }

    private void DisplayImage(String filename) {
        imageView.setImageURI(Uri.parse(filename));
    }
}