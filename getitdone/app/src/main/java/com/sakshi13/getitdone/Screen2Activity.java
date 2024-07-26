package com.sakshi13.getitdone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Screen2Activity extends AppCompatActivity {

    private List<Task> taskList = new ArrayList<>();
    private LinearLayout taskContainer;
    private Button addTaskButton;
    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        taskContainer = findViewById(R.id.taskContainer);
        addTaskButton = findViewById(R.id.addTaskButton);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen2Activity.this, Screen3Activity.class);
                startActivityForResult(intent, 1);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTasks();
            }
        });

        displayTasks(taskList);
    }

    private void displayTasks(List<Task> tasks) {
        taskContainer.removeAllViews();
        for (Task task : tasks) {
            View taskView = getLayoutInflater().inflate(R.layout.task_item, taskContainer, false);
            TextView titleTextView = taskView.findViewById(R.id.taskTitleTextView);
            TextView descriptionTextView = taskView.findViewById(R.id.taskDescriptionTextView);
            TextView deadlineTextView = taskView.findViewById(R.id.taskDeadlineTextView);
            CheckBox checkBox = taskView.findViewById(R.id.taskCheckBox);
            ImageView editTaskIcon = taskView.findViewById(R.id.editTaskIcon);
            ImageView deleteTaskIcon = taskView.findViewById(R.id.deleteTaskIcon);

            titleTextView.setText(task.getTitle());
            descriptionTextView.setText(task.getDescription());
            deadlineTextView.setText(task.getDeadline());
            checkBox.setChecked(task.isCompleted());

            editTaskIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Edit task logic here
                    Intent intent = new Intent(Screen2Activity.this, Screen3Activity.class);
                    intent.putExtra("editTask", task);
                    startActivityForResult(intent, 2);
                }
            });

            deleteTaskIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete task logic here
                    taskList.remove(task);
                    displayTasks(taskList);
                }
            });

            taskContainer.addView(taskView);
        }
    }

    private void searchTasks() {
        String query = searchEditText.getText().toString().trim();
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredTasks.add(task);
            }
        }
        displayTasks(filteredTasks);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Task newTask = (Task) data.getSerializableExtra("newTask");
            if (newTask != null) {
                taskList.add(newTask);
                displayTasks(taskList);
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
            if (updatedTask != null) {
                // Find and update the task in the list
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getTitle().equals(updatedTask.getTitle())) {
                        taskList.set(i, updatedTask);
                        break;
                    }
                }
                displayTasks(taskList);
            }
        }
    }
}
