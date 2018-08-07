package com.jarry.atmessage.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.jarry.atmessage.R;
import com.jarry.atmessage.bean.AtBean;
import com.jarry.atmessage.bean.AtMessage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jarry on 2018/7/8.
 */

public class AtEditText extends android.support.v7.widget.AppCompatEditText {
    //用于@成员
    private Map<String, Integer> atAcountMap = new HashMap<>();//统计某人被@的次数,key=account,integer,数量

    //监听@输入事件
    private OnAtInputListener onAtInputListener;

    private int atColor;//@高亮色

    public AtEditText(Context context) {
        this(context, null);
    }

    public AtEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public AtEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AtEditText);
        atColor = typedArray.getColor(R.styleable.AtEditText_atColor, AtSpan.DEFAULT_COLOR);
        init();
    }

    private void init() {
        //监听@消息用
        setAtListener();
    }

    /**
     * 设置监听@输入
     *
     * @param onAtInputListener
     */
    public void setOnAtInputListener(OnAtInputListener onAtInputListener) {
        this.onAtInputListener = onAtInputListener;
    }

    public boolean checkAtMsg() {
        Editable text = getText();
        AtSpan[] atSpans = text.getSpans(0, text.length(), AtSpan.class);
        if (atSpans != null && atSpans.length > 0) {
            return true;
        }
        return false;
    }

    public AtMessage getAtMsg() {
        Editable text = getText();
        AtSpan[] atSpans = text.getSpans(0, text.length(), AtSpan.class);
        boolean isAtAll = false;
        int len = atSpans.length;
        //@的索引位置
        int[] atIndexs = new int[len];
        for (int i = 0; i < len; i++) {
            AtSpan atSpan = atSpans[i];
            if (atSpan.isAtAll()) {
                isAtAll = true;//@全体的优先级最高
            }
            //开始做标记，不管是@全体还是@成员的都要做记录
            atIndexs[i] = text.getSpanStart(atSpan);
        }
        AtMessage atMessage;
        if (isAtAll) {
            atMessage = new AtMessage(AtMessage.AT_ALL);
            atMessage.setContent(text.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("atIndex", atIndexs);//索引位包含@成员的，展示的时候，需要进行解析突出展示
            atMessage.setExtension(jsonObject.toJSONString());
        } else {
            atMessage = new AtMessage(AtMessage.AT_SOMEONE);
            atMessage.setContent(text.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("receiver", atMapToArray());
            jsonObject.put("atIndex", atIndexs);
            atMessage.setExtension(jsonObject.toJSONString());
        }
        return atMessage;
    }

    private void setAtListener() {

        //监听@输入
        setFilters(new InputFilter[]{new AtInputFilter()});
        //@整体效果，点击时让其处于两边整个文字
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operateAtClickOrDelete(true);
            }
        });
        setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //监听删除
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return operateAtClickOrDelete(false);
                }
                return false;
            }
        });
    }

    private class AtInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String str = source.toString();
            if (str.equalsIgnoreCase("@")) {
//                selectAtUser();
                if (onAtInputListener != null)
                    onAtInputListener.onAtInput();
            }
            return source;
        }
    }//at相关的删除、点击操作

    private boolean operateAtClickOrDelete(boolean isClick) {
        int selectionStart = getSelectionStart();
        Editable textEditable = getText();
        AtSpan[] spans = textEditable.getSpans(0, textEditable.length(), AtSpan.class);
        for (AtSpan atSpan : spans) {
            int spanStart = textEditable.getSpanStart(atSpan);
            int spanEnd = textEditable.getSpanEnd(atSpan);
            int mid = (spanEnd + spanStart) / 2;
            if (isClick) {
                if (selectionStart >= spanStart && selectionStart < mid) {//前半段
                    setSelection(spanStart);
                    return true;
                } else if (selectionStart >= mid && selectionStart < spanEnd) {//后半段
                    setSelection(spanEnd);
                    return true;
                }
            } else {//删除操作
                if (selectionStart >= spanStart && selectionStart <= spanEnd) {
                    textEditable.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 监听输入@事件
     */
    public interface OnAtInputListener {
        void onAtInput();
    }

    /**
     * 设置@成员列表
     *
     * @param atSomeoneList
     */
    public void setAtUsers(List<AtBean> atSomeoneList) {
        int currIndex = this.getSelectionStart();//光标位置
        boolean isStart = true;//输入的@符号要去掉
        List<String> atStringList = new ArrayList<>();
        StringBuilder atStringBuilder = new StringBuilder();
        Editable editable = this.getText();
        for (AtBean item : atSomeoneList) {
            String account = item.getAccount();
            saveToMap(account);//保存记录
            //添加人
            String atUser = "@" + item.getvAliasName() + " ";
            atStringList.add(atUser);
            atStringBuilder.append(atUser);
            if (isStart) {
                editable.replace(currIndex - 1, currIndex, atUser);
                isStart = false;
            } else {
                editable.insert(currIndex, atUser);
            }
            currIndex = this.getSelectionStart();
        }
        //起始位置
        int startIndex = currIndex - atStringBuilder.length();
        //输入完文字后，要替换成span
        for (AtBean item : atSomeoneList) {
            int itemLen = item.getvAliasName().length() + 2;
            AtSpan atSpan = new AtSpan(item.getAccount(), item.getvAliasName(), new AtSpan.ClickListener() {
                @Override
                public void onClick(String account, String aliasName) {
//                    LogUtils.e("===AtSpan======点击了===>>>" + aliasName);

                }
            }, atColor);
            this.getText().setSpan(atSpan, startIndex, startIndex + itemLen, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
            startIndex += itemLen;
        }
    }

    /**
     * map转数组
     *
     * @return
     */
    private String[] atMapToArray() {
        String[] res = new String[atAcountMap.size()];
        int index = 0;
        Set<Map.Entry<String, Integer>> entries = atAcountMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            res[index++] = entry.getKey();
        }
        return res;
    }

    /**
     * 保存到map中
     *
     * @param account 账户
     */
    private void saveToMap(String account) {
        if (atAcountMap.containsKey(account)) {
            int count = atAcountMap.get(account);
            atAcountMap.put(account, count + 1);
        } else {
            atAcountMap.put(account, 1);
        }
    }

    /**
     * 从map中删除
     *
     * @param account 账户
     */
    private void deleteFromMap(String account) {
        if (atAcountMap.containsKey(account)) {
            int count = atAcountMap.get(account);
            if (count > 1)
                atAcountMap.put(account, --count);
            else
                atAcountMap.remove(account);
        }
    }

}
