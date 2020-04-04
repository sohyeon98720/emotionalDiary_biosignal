package com.example.sohyeon.emotionaldiary;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Emotion_Happy extends AppCompatActivity{
    Activity act = this;
    String[] filenames;
    int fileindex = 0;
    ImageView imageView;
    Button  bPrev, bNext,back;
    TextView countimage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_emotion_happy);
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
        String filename = "Happy.txt";

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
                Log.d("TEST", "Contents=" + ret);
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