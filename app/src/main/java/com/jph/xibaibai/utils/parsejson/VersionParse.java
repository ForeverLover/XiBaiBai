package com.jph.xibaibai.utils.parsejson;

import android.util.Log;

import com.jph.xibaibai.model.entity.Version;
import com.jph.xibaibai.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 2015/11/20.
 */
public class VersionParse {
    /**
     * 解析版本信息
     * @param json
     * @return
     * @throws JSONException
     */
    public static Version getVersionInfo(String json) throws JSONException {
        Log.i("Tag","versionJson=>"+json);
        Version version = null;
        JSONObject job = new JSONObject(json);
        if (json != null) {
            version = new Version();
            if (job.has("id") && !StringUtil.isNull(job.getString("id"))) {
                version.setId(job.getString("id"));
            }
            if (job.has("versionname") && !StringUtil.isNull(job.getString("versionname"))) {
                version.setVersion(job.getString("versionname"));
            }
            if (job.has("downloadaddress") && !StringUtil.isNull(job.getString("downloadaddress"))) {
                version.setPath(job.getString("downloadaddress"));
            }
            if (job.has("update_content") && !StringUtil.isNull(job.getString("update_content"))) {
                version.setContent(job.getString("update_content"));
            }
        }
        return version;
    }
}
