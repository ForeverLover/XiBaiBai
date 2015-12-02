package com.jph.xibaibai.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.UserInfo;
import com.jph.xibaibai.ui.activity.base.Init;
import com.jph.xibaibai.utils.MImageLoader;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的信息头部
 * Created by jph on 2015/9/16.
 */
public class ViewHolderProfileHeader extends RecyclerView.ViewHolder implements Init {

    private UserInfo userInfo;

    @ViewInject(R.id.profile_img_head)
    public ImageView imgHead;
    @ViewInject(R.id.profile_txt_name)
    public TextView txtName;
    @ViewInject(R.id.profile_txt_sex)
    public TextView txtSex;
    @ViewInject(R.id.profile_txt_age)
    public TextView txtAge;
    @ViewInject(R.id.profile_txt_phone)
    public TextView txtPhone;

    public ViewHolderProfileHeader(View itemView) {
        super(itemView);
        ViewUtils.inject(this, itemView);
        initData();
        initView();
        initListener();
    }


    public void initData() {
        userInfo = JSON.parseObject(SPUserInfo.getsInstance(itemView.getContext()).getSP(SPUserInfo.KEY_USERINFO), UserInfo.class);
    }

    public void initView() {
        MImageLoader.getInstance(itemView.getContext()).displayImageM(userInfo.getU_img(), imgHead);
        txtName.setText(userInfo.getUname());
        txtSex.setText(userInfo.getSex() == 1 ? "男" : "女");
        txtAge.setText(String.valueOf(userInfo.getAge()));
        txtPhone.setText(userInfo.getIphone());
    }

    public void initListener() {
        View.OnClickListener onClickListener = (View.OnClickListener) itemView.getContext();
        imgHead.setOnClickListener(onClickListener);
        txtName.setOnClickListener(onClickListener);
        txtSex.setOnClickListener(onClickListener);
        txtAge.setOnClickListener(onClickListener);
        txtPhone.setOnClickListener(onClickListener);
    }
}
