package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/4/8.
 */

public class AppInfo {
    private int appVersion;
    private boolean forced;
    private String apkUrl;

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appVersion=" + appVersion +
                ", forced=" + forced +
                ", apkUrl='" + apkUrl + '\'' +
                '}';
    }
}
