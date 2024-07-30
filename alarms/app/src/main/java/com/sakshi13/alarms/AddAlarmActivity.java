package com.sakshi13.alarms;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddAlarmActivity extends AppCompatActivity {
    private int year, month, day, hour, minute;
    private EditText noteEditText;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        Button setDateButton = findViewById(R.id.button_set_date);
        Button setTimeButton = findViewById(R.id.button_set_time);
        Button addAlarmButton = findViewById(R.id.button_add_alarm);
        noteEditText = findViewById(R.id.edit_text_note);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        setDateButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddAlarmActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        this.year = year;
                        this.month = monthOfYear;
                        this.day = dayOfMonth;
                    }, year, month, day);
            datePickerDialog.show();
        });

        setTimeButton.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddAlarmActivity.this,
                    (view, hourOfDay, minuteOfHour) -> {
                        this.hour = hourOfDay;
                        this.minute = minuteOfHour;
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        addAlarmButton.setOnClickListener(v -> {
            String note = noteEditText.getText().toString();
            Alarm newAlarm = new Alarm(hour, minute, day, month, year, note);

            setAlarm(newAlarm);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("newAlarm", newAlarm);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void setAlarm(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(alarm.getYear(), alarm.getMonth(), alarm.getDay(), alarm.getHour(), alarm.getMinute(), 0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("note", alarm.getNote());
        int notificationId = (int) System.currentTimeMillis();
        intent.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Alarm set for " + alarm.getTime() + " on " + alarm.getDate(), Toast.LENGTH_SHORT).show();
    }
}
