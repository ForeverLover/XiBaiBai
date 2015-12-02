package com.jph.xibaibai.utils;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;

/**
 * 录音类
 * Created by jph on 2015/8/23.
 */
public class VoiceRecorder {

    private MediaRecorder mediaRecorder = null;
    private File fileSource = null;
    private CallBack callBack;
    private int snum = 0;//计时

    public VoiceRecorder(File fileSource) {
        this.fileSource = fileSource;
    }

    public File getFileSource() {
        return fileSource;
    }

    public void setFileSource(File fileSource) {
        this.fileSource = fileSource;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void start() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(fileSource.getPath());
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
        threadTime.start();
    }

    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);//避免时间小于1s崩溃
            mediaRecorder.setPreviewDisplay(null);
//            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (callBack != null)
            callBack.onFinish(snum);
    }

    public void cancel(){
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);//避免时间小于1s崩溃
            mediaRecorder.setPreviewDisplay(null);
//            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    Thread threadTime = new Thread() {
        @Override
        public void run() {
            while (mediaRecorder != null) {
                try {
                    Thread.sleep(1000);
                    if (mediaRecorder != null)
                        handlerTime.sendEmptyMessage(++snum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Handler handlerTime = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (callBack != null)
                callBack.onRecording(msg.what);
        }
    };

    public interface CallBack {
        /**
         * 失败
         */
        void onFailed();

        /**
         * 录音中
         *
         * @param snum 已录多久s
         */
        void onRecording(int snum);

        /**
         * 录音完成
         */
        void onFinish(int snum);
    }
}
