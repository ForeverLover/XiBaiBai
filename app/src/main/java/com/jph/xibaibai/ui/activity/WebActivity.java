package com.jph.xibaibai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jph.xibaibai.R;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 显示网页
 *
 * @author jph
 */
public class WebActivity extends TitleActivity {

    /**
     * 传入标题
     */
    public static final String INTENTKEY_STRING_TITLE = "intentkey_string_title";
    /**
     * 传入网页地址
     */
    public static final String INTENTKEY_STRING_URL = "intentkey_string_url";

    private String title;// 标题
    private String url;// 网页地址

    @ViewInject(R.id.web_webview)
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView.loadUrl(url);
    }

    public static void startWebActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(INTENTKEY_STRING_TITLE, title);
        intent.putExtra(INTENTKEY_STRING_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void initData() {
        super.initData();

        title = getIntent().getStringExtra(INTENTKEY_STRING_TITLE);
        url = getIntent().getStringExtra(INTENTKEY_STRING_URL);
    }

    @Override
    public void initView() {
        super.initView();

        if (TextUtils.isEmpty(title))
            hideTitleView();
        else
            setTitle(title);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 调用拨号程序
                if (url.startsWith("mailto:") || url.startsWith("geo:")
                        || url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(url));
                    startActivity(intent);
                    return true;
                }

                view.loadUrl(url); // 在当前的webview中跳转到新的url

                return true;
            }
        });

        // webView.setWebChromeClient(new WebChromeClient() {
        // @Override
        // public void onProgressChanged(WebView view, int newProgress) {
        // mProgressBar.setProgress(newProgress);
        // mProgressBar.postInvalidate();
        // if (newProgress == 100) {
        // mProgressBar.setVisibility(View.GONE);
        // }
        // }
        // });
    }

}
