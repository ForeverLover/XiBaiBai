package com.jph.xibaibai.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.jph.xibaibai.R;

/**
 * Created by 鹏 on 2015/11/4.
 * 客服电话选择
 */
public class CustomPhoneActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_phone);
        findViewById(R.id.call_phone_tv).setOnClickListener(this);
        findViewById(R.id.cancel_phone_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.call_phone_tv:
                String phoneNumber = "400-0960-787";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.cancel_phone_tv:
                finish();
                break;
        }
    }
}
