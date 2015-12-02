package com.jph.xibaibai.ui.fragment.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jph.xibaibai.model.http.XRequestCallBack;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.jph.xibaibai.ui.activity.base.Init;
import com.lidroid.xutils.ViewUtils;

/**
 * Created by Administrator on 2015/7/29.
 */
public abstract  class BaseFragment extends Fragment implements Init,XRequestCallBack{
    protected final String TAG = getClass().getSimpleName();
    private boolean isDestroyedView = false;// View是否已销毁
    private boolean newObject;// 新对象
    private ProgressDialog progressDialog;

    public BaseFragment() {
        super();
        newObject = true;
    }

    @Override
    public final View onCreateView(LayoutInflater inflater,
                                   ViewGroup container, Bundle savedInstanceState) {
        isDestroyedView = false;
        View baseV = getContentView(inflater, container);
        ViewUtils.inject(this, baseV);

        progressDialog = new ProgressDialog(getActivity());
        this.progressDialog.setCanceledOnTouchOutside(false);

        init();
        onCreateView(baseV);
        newObject = false;

        return baseV;
    }

    /**
     * 在子类中的onCreateView方法中必须最先调用该方法
     *
     */
    protected abstract int getViewLayoutId();

    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getViewLayoutId(), container, false);
    }

    /** 初始化UI */
    protected abstract void onCreateView(View contentView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        isDestroyedView = true;
    }

    private void init() {
        initData();
        initView();
        initListener();
    }

    @Override
    public void onPrepare(int taskId) {
        showProgressDialog();
    }

    @Override
    public void onSuccess(int taskId, Object... params) {

    }

    @Override
    public void onFailed(int taskId, int errorCode, String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void onEnd(int taskId) {
        dissmissProgressDialog();
    }

    @Override
    public void onLoading(int taskId, long count, long current) {

    }

    @Override
    public boolean isCallBack() {
        return !isDestroyedView;
    }

    /**
     * 是否是新对象
     *
     * @return
     */
    public boolean isNewObject() {
        return newObject;
    }

    /***
     *
     * (初始化数据)
     *
     * @Title: initData
     *
     */
    @Override
    public void initData() {
    }

    /**
     *
     * (初始化UI)
     *
     * @Title: initView
     *
     */
    @Override
    public void initView() {
    }

    /**
     *
     * (设置监听)
     *
     * @Title: initListener
     *
     */
    @Override
    public void initListener() {
    }

    public void showProgressDialog() {
        progressDialog.setMessage("加载中..");
        progressDialog.show();
    }

    public void showProgressDialog(int resId) {
        progressDialog.setMessage(getString(resId));
        progressDialog.show();
    }

    public void showProgressDialog(String str) {
        this.progressDialog.setMessage(str);
        this.progressDialog.show();
    }

    public void dissmissProgressDialog() {
        this.progressDialog.dismiss();
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    protected BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity)
            return (BaseActivity) activity;

        return null;

    }

}

