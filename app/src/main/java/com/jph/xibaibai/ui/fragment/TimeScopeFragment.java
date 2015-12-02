package com.jph.xibaibai.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerTimeScopeAdapter;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.TimeScope;
import com.jph.xibaibai.mview.DividerGridItemDecoration;
import com.jph.xibaibai.ui.activity.ApointmentTimeActivity;
import com.jph.xibaibai.ui.activity.ReserveActivity;
import com.jph.xibaibai.ui.fragment.base.BaseFragment;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.SystermUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jph on 2015/8/23.
 */
public class TimeScopeFragment extends BaseFragment implements BaseRecyclerAdapter.OnItemClickListener {

    public static final String EXTRA_DAY = "extra_day";
    public static final String EXTRA_LIST_TIMESCOPE = "extra_list_timescope";
    public static final String CURRENT_TIME = "current_time";
    private long day;
    private String currentTime;
    private RecyclerTimeScopeAdapter timeScopeAdapter;

    @ViewInject(R.id.timescope_recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreateView(View contentView) {
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_timescope;
    }

    @Override
    public void initData() {
        super.initData();
        day = getArguments().getLong(EXTRA_DAY, 0);
        currentTime = getArguments().getString(CURRENT_TIME);
        List<TimeScope> listTimeScope = (List<TimeScope>) getArguments().getSerializable(EXTRA_LIST_TIMESCOPE);
        timeScopeAdapter = new RecyclerTimeScopeAdapter(listTimeScope);
        timeScopeAdapter.setDay(day);
        timeScopeAdapter.setCurrentTime(currentTime);
        Log.i("Tag", "IntentDay=>" + day);
    }

    @Override
    public void initView() {
        super.initView();
        GridLayoutManager gl = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(gl);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity(), R.drawable.shape_divideline_timescope));
        recyclerView.setAdapter(timeScopeAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        timeScopeAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View v, int position) {
        if (!timeScopeAdapter.isReservable(position)) {
            return;
        }

        Calendar dayCalendar = SystermUtils.getCalendar(day * 1000);
        Calendar currentCalendar = SystermUtils.getCalendar(SystermUtils.getTimeScope(currentTime));
        if(dayCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)){
            Calendar calendar = Calendar.getInstance();
            long currentTimeNum = SystermUtils.getTimeScope(currentTime);
            if(!StringUtil.isNull(timeScopeAdapter.getItem(position).getTime()) && timeScopeAdapter.getItem(position).getTime().contains("-")){
                String[] timeStr = timeScopeAdapter.getItem(position).getTime().split("-");
                String dayTime = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH) + 1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
                long afternoonTime = SystermUtils.getTimeScope(dayTime + " " + timeStr[1]);
                if(afternoonTime <= currentTimeNum){
                    showToast("当前时间不可选");
                }else {
                    ((ApointmentTimeActivity) getActivity()).setCheckTimeScopeId(timeScopeAdapter.getItem(position).getId());
                }
            }
        }else {
            ((ApointmentTimeActivity) getActivity()).setCheckTimeScopeId(timeScopeAdapter.getItem(position).getId());
        }
    }

    /**
     * 当前时间点是否可选
     */
    private void isCanChoice(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(((ApointmentTimeActivity) getActivity()).getCheckDay()*1000);
        String d = format.format(time);
        try {
            Date date=format.parse(d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Log.i("Tag","日期=>"+calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    public void notifyRefresh() {
        timeScopeAdapter.notifyDataSetChanged();
    }

}
