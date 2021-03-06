package com.hangon.common;

/**
 * Created by Administrator on 2016/4/2.
 */
public class Constants {
    //网络请求URL 10.163.200.124 10.59.3.167    邱勇腾讯云 123.207.136.35   学校服务器 111.116.20.180 南航寝室192.168.253.11   南航教室:172.18.126.95
    public static final String HOST_IP = "111.116.20.180";
    public static final String HOST_PORT = "";

    //关于用户信息的网络请求地址
    public static final String LOGIN_URL = "http://" + HOST_IP + HOST_PORT+"/wind/UserLogin";
    public static final String REGISTER_URL = "http://" + HOST_IP + HOST_PORT+"/wind/UserRegister?";
    public static final String JUDGE_USER_URL = "http://" + HOST_IP + HOST_PORT+"/wind/UserJudge?";
    public static final String UPDATE_USER_URL = "http://" + HOST_IP +HOST_PORT+"/wind/UpdateUserInfo?";
    public static final String ADD_USER_ICON_URL = "http://" + HOST_IP + HOST_PORT+"/wind/AddUserIcon?";
    public static final String JUDGE_USERPASS_URL = "http://" + HOST_IP +HOST_PORT+"/wind/user/JdugeUserPass";
    public static final String UPDATE_USERPASS_URL = "http://" + HOST_IP + HOST_PORT+"/wind/user/UpdateUserPass";
    public static final String CHANGE_LOGINFLAG_URL = "http://" + HOST_IP + HOST_PORT+"/wind/user/ChangeLoginFlag";

    //关于车辆信息的网络请求地址
    public static final String GET_BRAND_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/BrandInfo";
    public static final String GET_CAR_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/CarInfo";
    public static final String ADD_CAR_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/AddCarInfo";
    public static final String UPDATE_CAR_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/UpdateCarInfo";
    public static final String DELETE_CAR_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/DeleteCarInfo";
    public static final String GET_ZNWH_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/GetZnwhInfo";
    public static final String UPDATE_ZNWH_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/UpdateZnwhInfo";
    public static final String MY_ADD_CAR_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/MyAddCarInfo?";
    //获取默认车辆的违章信息违章地址
    public static final String GET_WEIZHANG_INFO_URL = "http://" + HOST_IP +HOST_PORT+"/wind/Car/GetWeiZhangInfo?";
    public static final String QUERY_CAR_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/QueryCar";
   //获取保养手册信息
   public static final String QUERY_BYSC_CONTENT_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Car/QueryByscContent";


    //关于订单的网络请求地址
    public static final String ADD_ORDER_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Order/AddOrderInfo";
    public static final String GET_ORDER_INFOS_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Order/GetOrderInfos";
    public static final String DELETE_ORDER_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Order/DeleteOrderInfo";
    public static final String CHANGE_ORDER_INFO_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Order/ChangeOrderState";
    public static final String GET_WZF_ORDER_INFOS_URL = "http://" + HOST_IP +HOST_PORT+"/wind/Order/GetWzfOrderInfos";
    public static final String GET_YZF_ORDER_INFOS_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Order/GetYzfOrderInfos";
    public static final String DELETE_ORDER_INFOS_URL = "http://" + HOST_IP + HOST_PORT+"/wind/Order/DeleteHopedOrderInfo";
    //车logo图片资源
    public static final String CAR_FLAG_URL = "http://" + HOST_IP + HOST_PORT+"/wind/img/car_icon/";
    //用户头像图片资源
    public static final String LOAD_USER_ICON_URL = "http://" + HOST_IP + HOST_PORT+"/wind/img/user_icon";

    //说说图片地址
    public static final String LOAD_SAYING_IMG_URL="http://" + HOST_IP + HOST_PORT+"/wind/img/saying_img";

    public static final String SHARE_APP_URL="http://" + HOST_IP + HOST_PORT+"/wind/img/background/ccut_znlsj.apk";

    //关于车友圈说说的地址
    public static final String GET_QZ_SAYINGS ="http://" + HOST_IP + HOST_PORT+"/wind/Saying/GetQzSayings";
    public static final String GET_CZW_SAYINGS ="http://" + HOST_IP + HOST_PORT+"/wind/Saying/GetCzwSayings";
    public static final String ADD_SAYING ="http://" + HOST_IP + HOST_PORT+"/wind/Saying/AddSaying";
    public static final String DELETE_SAYING ="http://" + HOST_IP + HOST_PORT+"/wind/Saying/DeleteSaying";

    //定时发送用户经纬度
    public static final String SEND_JWD ="http://" + HOST_IP + HOST_PORT+"/wind/location/saveUserLocation";

    //音乐播放状态
    public static final int IDLE = 0;// 空闲状态
    public static final int PLAY = 1;//播放状态
    public static final int PAUSE = 2;//暂停状态
    public static final int STOP = 3;//停止状态

    //音乐播放模式
    public static final int SEQUENCE_MODEL = 0;//顺序播放
    public static final int CIRCULATION_MODEL = 1;//循环播放
    public static final int RANDOM_MODEL = 2;//随机播放

    //车牌号
    public static final String PROVINCE_VALUE = "京津沪川鄂甘赣桂贵黑吉翼晋辽鲁蒙闽宁青琼陕苏皖湘新渝豫粤云藏浙";

    //动画参数
    public static final int VERSION = Integer.valueOf(android.os.Build.VERSION.SDK);

    //用户id---及其重要
    public static    int USER_ID=0;

    public static  String SAYING_TYPE="";

}
