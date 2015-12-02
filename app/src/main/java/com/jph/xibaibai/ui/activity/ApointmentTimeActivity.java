package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.PageFragAdapter;
import com.jph.xibaibai.model.entity.AllTimeScope;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.jph.xibaibai.ui.fragment.TimeScopeFragment;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2015/11/8.
 * 预约时间点选择
 */
public class ApointmentTimeActivity extends BaseActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @ViewInject(R.id.apoint_rgroup_day)
    private RadioGroup apoint_rgroup_day;// group
    @ViewInject(R.id.apoint_rbtn_today)
    private RadioButton apoint_rbtn_today;//今天
    @ViewInject(R.id.apoint_rbtn_tommorow)
    private RadioButton apoint_rbtn_tommorow;//明天
    @ViewInject(R.id.apoint_rbtn_aftertommorow)
    private RadioButton apoint_rbtn_aftertommorow;//后天
    @ViewInject(R.id.apoint_time_gridview)
    private GridView apoint_time_gridview;
    @ViewInject(R.id.apoint_cancel_btn)
    private Button apoint_cancel_btn; //取消按钮
    @ViewInject(R.id.apoint_cofirm_btn)
    private Button apoint_cofirm_btn; // 确认按钮
    @ViewInject(R.id.reserve_pager_timescope)
    private ViewPager reserve_pager_timescope;

    private long day0, day1, day2;

    private IAPIRequests mAPIRequests;

    private int mCheckTimeScopeId = -1;  //时间段的id

    private int pagePosition = -1; // 选中的是哪一个page页面

    private PageFragAdapter fragAdapter;

    private AllTimeScope allTimeScope = null;

    private String currentTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        setContentView(R.layout.activity_apointtime);
    }

    @Override
    public void initData() {
        apoint_rbtn_today.setText("今天");
        day0 = System.currentTimeMillis()/1000;
        day1 = day0 + 24 * 60 * 60;
        day2 = day0 + 2 * 24 * 60 * 60;
        apoint_rgroup_day.setOnCheckedChangeListener(this);
        apoint_cancel_btn.setOnClickListener(this);
        apoint_cofirm_btn.setOnClickListener(this);
        reserve_pager_timescope.addOnPageChangeListener(this);
    }

    @Override
    public void initView() {
        mAPIRequests = new APIRequests(this);
        mAPIRequests.getTimeScope(System.currentTimeMillis() / 1000);
        apoint_rbtn_today.setText("今天");
        apoint_rbtn_tommorow.setText(TimeUtil.getFormatStringByMill(day1, "MM月dd日"));
        apoint_rbtn_aftertommorow.setText(TimeUtil.getFormatStringByMill(day2, "MM月dd日"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apoint_cancel_btn:
                // 取消按钮
                finish();
                break;
            case R.id.apoint_cofirm_btn:
                // 确认按钮
                Log.i("Tag", "currentDay=" + getCheckDay() + "productId=" + getCheckTimeScopeId() + "pageIndex=" + pagePosition);
                if(allTimeScope != null){
                }
                Intent intent = new Intent();
                intent.putExtra("selectedDay",getCheckDay());
                intent.putExtra("selectedTimeScopeId", getCheckTimeScopeId());
                intent.putExtra("selectedTimeScope",getCurrentTime());
                intent.putExtra("selectedDate", getCurrentDate());
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    private String getCurrentDate(){
        switch (pagePosition){
            case 1:
                return apoint_rbtn_tommorow.getText().toString();
            case 2:
                return apoint_rbtn_tommorow.getText().toString();
            case 3:
                return apoint_rbtn_aftertommorow.getText().toString();
        }
        return "";
    }

    private String getCurrentTime(){
        if(allTimeScope == null){
            return "";
        }
        switch (pagePosition){
            case 1:
                return allTimeScope.getOne().get(getCheckTimeScopeId()).getTime();
            case 2:
                return allTimeScope.getTwo().get(getCheckTimeScopeId()).getTime();
            case 3:
                return allTimeScope.getThree().get(getCheckTimeScopeId()).getTime();
        }
        return "";
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.apoint_rbtn_today:
                reserve_pager_timescope.setCurrentItem(0);
                break;
            case R.id.apoint_rbtn_tommorow:
                reserve_pager_timescope.setCurrentItem(1);
                break;
            case R.id.apoint_rbtn_aftertommorow:
                reserve_pager_timescope.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPrepare(int taskId) {

    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GET_TIME_SCOPE:
                Log.i("Tag", "预约时间点：" + responseJson.getResult().toString());
                allTimeScope = JSON.parseObject(responseJson.getResult().toString(), AllTimeScope.class);
                if (allTimeScope != null) {
//                    Log.i("Tag","currenttime="+allTimeScope.getCurrenttime());
                    currentTime = allTimeScope.getCurrenttime();
                    setViewPager(allTimeScope);
                }
                break;
        }
    }

    private void setViewPager(AllTimeScope allTimeScope) {
        List<Fragment> listFrag = new ArrayList<Fragment>();

        Fragment fragToday = new TimeScopeFragment();
        Bundle bToday = new Bundle();
        bToday.putLong(TimeScopeFragment.EXTRA_DAY, day0);
        bToday.putString(TimeScopeFragment.CURRENT_TIME, currentTime);
        bToday.putSerializable(TimeScopeFragment.EXTRA_LIST_TIMESCOPE, (Serializable) allTimeScope.getOne());
        fragToday.setArguments(bToday);
        listFrag.add(fragToday);

        Fragment fragTommorow = new TimeScopeFragment();
        Bundle bTommorow = new Bundle();
        bTommorow.putLong(TimeScopeFragment.EXTRA_DAY, day1);
        bTommorow.putString(TimeScopeFragment.CURRENT_TIME, currentTime);
        bTommorow.putSerializable(TimeScopeFragment.EXTRA_LIST_TIMESCOPE, (Serializable) allTimeScope.getTwo());
        fragTommorow.setArguments(bTommorow);
        listFrag.add(fragTommorow);

        Fragment fragAfterTommorow = new TimeScopeFragment();
        Bundle bAftertommorow = new Bundle();
        bAftertommorow.putLong(TimeScopeFragment.EXTRA_DAY, day2);
        bAftertommorow.putString(TimeScopeFragment.CURRENT_TIME, currentTime);
        bAftertommorow.putSerializable(TimeScopeFragment.EXTRA_LIST_TIMESCOPE, (Serializable) allTimeScope.getThree());
        fragAfterTommorow.setArguments(bAftertommorow);
        listFrag.add(fragAfterTommorow);

        fragAdapter = new PageFragAdapter(getSupportFragmentManager(), listFrag);
        reserve_pager_timescope.setAdapter(fragAdapter);
        reserve_pager_timescope.setOffscreenPageLimit(3);

        apoint_rgroup_day.check(R.id.apoint_rbtn_today);
    }

    public int getCheckTimeScopeId() {
        return mCheckTimeScopeId;
    }

    public void setCheckTimeScopeId(int checkTimeScopeId) {
        this.mCheckTimeScopeId = checkTimeScopeId;
        for (int i = 0; i < fragAdapter.getCount(); i++) {
            TimeScopeFragment timeScopeFrag = (TimeScopeFragment) fragAdapter.getItem(i);
            timeScopeFrag.notifyRefresh();
        }
    }

    public long getCheckDay() {
        switch (reserve_pager_timescope.getCurrentItem()) {
            case 0:
                pagePosition = 1;
                return day0;
            case 1:
                pagePosition = 2;
                return day1;
            case 2:
                pagePosition = 3;
                return day2;
        }
        return 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                apoint_rgroup_day.check(R.id.apoint_rbtn_today);
                break;
            case 1:
                apoint_rgroup_day.check(R.id.apoint_rbtn_tommorow);
                break;
            case 2:
                apoint_rgroup_day.check(R.id.apoint_rbtn_aftertommorow);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
