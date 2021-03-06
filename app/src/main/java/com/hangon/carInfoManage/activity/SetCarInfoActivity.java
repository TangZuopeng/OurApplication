package com.hangon.carInfoManage.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.fd.ourapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangon.bean.carInfo.BrandVO;
import com.hangon.bean.carInfo.CarInfoVO;
import com.hangon.bean.carInfo.CarMessageVO;
import com.hangon.common.Constants;
import com.hangon.common.DialogTool;
import com.hangon.common.JsonUtil;
import com.hangon.common.Topbar;
import com.hangon.common.UserUtil;
import com.hangon.common.VolleyInterface;
import com.hangon.common.VolleyRequest;
import com.hangon.home.activity.HomeActivity;
import com.hangon.map.util.JudgeNet;
import com.xys.libzxing.zxing.activity.CaptureActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/26.
 */
public class SetCarInfoActivity extends Activity implements View.OnClickListener {
    public final static int INTEBNT_SAO = 1;
    ListView listView;//车辆信息列表
    SetCarInfoAdapter adapter;//车辆信息适配器

    ImageView btnShou;
    ImageView btnSao;
    ImageView topbarLeft, topbarRight;
    TextView topbarTitle;
    List<CarMessageVO> carMessageList;//车辆信息列表数据
    Gson gson;//解析json数据

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_set_carinfo);
        init();
        getCarMessageList();
    }

    //初始化组件
    private void init() {
        btnSao = (ImageView) findViewById(R.id.btnSao);
        btnShou = (ImageView) findViewById(R.id.btnShou);

        btnSao.setOnClickListener(this);
        btnShou.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.carInfoList);
        topbarLeft = (ImageView) findViewById(R.id.topbar_left);
        topbarTitle = (TextView) findViewById(R.id.topbar_title);
        topbarRight = (ImageView) findViewById(R.id.topbar_right);
        topbarRight.setVisibility(View.GONE);
        topbarTitle.setText("车辆管理");
        topbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", 1);
                SetCarInfoActivity.this.setResult(RESULT_OK, intent);
                SetCarInfoActivity.this.finish();
            }
        });

    }

    //初始化适配器
    private void initAdapter(final List<CarMessageVO> carMessageList) {
        adapter = new SetCarInfoAdapter(carMessageList, SetCarInfoActivity.this);
        adapter.setBtnClickListener(new SetCarInfoAdapter.btnClickListener() {
            @Override
            public void btnEditeClick(int position) {
                Intent intent = new Intent();
                intent.setClass(SetCarInfoActivity.this, AlterCarMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("carMessage", carMessageList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void btnDeleteClick(final int position) {

                DialogTool.createNormalDialog(SetCarInfoActivity.this, "删除车辆信息", "真的要删除吗?", "确认", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(position);

                    }
                }, null).show();
            }

            @Override
            public void btnScanClick(int position) {
                Intent intent = new Intent();
                intent.setClass(SetCarInfoActivity.this, ScanCarMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("carMessage", carMessageList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        listView.setAdapter(adapter);
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShou:
                Intent intent = new Intent();
                intent.setClass(SetCarInfoActivity.this, AddCarMessageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSao:
                intent = new Intent(SetCarInfoActivity.this, CaptureActivity.class);
                startActivityForResult(intent, SetCarInfoActivity.INTEBNT_SAO);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case INTEBNT_SAO:
                if (resultCode == RESULT_OK) {
                    Intent intent = data;
                    Bundle bundle = intent.getExtras();
                    String result = bundle.getString("result").trim();
                    saoAdd(result);
                }
                break;
        }
    }

    private void saoAdd(String aimUrl) {
        String url = Constants.MY_ADD_CAR_INFO_URL + aimUrl;
        VolleyRequest.RequestGet(SetCarInfoActivity.this, url, "saoAdd", new VolleyInterface(SetCarInfoActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                init();
                getCarMessageList();
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(SetCarInfoActivity.this, "网络或者服务器异常,扫描二维码添加失败,请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取车辆信息列表
    private void getCarMessageList() {
        UserUtil.instance(SetCarInfoActivity.this);
        String userId = UserUtil.getInstance().getIntegerConfig("userId") + "";
        String url = Constants.GET_CAR_INFO_URL + "?userId=" + userId;
        VolleyRequest.RequestGet(SetCarInfoActivity.this, url, "getCarMessageList", new VolleyInterface(SetCarInfoActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {

            @Override
            public void onMySuccess(String result) {
                gson = new Gson();
                carMessageList = new ArrayList<CarMessageVO>();
                carMessageList = gson.fromJson(result, new TypeToken<List<CarMessageVO>>() {
                }.getType());
                initAdapter(carMessageList);
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(SetCarInfoActivity.this, "网络异常,车辆加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    int p;//要删除的数据的位置

    private void delete(int position) {
        p = position;
        String url = Constants.DELETE_CAR_INFO_URL + "?carInfoId=" + carMessageList.get(position).getCarInfoId();

        VolleyRequest.RequestGet(SetCarInfoActivity.this, url, "deleteCarMessage", new VolleyInterface(SetCarInfoActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                carMessageList.remove(p);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(SetCarInfoActivity.this, "网络异常，删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
