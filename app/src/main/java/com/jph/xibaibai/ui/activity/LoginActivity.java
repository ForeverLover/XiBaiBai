package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.entity.User;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.LineNumberReader;

/**
 * 登录界面
 * Created by jph on 2015/8/20.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private IAPIRequests apiRequests;

    @ViewInject(R.id.login_phone_et)
    EditText edtAccount;
    @ViewInject(R.id.login_pwd_et)
    EditText edtPswd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initData() {
        super.initData();
        apiRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @OnClick({R.id.login_btn_login,R.id.login_forget_layout,R.id.login_reg_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                //登录
                if (edtAccount.length() == 0) {
                    showToast("请输入您的账号");
                    break;
                }
                if (edtPswd.length() == 0) {
                    showToast("请输入您的密码");
                    break;
                }
                apiRequests.login(edtAccount.getText().toString(), edtPswd.getText().toString());
                break;
            case R.id.login_forget_layout:
                Intent forgetIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                forgetIntent.putExtra("isRegister",false);
                startActivity(forgetIntent);
                break;
            case R.id.login_reg_btn:
                Intent rgIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                rgIntent.putExtra("isRegister",true);
                startActivity(rgIntent);
                break;
        }
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);

        ResponseJson responseJson = (ResponseJson) params[0];

        switch (taskId) {
            case Tasks.LOGIN:
                //登录成功
                User user = JSON.parseObject(responseJson.getResult().toString(), User.class);
                SPUserInfo.getsInstance(this).setSP(SPUserInfo.KEY_USERID, user.getId());
                startActivity(HomeActivity.class);
                finish();
                break;
        }
    }
}
