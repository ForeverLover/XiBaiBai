package com.jph.xibaibai.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;

/**
 * 录音播放
 * Created by jph on 2015/8/23.
 */
public class VoicePlayer {

    private MediaPlayer mediaPlayer;
    private int snum;//剩余时间 s
    private int duration;
    private CallBack callBack;

    private int count = 0;

    public VoicePlayer(Context context, File fileSource) {
        this.mediaPlayer = MediaPlayer.create(context, Uri.fromFile(fileSource));
        duration = mediaPlayer.getDuration() / 1000;
        snum = duration;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public int getDuration() {
        return duration;
    }

    public void start() {
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
        threadTime.start();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    Thread threadTime = new Thread() {
        @Override
        public void run() {
            while (mediaPlayer != null && snum != 0) {
                try {
                    Thread.sleep(1000);
                    if (snum != 0){
//                        handlerTime.sendEmptyMessage(--snum);
                        if(count != snum){
                            handlerTime.sendEmptyMessage(++count);
                        }else {
                            count = 0;
                        }
                    }
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
            if (callBack != null){
                Log.i("Tag","what = "+msg.what);
                callBack.onPlaying(msg.what);
            }
            if (msg.what == 0 && callBack != null){
                callBack.onFinish(snum);
            }
        }
    };

    public interface CallBack {

        /**
         * 播放中
         *
         * @param snum 已录多久s
         */
        void onPlaying(int snum);

        /**
         * 播放结束
         */
        void onFinish(int snum);
    }
}
