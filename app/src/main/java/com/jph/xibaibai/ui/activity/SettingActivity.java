package com.jph.xibaibai.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.utils.Constants;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 设置
 * Created by jph on 2015/8/27.
 */
public class SettingActivity extends TitleActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("设置");
    }

    @OnClick({R.id.setting_vg_address, R.id.setting_vg_car, R.id.setting_vg_comment,
            R.id.setting_vg_suggestion, R.id.setting_vg_rule, R.id.setting_vg_about,
            R.id.setting_vg_logout})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_vg_address:
                startActivity(AddressActivity.class);
                break;
            case R.id.setting_vg_car:
                startActivity(CarsActivity.class);
                break;
            case R.id.setting_vg_comment:
                Uri uri = Uri.parse("market://details?id="
                        + this.getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "您的手机上没有市场应用",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting_vg_suggestion:
                startActivity(SuggestionActivity.class);
                break;
            case R.id.setting_vg_rule:
                WebActivity.startWebActivity(this, "法律条款", Constants.BASE_URL + "/Weixin/Diy/aboutLow");
                break;
            case R.id.setting_vg_about:
                WebActivity.startWebActivity(this, "关于我们", "http://mp.weixin.qq.com/s?" +
                        "__biz=MzAxMTY4ODEyOQ==&mid=207988342&idx=1&sn=a011a2b05c04d12fadc4eae58d404353&scene=18#rd");
                break;
            case R.id.setting_vg_logout:
                SPUserInfo.getsInstance(this).logout();

                Intent intentRelogin = new Intent(this, LoginActivity.class);
                intentRelogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentRelogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentRelogin);
                finish();
                break;
        }
    }
}
