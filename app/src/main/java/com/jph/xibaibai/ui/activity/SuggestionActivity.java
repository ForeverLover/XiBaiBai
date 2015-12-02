package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by jph on 2015/8/28.
 */
public class SuggestionActivity extends TitleActivity {

    private IAPIRequests mAPIRequests;

    @ViewInject(R.id.suggestion_edt_content)
    EditText edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("帮助与反馈");
    }

    /**
     * 提交反馈
     *
     * @param v
     */
    @OnClick(R.id.suggestion_btn_commit)
    public void onClickCommitSuggestion(View v) {
        if (edtContent.length() == 0) {
            showToast("内容不能为空");
            return;
        }
        mAPIRequests.suggestion(SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID), edtContent.getText().toString());
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        showToast("提交成功");
        finish();
    }
}
