package com.jarry.atmessage.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

/**
 * 所在包名：com.example.module_mc_link.view
 * 描述：带颜色的@消息，1蓝底白色，2蓝色
 * 作者：biantong
 * 创建时间：2018/6/21
 * 修改人：
 * 修改时间：
 * 修改描述：
 */
public class AtColorSpan extends ReplacementSpan implements ParcelableSpan {
    private final String COLOR_BLUE_BG = "#40add3";//蓝底背景
    private final int COLOR_BLUE = Color.parseColor(COLOR_BLUE_BG);
    private final int COLOR_WHITE = Color.parseColor("#ffffff");
    private boolean isAtAll = false;
    private String aliasName;
    ClickListener clickListener;
    //只要跟自己有有关的，都是蓝底白色
    private boolean aboutSelf = false;
    private int mColor;

    public static AtColorSpan createAtAll() {
        AtColorSpan atColorSpan = new AtColorSpan();
        atColorSpan.setAtAll(true);
        atColorSpan.setAboutSelf(true);
        return atColorSpan;
    }

    public AtColorSpan() {
    }

    public AtColorSpan(String aliasName, boolean aboutSelf) {
        this.aliasName = aliasName;
        this.aboutSelf = aboutSelf;
    }

    public AtColorSpan(String aliasName, boolean aboutSelf, ClickListener clickListener) {
        this.aliasName = aliasName;
        this.aboutSelf = aboutSelf;
        this.clickListener = clickListener;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }

    public boolean isAboutSelf() {
        return aboutSelf;
    }

    public void setAboutSelf(boolean aboutSelf) {
        this.aboutSelf = aboutSelf;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

//    @Override
//    public void onClick(View widget) {
//        if (clickListener != null)
//            clickListener.onClick(aliasName);
//    }

    @Override
    public void updateDrawState(TextPaint ds) {
//        if (aboutSelf) {//蓝底白色
//            ds.setColor(COLOR_WHITE);
//            ds.bgColor = COLOR_BLUE;
//        } else {//蓝色
//            ds.setColor(COLOR_BLUE);
//            ds.bgColor = Color.TRANSPARENT;
//        }
    }

    public interface ClickListener {
        void onClick(String aliasName);
    }


    public int describeContents() {
        return 0;
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    /**
     * @hide
     */
    public int getSpanTypeIdInternal() {
        return 12;//TextUtils.BACKGROUND_COLOR_SPAN
    }

    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    /**
     * @hide
     */
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeInt(mColor);
    }

    public int getBackgroundColor() {
        return mColor;
    }

    /**
     * ==========扩展圆角功能========START=========================
     */
    private final int RADIUS_DEFAULT = 15;
    private final int TEXT_DEFAULT = 15;

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return ((int) paint.measureText(text, start, end) + (aboutSelf ? 30 : 0));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int defColor = paint.getColor();
        if (aboutSelf) {//蓝底白色
            paint.setColor(COLOR_BLUE);//设置背景颜色
            canvas.drawRoundRect(new RectF(x, top + 1, x + ((int) paint.measureText(text, start, end) + TEXT_DEFAULT * 2), bottom - 1), RADIUS_DEFAULT, RADIUS_DEFAULT, paint);
            paint.setColor(COLOR_WHITE);//恢复画笔的文字颜色
            canvas.drawText(text, start, end, x + TEXT_DEFAULT, y, paint);//绘制文字
        } else {
            paint.setColor(COLOR_BLUE);
//           //恢复画笔的文字颜色
            canvas.drawText(text, start, end, x, y, paint);//绘制文字
        }
        paint.setColor(defColor);

    }
    /**
     * ==========扩展圆角功能========START=========================
     */
}
