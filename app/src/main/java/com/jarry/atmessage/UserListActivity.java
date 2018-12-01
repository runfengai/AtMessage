package com.jarry.atmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jarry.atmessage.adapter.AiteMemAdapter;
import com.jarry.atlibrary.bean.AtBean;

import java.util.ArrayList;
import java.util.List;


public class UserListActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "users";

    private RecyclerView recyclerView; //recyclerView
    private AiteMemAdapter aiteMemAdapter; //适配器
    private List<AtBean> datas = new ArrayList<>();
    private TextView okTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = findViewById(R.id.aite_member_rlv);
        findViewById(R.id.aite_member_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        okTv = findViewById(R.id.aite_member_tv_editoritem);
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelected();
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(EXTRA_USER, selected);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aiteMemAdapter = new AiteMemAdapter(this, getData());
        recyclerView.setAdapter(aiteMemAdapter);
        aiteMemAdapter.setOnItemClickLitener(new AiteMemAdapter.OnItemClickLitener() {
            @Override
            public void onItemOnclick(RecyclerView.ViewHolder view, int position) {
                AtBean item = datas.get(position);
                boolean checked = item.isChecked();
                item.setChecked(!checked);
                aiteMemAdapter.notifyDataSetChanged();
            }
        });

    }

    private ArrayList<AtBean> selected = new ArrayList<>();

    private void getSelected() {
        for (AtBean data : datas) {
            if (data.isChecked())
                selected.add(data);
        }
    }

    private List<AtBean> getData() {
        for (int i = 0; i < 20; i++) {
            AtBean aiteValueBean = new AtBean(i + "", "名字" + i);
            datas.add(aiteValueBean);
        }
        return datas;
    }
}
