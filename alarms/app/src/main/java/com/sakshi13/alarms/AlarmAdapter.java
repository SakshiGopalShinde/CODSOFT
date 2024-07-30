package com.sakshi13.alarms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private final List<Alarm> alarmList = new ArrayList<>();
    private final Context context;

    public AlarmAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.timeTextView.setText(alarm.getTime());
        holder.dateTextView.setText(alarm.getDate());
        holder.noteTextView.setText(alarm.getNote());

        holder.onOffSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(context, "Alarm is ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Alarm is OFF", Toast.LENGTH_SHORT).show();
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            alarmList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Alarm Deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public void addAlarm(Alarm alarm) {
        alarmList.add(alarm);
        notifyItemInserted(alarmList.size() - 1);
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;
        TextView dateTextView;
        TextView noteTextView;
        CompoundButton onOffSwitch;
        View deleteButton;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            noteTextView = itemView.findViewById(R.id.note_text_view);
            onOffSwitch = itemView.findViewById(R.id.alarm_switch);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
