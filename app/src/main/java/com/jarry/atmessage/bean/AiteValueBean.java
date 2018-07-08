package com.jarry.atmessage.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author jarry
 * created at 2018/7/9 0:25
 */

public class AiteValueBean implements Parcelable {
    transient boolean isChecked;
    private String account;
    private String vAliasName;

    public AiteValueBean(String account, String vAliasName) {
        this.account = account;
        this.vAliasName = vAliasName;
    }

    protected AiteValueBean(Parcel in) {
        account = in.readString();
        vAliasName = in.readString();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static final Creator<AiteValueBean> CREATOR = new Creator<AiteValueBean>() {
        @Override
        public AiteValueBean createFromParcel(Parcel in) {
            return new AiteValueBean(in);
        }

        @Override
        public AiteValueBean[] newArray(int size) {
            return new AiteValueBean[size];
        }
    };

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getvAliasName() {
        return vAliasName;
    }

    public void setvAliasName(String vAliasName) {
        this.vAliasName = vAliasName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(account);
        parcel.writeString(vAliasName);
    }
}
