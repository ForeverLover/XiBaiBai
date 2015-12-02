package com.jph.xibaibai.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.BaseRecyclerAdapter;
import com.jph.xibaibai.adapter.viewholder.ViewHolderDIYFooter;
import com.jph.xibaibai.adapter.viewholder.ViewHolderDIYHeader;
import com.jph.xibaibai.model.entity.DIYGroup;
import com.jph.xibaibai.model.entity.DIYProduct;
import com.jph.xibaibai.ui.activity.DIYProActivity;
import com.jph.xibaibai.utils.MImageLoader;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jph on 2015/9/14.
 */
public class RecyclerDIYProAdapter extends
        BaseRecyclerAdapter<RecyclerView.ViewHolder, DIYProduct> {

    private ViewHolderDIYHeader viewHolderDIYHeader;
    private ViewHolderDIYFooter viewHolderDIYFooter;
    private List<DIYProduct> listP;
    private List<DIYGroup> listGroup;
    private List<DIYProduct> listCheckedService = new ArrayList<DIYProduct>();

    public RecyclerDIYProAdapter(List<DIYGroup> listGroup, List<DIYProduct> listP, List<DIYProduct> listS) {
        super(listS);
        this.listP = listP;
        this.listGroup = listGroup;
    }

    public void changeCheckStatus(int position) {
        viewHolderDIYHeader.mRGroupGroup.check(0);
        if (listCheckedService.contains(getItem(position))) {
            listCheckedService.remove(getItem(position));
        } else {
            listCheckedService.add(getItem(position));
        }
        notifyDataSetChanged();
        if (viewHolderDIYFooter != null) {
            viewHolderDIYFooter.txtTotalPrice.setText(
                    String.format(viewHolderDIYHeader.mRGroupGroup.getContext().getString(R.string.format_price), getTotalPrice()));
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        int checkProId = viewHolderDIYHeader.mRGroupPro.getCheckedRadioButtonId();
        if (checkProId > 0) {
            View vCheck = viewHolderDIYHeader.mRGroupPro.findViewById(checkProId);
            DIYProduct product = (DIYProduct) vCheck.getTag();
            totalPrice += product.getP_price();
        }
        for (DIYProduct diyProduct : listCheckedService) {
            totalPrice += diyProduct.getP_price();
        }
        return totalPrice;
    }

    private void handleResult(Activity activity) {
        double totalPrice = 0;
        List<Integer> listProIds = new ArrayList<Integer>();
        int checkProId = viewHolderDIYHeader.mRGroupPro.getCheckedRadioButtonId();
        if (checkProId > 0) {
            View vCheck = viewHolderDIYHeader.mRGroupPro.findViewById(checkProId);
            DIYProduct product = (DIYProduct) vCheck.getTag();
            listProIds.add(product.getId());
            totalPrice += product.getP_price();
        } else {
            Toast.makeText(activity, "DIY服务需要和洗车服务一起下单", Toast.LENGTH_SHORT).show();
            return;
        }
        for (DIYProduct diyProduct : listCheckedService) {
            listProIds.add(diyProduct.getId());
            totalPrice += diyProduct.getP_price();
        }

        Intent intentResult = new Intent();
        if (listProIds == null || listProIds.isEmpty()) {
            Toast.makeText(activity, "请至少选择一个产品", Toast.LENGTH_SHORT).show();
            return;
        }
        intentResult.putExtra(DIYProActivity.RESULT_PROIDS, (Serializable) listProIds);
        intentResult.putExtra(DIYProActivity.RESULT_TOTAL_PRICE, totalPrice);
        activity.setResult(Activity.RESULT_OK, intentResult);
        activity.finish();
    }

    @Override
    public int getRealItemCount() {
        return super.getRealItemCount() + 2;
    }

    @Override
    public int getRealItemViewType(int position) {
        if (position == 0) {
            return R.layout.v_header_diypro;
        } else if (position == getRealItemCount() - 1) {
            return R.layout.v_footer_diypro;
        }
        return R.layout.item_recycler_diypro;
    }

    @Override
    public RecyclerView.ViewHolder onRealCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = View.inflate(viewGroup.getContext(), viewType,
                null);
        if (viewType == R.layout.v_header_diypro) {
            viewHolderDIYHeader = new ViewHolderDIYHeader(v, listGroup, listP);
            viewHolderDIYHeader.mRGroupGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId < 1) {
                        return;
                    }
                    DIYGroup diyGroup = listGroup.get(checkedId - 1);
                    listCheckedService.clear();
                    for (DIYProduct product : getList()) {
                        if (diyGroup.getPro_ids().contains(product.getId())) {
                            listCheckedService.add(product);
                        }
                    }
                    notifyDataSetChanged();
                    if (viewHolderDIYFooter != null) {
                        viewHolderDIYFooter.txtTotalPrice.setText(
                                String.format(group.getContext().getString(R.string.format_price), getTotalPrice()));
                    }
                }
            });
            viewHolderDIYHeader.mRGroupPro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (viewHolderDIYFooter != null) {
                        viewHolderDIYFooter.txtTotalPrice.setText(
                                String.format(group.getContext().getString(R.string.format_price), getTotalPrice()));
                    }
                }
            });
            return viewHolderDIYHeader;
        } else if (viewType == R.layout.v_footer_diypro) {
            viewHolderDIYFooter = new ViewHolderDIYFooter(v);
            viewHolderDIYFooter.txtTotalPrice.setText(
                    String.format(viewGroup.getContext().getString(R.string.format_price), getTotalPrice()));
            viewHolderDIYFooter.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) v.getContext();
                    handleResult(activity);
                }
            });
            return viewHolderDIYFooter;
        }
        return new ViewHolder(v);
    }

    @Override
    public void onRealBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0 || position == getRealItemCount() - 1) {
            return;
        }
        position = position - 1;
        super.onRealBindViewHolder(viewHolder, position);
        ViewHolder viewHolderDIY = (ViewHolder) viewHolder;
        Context context = viewHolder.itemView.getContext();
        DIYProduct diyProduct = getItem(position);

        viewHolderDIY.txtName.setText(diyProduct.getP_name());
        viewHolderDIY.txtPrice.setText(String.format(context.
                getString(R.string.format_price), diyProduct.getP_price()));
        Log.i("Tag", "picUrl=>" + diyProduct.getP_ximg());
        if (listCheckedService.contains(getItem(position))) {
            MImageLoader.getInstance(context).displayImageM(diyProduct.getP_ximg(), viewHolderDIY.imgIcon);
        } else {
            MImageLoader.getInstance(context).displayImageM(diyProduct.getP_wimg(), viewHolderDIY.imgIcon);
        }
    }

    @Override
    public View createFooterView(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_footer_diypro,
                parent, false);
        return v;
    }

    @Override
    public boolean isFootable() {
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.itemdiypro_img_icon)
        ImageView imgIcon;
        @ViewInject(R.id.itemdiypro_txt_name)
        TextView txtName;
        @ViewInject(R.id.itemdiypro_txt_price)
        TextView txtPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }

    }


}

