package com.jph.xibaibai.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerMoneyRecordAdapter;
import com.jph.xibaibai.adapter.viewholder.ViewHolderMoneyHeader;
import com.jph.xibaibai.alipay.Alipay;
import com.jph.xibaibai.alipay.Product;
import com.jph.xibaibai.model.entity.Balance;
import com.jph.xibaibai.model.entity.MoneyRecord;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.entity.TopUpInfo;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.BaseAPIRequest;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 我的余额
 * Created by jph on 2015/8/30.
 */
public class BalanceActivity extends TitleActivity implements ViewHolderMoneyHeader.OnHeaderOperationListener {

    private IAPIRequests mAPIRequests;
    private RecyclerMoneyRecordAdapter mRecordAdapter;
    private int uid;

    @ViewInject(R.id.balance_recycler_detailrecord)
    RecyclerView mRecyclerViewRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        uid = SPUserInfo.getsInstance(this).getSPInt(SPUserInfo.KEY_USERID);
        mAPIRequests.getAccountBalance(uid);
        mAPIRequests.getPayRecords(uid);
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
        mRecordAdapter = new RecyclerMoneyRecordAdapter(new ArrayList<MoneyRecord>());
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("我的余额");

        mRecyclerViewRecords.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewRecords.setAdapter(mRecordAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mRecordAdapter.setOnHeaderOperationListener(this);
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];

        switch (taskId) {
            case Tasks.GETACCOUNTBALANCE:
                //得到账户余额
                Balance balance = JSON.parseObject(responseJson.getResult().toString(), Balance.class);
                mRecordAdapter.setBalance(balance);
                break;
            case Tasks.GETPAYRECORDS:
                //得到资金记录
                if (responseJson.getResult() == null) {
                    break;
                }
                mRecordAdapter.setList(JSON.parseArray(responseJson.getResult().toString(), MoneyRecord.class));
                break;
            case Tasks.GET_TOP_UP_INFO:
                //得到充值信息
                TopUpInfo topUpInfo = JSON.parseObject(responseJson.getResult().toString(), TopUpInfo.class);
                topUp(topUpInfo);
                break;
            case Tasks.WITHDRAW:
                //提现
                showToast("提现申请已提交,将线下转账到你的支付宝账户");
                mAPIRequests.getAccountBalance(uid);
                mAPIRequests.getPayRecords(uid);
                break;
        }

    }

    @Override
    public void onClickUpTop() {
        //点击充值
        showDialogMoney();
    }

    private void topUp(final TopUpInfo topUpInfo) {
        Product product = new Product(topUpInfo.getRecharge_name(),
                topUpInfo.getRecharge_name(), topUpInfo.getRecharge_price(), topUpInfo.getRecharge_num());
        Alipay alipay = new Alipay(this, BaseAPIRequest.URL_API
                + "/alipay_return_recharge");
        alipay.setCallBack(new Alipay.CallBack() {
            @Override
            public void onSuccess() {
                //充值成功
                Balance balance = mRecordAdapter.getBalance();
                balance.setMoney(balance.getMoney() + topUpInfo.getRecharge_price());
                mRecordAdapter.setBalance(balance);
            }

            @Override
            public void onFailed() {
                showToast("充值失败");
            }
        });
        alipay.pay(product);
    }

    private void showDialogMoney() {
        final EditText edt = new EditText(this);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        Dialog dialog = new AlertDialog.Builder(this).setTitle("输入金额").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!StringUtil.isNull(edt.getText().toString())){
                    double money = Double.valueOf(edt.getText().toString());
                    mAPIRequests.withdraw(SPUserInfo.getsInstance(BalanceActivity.this).getSPInt(SPUserInfo.KEY_USERID), money);
                }else {
                    showToast("请输入提现金额");
                }
//                mAPIRequests.getTopUpInfo(SPUserInfo.getsInstance(BalanceActivity.this).getSPInt(SPUserInfo.KEY_USERID), money);
            }
        }).setNegativeButton("取消", null).setView(edt).create();
        dialog.show();
    }
}
