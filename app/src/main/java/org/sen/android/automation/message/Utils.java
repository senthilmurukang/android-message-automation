package org.sen.android.automation.message;

import org.json.JSONObject;

import java.util.Map;

public class Utils {
    public static String convertMapToJsonString(Map<String, Object> inputMap) {
        JSONObject jsonObject = new JSONObject(inputMap);
        return jsonObject.toString();
    }
}
