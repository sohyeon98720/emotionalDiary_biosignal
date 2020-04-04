package com.example.sohyeon.emotionaldiary;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class NewSubActivity extends AppCompatActivity {
    Activity act = this;
    Button b3, choiceDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsub);

        b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act.finish();
            }
        });//뒤로가기 버튼 클릭하면 activity_main으로 이동

        choiceDialog = (Button) findViewById(R.id.button4);

        choiceDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"날짜형", "감성형"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(NewSubActivity.this);
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
    }
}