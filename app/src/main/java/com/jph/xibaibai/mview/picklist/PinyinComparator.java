package com.jph.xibaibai.mview.picklist;

import com.jph.xibaibai.model.entity.CarBrand;

import java.util.Comparator;


/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<CarBrand> {

	public int compare(CarBrand o1, CarBrand o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
