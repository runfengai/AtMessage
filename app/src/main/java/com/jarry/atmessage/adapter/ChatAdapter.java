package com.jarry.atmessage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarry.atmessage.R;
import com.jarry.atmessage.bean.ChatInfo;

import java.util.List;

/**
 *
 */
public class ChatAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ChatInfo> chatList;
    private View view;
    private OnItemClickLitener mOnItemClickLitener;

    public ChatAdapter(Context mContext, List<ChatInfo> chatList) {
        this.mContext = mContext;
        this.chatList = chatList;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_user_list, parent, false);
        return new MyMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        MyMemberViewHolder myMemberViewHolder = (MyMemberViewHolder) holder;
        ChatInfo chatInfo = chatList.get(position);
        myMemberViewHolder.textView.setText(chatInfo.content);
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
            imageView = (ImageView) itemView.findViewById(R.id.tv_content);
            textView = (TextView) itemView.findViewById(R.id.iv_userhead);
        }
    }


}
