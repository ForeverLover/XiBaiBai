package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 搜索地址列表
 * Created by jph on 2015/9/13.
 */
public class RecyclerSearchAddressAdapter extends BaseRecyclerAdapter<RecyclerSearchAddressAdapter.ViewHolder, PoiInfo> {


    public RecyclerSearchAddressAdapter(List<PoiInfo> listData) {
        super(listData);
    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_searchaddress,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, int position) {
        super.onRealBindViewHolder(viewHolder, position);
        PoiInfo poiInfo = getItem(position);
        viewHolder.txtName.setText(poiInfo.name);
        viewHolder.txtAddress.setText(poiInfo.address);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.itemsearchaddress_txt_name)
        TextView txtName;
        @ViewInject(R.id.itemsearchaddress_txt_address)
        TextView txtAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
