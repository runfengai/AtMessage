package com.jarry.atmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jarry.atmessage.adapter.ChatAdapter;
import com.jarry.atmessage.bean.AtBean;
import com.jarry.atmessage.bean.ChatInfo;
import com.jarry.atmessage.widget.AtEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AtEditText.OnAtInputListener {
    private static final int AT_CODE = 1;
    private AtEditText atEditText;
    private List<ChatInfo> datas = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atEditText = findViewById(R.id.at_edit_text);
        recyclerView = findViewById(R.id.rv);
        chatAdapter = new ChatAdapter(this, datas);
        recyclerView.setAdapter(chatAdapter);
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
            List<AtBean> list = (List<AtBean>) data.getSerializableExtra(UserListActivity.EXTRA_USER);
            if (list != null && list.size() > 0) {
                atEditText.setAtUsers(list);
            }
        }
    }

}
