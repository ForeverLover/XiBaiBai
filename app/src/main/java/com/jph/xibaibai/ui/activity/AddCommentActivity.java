package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.MImageLoader;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 添加评论
 * Created by jph on 2015/9/13.
 */
public class AddCommentActivity extends TitleActivity {

    public static final String EXTRA_ORDER = "extra_order";

    private Order order;
    private IAPIRequests mAPIRequests;

    @ViewInject(R.id.addcomment_img_employeehead)
    ImageView mImgHead;
    @ViewInject(R.id.addcomment_txt_employeename)
    TextView mTxtEmployeeName;
    @ViewInject(R.id.addcomment_txt_employeephone)
    TextView mTxtEmployeePhone;
    @ViewInject(R.id.addcomment_ratingbar_employeestar)
    RatingBar mRatingBarStar;
    @ViewInject(R.id.addcomment_edt_comment)
    EditText edtComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomment);
    }

    @Override
    public void initData() {
        super.initData();
        order = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
        mAPIRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        MImageLoader.getInstance(this).displayImageM(order.getEmp_img(), mImgHead);
        mTxtEmployeeName.setText(order.getEmp_name());
        mTxtEmployeePhone.setText(order.getEmp_iphone());
    }

    @OnClick(R.id.addcomment_btn_confirm)
    public void onClickConfirm(View v) {
        String comment = edtComment.getText().toString();
        if (comment.length() == 0) {
            showToast("评价内容不能为空");
            return;
        }
        mAPIRequests.addComment(SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID),
                order.getOrder_reg_id(), order.getId(), comment, mRatingBarStar.getRating());
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        showToast("评价成功");
        finish();
    }
}
