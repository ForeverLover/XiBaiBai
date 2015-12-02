package com.jph.xibaibai.model.http;

import com.lidroid.xutils.http.RequestParams;

/**
 * Created by jph on 2015/8/25.
 */
public class XRequestParams extends RequestParams {

    public XRequestParams() {
    }

    public XRequestParams(String charset) {
        super(charset);
    }

    @Override
    public void addBodyParameter(String name, String value) {
        if (value == null) {
            return;
        }
        super.addBodyParameter(name, value);
    }
}
