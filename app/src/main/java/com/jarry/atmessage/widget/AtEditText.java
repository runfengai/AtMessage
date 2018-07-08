package com.jarry.atmessage.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.jarry.atmessage.R;

/**
 * Created by Jarry on 2018/7/8.
 */

public class AtEditText extends android.support.v7.widget.AppCompatEditText {
    //监听@输入事件
    private OnAtInputListener onAtInputListener;

    public AtEditText(Context context) {
        this(context, null);
    }

    public AtEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public AtEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
