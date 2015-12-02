package com.jph.xibaibai.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.TimeScope;
import com.jph.xibaibai.ui.activity.ApointmentTimeActivity;
import com.jph.xibaibai.ui.activity.ReserveActivity;
import com.jph.xibaibai.utils.StringUtil;
import com.jph.xibaibai.utils.SystermUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 预约时间段列表适配器
 * Created by jph on 2015/8/23.
 */
public class RecyclerTimeScopeAdapter extends
        BaseRecyclerAdapter<RecyclerTimeScopeAdapter.ViewHolder, TimeScope> {

    private long day;

    private String currentTime;

    public RecyclerTimeScopeAdapter(List<TimeScope> listData) {
        super(listData);
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
        Log.i("Tag", "currentCalendar=>" + SystermUtils.getTimeScope(currentTime));
    }

    public void setDay(long day) {
        this.day = day;
    }

    /**
     * 是否可预约
     *
     * @param position
     * @return
     */
    public boolean isReservable(int position) {
        TimeScope timeScope = getItem(position);
        return timeScope.getMax() > timeScope.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.itemtimescope_vg_bg)
        ViewGroup vgBg;
        @ViewInject(R.id.itemtimescope_txt_enable)
        TextView txtEnable;
        @ViewInject(R.id.itemtimescope_txt_time)
        TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }

    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = View.inflate(viewGroup.getContext(), R.layout.item_recycler_timescope,
                null);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, int position) {
        super.onRealBindViewHolder(viewHolder, position);
        ApointmentTimeActivity reserveActivity = (ApointmentTimeActivity) viewHolder.itemView.getContext();
        TimeScope timeScope = getItem(position);
        Resources resources = viewHolder.itemView.getResources();
        Calendar dayCalendar = SystermUtils.getCalendar(day * 1000);
        Calendar currentCalendar = SystermUtils.getCalendar(SystermUtils.getTimeScope(currentTime));
        Log.i("Tag", "getCheckDay()=" + reserveActivity.getCheckDay());
        if (reserveActivity.getCheckDay() == day && reserveActivity.getCheckTimeScopeId() == timeScope.getId()) {
            viewHolder.vgBg.setBackgroundColor(resources.getColor(R.color.txt_red));
            viewHolder.txtTime.setTextColor(resources.getColor(R.color.white));
            viewHolder.txtEnable.setTextColor(resources.getColor(R.color.white));
        } else {
            viewHolder.vgBg.setBackgroundColor(resources.getColor(R.color.transparent));
            if (isReservable(position)) {
                viewHolder.txtTime.setTextColor(resources.getColor(R.color.txt_red));
                viewHolder.txtEnable.setTextColor(resources.getColor(R.color.txt_red));
            } else {
                viewHolder.txtTime.setTextColor(resources.getColor(R.color.txt_darkgray));
                viewHolder.txtEnable.setTextColor(resources.getColor(R.color.txt_darkgray));
            }
        }
        if (dayCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)) {
            Calendar calendar = Calendar.getInstance();
            long currentTimeNum = SystermUtils.getTimeScope(currentTime);
            if (!StringUtil.isNull(timeScope.getTime()) && timeScope.getTime().contains("-")) {
                Log.i("Tag", "dayCalendar=" + dayCalendar.get(Calendar.DAY_OF_MONTH) + "/currentCalendar=" + currentCalendar.get(Calendar.DAY_OF_MONTH));
                String[] timeStr = timeScope.getTime().split("-");
                String dayTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                long afternoonTime = SystermUtils.getTimeScope(dayTime + " " + timeStr[1]);
                if (afternoonTime <= currentTimeNum) {
                    viewHolder.vgBg.setBackgroundColor(resources.getColor(R.color.txt_e_gray));
                }
            }
        }
        viewHolder.txtTime.setText(timeScope.getTime());
        viewHolder.txtEnable.setText(isReservable(position) ? "可预约" : "已约满");
    }

    @Override
    public boolean isFootable() {
        return false;
    }
}

