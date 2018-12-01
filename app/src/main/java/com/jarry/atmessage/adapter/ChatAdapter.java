package com.jarry.atmessage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarry.atmessage.R;
import com.jarry.atlibrary.bean.AtMessage;
import com.jarry.atlibrary.bean.ChatInfo;
import com.jarry.atlibrary.widget.AtColorSpan;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class ChatAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ChatInfo> chatList;
    private View view;
    private OnItemClickLitener mOnItemClickLitener;
    private String aliasName = "乔布斯";//自己的名字

    public ChatAdapter(Context mContext, List<ChatInfo> chatList) {
        this.mContext = mContext;
        this.chatList = chatList;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.ftx_row_received_message, parent, false);
        return new MyMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        MyMemberViewHolder myMemberViewHolder = (MyMemberViewHolder) holder;
        ChatInfo chatInfo = chatList.get(position);
        if (chatInfo instanceof AtMessage) {
            showAt(myMemberViewHolder.textView, (AtMessage) chatInfo);
        } else
            myMemberViewHolder.textView.setText(chatInfo.content);
    }

    private void showAt(TextView textView, AtMessage receivedMsg) {

        String content = receivedMsg.getContent();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        //获取a索引
        List<Integer> atIndex = receivedMsg.getAtIndex();
        //存储开始位置及结束位置
        HashMap<Integer, Integer> saveStartEnd = new LinkedHashMap<>();
        //获取空格位置

        for (Integer integer : atIndex) {
            int spaceIndex = content.indexOf(" ", integer);
            if (spaceIndex >= 0) {//
                saveStartEnd.put(integer, spaceIndex);
            }
        }
        Set<Integer> integers = saveStartEnd.keySet();
        for (Integer start : integers) {
            int end = saveStartEnd.get(start);
            //先判断如果是@自己，则变蓝底白字
            String atPerson = null;
            try {
                atPerson = content.substring(start, end) + " ";
            } catch (Exception e) {
            }
            if (!TextUtils.isEmpty(atPerson)) {
                String atAliasName = "@" + aliasName + " ";
                //跟自己有关的都是蓝底白色，无关的都是蓝色
                boolean aboutSelf = atAliasName.equals(atPerson);
                spannableStringBuilder.setSpan(new AtColorSpan(atPerson, aboutSelf, new AtColorSpan.ClickListener() {
                    @Override
                    public void onClick(String aliasName) {
                    }
                }), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        textView.setText(spannableStringBuilder);
    }

    @Override
    public int getItemCount() {
        return chatList == null ? 0 : chatList.size();
    }

    public void notifi(List list) {
        this.chatList = list;
        notifyDataSetChanged();
    }

    //单选回调接口
    public interface OnItemClickLitener {
        //void onImageheardOnclick(RecyclerView.ViewHolder view, int position);
        void onItemOnclick(RecyclerView.ViewHolder view, int position);
    }

    //成员ViewHolder
    class MyMemberViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView; //头像
        private TextView textView; //名称

        public MyMemberViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_userhead);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }


}
