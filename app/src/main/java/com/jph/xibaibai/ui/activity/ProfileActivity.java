package com.jph.xibaibai.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.ProfileAdapter;
import com.jph.xibaibai.adapter.RecyclerCarAdapter;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.AllCar;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.entity.UserInfo;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.DividerItemDecoration;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.FileUtil;
import com.jph.xibaibai.utils.PhotoUtil;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * 个人中心
 * Created by jph on 2015/8/28.
 */
public class ProfileActivity extends TitleActivity implements View.OnClickListener {

    private UserInfo userInfo;
    private IAPIRequests mAPIRequests;
    private int uid;
    private ProfileAdapter profileAdapter;

    private int flagModify = 0; // 修改个人信息的标志

    public static final int REFRESHCAR = 1010;

    @ViewInject(R.id.profile_recycler_car)
    RecyclerView mRecyclerViewCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAPIRequests.getCar(uid);
    }

    @Override
    public void initData() {
        super.initData();
        userInfo = JSON.parseObject(SPUserInfo.getsInstance(this).getSP(SPUserInfo.KEY_USERINFO), UserInfo.class);
        mAPIRequests = new APIRequests(this);
        uid = SPUserInfo.getsInstance(ProfileActivity.this).getSPInt(SPUserInfo.KEY_USERID);

        profileAdapter = new ProfileAdapter(new AllCar(),ProfileActivity.this);
        profileAdapter.setDefaultShowable(false);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("我的信息");

        mRecyclerViewCar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewCar.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, R.drawable.shape_divideline_car));

        mRecyclerViewCar.setAdapter(profileAdapter);
    }

    public void toAddCar(){
        Intent intentEdit = new Intent(ProfileActivity.this, AddCarActivity.class);
        startActivityForResult(intentEdit, REFRESHCAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PhotoUtil.TAKEPHOTO:
                Uri uri = Uri.fromFile(
                        FileUtil.getFile(PhotoUtil.PATH_PHOTOTEMP));
                PhotoUtil.cropPhoto(ProfileActivity.this, uri, uri);
                break;
            case PhotoUtil.PICKPHOTO:
                PhotoUtil.cropPhoto(ProfileActivity.this, Uri.fromFile(new File(PhotoUtil.getPickPhotoPath(this, data))),
                        Uri.fromFile(FileUtil.getFile(PhotoUtil.PATH_PHOTOTEMP)));
                break;
            case PhotoUtil.CROPPHOTO:
                File fileHead = FileUtil.getFile(PhotoUtil.PATH_PHOTOTEMP);
                profileAdapter.getViewHolderProfileHeader().imgHead.setImageURI(Uri.fromFile(fileHead));
                mAPIRequests.changeUserHead(uid, fileHead);
                break;
            case REFRESHCAR:
                if(mAPIRequests != null){
                    mAPIRequests.getCar(uid);
                }
                break;
        }
    }

    //    @OnClick({R.id.profile_img_head, R.id.profile_txt_name, R.id.profile_txt_sex,
//            R.id.profile_txt_age, R.id.profile_txt_phone})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_img_head:
                //点击头像
                showDialogHead();
                break;
            case R.id.profile_txt_name:
                showDialogName();
                break;
            case R.id.profile_txt_sex:
                showDialogSex();
                break;
            case R.id.profile_txt_age:
                showDialogAge();
                break;
            case R.id.profile_txt_phone:
                break;
        }
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.CHANGEUSERHEAD:
            case Tasks.CHANGEUSERINFO:
                showToast("修改成功");
                SPUserInfo.getsInstance(ProfileActivity.this).setSP(SPUserInfo.KEY_USERINFO, JSON.toJSONString(userInfo));
                break;
            case Tasks.GETCAR:
                //得到车辆列表
                AllCar allCar = null;
                Log.i("Tag","车辆信息=>"+responseJson.getResult().toString());
                if (responseJson.getResult() == null) {
                    allCar = new AllCar();
                } else {
                    allCar = JSON.parseObject(responseJson.getResult().toString(), AllCar.class);
                }
                profileAdapter = new ProfileAdapter(allCar,ProfileActivity.this);
                profileAdapter.setDefaultShowable(false);
                mRecyclerViewCar.setAdapter(profileAdapter);
                profileAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intentEdit = new Intent(ProfileActivity.this, AddCarActivity.class);
                        intentEdit.putExtra(AddCarActivity.EXTRA_CAR, profileAdapter.getItem(position));
                        startActivityForResult(intentEdit,REFRESHCAR);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {
                    }
                });
                break;
        }
    }

    private void showDialogHead() {
        final String[] strs = new String[]{"拍照", "相册选择"};
        Dialog dialog = new AlertDialog.Builder(this).setTitle("更换头像").setItems(strs,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            PhotoUtil.takePhoto(ProfileActivity.this, Uri.fromFile(
                                    FileUtil.getFile(PhotoUtil.PATH_PHOTOTEMP)));
                        } else {
                            PhotoUtil.pickPhoto(ProfileActivity.this, Uri.fromFile(
                                    FileUtil.getFile(PhotoUtil.PATH_PHOTOTEMP)));
                        }
                    }
                }).create();
        dialog.show();
    }

    private void showDialogName() {
        final EditText edt = new EditText(this);
        Dialog dialog = new AlertDialog.Builder(this).setTitle("修改昵称").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edt.getText().toString();
                if (!StringUtil.isNull(name)) {
                    profileAdapter.getViewHolderProfileHeader().txtName.setText(name);
                    userInfo.setUname(name);
                    mAPIRequests.changeUserInfo(uid, userInfo, flagModify + 1);
                } else {
                    showToast(getString(R.string.toast_nullname));
                }
            }
        }).setNegativeButton("取消", null).setView(edt).create();
        dialog.show();
    }

    private void showDialogSex() {
        final String[] strs = new String[]{"女", "男"};
        Dialog dialog = new AlertDialog.Builder(this).setTitle("选择性别").setItems(strs,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profileAdapter.getViewHolderProfileHeader().txtSex.setText(strs[which]);
                        userInfo.setSex(which == 1 ? 1 : 2);
                        mAPIRequests.changeUserInfo(uid, userInfo, flagModify + 2);
                    }
                }).create();
        dialog.show();
    }

    private void showDialogAge() {
        final EditText edt = new EditText(this);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        Dialog dialog = new AlertDialog.Builder(this).setTitle("修改年龄").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!StringUtil.isNull(edt.getText().toString())) {
                    int age = Integer.parseInt(edt.getText().toString());
                    profileAdapter.getViewHolderProfileHeader().txtAge.setText(String.valueOf(age));
                    userInfo.setAge(age);
                    mAPIRequests.changeUserInfo(uid, userInfo, flagModify + 3);
                } else {
                    showToast(getString(R.string.toast_nullage));
                }
            }
        }).setNegativeButton("取消", null).setView(edt).create();
        dialog.show();
    }

}
