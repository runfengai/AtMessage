package com.jarry.atmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jarry.atmessage.widget.AtEditText;

public class MainActivity extends AppCompatActivity {
    AtEditText atEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atEditText = findViewById(R.id.at_edit_text);
    }
}
