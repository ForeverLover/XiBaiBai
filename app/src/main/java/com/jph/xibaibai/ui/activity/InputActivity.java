package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jph.xibaibai.R;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 输入内容
 * Created by jph on 2015/9/7.
 */
public class InputActivity extends TitleActivity {

    public static String EXTRA_INIT_CONTENT = "extra_init_content";
    public static String RESULT_CONTENT = "result_content";

    @ViewInject(R.id.input_edt_content)
    EditText edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    @Override
    public void initView() {
        super.initView();
        showTitleBtnRight("完成");
        edtContent.setText(getIntent().getStringExtra(EXTRA_INIT_CONTENT));
    }

    @Override
    protected void onClickTitleRight(View v) {
        super.onClickTitleRight(v);
        Intent intentResult = new Intent();
        intentResult.putExtra(RESULT_CONTENT, edtContent.getText().toString());
        setResult(RESULT_OK, intentResult);
        finish();
    }
}
