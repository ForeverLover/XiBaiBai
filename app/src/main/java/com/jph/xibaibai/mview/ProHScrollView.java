package com.jph.xibaibai.mview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Product;
import com.jph.xibaibai.ui.activity.WebActivity;
import com.jph.xibaibai.utils.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 首页产品横向滑动的srollview
 * Created by jph on 2015/9/4.
 */
public class ProHScrollView extends HorizontalScrollView {
    private int itemWidth = 0;
    private int itemHeight = 0;
    private int spaceWidth = 0;
    private int checkPosition = -1;
    private OnItemCheckListener onItemCheckListener;
    private List<Product> mListPro;

    private LocalBroadcastManager localBroadcastManager = null;

    private LocalReceiver localReceiver = null;

    @ViewInject(R.id.prohscroll_ll_pros)
    LinearLayout llPros;
    @ViewInject(R.id.prohscroll_v_left)
    View vLeft;
    @ViewInject(R.id.prohscroll_v_right)
    View vRight;

    public ProHScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        getDataBroadcast(context);
    }

    public ProHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        getDataBroadcast(context);
    }

    public ProHScrollView(Context context) {
        super(context);
        init();
        getDataBroadcast(context);
    }

    private void getDataBroadcast(Context context){
        localReceiver = new LocalReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xbb.broadcast.LOCAL_UPDATE_PRICE");
        //通过LocalBroadcastManager的getInstance()方法得到它的一个实例
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.v_prohscroll, this, true);
        ViewUtils.inject(this, this);

        itemWidth = getResources().getDimensionPixelOffset(R.dimen.main_proitem_width);
        itemHeight = getResources().getDimensionPixelOffset(R.dimen.main_proitem_height);
    }

    public void setup(List<Product> listPro,int flag) {
        if (listPro == null) {
            return;
        }
        mListPro = listPro;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        llPros.removeAllViews();
        int count = listPro.size();
        for (int i = 0; i < count; i++) {
            final Product product = listPro.get(i);
            View vItem = layoutInflater.inflate(R.layout.item_recycler_product, llPros, false);
            ViewGroup vgBg = (ViewGroup) vItem.findViewById(R.id.itemproduct_vg_bg);
            TextView txtTitle = (TextView) vItem.findViewById(R.id.itemproduct_txt_title);
            TextView txtPrice = (TextView) vItem.findViewById(R.id.itemproduct_txt_price);
            TextView txtContent = (TextView) vItem.findViewById(R.id.itemproduct_txt_content);

            txtTitle.setText(product.getP_name());
            txtContent.setText(product.getP_info());
            txtPrice.setText(String.format(getContext().
                    getString(R.string.format_price), product.getP_price()));
            vItem.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    WebActivity.startWebActivity(v.getContext(), product.getP_name(), Constants.URL_WEB_BASE_PRODUCT + product.getId());
                    return true;
                }
            });
            llPros.addView(vItem);
        }
        if(flag == 0){
            checkItem(0);
        }else {
            checkItem(mListPro.size() - 1);
            changeShowStatus(mListPro.size()-1,true);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        spaceWidth = (getMeasuredWidth() - itemWidth) / 2;
        LinearLayout.LayoutParams lpSpace = new LinearLayout.LayoutParams(spaceWidth, itemHeight);
        vLeft.setLayoutParams(lpSpace);
        vRight.setLayoutParams(lpSpace);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (llPros.getChildCount() == 0) {
            return super.onTouchEvent(ev);
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int position = -1;
            int remainder = getScrollX() % itemWidth;
//            if (remainder != 0 && getScrollX() != getWidth()) {
            int scrollX = 0;
            if (remainder < itemWidth / 2) {
                position = getScrollX() / itemWidth;
            } else {
                position = getScrollX() / itemWidth + 1;
            }
            scrollX = itemWidth * position;
            post(new RScroll(scrollX));
            checkItem(position);
//            }

            return true;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 选中某个
     *
     * @param position
     */
    private void checkItem(int position) {
        if (checkPosition == position) {
            return;
        }
        if (checkPosition >= 0) {
            changeShowStatus(checkPosition, false);
        }
        if (position >= 0) {
            changeShowStatus(position, true);
        }
        checkPosition = position;
        if (onItemCheckListener != null) {
            onItemCheckListener.onItemCheck(position, mListPro.get(position));
        }
    }

    /**
     * 改变是否选中显示状态
     */

    private void changeShowStatus(int position, boolean isCheck) {
        View vItem = llPros.getChildAt(position);
        ViewGroup vgBg = (ViewGroup) vItem.findViewById(R.id.itemproduct_vg_bg);
        TextView txtTitle = (TextView) vItem.findViewById(R.id.itemproduct_txt_title);
        TextView txtPrice = (TextView) vItem.findViewById(R.id.itemproduct_txt_price);
        TextView txtContent = (TextView) vItem.findViewById(R.id.itemproduct_txt_content);
        if (isCheck) {
            vgBg.setBackgroundColor(getResources().getColor(R.color.bg_transp));
            txtTitle.setTextColor(getResources().getColor(R.color.white));
            txtPrice.setTextColor(getResources().getColor(R.color.white));
            txtContent.setTextColor(getResources().getColor(R.color.white));
        } else {
            vgBg.setBackgroundColor(getResources().getColor(R.color.bg_transp));
            txtTitle.setTextColor(getResources().getColor(R.color.black));
            txtPrice.setTextColor(getResources().getColor(R.color.txt_orange));
            txtContent.setTextColor(getResources().getColor(R.color.txt_darkgray));
        }
    }

    public OnItemCheckListener getOnItemCheckListener() {
        return onItemCheckListener;
    }

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

    private class RScroll implements Runnable {

        private int scrollX;

        public RScroll(int scrollX) {
            super();
            this.scrollX = scrollX;
        }

        @Override
        public void run() {
            smoothScrollTo(scrollX, 0);
        }

    }

    public List<Product> getListPro() {
        return mListPro;
    }

    public interface OnItemCheckListener {
        void onItemCheck(int position, Product product);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.xbb.broadcast.LOCAL_UPDATE_PRICE")){
                double totalprice = intent.getDoubleExtra("DIY_Totalprice",0);
//                Log.i("Tag","DIYprice=>"+totalprice);
                if(mListPro != null){
                    mListPro.get(mListPro.size()-1).setP_price(totalprice);
                    setup(mListPro, -1);
                }
            }
        }
    }
}

