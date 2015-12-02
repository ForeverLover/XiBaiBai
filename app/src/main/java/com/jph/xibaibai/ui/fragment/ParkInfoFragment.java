package com.jph.xibaibai.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.model.entity.AllAddress;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.model.utils.MLog;
import com.jph.xibaibai.ui.activity.PayOrderActivity;
import com.jph.xibaibai.ui.activity.ReserveActivity;
import com.jph.xibaibai.ui.fragment.base.BaseFragment;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.FileUtil;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.VoicePlayer;
import com.jph.xibaibai.utils.VoiceRecorder;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

/**
 * 即刻上门
 * Created by jph on 2015/8/21.
 */
public class ParkInfoFragment extends BaseFragment implements View.OnClickListener {

    public static final String BUNDLEKEY_ORDER = "bundlekey_order";

    private Order order;
    private AllAddress mAllAddress;
    //    private File fileVoice;
    private VoicePlayer voicePlayer;
    private IAPIRequests apiRequests;

    @ViewInject(R.id.parkinfo_img_pressvoice)
    ImageView imgRecord;//录音
    @ViewInject(R.id.parkinfo_txt_recordtime)
    TextView txtRecordTime;//录音时间
    @ViewInject(R.id.parkinfo_img_clear)
    ImageView imgClear;//清除已输入
    @ViewInject(R.id.parkinfo_edt_txtdesc)
    EditText edtTxtDesc;//文字描述
    @ViewInject(R.id.parkinfo_vg_contentvoice)
    ViewGroup vgVoiceDesc;
    @ViewInject(R.id.parkinfo_vg_contenttxt)
    ViewGroup vgTxtDesc;
    @ViewInject(R.id.parkinfo_img_homepchecked)
    ImageView imgHomeChecked;
    @ViewInject(R.id.parkinfo_img_companypchecked)
    ImageView imgCompanyChecked;
    @ViewInject(R.id.parkinfo_txt_totalprice)
    TextView mTxtTotalPrice;
    @ViewInject(R.id.parkinfo_txt_address)
    TextView mTxtAddress;
    @ViewInject(R.id.parkinfo_txt_carinfo)
    TextView mTxtCarInfo;
    @ViewInject(R.id.parkinfo_txt_title)
    TextView mTxtTitle;

    @Override
    protected void onCreateView(View contentView) {

    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_parkinfo;
    }

    @Override
    public void initData() {
        super.initData();
        order = (Order) getArguments().getSerializable(BUNDLEKEY_ORDER);
        Log.i("Tag","预约界面=》"+order.getTotal_price());
        SPUserInfo spUserInfo = SPUserInfo.getsInstance(getActivity());
//        Log.i("Tag","getSP=>"+spUserInfo.getSP(SPUserInfo.KEY_ALL_ADDRESS));
        if(!StringUtil.isNull(spUserInfo.getSP(SPUserInfo.KEY_ALL_ADDRESS))){
            mAllAddress = JSON.parseObject(spUserInfo.getSP(SPUserInfo.KEY_ALL_ADDRESS), AllAddress.class);
        }
        apiRequests = new APIRequests(this);
    }

    @Override
    public void initView() {
        super.initView();
        mTxtTitle.setText(order.getOrder_name());
        mTxtAddress.setText(order.getLocation());
        mTxtTotalPrice.setText(String.format(getString(R.string.format_price), order.getTotal_price()));
        mTxtCarInfo.setText(order.getC_plate_num() + " " + order.getC_brand() + " " + order.getC_color());
    }

    @Override
    public void initListener() {
        super.initListener();

        imgRecord.setOnTouchListener(new RecordOnTouchListener());
    }

