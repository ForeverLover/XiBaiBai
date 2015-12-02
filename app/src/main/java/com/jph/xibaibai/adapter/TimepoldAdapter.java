package com.jph.xibaibai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.TimeScope;

import java.util.List;

/**
 * Created by Eric on 2015/11/8.
 * 时间段的适配器
 */
public class TimepoldAdapter extends BaseAdapter {

    private List<TimeScope> timeList = null;

    private int clickPosition;

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }

    public TimepoldAdapter(List<TimeScope> timeList){
        this.timeList = timeList;
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timesold_apoint,null);
            holder.item_timesold_layout = (RelativeLayout) convertView.findViewById(R.id.item_timesold_layout);
            holder.item_soldshow_tv = (TextView) convertView.findViewById(R.id.item_soldshow_tv);
            holder.item_isappoint_tv = (TextView) convertView.findViewById(R.id.item_isappoint_tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_soldshow_tv.setText(timeList.get(position).getTime());
        holder.item_isappoint_tv.setText("可预约");
        return convertView;
    }

    class ViewHolder{
        private RelativeLayout item_timesold_layout;
        private TextView item_soldshow_tv;
        private TextView item_isappoint_tv;
    }
}
