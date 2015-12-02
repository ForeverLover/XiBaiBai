package com.jph.xibaibai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.SystermUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ta.utdid2.android.utils.StringUtils;

/**
 * 注册
 * Created by Eric on 2015/11/27.
 */
public class RegisterActivity extends TitleActivity implements View.OnClickListener{

    /**是否是注册*/
    private boolean isRegister = false;

    private IAPIRequests mAPIRequests;
    /**
     * 获取验证码倒计时总计
     */
    private int timeSum = 60;

    private boolean isTimeStop = false;

    @ViewInject(R.id.register_phone_et)
    EditText edtPhone; //手机号
    @ViewInject(R.id.register_code_et)
    EditText edtAuthCode; //验证码
    @ViewInject(R.id.register_pwd_et)
    EditText edtPswd; //密码
    @ViewInject(R.id.register_btn)
    Button register_btn;//注册按钮
    @ViewInject(R.id.register_code_layout)
    RelativeLayout edtConfirmPswd; //验证码背景框
    @ViewInject(R.id.register_code_tv)
    TextView txtSendAuthCode; //验证码提醒

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (isTimeStop) {
                        timeSum--;
                        if(timeSum > 0){
                            txtSendAuthCode.setText(timeSum + "S后重新发送");
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                        }else {
                            isTimeStop = false;
                            timeSum = 60;
                            txtSendAuthCode.setText("发送验证码");
                            txtSendAuthCode.setClickable(true);
                            txtSendAuthCode.setTextColor(getResources().getColor(R.color.rg_code_color));
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initData() {
        super.initData();
        isRegister = getIntent().getBooleanExtra("isRegister", false);
        mAPIRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        if (isRegister) {
            setTitle("注册");
            register_btn.setText("注册");
        } else {
            setTitle("重置密码");
            register_btn.setText("确定");
        }
        edtPhone.addTextChangedListener(watcher);
    }

    private void countTime(){
        isTimeStop = true;
        txtSendAuthCode.setText(timeSum + "S后重新发送");
        txtSendAuthCode.setTextColor(getResources().getColor(R.color.rg_code_color));
        txtSendAuthCode.setClickable(false);
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.REGISTER:
                //注册成功
                if(responseJson.getCode() == 1){
                    finish();
                }
                showToast(responseJson.getMsg());
//                Log.i("Tag","REGISTER=>"+responseJson.getCode());
                break;
            case Tasks.RESET_PSWD:
                //重置密码
                if(responseJson.getCode() == 1){
                    finish();
                }
                showToast(responseJson.getMsg());
//                Log.i("Tag", "RESET_PSWD=>" + responseJson.getCode());
                break;
            case Tasks.SEND_CODE:
                Log.i("Tag","Code is ="+responseJson.getResult().toString());
            case Tasks.SEND_RESET_CODE:
                //发送验证码成功
                showToast("验证码已发送，请注意查收");
                countTime();
                break;
        }
    }

    @OnClick({R.id.register_code_layout,R.id.register_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_code_layout:
                if (edtPhone.length() == 0) {
                    showToast("手机号不能为空");
                    return;
                }
                if(!SystermUtils.isTrueTel(edtPhone.getText().toString())){
                    showToast("请输入正确的手机号");
                    return;
                }
                if (isRegister) {
                    mAPIRequests.sendCode(edtPhone.getText().toString(), StringUtil.createRandomCode(6));
                } else {
                    mAPIRequests.sendResetPswdCode(edtPhone.getText().toString(), StringUtil.createRandomCode(6));
                }
                break;
            case R.id.register_btn:
                String phone = edtPhone.getText().toString();
                String pswd = edtPswd.getText().toString();
                String code = edtAuthCode.getText().toString();
                if (phone.length() == 0) {
                    showToast("请输入手机号");
                    return;
                }
                if(code.length() == 0){
                    showToast("请输入验证码");
                    return;
                }
                if (pswd.length() == 0) {
                    showToast("请输入密码");
                    return;
                }
                if (isRegister) {
                    mAPIRequests.register(edtPhone.getText().toString(), edtPswd.getText().toString());
                } else {
                    mAPIRequests.resetPswd(edtPhone.getText().toString(), edtPswd.getText().toString());
                }
                break;
        }
    }
    /**监听手机号输入*/
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() == 11){
                txtSendAuthCode.setTextColor(getResources().getColor(R.color.them_color));
            }else {
                txtSendAuthCode.setTextColor(getResources().getColor(R.color.rg_code_color));
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
