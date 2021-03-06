package com.hangon.fragment.order;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.fd.ourapplication.R;
import com.google.gson.Gson;
import com.hangon.common.Constants;
import com.hangon.common.JsonUtil;
import com.hangon.common.UserUtil;
import com.hangon.common.VolleyInterface;
import com.hangon.common.VolleyRequest;
import com.hangon.home.activity.HomeActivity;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.Hashon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Chuan on 2016/5/9.
 */
public class ZnwhService extends Service {

    NotificationAdmain admain;
    Intent znwhIntent;
    Intent tagIntent;
    ZnwhInfoVO znwhInfoVO;//智能维护信息;
    static int NOTIFICATION_ID = 13565400;

    public static final String ACTION_UPDATE_ZNWH = "com.hangon.fragment.order.ZnwhService";
    public static final String ACTION_TAG_CHANGE = "com.hangon.home.activity.HomeActivity";

    private Thread thread;

    int smallIcon = R.mipmap.ic_launcher;

    private int isGoodEngine, isGoodTran, isGoodLight;
    private int oldEngine = 0, oldTran = 0, oldLight = 0;

    private ZnwhInfoVO znwhInfo = new ZnwhInfoVO();

    private MyBinder myBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        admain = new NotificationAdmain(this,NOTIFICATION_ID);
        znwhIntent = new Intent(this, HomeActivity.class);
        myBinder = new MyBinder();
        return myBinder;
    }

    //初始化切换界面intent
    public void initChangeTag(){
        tagIntent = new Intent();
        tagIntent.setAction(ACTION_UPDATE_ZNWH);
    }

    public void reflush(ZnwhInfoVO znwhInfoVO){
        isGoodEngine=znwhInfoVO.getIsGoodEngine();
        isGoodLight=znwhInfoVO.getIsGoodLight();
        isGoodTran=znwhInfoVO.getIsGoodTran();
        if (znwhInfoVO.getOddGasAmount()<15){
            admain.normal_notification(znwhIntent,smallIcon,"汽车智能维护","你的汽车油量不足15%","请尽快加油");
        }
        if (znwhInfoVO.getMileage()>=1500&&znwhInfoVO.getMileage()%1500>=0&&znwhInfoVO.getMileage()%1500<=100){
            admain.normal_notification(znwhIntent,smallIcon,"汽车智能维护","你的里程数为:"+znwhInfoVO.getMileage(),"请及时保养你的汽车");
        }

        if (isGoodEngine != oldEngine){
            if (znwhInfoVO.getIsGoodEngine() == 1){
                admain.normal_notification(znwhIntent, smallIcon, "汽车智能维护", "你的汽车需要维修！",
                        "发动机出现异常！");
            }
            else{
                admain.normal_notification(znwhIntent, smallIcon, "汽车智能维护", "你的汽车完成维修！",
                        "发动机可以正常运行！");
            }
        }
        if (isGoodTran != oldTran){
            if (isGoodTran == 1){
                admain.normal_notification(znwhIntent, smallIcon, "汽车智能维护", "你的汽车需要维修！",
                        "变速器出现异常！");
            }
            else{
                admain.normal_notification(znwhIntent, smallIcon, "汽车智能维护", "你的汽车完成维修！",
                        "变速器可以正常运行！");
            }
        }
        if (isGoodLight != oldLight){
            if (isGoodEngine == 1){
                admain.normal_notification(znwhIntent, smallIcon, "汽车智能维护", "你的汽车需要维修！",
                        "车灯出现异常！");
            }
            else{
                admain.normal_notification(znwhIntent, smallIcon, "汽车智能维护", "你的汽车完成维修！",
                        "车灯可以正常运行！");
            }
        }
        oldEngine = isGoodEngine;
        oldTran = isGoodTran;
        oldLight = isGoodLight;
    }

    public void getZnwhInfo(){
        UserUtil.instance(ZnwhService.this);
        int userId=UserUtil.getInstance().getIntegerConfig("userId");
        String url = Constants.GET_ZNWH_INFO_URL.trim()+ "?userId=" +userId;
        VolleyRequest.RequestGet(ZnwhService.this, url, "getZnwhInfo", new VolleyInterface(ZnwhService.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                Log.d("res11", result);
                Gson gson=new Gson();
                if(result!=null&&!result.equals("")){
                    znwhInfoVO= (ZnwhInfoVO) JsonUtil.jsonToBean(result,ZnwhInfoVO.class);
                    reflush(znwhInfoVO);//发送通知
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ZnwhInfo", znwhInfoVO);
                    intent.putExtras(bundle);
                    intent.setAction(ACTION_UPDATE_ZNWH);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                Log.d("error",error.toString());
            }
        });

    }

    private void updateZnwhInfo(){
        UserUtil.instance(ZnwhService.this);
        int userId=UserUtil.getInstance().getIntegerConfig("userId");
        String url=Constants.UPDATE_ZNWH_INFO_URL+"?userId="+userId;
        VolleyRequest.RequestGet(ZnwhService.this, url, "updateZnwhInfo", new VolleyInterface(ZnwhService.this,VolleyInterface.mListener,VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {

            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }


    public class MyBinder extends Binder{
        Timer timer = null;
        public void off(){
            if (timer != null){
                timer.cancel();
                timer = null;
            }
        }

        public void on(){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getZnwhInfo();
                    updateZnwhInfo();
                }
            }, 0, 5000);
        }
    }
}
