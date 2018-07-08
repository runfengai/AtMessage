package com.jarry.atmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jarry.atmessage.widget.AtEditText;

public class MainActivity extends AppCompatActivity implements AtEditText.OnAtInputListener {
    private static final int AT_CODE = 1;
    AtEditText atEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atEditText = findViewById(R.id.at_edit_text);
        atEditText.setOnAtInputListener(this);
    }

    @Override
    public void onAtInput() {
        Intent intent = new Intent(this, UserListActivity.class);
        startActivityForResult(intent, AT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == AT_CODE) {//请求码
            

        }
    }
}
