package com.sakshi13.getitdone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Screen1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        // Find the TextView and Button in the layout
        TextView greetingTextView = findViewById(R.id.greetingTextView);
        TextView quoteTextView = findViewById(R.id.quoteTextView);
        Button getStartedButton = findViewById(R.id.getStartedButton);

        // Set greeting and quote
        greetingTextView.setText("Hello!");
        quoteTextView.setText("The future belongs to those who believe in the beauty of their dreams.");

        // Set up the button click listener
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Screen2Activity when the button is clicked
                Intent intent = new Intent(Screen1Activity.this, Screen2Activity.class);
                startActivity(intent);
            }
        });
    }
}
