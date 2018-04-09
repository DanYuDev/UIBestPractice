package com.example.coderlt.uibestpractice.service;

/**
 * Created by coderlt on 2018/4/8.
 */

public interface DownloadListener {
    void onSuccess();
    void onCanceled();
    void onPaused();
    void onFailed();
    void onProgress(int progress);
}
