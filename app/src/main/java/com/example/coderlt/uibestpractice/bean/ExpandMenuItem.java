package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/3/28.
 */

public class ExpandMenuItem {
    private String title;
    private int    iconId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public ExpandMenuItem(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }
}
