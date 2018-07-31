package com.jarry.atmessage.widget;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by Jarry on 2018/6/18.
 */

public class AtSpan extends ClickableSpan {
    public static final String STR_AT_ALL = "@全体成员 ";
    private boolean isAtAll = false;
    private String account;
    private String aliasName;
    ClickListener clickListener;

    public static AtSpan createAtAll() {
        AtSpan atSpan = new AtSpan();
        atSpan.setAliasName(STR_AT_ALL);
        atSpan.setAtAll(true);
        return atSpan;
    }

    public AtSpan() {
    }

    public AtSpan(String account, String aliasName) {
        this.account = account;
        this.aliasName = aliasName;
    }

    public AtSpan(String account, String aliasName, ClickListener clickListener) {
        this.account = account;
        this.aliasName = aliasName;
        this.clickListener = clickListener;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }

    public String getAccount() {
        return account;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public void onClick(View widget) {
        //暂时不触发
        if (clickListener != null)
            clickListener.onClick(account, aliasName);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#006EFE"));
    }

    public interface ClickListener {
        void onClick(String account, String aliasName);
    }

}
