package com.sakshi13.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout alarmsContainer;
    private FloatingActionButton addAlarmButton;
    private List<Alarm> alarms = new ArrayList<>();
    private AlarmManager alarmManager;
    private TextView currentTime;
    private TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmsContainer = findViewById(R.id.alarms_container);
        addAlarmButton = findViewById(R.id.add_alarm_button);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        currentTime = findViewById(R.id.current_time);
        currentDate = findViewById(R.id.current_date);

        updateTimeAndDate();

        addAlarmButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAlarmActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void updateTimeAndDate() {
        Calendar calendar = Calendar.getInstance();
        String time = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        String date = String.format("%02d/%02d/%04d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        currentTime.setText(time);
        currentDate.setText(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Alarm newAlarm = (Alarm) data.getSerializableExtra("newAlarm");
            alarms.add(newAlarm);
            addAlarmToList(newAlarm);
        }
    }

    private void addAlarmToList(Alarm alarm) {
        View alarmView = getLayoutInflater().inflate(R.layout.alarm_item, null);

        TextView alarmTime = alarmView.findViewById(R.id.alarm_time);
        TextView alarmDate = alarmView.findViewById(R.id.alarm_date);
        TextView alarmNote = alarmView.findViewById(R.id.alarm_note);
        Switch alarmSwitch = alarmView.findViewById(R.id.alarm_switch);
        View deleteButton = alarmView.findViewById(R.id.delete_button);

        alarmTime.setText(alarm.getTime());
        alarmDate.setText(alarm.getDate());
        alarmNote.setText(alarm.getNote());

        alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setAlarm(alarm);
            } else {
                cancelAlarm(alarm);
            }
        });

        deleteButton.setOnClickListener(v -> {
            alarms.remove(alarm);
            alarmsContainer.removeView(alarmView);
            cancelAlarm(alarm);
        });

        alarmsContainer.addView(alarmView);
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

    private void cancelAlarm(Alarm alarm) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
