package com.example.baidumapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baidumapdemo.activity.AddMapActivity;
import com.example.baidumapdemo.activity.LocationAct;
import com.tbruyelle.rxpermissions3.RxPermissions;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.tvAddPlace)
    TextView tvAddPlace;
    @BindView(R.id.tvShowAll)
    TextView tvShowAll;
    //显示记录的位置
    private Intent intent;

    private RxPermissions rxPermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
    }
    @OnClick({R.id.tvAddPlace, R.id.tvShowAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAddPlace: // ========添加===========
                if (isPermission()){
                    intent = new Intent(MainActivity.this, AddMapActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.tvShowAll:    // =====查询历史记录======
                if (isPermission()){
                    intent = new Intent(MainActivity.this, LocationAct.class);
                    startActivity(intent);
                }
                break;
        }
    }
    boolean myPermi = false;
    private boolean isPermission(){
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.ACCESS_FINE_LOCATION
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.READ_PHONE_STATE).subscribe(granted -> {
            if (granted) {
                myPermi = true;
            } else {
                //没用权限
                Toast.makeText(MainActivity.this,"Please go to the settings center to open permissions!",Toast.LENGTH_LONG).show();
                myPermi = false;
            }
        });
        return myPermi;
    }
}