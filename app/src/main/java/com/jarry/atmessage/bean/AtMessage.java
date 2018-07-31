package com.jarry.atmessage.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * @Author jarry
 * created at 2018/8/1 0:02
 */


public class AtMessage extends ChatInfo {
    protected String extension = "";
    protected Object extern;//扩展字段，如果是群文本消息，支持@某人功能,则为Boolean类
    /**
     * at消息状态
     */
    private int atState;
    public static final int AT_NONE = 1;//无@
    public static final int AT_ALL = 2;//@全体
    public static final int AT_SOMEONE = 3;//@部分

    private List<Integer> atIndex;
    private List<String> receiver;

    public AtMessage() {
    }

    public AtMessage(int atState) {
        this.atState = atState;
    }

    public int getAtState() {
        return atState;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setAtState(int atState) {
        this.atState = atState;
    }

    public void setExtension(String extension) {
        this.extension = extension;
        //解析
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(extension);
        } catch (Exception e) {
        }
        if (jsonObject != null) {
            if (jsonObject.containsKey("atIndex")) {
                JSONArray atIndexArr = (JSONArray) jsonObject.get("atIndex");
                try {
                    atIndex = atIndexArr.toJavaList(Integer.class);
                } catch (Exception e) {
                }
            }
            if (AT_SOMEONE == atState) {
                if (jsonObject.containsKey("receiver")) {
                    try {
                        JSONArray jsonArray = (JSONArray) jsonObject.get("receiver");
                        receiver = jsonArray.toJavaObject(List.class);
                    } catch (Exception e) {
                    }
                }

            }
        }
    }

    public List<Integer> getAtIndex() {
        return atIndex;
    }

    public void setAtIndex(List<Integer> atIndex) {
        this.atIndex = atIndex;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }
}