    @OnClick({R.id.parkinfo_img_clear, R.id.parkinfo_img_change, R.id.parkinfo_btn_reserve, R.id.parkinfo_txt_recordtime})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parkinfo_img_clear:
                //点击清除
                edtTxtDesc.setText("");
                break;
            case R.id.parkinfo_img_change:
                //改变描述方式
                vgTxtDesc.setVisibility(vgTxtDesc.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                vgVoiceDesc.setVisibility(vgVoiceDesc.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.parkinfo_btn_reserve:
                //下单
                if(order != null){
                    if(!(order.getTotal_price() > 0)){
                        showToast("请先选择服务");
                        return;
                    }
                }
                if (getActivity() instanceof ReserveActivity) {
                    ReserveActivity reserveActivity = (ReserveActivity) getActivity();
                    int checkTimeScopeId = reserveActivity.getCheckTimeScopeId();
                    if (checkTimeScopeId <= 0) {
                        showToast("请选择预约时间段");
                        return;
                    }
                    order.setDay(reserveActivity.getCheckDay() / 1000);
                    order.setP_order_time_cid(checkTimeScopeId);
                }
                //跳转支付
                Intent intentPay = new Intent(getActivity(), PayOrderActivity.class);
                intentPay.putExtra(PayOrderActivity.EXTRA_ORDER, order);
                startActivity(intentPay);
//                getActivity().finish();
//                apiRequests.newOrder(order);
                break;
            case R.id.parkinfo_txt_recordtime:
                //点击播放语音
                if (order.getFileVoice() != null) {
                    if (voicePlayer != null) {
                        voicePlayer.stop();
                    } else {
                        voicePlayer = new VoicePlayer(getActivity(), order.getFileVoice());
                        voicePlayer.setCallBack(new VoicePlayer.CallBack() {
                            @Override
                            public void onPlaying(int snum) {
                                txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                        , snum));
                            }

                            @Override
                            public void onFinish(int snum) {
                                txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                        , voicePlayer.getDuration()));
                                voicePlayer = null;
                            }
                        });
                        txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                , voicePlayer.getDuration()));
                        voicePlayer.start();
                    }
                }
                break;
        }
    }

    @OnClick(R.id.parkinfo_vg_homepark)
    public void onClickHomePark(View v) {
        //点击家停车位
        Address addressHome = null;
        if(mAllAddress != null){
            addressHome = mAllAddress.getHomeAddress();
        }
        if (addressHome == null) {
            return;
        }
        imgHomeChecked.setVisibility(View.VISIBLE);
        imgCompanyChecked.setVisibility(View.GONE);
        edtTxtDesc.setText(addressHome.getAddress_info());
        order.setLocation_lt(addressHome.getAddress_lt());
        order.setLocation_lg(addressHome.getAddress_lg());
        vgTxtDesc.setVisibility(View.VISIBLE);
        vgVoiceDesc.setVisibility(View.GONE);
    }

    @OnClick(R.id.parkinfo_vg_companypark)
    public void onClickCompanyPark(View v) {
        //点击公司停车位
        Address addressCompany = null;
        if(mAllAddress != null){
            addressCompany = mAllAddress.getCompanyAddress();
        }
        if (addressCompany == null) {
            return;
        }
        imgHomeChecked.setVisibility(View.GONE);
        imgCompanyChecked.setVisibility(View.VISIBLE);
        edtTxtDesc.setText(addressCompany.getAddress_info());
        order.setLocation_lt(addressCompany.getAddress_lt());
        order.setLocation_lg(addressCompany.getAddress_lg());
        vgTxtDesc.setVisibility(View.VISIBLE);
        vgVoiceDesc.setVisibility(View.GONE);
    }

    @Override
    public void onPrepare(int taskId) {
        super.onPrepare(taskId);
        showProgressDialog();
    }

    @Override
    public void onEnd(int taskId) {
        super.onEnd(taskId);
        dissmissProgressDialog();
    }

    class RecordOnTouchListener implements View.OnTouchListener {

        private VoiceRecorder voiceRecorder;

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //按下开始录音
                    MLog.i(TAG, "----开始录音---");
                    voiceRecorder = new VoiceRecorder(FileUtil.getFile(
                            Constants.PATH_VOICE + File.separator + System.currentTimeMillis() + ".aac"));
                    voiceRecorder.setCallBack(new VoiceRecorder.CallBack() {
                        @Override
                        public void onFailed() {
                            showToast("录音出错");
                        }

                        @Override
                        public void onRecording(int snum) {

                            txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time), snum));
                        }

                        @Override
                        public void onFinish(int snum) {
                            if (snum == 0) {
                                showToast("录制时间过短");
                                if (order.getFileVoice() == null) {
                                    txtRecordTime.setVisibility(View.GONE);
                                } else {
                                    int duration = MediaPlayer.create(getActivity(), Uri.fromFile(order.getFileVoice())).getDuration() / 1000;
                                    txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                                            , duration));
                                }
                            } else {
                                txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time), snum));
                                order.setFileVoice(voiceRecorder.getFileSource());
                                voiceRecorder = null;
                            }
                        }
                    });

                    txtRecordTime.setVisibility(View.VISIBLE);
                    txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time), 0));

                    voiceRecorder.start();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    cancelRecord(voiceRecorder);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (voiceRecorder == null) {
                        break;
                    }
                    final int location[] = new int[2];
                    v.getLocationOnScreen(location);
                    final float fx = location[0] + event.getX();
                    final float fy = location[1] + event.getY();

                    final Rect rect = new Rect();
                    v.getGlobalVisibleRect(rect);
                    rect.offsetTo(location[0], location[1]);

                    if (!rect.contains((int) fx, (int) fy)) {
                        //手指移出按钮外
                        cancelRecord(voiceRecorder);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //停止录音
                    MLog.i(TAG, "----结束录音---");
                    if (voiceRecorder != null)
                        voiceRecorder.stop();
                    break;
            }

            return false;
        }
    }

    /**
     * 取消录音
     *
     * @param voiceRecorder
     */
    private void cancelRecord(VoiceRecorder voiceRecorder) {
        voiceRecorder.cancel();
        voiceRecorder = null;

        if (order.getFileVoice() != null) {
            int duration = MediaPlayer.create(getActivity(), Uri.fromFile(order.getFileVoice())).getDuration() / 1000;
            txtRecordTime.setText(String.format(getString(R.string.nowvisit_format_time)
                    , duration));
        } else {
            txtRecordTime.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.NEWORDER:
                //下单成功
                Order order = JSON.parseObject(responseJson.getResult().toString(), Order.class);
                Intent intentPay = new Intent(getActivity(), PayOrderActivity.class);
                intentPay.putExtra(PayOrderActivity.EXTRA_ORDER, order);
                startActivity(intentPay);
                getActivity().finish();
                break;
        }
    }
}
