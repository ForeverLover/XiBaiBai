package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.model.entity.AllAddress;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 地址车位
 * Created by jph on 2015/8/28.
 */
public class AddressActivity extends TitleActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_HOME = 1023;
    private static final int REQUEST_CODE_HOME_PARK = 1024;
    private static final int REQUEST_CODE_COMPANY = 1025;
    private static final int REQUEST_CODE_COMPANY_PARK = 1026;

    private IAPIRequests mAPIRequests;
    private AllAddress allAddress;
    private int uid;

    @ViewInject(R.id.address_txt_home)
    TextView mTxtHome;
    @ViewInject(R.id.address_txt_home_park)
    TextView mTxtHomePark;
    @ViewInject(R.id.address_txt_company)
    TextView mTxtCpmpany;
    @ViewInject(R.id.address_txt_company_park)
    TextView mTxtCpmpanyPark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        mAPIRequests.getAddress(uid);
    }

    @Override
    public void initData() {
        super.initData();
        uid = SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID);
        mAPIRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("常用地址车位");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_HOME:
                Address addressRHome = (Address) data.getSerializableExtra(GetLocationActivity.RESULT_ADDRESS);
                mTxtHome.setText(addressRHome.getAddress());
                allAddress.setHomeAddress(addressRHome);
                try {
                    Address address0 = addressRHome.clone();
                    address0.setAddress_info(null);
                    mAPIRequests.setAddress(address0);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_CODE_HOME_PARK:
                String homePark = data.getStringExtra(InputActivity.RESULT_CONTENT);
                mTxtHomePark.setText(homePark);
                allAddress.getHomeAddress().setAddress_info(homePark);
                try {
                    Address address0 = allAddress.getHomeAddress().clone();
                    address0.setAddress(null);
                    address0.setAddress_lg(null);
                    address0.setAddress_lt(null);
                    mAPIRequests.setAddress(address0);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_CODE_COMPANY:
                Address addressRCompany = (Address) data.getSerializableExtra(GetLocationActivity.RESULT_ADDRESS);
                mTxtCpmpany.setText(addressRCompany.getAddress());
                allAddress.setCompanyAddress(addressRCompany);
                try {
                    Address address0 = addressRCompany.clone();
                    address0.setAddress_info(null);
                    mAPIRequests.setAddress(address0);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_CODE_COMPANY_PARK:
                String companyPark = data.getStringExtra(InputActivity.RESULT_CONTENT);
                mTxtCpmpanyPark.setText(companyPark);
                allAddress.getCompanyAddress().setAddress_info(companyPark);
                try {
                    Address address0 = allAddress.getCompanyAddress().clone();
                    address0.setAddress(null);
                    address0.setAddress_lg(null);
                    address0.setAddress_lt(null);
                    mAPIRequests.setAddress(address0);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @OnClick({R.id.address_txt_home, R.id.address_txt_home_park, R.id.address_txt_company, R.id.address_txt_company_park})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_txt_home:
                //选择家地址
                Address addressHome = allAddress.getHomeAddress();
                Intent intentHome = new Intent(this, GetLocationActivity.class);
                if (addressHome == null) {
                    addressHome = new Address();
                }
                intentHome.putExtra(GetLocationActivity.EXTRA_INIT_ADDRESS, addressHome);
                startActivityForResult(intentHome, REQUEST_CODE_HOME);
                break;
            case R.id.address_txt_home_park:
                //家停车位
                Address addressHomePark = allAddress.getHomeAddress();
                Intent intentHomePark = new Intent(this, InputActivity.class);
                if (addressHomePark != null) {
                    intentHomePark.putExtra(InputActivity.EXTRA_INIT_CONTENT, addressHomePark.getAddress_info());
                }
                startActivityForResult(intentHomePark, REQUEST_CODE_HOME_PARK);
                break;
            case R.id.address_txt_company:
                //公司地址
                Address addressCompany = allAddress.getCompanyAddress();
                Intent intentCompany = new Intent(this, GetLocationActivity.class);
                if (addressCompany == null) {
                    addressCompany = new Address();
                }
                intentCompany.putExtra(GetLocationActivity.EXTRA_INIT_ADDRESS, addressCompany);
                startActivityForResult(intentCompany, REQUEST_CODE_COMPANY);
                break;
            case R.id.address_txt_company_park:
                //公司车位
                Address addressCompanyPark = allAddress.getCompanyAddress();
                Intent intentCompanyPark = new Intent(this, InputActivity.class);
                if (addressCompanyPark != null) {
                    intentCompanyPark.putExtra(InputActivity.EXTRA_INIT_CONTENT, addressCompanyPark.getAddress_info());
                }
                startActivityForResult(intentCompanyPark, REQUEST_CODE_COMPANY_PARK);
                break;
        }
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GETADDRESS:
                //查询用户地址
                allAddress = null;
                if (responseJson.getResult() != null) {
                    allAddress = JSON.parseObject(responseJson.getResult().toString(), AllAddress.class);
                }
                if (allAddress == null) {
                    allAddress = new AllAddress();
                }
                Address addressHome = allAddress.getHomeAddress();
                if (addressHome != null) {
                    mTxtHome.setText(addressHome.getAddress());
                    mTxtHomePark.setText(addressHome.getAddress_info());
                } else {
                    addressHome = new Address();
                    addressHome.setUid(uid);
                    allAddress.setHomeAddress(addressHome);
                }

                Address addressCompany = allAddress.getCompanyAddress();
                if (addressCompany != null) {
                    mTxtCpmpany.setText(addressCompany.getAddress());
                    mTxtCpmpanyPark.setText(addressCompany.getAddress_info());
                } else {
                    addressCompany = new Address();
                    addressCompany.setUid(uid);
                    allAddress.setCompanyAddress(addressCompany);
                }

                break;
        }
    }
}
