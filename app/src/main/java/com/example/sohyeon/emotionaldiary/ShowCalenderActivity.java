package com.example.sohyeon.emotionaldiary;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.ECLAIR & Build.VERSION_CODES.JELLY_BEAN_MR2)

public class ShowCalenderActivity extends AppCompatActivity {

    CalendarView calendar;
    Activity act = this;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcalender);

        back = (Button) findViewById(R.id.button90);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act.finish();
                //imageView = (ImageView) findViewById(R.id.image_view);
            }
        });

        final CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String sYear = Integer.toString(year);
                String sMonth = Integer.toString(month + 1);  if(sMonth.length() == 1) sMonth = "0" + sMonth;
                String sDay = Integer.toString(dayOfMonth);  if(sDay.length() == 1) sDay = "0" + sDay;
                String date = sYear + sMonth + sDay;
                Intent intent = new Intent(act, CalenderActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "" + year + "/" +
//                        (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
