package com.hangon.fragment.userinfo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.fd.ourapplication.R;
import com.hangon.bean.user.UserInfo;
import com.hangon.common.Constants;
import com.hangon.common.DialogTool;
import com.hangon.common.Topbar;
import com.hangon.common.UserUtil;
import com.hangon.common.VolleyInterface;
import com.hangon.common.VolleyRequest;
import com.hangon.home.activity.HomeActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/19.
 */
public class UpdateUserActivity extends Activity {
    EditText uNickname;
    TextView uSex;
    EditText uAge;
    EditText uDriverNum;

    ImageView topbarLeft, topbarRight;
    TextView topbarTitle;


    UserInfo userInfo;
    String userName;

    public final static int RESULT_UPDATE = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        init();
        initEditext();
        topbarClick();
    }

    //初始化组件
    private void init() {
        uNickname = (EditText) findViewById(R.id.uNickname);
        uSex = (TextView) findViewById(R.id.uSex);
        uSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uSex.getText().toString().trim().equals("男")){
                uSex.setText("女");}
            else{
                    uSex.setText("男");
                }

            }

        });
        uAge = (EditText) findViewById(R.id.uAge);
        uDriverNum = (EditText) findViewById(R.id.uDriverNum);
        topbarLeft = (ImageView) findViewById(R.id.topbar_left);
        topbarRight = (ImageView) findViewById(R.id.topbar_right);
        topbarTitle = (TextView) findViewById(R.id.topbar_title);
        topbarRight.setImageResource(R.drawable.lj_xcdxq_001);
        topbarTitle.setText("编辑用户信息");
    }

    private void initEditext() {
        UserUtil.instance(UpdateUserActivity.this);
        UserInfo userInfo = UserUtil.getInstance().getUserInfo();
        uNickname.setText(userInfo.getNickname());
        uSex.setText(userInfo.getSex());
        uAge.setText(userInfo.getAge() + "");
        uDriverNum.setText(userInfo.getDriverNum());
    }

    //标题栏的点击栏
    private void topbarClick() {
        topbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到用户信息页面
                Intent intent = new Intent();
                intent.putExtra("id", 4);
                setResult(RESULT_OK, intent);
                UpdateUserActivity.this.finish();
            }
        });

        topbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTool.createNormalDialog(UpdateUserActivity.this, "编辑用户信息", "你确定要修改吗?", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (judgeEditext()) {
                            makeUser();
                            //发送要更新的信息
                            postUpdateUser();
                            //跳转到用户信息页面
                            Intent intent = new Intent();
                            intent.putExtra("id", 4);
                            setResult(RESULT_UPDATE, intent);
                            UpdateUserActivity.this.finish();
                        }
                    }
                }, null).show();
            }
        });
    }

    private boolean judgeEditext() {
        boolean state = false;
        String nickname = uNickname.getText().toString().trim();
        String sex = uSex.getText().toString().trim();
        String driverNum = uDriverNum.getText().toString().trim();
        String age = uAge.getText().toString().trim();

        if (!nickname.isEmpty()) {
            if (nickname.length() < 2) {
                Toast.makeText(UpdateUserActivity.this, "昵称必须大于两位", Toast.LENGTH_SHORT).show();
                state = false;
                return false;
            }
        }

        if (!age.isEmpty()) {
            if (Integer.parseInt(age) <= 0 && Integer.parseInt(age) > 140) {
                Toast.makeText(UpdateUserActivity.this, "年龄必须在0-140之间", Toast.LENGTH_SHORT).show();
                state = false;
                return false;
            }
        }

        if (!driverNum.isEmpty()) {
            if (driverNum.length() != 18) {
                Toast.makeText(UpdateUserActivity.this, "身份证号必须为18位", Toast.LENGTH_SHORT).show();
                state = false;
                return false;
            }
        }

        if (nickname.isEmpty() && sex.isEmpty() && driverNum.isEmpty() && age.isEmpty()) {
            Toast.makeText(UpdateUserActivity.this, "不能全部都为空", Toast.LENGTH_SHORT).show();
            state = false;
            return state;
        }

        if (nickname.length() >= 2 || sex.equals("男") || sex.equals("女") || driverNum.length() == 18 || Integer.parseInt(age) > 0 || Integer.parseInt(age) <= 140) {
            state = true;
        }
        return state;
    }

    //把editext里的值封装到一个对象
    private void makeUser() {
        UserUtil.instance(UpdateUserActivity.this);
        userName = UserUtil.getInstance().getStringConfig("userName");
        UserInfo user = new UserInfo();
        user.setNickname(uNickname.getText().toString());
        user.setSex(uSex.getText().toString());
        if (uAge.getText().toString().isEmpty()) {
            user.setAge(-1);
        } else {
            user.setAge(Integer.parseInt(uAge.getText().toString()));
        }
        user.setDriverNum(uDriverNum.getText().toString());
        user.setUserName(userName);
        userInfo = user;
    }


    //发送更新信息
    private void postUpdateUser() {
        String url = Constants.UPDATE_USER_URL;
        Map<String, Object> map = new HashMap<String, Object>();


        map.put("userName", userInfo.getUserName());
        map.put("sex", userInfo.getSex());
        map.put("nickname", userInfo.getNickname());
        map.put("age", userInfo.getAge() + "");
        map.put("driverNum", userInfo.getDriverNum());

        VolleyRequest.RequestPost(UpdateUserActivity.this, url, "postUpdateUser", map, new VolleyInterface(UpdateUserActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                if (result.equals("ok")) {
                    //保存用户信息到cookies
                    UserUtil.getInstance().saveUpdateUserInfo(userInfo);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(UpdateUserActivity.this, "网络异常，请重新提交.", Toast.LENGTH_SHORT);
            }
        });
    }

}
