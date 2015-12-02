package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.ui.fragment.ParkInfoFragment;
import com.jph.xibaibai.ui.fragment.TimeScopeFragment;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 预约上门
 * Created by jph on 2015/8/23.
 */
public class ReserveActivity extends TitleActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    public static final String INTENTKEY_ORDER = "intentkey_order";
    private PageFragAdapter fragAdapter;
    private IAPIRequests mAPIRequests;
    private int mCheckTimeScopeId = -1;
    private long day0, day1, day2;

    @ViewInject(R.id.reserve_rgroup_day)
    RadioGroup mRGroupDay;
    @ViewInject(R.id.reserve_rbtn_today)
    RadioButton mRBtnToday;
    @ViewInject(R.id.reserve_rbtn_tommorow)
    RadioButton mRBtnTommorow;
    @ViewInject(R.id.reserve_rbtn_aftertommorow)
    RadioButton mRBtnAfterTommorow;
    @ViewInject(R.id.reserve_pager_timescope)
    ViewPager viewPagerTimeScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        mAPIRequests.getTimeScope(System.currentTimeMillis() / 1000);
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
        day0 = System.currentTimeMillis()/1000;
        day1 = day0 + 24 * 60 * 60 ;
        day2 = day0 + 2 * 24 * 60 * 60 ;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("预约上门");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment parkInfoFragment = new ParkInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ParkInfoFragment.BUNDLEKEY_ORDER, getIntent().getSerializableExtra(INTENTKEY_ORDER));
        parkInfoFragment.setArguments(bundle);
        ft.add(R.id.reserve_frame_container2, parkInfoFragment, "Reserve");
        ft.commit();

        mRBtnToday.setText("今天");
        mRBtnTommorow.setText(TimeUtil.getFormatStringByMill(day1, "MM月dd日"));
        mRBtnAfterTommorow.setText(TimeUtil.getFormatStringByMill(day2, "MM月dd日"));
    }

    @Override
    public void initListener() {
        super.initListener();
        viewPagerTimeScope.addOnPageChangeListener(this);
    }

    @OnRadioGroupCheckedChange(R.id.reserve_rgroup_day)
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.reserve_rbtn_today:
                viewPagerTimeScope.setCurrentItem(0);
                break;
            case R.id.reserve_rbtn_tommorow:
                viewPagerTimeScope.setCurrentItem(1);
                break;
            case R.id.reserve_rbtn_aftertommorow:
                viewPagerTimeScope.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRGroupDay.check(R.id.reserve_rbtn_today);
                break;
            case 1:
                mRGroupDay.check(R.id.reserve_rbtn_tommorow);
                break;
            case 2:
                mRGroupDay.check(R.id.reserve_rbtn_aftertommorow);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GET_TIME_SCOPE:
                AllTimeScope allTimeScope = JSON.parseObject(responseJson.getResult().toString(), AllTimeScope.class);
                setViewPager(allTimeScope);
                break;

        }
    }

    private void setViewPager(AllTimeScope allTimeScope) {
        List<Fragment> listFrag = new ArrayList<Fragment>();

        Fragment fragToday = new TimeScopeFragment();
        Bundle bToday = new Bundle();
        bToday.putLong(TimeScopeFragment.EXTRA_DAY, day0);
        bToday.putSerializable(TimeScopeFragment.EXTRA_LIST_TIMESCOPE, (Serializable) allTimeScope.getOne());
        fragToday.setArguments(bToday);
        listFrag.add(fragToday);

        Fragment fragTommorow = new TimeScopeFragment();
        Bundle bTommorow = new Bundle();
        bTommorow.putLong(TimeScopeFragment.EXTRA_DAY, day1);
        bTommorow.putSerializable(TimeScopeFragment.EXTRA_LIST_TIMESCOPE, (Serializable) allTimeScope.getTwo());
        fragTommorow.setArguments(bTommorow);
        listFrag.add(fragTommorow);

        Fragment fragAfterTommorow = new TimeScopeFragment();
        Bundle bAftertommorow = new Bundle();
        bAftertommorow.putLong(TimeScopeFragment.EXTRA_DAY, day2);
        bAftertommorow.putSerializable(TimeScopeFragment.EXTRA_LIST_TIMESCOPE, (Serializable) allTimeScope.getThree());
        fragAfterTommorow.setArguments(bAftertommorow);
        listFrag.add(fragAfterTommorow);

        fragAdapter = new PageFragAdapter(getSupportFragmentManager(), listFrag);
        viewPagerTimeScope.setAdapter(fragAdapter);
        viewPagerTimeScope.setOffscreenPageLimit(3);
        mRGroupDay.check(R.id.reserve_rbtn_today);
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
        switch (viewPagerTimeScope.getCurrentItem()) {
            case 0:
                return day0;
            case 1:
                return day1;
            case 2:
                return day2;
        }
        return 0;
    }
}
