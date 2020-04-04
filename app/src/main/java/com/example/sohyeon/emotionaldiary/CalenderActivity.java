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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CalenderActivity extends AppCompatActivity {
    Activity act = this;
    Button back, bPrev, bNext;
    ImageView imageView;
    TextView txt_date;
    TextView countimage;
    String[] filenames;
    int fileindex = 0;
    Activity act_next = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendersub);

        Intent intent = getIntent();
        String today_date = intent.getStringExtra("date");
        txt_date = (TextView)findViewById(R.id.txt_date);
        txt_date.setText("Date : "+today_date);
        countimage = (TextView) findViewById(R.id.countimage);


        back = (Button) findViewById(R.id.button90);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act.finish();
                //imageView = (ImageView) findViewById(R.id.image_view);
            }
        });


        bPrev = (Button) findViewById(R.id.button_prev);
        bPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(fileindex > 0) {
                    fileindex -= 1;
                    DisplayImage(filenames[fileindex]);
                    countimage.setText((fileindex + 1) + "/" + filenames.length);
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
                    countimage.setText((fileindex + 1) + "/" + filenames.length);
                } else {
                    // Toast
                }
            }
        });//다음 사진 보여주기

        imageView = (ImageView) findViewById(R.id.image_view);
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String filename = today_date+".txt";
        Log.d("TEST", "filename : "+filename);

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

            if(!ret.equals("")) {
                filenames = ret.split("\n");
                List<String> Listfilenames = Arrays.asList(filenames);
                Collections.reverse(Listfilenames);

                countimage.setText((fileindex + 1) + "/" + filenames.length);

                DisplayImage(filenames[0]);
            }
        }

    private void DisplayImage(String filename) {
        imageView.setImageURI(Uri.parse(filename));
    }
    protected  void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "종료!", Toast.LENGTH_LONG).show();
    }
}