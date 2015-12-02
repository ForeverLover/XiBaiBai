package com.jph.xibaibai.model.http;

import com.jph.xibaibai.model.utils.Constants;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by jph on 2015/8/13.
 */
public class BaseAPIRequest {


    /**
     * 接口地址
     */
    public static final String URL_API = Constants.BASE_URL
            + "/Api/Indextwo";

    protected static void request(XRequestCallBack XRequestCallBack, int taskId, String doUrl) {
        request(XRequestCallBack, taskId, doUrl, null);
    }

    protected static void request(XRequestCallBack XRequestCallBack, int taskId, String doUrl,
                                  RequestParams requestParams) {
        XHttpRequest xHttpRequest = new XHttpRequest(taskId, XRequestCallBack, URL_API + doUrl);
        xHttpRequest.requestPost(requestParams);
    }

    protected RequestParams createRequestParams() {
        return new XRequestParams();
    }
}
