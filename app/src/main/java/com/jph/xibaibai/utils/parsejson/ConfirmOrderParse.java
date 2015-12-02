package com.jph.xibaibai.utils.parsejson;

import com.jph.xibaibai.model.entity.BeautyItemProduct;
import com.jph.xibaibai.model.entity.ConfirmOrderData;
import com.jph.xibaibai.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Eic on 2015/11/11.
 * 解析确认订单的结果
 */
public class ConfirmOrderParse {
    public static ConfirmOrderData parseResult(String json){
        ConfirmOrderData confirmData = null;
        try {
            confirmData = new ConfirmOrderData();
            JSONObject job = new JSONObject(json);
            if(job.has("id") && !StringUtil.isNull(job.getString("id"))){
                confirmData.setOderId(job.getString("id"));
            }
            if(job.has("order_num") && !StringUtil.isNull(job.getString("order_num"))){
                confirmData.setOrderNum(job.getString("order_num"));
            }
            if(job.has("order_time") && !StringUtil.isNull(job.getString("order_time"))){
                confirmData.setOrderTime(job.getString("order_time"));
            }
            if(job.has("total_price") && !StringUtil.isNull(job.getString("total_price"))){
                confirmData.setOrderPrice(job.getString("total_price"));
            }
            if(job.has("products") && !StringUtil.isNull(job.getString("products"))){
                JSONArray jsonArray = job.getJSONArray("products");
                List<BeautyItemProduct> productList = null;
                productList = BeautyServiceParse.commonParse(jsonArray);
                confirmData.setProductList(productList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return confirmData;
    }
}
