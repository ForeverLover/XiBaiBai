package com.jph.xibaibai.model.http;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.utils.MLog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ta.utdid2.android.utils.StringUtils;

/**
 * 封装统一的接口请求类
 * Created by jph on 2015/8/12.
 */
public class XHttpRequest extends RequestCallBack<String> {


    private static final String TAG = "XHttpRequest";
    private static final String REMIND_DEFAULTFAILINFO = "请求失败，稍后再试试看";
    private static final String REMIND_CHECKNET = "请检查网络";

    private int taskId;
    protected XRequestCallBack XRequestCallBack;
    private String url;// 请求地址

    public XHttpRequest(int taskId, XRequestCallBack XRequestCallBack, String url) {
        super();
        this.taskId = taskId;
        this.XRequestCallBack = XRequestCallBack;
        this.url = url;
    }

    public void requestPost(RequestParams requestParams) {
        request(HttpRequest.HttpMethod.POST, requestParams);
    }


    public void requestGet(RequestParams requestParams) {
        request(HttpRequest.HttpMethod.GET, requestParams);
    }

    public void request(HttpRequest.HttpMethod httpMethod, RequestParams requestParams) {

        MLog.i(TAG, "url:" + url);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(httpMethod, url, requestParams, this);
    }

    @Override
    public void onStart() {


        if (XRequestCallBack != null && XRequestCallBack.isCallBack())
            XRequestCallBack.onPrepare(taskId);
    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {

        if (XRequestCallBack != null)
            XRequestCallBack.onLoading(taskId, total, current);
    }

    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {

        MLog.i(TAG, "Result:" + responseInfo.result);
        // 非活动状态,不对数据进行处理..
        if (XRequestCallBack == null || !XRequestCallBack.isCallBack()) {
            return;
        }
        XRequestCallBack.onEnd(taskId);
        if(!StringUtils.isEmpty(responseInfo.result.toString())){
            ResponseJson responseJson = JSON.parseObject(responseInfo.result.toString(), ResponseJson.class);
            if (responseJson.getCode() == 1) {
                Log.v("Tag","request success");
                XRequestCallBack.onSuccess(taskId, responseJson);
            } else {
                XRequestCallBack.onFailed(taskId, responseJson.getCode(),
                        responseJson.getMsg());
            }
        }
    }

    @Override
    public void onFailure(HttpException e, String msg) {
        MLog.e(TAG, "onFailure: " + msg);
        Log.i("Tag", "onFailure: " + msg);
        if (TextUtils.isEmpty(msg)) {
            msg = REMIND_DEFAULTFAILINFO;
        }

        if (e.getExceptionCode() == 0) {
            // 请检查网络
            msg = REMIND_CHECKNET;
        }

        if (XRequestCallBack != null && XRequestCallBack.isCallBack()) {
            XRequestCallBack.onEnd(taskId);
            XRequestCallBack.onFailed(taskId, e.getExceptionCode(), msg);
        }
    }

}
