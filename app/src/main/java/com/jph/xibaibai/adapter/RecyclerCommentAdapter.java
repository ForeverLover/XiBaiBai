package com.jph.xibaibai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.model.entity.Comment;
import com.jph.xibaibai.utils.MImageLoader;
import com.jph.xibaibai.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 评价列表
 * Created by jph on 2015/9/13.
 */
public class RecyclerCommentAdapter extends BaseRecyclerAdapter<RecyclerCommentAdapter.ViewHolder, Comment> {


    public RecyclerCommentAdapter(List<Comment> listData) {
        super(listData);
    }

    @Override
    public ViewHolder onRealCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_comment,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(ViewHolder viewHolder, int position) {
        super.onRealBindViewHolder(viewHolder, position);
        Comment comment = getItem(position);
        viewHolder.txtTime.setText(TimeUtil.getMFormatStringByMill(comment.getComment_time()));
        viewHolder.txtCar.setText(comment.getC_plate_num() + " " + comment.getC_car());
        viewHolder.txtEmployeeName.setText(comment.getEmp_name());
        viewHolder.txtEmployeePhone.setText(comment.getEmp_iphone());
        viewHolder.txtEmployeeStar.setText(comment.getStar() + "分");
        viewHolder.ratingBarEmployeeStar.setRating(comment.getStar());
        MImageLoader.getInstance(viewHolder.itemView.getContext()).displayImageM(comment.getEmp_img(), viewHolder.imgEmployeeHead);
        viewHolder.txtContent.setText(comment.getComment());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.itemcomment_v_line1)
        View vLine1;
        @ViewInject(R.id.itemcomment_v_line2)
        View vLine2;
        @ViewInject(R.id.itemcomment_img_employeehead)
        ImageView imgEmployeeHead;
        @ViewInject(R.id.itemcomment_txt_employeename)
        TextView txtEmployeeName;
        @ViewInject(R.id.itemcomment_txt_employeephone)
        TextView txtEmployeePhone;
        @ViewInject(R.id.itemcomment_txt_employeestar)
        TextView txtEmployeeStar;
        @ViewInject(R.id.itemcomment_ratingbar_employeestar)
        RatingBar ratingBarEmployeeStar;
        @ViewInject(R.id.itemcomment_txt_car)
        TextView txtCar;
        @ViewInject(R.id.itemcomment_txt_time)
        TextView txtTime;
        @ViewInject(R.id.itemcomment_txt_content)
        TextView txtContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
            vLine1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            vLine2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
}
