package com.hangon.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hangon.common.JsonUtil;
import com.hangon.common.SystemUtils;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Chuan on 2016/8/10.
 */
public class PushReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);

            Map map = JsonUtil.jsonToMap(extra);
            String address = (String) map.get("qzrPostAddress");
            if (address == null){
                Toast.makeText(context, "服务器繁忙！", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(context, PushActivity.class);  //自定义打开的界面
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Log.d("失败", "Unhandled intent - " + intent.getAction());
        }
    }

}
