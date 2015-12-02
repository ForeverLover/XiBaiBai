package com.jph.xibaibai.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.DIYGroup;
import com.jph.xibaibai.model.entity.DIYProduct;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by jph on 2015/9/17.
 */
public class ViewHolderDIYHeader extends RecyclerView.ViewHolder {
    @ViewInject(R.id.diypro_rgroup_pros)
    public RadioGroup mRGroupPro;
    @ViewInject(R.id.diy_rgroup_group)
    public RadioGroup mRGroupGroup;

    public ViewHolderDIYHeader(View itemView, List<DIYGroup> listGroup, List<DIYProduct> listP) {
        super(itemView);
        ViewUtils.inject(this, itemView);
        showViewContent(listGroup, listP);
    }

    private void showViewContent(List<DIYGroup> listGroup, List<DIYProduct> listP) {
        showGroupView(listGroup);
        showProductView(listP);
    }

    private void showGroupView(List<DIYGroup> listGroup) {
        if (listGroup == null || listGroup.isEmpty()) {
            return;
        }
        mRGroupGroup.removeAllViews();
        Context context = itemView.getContext();
        int dividerHeight = context.getResources().getDimensionPixelOffset(R.dimen.size_divider);
        for (int i = 0; i < listGroup.size(); i++) {
            if (i != 0) {
                View vDivider = new View(context);
                vDivider.setBackgroundColor(context.getResources().getColor(R.color.divider));
                mRGroupGroup.addView(vDivider, RadioGroup.LayoutParams.MATCH_PARENT, dividerHeight);
            }
            DIYGroup group = listGroup.get(i);
            RadioButton rbtn = (RadioButton) LayoutInflater.from(context).inflate(R.layout.rgroup_item_product, mRGroupPro, false);
            rbtn.setText(group.getGroupName());
            rbtn.setId(i + 1);//索引+1为id
            mRGroupGroup.addView(rbtn);
        }
    }

    private void showProductView(List<DIYProduct> listP) {
        if (listP == null || listP.isEmpty()) {
            return;
        }
        mRGroupPro.removeAllViews();
        Context context = itemView.getContext();
        int dividerHeight = context.getResources().getDimensionPixelOffset(R.dimen.size_divider);
        for (int i = 0; i < listP.size(); i++) {
            if (i != 0) {
                View vDivider = new View(context);
                vDivider.setBackgroundColor(context.getResources().getColor(R.color.divider));
                mRGroupPro.addView(vDivider, RadioGroup.LayoutParams.MATCH_PARENT, dividerHeight);
            }
            DIYProduct pro = listP.get(i);
            RadioButton rbtn = (RadioButton) LayoutInflater.from(context).inflate(R.layout.rgroup_item_product, mRGroupPro, false);
            rbtn.setText(pro.getP_name() + " (" + String.format(context.getString(R.string.format_price), pro.getP_price()) + ")");
            rbtn.setId(i + 1);//设置控件id为对应的产品id
            rbtn.setTag(pro);
            mRGroupPro.addView(rbtn);
        }
    }

    /**
     * 得到选中的产品id
     *
     * @return
     */
    public int getCheckedProductID() {
        return mRGroupPro.getCheckedRadioButtonId();
    }
}
