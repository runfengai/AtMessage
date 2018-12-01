package com.jarry.atmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jarry.atmessage.adapter.ChatAdapter;
import com.jarry.atlibrary.bean.AtBean;
import com.jarry.atlibrary.bean.AtMessage;
import com.jarry.atlibrary.bean.ChatInfo;
import com.jarry.atlibrary.widget.AtEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * demo示例
 *
 * @Author jarry
 * created at 2018/7/24 23:14
 */

public class MainActivity extends AppCompatActivity implements AtEditText.OnAtInputListener, View.OnClickListener {
    private static final int AT_CODE = 1;
    private AtEditText atEditText;
    private List<ChatInfo> datas = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppCompatButton sendBtn;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atEditText = findViewById(R.id.at_edit_text);
        recyclerView = findViewById(R.id.rv);
        sendBtn = findViewById(R.id.send);
        chatAdapter = new ChatAdapter(this, datas);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(chatAdapter);
        atEditText.setOnAtInputListener(this);
        sendBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            if (atEditText.checkAtMsg()) {//@消息
                AtMessage atMessage = atEditText.getAtMsg();
                datas.add(atMessage);
                chatAdapter.notifi(datas);
            } else {//普通消息
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.content = atEditText.getText().toString();
                datas.add(chatInfo);
                chatAdapter.notifi(datas);
            }
            atEditText.setText("");
        }
    }


}
