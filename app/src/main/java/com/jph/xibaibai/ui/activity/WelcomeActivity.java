package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.jph.xibaibai.R;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.jph.xibaibai.utils.sp.SPUserInfo;

/**
 * Created by jph on 2015/9/3.
 */
public class WelcomeActivity extends BaseActivity {

    private boolean isToed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        handerTO.sendEmptyMessageDelayed(0, 2000);

    }

//    @OnClick(R.id.welcome_img_start)
//    public void onClickStart() {
//        toActivity();
//    }

    Handler handerTO = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isToed) {
                toActivity();
            }
        }
    };

    private void toActivity() {
        if (SPUserInfo.getsInstance(getApplicationContext()).isLogined()) {
            startActivity(HomeActivity.class);
        } else {
            startActivity(LoginActivity.class);
        }
        finish();
        isToed = true;
    }
}
