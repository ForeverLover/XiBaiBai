package com.jph.xibaibai.mview.picklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.CarBrand;

import java.util.List;


public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<CarBrand> list = null;
	private Context mContext;

	public SortAdapter(Context mContext, List<CarBrand> list) {
		this.mContext = mContext;
		this.list = list;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<CarBrand> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final CarBrand mContent = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_list_sort, null);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
			viewHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) convertView
					.findViewById(R.id.catalog);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		final CarBrand carBrand = list.get(position);
		viewHolder.tvTitle.setText(carBrand.getMake_name());

		return convertView;

	}

	final static class ViewHolder {
		ImageView img;
		TextView tvLetter;
		TextView tvTitle;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}

}