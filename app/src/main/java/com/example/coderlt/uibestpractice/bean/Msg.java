package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2017/12/28.
 */

public class Msg {
    private String msg;
    private int type;
    public static final int OTHER=0;
    public static final int MINE=1;
    public static final int VOICE=2;

    public Msg(){
        msg="";
        type=OTHER;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
