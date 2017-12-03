package com.dev.noname.lover.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.dev.noname.lover.R;
import com.dev.noname.lover.data.DbHelper;

import java.util.Calendar;

public class NewDateActivity extends AppCompatActivity {
    private DbHelper helper;
    private TextInputLayout edtTitle;
    private TextInputLayout edtDate;
    private TextInputLayout edtAddress;
    private TextInputLayout edtMess;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_date);
        helper=new DbHelper(this);
        edtAddress=findViewById(R.id.edt_dating_address);
        edtTitle=findViewById(R.id.edt_dating_title);
        edtMess=findViewById(R.id.edt_dating_mess);
        edtDate=findViewById(R.id.edt_dating_date);
        btnSave=findViewById(R.id.btn_save);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getEditText().getText().toString();
                String date = edtDate.getEditText().getText().toString();
                String mess = edtMess.getEditText().getText().toString();
                String add = edtAddress.getEditText().getText().toString();
                String id=title+date;
                helper.createDating(title,date,mess,id,add);
            }
        });
    }
    private void pickTime(){
        DatePickerDialog datePickerDialog;
        final TimePickerDialog timePickerDialog;
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        int mHour= c.get(Calendar.HOUR);
        int mMin = c.get(Calendar.MINUTE);
      //  final SharedPreferences pref = activity.getSharedPreferences("Lover", 0); // 0 - for private mode
   //     final SharedPreferences.Editor editor = pref.edit();
        // date picker dialog
        timePickerDialog = new TimePickerDialog(this, 2,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
               // Toast.makeText(activity,i+" "+i1, Toast.LENGTH_SHORT).show();


//              //  Intent intent = new Intent(this, OnAlarmReceiver.class);
//                PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent,
//                        PendingIntent.FLAG_ONE_SHOT);
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 10);
//                AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

            }
        },mHour,mMin,true);
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text

//                        Toast.makeText(activity,dayOfMonth + "/"
//                                + (monthOfYear + 1) + "/" + year, Toast.LENGTH_SHORT).show();
//                        timePickerDialog.show();
//                        editor.putInt("YEAR",year);
//                        editor.putInt("MONTH",monthOfYear);
//                        editor.putInt("DAY",dayOfMonth);

                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }
}
