package com.jph.xibaibai.utils.parsejson;

import android.util.Log;

import com.jph.xibaibai.model.entity.OverlyCoordinate;
import com.jph.xibaibai.model.entity.PolugonCoord;
import com.jph.xibaibai.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Eric on 2015/11/6.
 * 服务区域的解析
 */
public class OverlycdParse {
    public static OverlyCoordinate getResult(String json) {
        OverlyCoordinate overlyCd = new OverlyCoordinate();
        try {
            JSONObject jobgh = new JSONObject(json);
            if (jobgh == null) {
                return null;
            }
            if (jobgh.has("muilt") && !StringUtil.isNull(jobgh.getString("muilt"))) {
                Log.i("Tag","muilt=>"+jobgh.getString("muilt"));
                List<ArrayList<PolugonCoord>> allCity = new ArrayList<>();
                JSONObject jobOneM = jobgh.getJSONObject("muilt");
                Log.i("Tag","jobOneM=>"+jobOneM.toString());
                Iterator it = jobOneM.keys();
                Log.i("Tag","keySize=>"+it.hasNext());
                while (it.hasNext()) {
                    JSONArray jsonArray = jobOneM.getJSONArray(it.next().toString());
                    List<PolugonCoord> circleList = includeMethod(jsonArray);
                    if (circleList != null) {
                        allCity.add((ArrayList<PolugonCoord>) circleList);
                    }
                }
                overlyCd.setAllCityList(allCity);
            }
            if (jobgh.has("circle") && !StringUtil.isNull(jobgh.getString("circle"))) {
                JSONArray jArrayC = jobgh.getJSONArray("circle");
                if (jArrayC != null && jArrayC.length() > 0) {
                    List<PolugonCoord> circleList = includeMethod(jArrayC);
                    if (circleList != null) {
                        overlyCd.setCircleList(circleList);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return overlyCd;
    }

    private static List<PolugonCoord> includeMethod(JSONArray jArrayC) throws JSONException {
        List<PolugonCoord> list = new ArrayList<>();
        for (int i = 0; i < jArrayC.length(); i++) {
            PolugonCoord circleCd = new PolugonCoord();
            JSONObject circleJob = (JSONObject) jArrayC.get(i);
            if (circleJob.has("id") && !StringUtil.isNull(circleJob.getString("id"))) {
                circleCd.setId(circleJob.getString("id"));
            }
            if (circleJob.has("server_lng") && !StringUtil.isNull(circleJob.getString("server_lng"))) {
                circleCd.setServer_lng(circleJob.getString("server_lng"));
            }
            if (circleJob.has("server_lat") && !StringUtil.isNull(circleJob.getString("server_lat"))) {
                circleCd.setServer_lat(circleJob.getString("server_lat"));
            }
            if (circleJob.has("radius") && !StringUtil.isNull(circleJob.getString("radius"))) {
                circleCd.setRadius(circleJob.getString("radius"));
            }
            if (circleJob.has("type") && !StringUtil.isNull(circleJob.getString("type"))) {
                circleCd.setType(circleJob.getString("type"));
            }
            list.add(circleCd);
        }
        return list;
    }

}
