package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Message;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 系统消息列表适配器
 * Created by jph on 2015/9/20.
 */
public class RecyclerMessageAdapter extends BaseRecyclerAdapter<RecyclerMessageAdapter.ViewHolder, Message> {

    public RecyclerMessageAdapter(List<Message> listData) {
        super(listData);
    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, int position) {
        Message message = getItem(position);
        viewHolder.txtTitle.setText(message.getA_m_tit());
        viewHolder.txtContent.setText(message.getA_m_con());
        viewHolder.txtTime.setText(TimeUtil.getMFormatStringByMill(message.getA_m_time()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.item_message_txt_title)
        TextView txtTitle;
        @ViewInject(R.id.item_message_txt_content)
        TextView txtContent;
        @ViewInject(R.id.item_message_txt_time)
        TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

}
