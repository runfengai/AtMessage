package com.jarry.atmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jarry.atmessage.adapter.AiteMemAdapter;
import com.jarry.atmessage.bean.AiteValueBean;

import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView; //recyclerView
    private AiteMemAdapter aiteMemAdapter; //适配器
    private List<AiteValueBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = findViewById(R.id.aite_member_rlv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aiteMemAdapter = new AiteMemAdapter(this, getData());
    }

    private List<AiteValueBean> getData() {
        if (datas != null) return datas;
        for (int i = 0; i < 20; i++) {
            AiteValueBean aiteValueBean = new AiteValueBean(i + "", "名字" + i);
            datas.add(aiteValueBean);
        }
        return datas;
    }
}
