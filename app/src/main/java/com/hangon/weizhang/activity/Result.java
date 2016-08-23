package com.hangon.weizhang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fd.ourapplication.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Result extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.csy_activity_re);
        // 标题
        TextView txtTitle = (TextView) findViewById(R.id.topbar_title);
        txtTitle.setText("违章查询结果");
        // 返回按钮
        ImageView btnBack = (ImageView) findViewById(R.id.topbar_left);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView rightBtn = (ImageView) findViewById(R.id.topbar_right);
        rightBtn.setVisibility(View.INVISIBLE);
    }
}
