package com.jarry.atmessage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarry.atmessage.R;
import com.jarry.atlibrary.bean.AtBean;

import java.util.List;

/**
 *
 */
public class AiteMemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<AtBean> listgroupNumbers;
    private View view;
    private OnItemClickLitener mOnItemClickLitener;

    public AiteMemAdapter(Context mContext, List<AtBean> listgroupNumbers) {
        this.mContext = mContext;
        this.listgroupNumbers = listgroupNumbers;
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
        //单选框
        myMemberViewHolder.checkBox.setChecked(listgroupNumbers.get(position).isChecked());
        //名称
        myMemberViewHolder.textView.setText(listgroupNumbers.get(position).getvAliasName());
        if (mOnItemClickLitener != null) {
            myMemberViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemOnclick(holder, position);
                }
            });
            myMemberViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemOnclick(holder, position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listgroupNumbers == null ? 0 : listgroupNumbers.size();
    }

    public void notifi(List list) {
        this.listgroupNumbers = list;
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
        private CheckBox checkBox; //选择

        public MyMemberViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.channel_addmanage_item_photo_iv);
            textView = (TextView) itemView.findViewById(R.id.channel_addmanage_item_name_tv);
            checkBox = (CheckBox) itemView.findViewById(R.id.channel_addmanage_item_checkbox);
        }
    }


}
