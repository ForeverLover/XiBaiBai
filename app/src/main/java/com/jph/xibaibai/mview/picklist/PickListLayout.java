package com.jph.xibaibai.mview.picklist;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.CarBrand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 选择的布局
 *
 * @author User
 */
public class PickListLayout extends LinearLayout {

    private CharacterParser characterParser;
    private SortAdapter sortAdapter;
    private List<CarBrand> listSortDatas;
    private String headerContent;// 重新设置的head内容

    private ListView listViewCitys;//
    private SideBar sideBar;//
    private TextView txtDia;// 字母弹出
    private ClearEditText edtFilt;// 筛选
    private AdapterView.OnItemClickListener mOnItemClickListener;

    // Heder
    private boolean isShowHeader = false;// 是否显示头部
    private String headerTag;// header的Tag
    private String headerDefaultContent;// header内容

    private TextView txtLocationCity;// 定位的城市

    public PickListLayout(Context context) {
        this(context, null);
    }

    public PickListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 设置布局文件
        LayoutInflater.from(context).inflate(R.layout.ll_pickcity, this, true);

        // 获得属性值
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PickListLayout);
        isShowHeader = a.getBoolean(R.styleable.PickListLayout_isShowHeader,
                false);
        headerTag = a.getString(R.styleable.PickListLayout_headerTag);
        headerDefaultContent = a
                .getString(R.styleable.PickListLayout_headerContent);

        a.recycle();

    }

    public void setData(List<CarBrand> datas) {

        if (datas != null) {
            listSortDatas.clear();
            listSortDatas.addAll(filledData(datas));

            sortAdapter.updateListView(listSortDatas);
            // notifi
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initData();
        initView();
        initListener();

    }

    protected void initData() {

        characterParser = CharacterParser.getInstance();

        listSortDatas = filledData(new ArrayList<CarBrand>());
        sortAdapter = new SortAdapter(getContext(), listSortDatas);
    }

    protected void initView() {
        listViewCitys = (ListView) findViewById(R.id.picklist_list);
        sideBar = (SideBar) findViewById(R.id.picklist_sidrbar);
        txtDia = (TextView) findViewById(R.id.picklist_txt_dia);
        edtFilt = (ClearEditText) findViewById(R.id.picklist_edt_filt);

        sideBar.setTextView(txtDia);

        listViewCitys.setAdapter(sortAdapter);
    }

    protected void initListener() {

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listViewCitys.setSelection(position);
                }

            }
        });

        // 根据输入框输入值的改变来过滤搜索
        edtFilt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 定位城市区域
     *
     * @return
     */
    private View getHeaderView(String tag, String content) {
        View viewHeader = View.inflate(getContext(), R.layout.item_list_sort,
                null);

        txtLocationCity = (TextView) viewHeader.findViewById(R.id.title);
        TextView txtTag = (TextView) viewHeader.findViewById(R.id.catalog);
        txtTag.setText(tag);
        txtLocationCity.setText(content);

        return viewHeader;
    }

    /**
     * 为ListView填充数据
     *
     * @return
     */
    private List<CarBrand> filledData(List<CarBrand> listBrands) {
        List<CarBrand> mSortList = new ArrayList<CarBrand>();

        for (int i = 0; i < listBrands.size(); i++) {
            CarBrand sortModel = listBrands.get(i);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(sortModel.getMake_name());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }

        // 根据a-z进行排序源数据
        Collections.sort(mSortList, new PinyinComparator());

        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CarBrand> filterDateList = new ArrayList<CarBrand>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = listSortDatas;
        } else {
            filterDateList.clear();
            for (CarBrand sortModel : listSortDatas) {
                String name = sortModel.getMake_name();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {

                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, new PinyinComparator());
        sortAdapter.updateListView(filterDateList);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
        listViewCitys.setOnItemClickListener(mOnItemClickListener);
    }

    public final AdapterView.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public CarBrand getItem(int position) {
        return (CarBrand)sortAdapter.getItem(position);
    }
}
