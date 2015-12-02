package com.jph.xibaibai.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Car;
import com.jph.xibaibai.model.entity.CarBrand;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.SystermUtils;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 添加车辆
 * Created by jph on 2015/8/27.
 */
public class AddCarActivity extends TitleActivity implements View.OnClickListener {
    public static final String EXTRA_CAR = "extra_car";
    private static final int REQUESTCODE_BRAND = 9087;

    private boolean isEdit = false;
    private Car car;
    private IAPIRequests mAPiRequests;
    private CarBrand carBrand;

    @ViewInject(R.id.addcar_txt_brand)
    TextView mTxtBrand;
    @ViewInject(R.id.addcar_edt_platenum)
    EditText mEdtPlateNum;
    @ViewInject(R.id.addcar_edt_color)
    EditText mEdtColor;
    @ViewInject(R.id.addcar_txt_type)
    TextView mTxtType;
    @ViewInject(R.id.addcar_btn_confirm)
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);
    }

    @Override
    public void initData() {
        super.initData();
        car = (Car) getIntent().getSerializableExtra(EXTRA_CAR);
        if (car != null) {
            isEdit = true;
        } else {
            car = new Car();
            car.setUid(SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID));
        }
        mAPiRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        if (isEdit) {
            setTitle("修改车辆信息");
            btnConfirm.setText("修改");
            mEdtPlateNum.setText(car.getC_plate_num());
            mTxtBrand.setText(car.getC_brand());
            mEdtColor.setText(car.getC_color());
            mTxtType.setText(SystermUtils.getCarTypes(car.getC_type()));
        } else {
            setTitle("添加常用车辆");
            btnConfirm.setText("确定");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUESTCODE_BRAND:
                //选择品牌
                CarBrand carBrand = (CarBrand) data.getSerializableExtra(CarBrandActivity.RESULT_BRAND);
                mTxtBrand.setText(carBrand.getMake_name());
                break;
        }
    }

    @OnClick(R.id.addcar_btn_confirm)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addcar_btn_confirm:
                //确认添加
                if (mEdtPlateNum.getText().length() == 0 || mTxtBrand.getText().length() == 0 ||
                        mEdtColor.getText().length() == 0) {
                    showToast(getString(R.string.toast_notfull));
                    break;
                }
                car.setAdd_time(System.currentTimeMillis());
                car.setC_color(mEdtColor.getText().toString());
                car.setC_brand(mTxtBrand.getText().toString());
                car.setC_plate_num(mEdtPlateNum.getText().toString());
                if (isEdit) {
                    mAPiRequests.changeCar(car);
                } else {
                    mAPiRequests.addCar(car);
                }
                break;
        }
    }

    /**
     * 点击选择品牌
     *
     * @param v
     */
    @OnClick(R.id.addcar_txt_brand)
    public void onClickBrand(View v) {
        Intent intentBrand = new Intent(this, CarBrandActivity.class);
        startActivityForResult(intentBrand, REQUESTCODE_BRAND);
    }

    /**
     * 点击选择类型
     *
     * @param v
     */
    @OnClick(R.id.addcar_txt_type)
    public void onClickType(View v) {
        final String[] strs = new String[]{"轿车", "SUV", "MPV"};
        new AlertDialog.Builder(this).setTitle("选择类型").setItems(
                strs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mTxtType.setText(strs[which]);
                        car.setC_type(which + 1);
                    }
                }).show();
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.ADDCAR:
                showToast("添加车辆成功");
                setResult(RESULT_OK);
                finish();
                break;
            case Tasks.CHANGE_CAR:
                showToast("修改车辆成功");
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
