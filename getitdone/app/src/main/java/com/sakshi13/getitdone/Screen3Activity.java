package com.sakshi13.getitdone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Screen3Activity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private TextView deadlineTextView;
    private Button addTaskButton;
    private Calendar calendar;
    private Task taskToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        // Initialize UI elements
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        deadlineTextView = findViewById(R.id.deadlineTextView);
        addTaskButton = findViewById(R.id.addTaskButton);

        calendar = Calendar.getInstance();

        // Set default deadline to current date and time
        updateDeadlineTextView();

        deadlineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        Intent intent = getIntent();
        taskToEdit = (Task) intent.getSerializableExtra("editTask");
        if (taskToEdit != null) {
            titleEditText.setText(taskToEdit.getTitle());
            descriptionEditText.setText(taskToEdit.getDescription());
            deadlineTextView.setText(taskToEdit.getDeadline());
            addTaskButton.setText("Update Task");
        }
    }

    private void addTask() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String deadline = deadlineTextView.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
            // Show an error message or toast
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (taskToEdit != null) {
            // Update existing task
            taskToEdit.setTitle(title);
            taskToEdit.setDescription(description);
            taskToEdit.setDeadline(deadline);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedTask", taskToEdit);
            setResult(RESULT_OK, resultIntent);
        } else {
            // Add new task
            Task newTask = new Task(title, description, deadline, false);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("newTask", newTask);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Screen3Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        updateDeadlineTextView();
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void updateDeadlineTextView() {
        String date = android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString();
        String time = android.text.format.DateFormat.format("hh:mm a", calendar).toString();
        deadlineTextView.setText(date + " " + time);
    }
}
