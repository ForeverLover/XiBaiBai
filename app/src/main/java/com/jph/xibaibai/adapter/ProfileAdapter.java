package com.jph.xibaibai.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.viewholder.ViewHolderProfileHeader;
import com.jph.xibaibai.model.entity.AllCar;
import com.jph.xibaibai.ui.activity.AddCarActivity;
import com.jph.xibaibai.ui.activity.ProfileActivity;

/**
 * 个人中心的车辆列表适配器
 * Created by jph on 2015/9/16.
 */
public class ProfileAdapter extends RecyclerCarAdapter {
    private ViewHolderProfileHeader viewHolderProfileHeader;
    private ProfileActivity activity;

    public ProfileAdapter(AllCar allCar,ProfileActivity activity) {
        super(allCar);
        this.activity = activity;
    }

    @Override
    public int getRealItemCount() {
        return super.getRealItemCount() + 1;
    }

    @Override
    public int getRealItemViewType(int position) {
        if (position == 0) {
            return R.layout.v_header_profile;
        }
        return super.getRealItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == R.layout.v_header_profile) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(viewType, viewGroup, false);
            viewHolderProfileHeader = new ViewHolderProfileHeader(v);
            return viewHolderProfileHeader;
        }
        return super.onRealCreateViewHolder(viewGroup, viewType);
    }

    @Override
    public void onRealBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position > 0) {
            super.onRealBindViewHolder(viewHolder, position - 1);
        }
    }

    public ViewHolderProfileHeader getViewHolderProfileHeader() {
        return viewHolderProfileHeader;
    }

    @Override
    public View createFooterView(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_footer_profile, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intentAddCar = new Intent(v.getContext(), AddCarActivity.class);
                v.getContext().startActivity(intentAddCar);*/
                activity.toAddCar();
            }
        });
        return v;
    }
}
