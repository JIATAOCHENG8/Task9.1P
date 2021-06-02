package com.example.baidumapdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.example.baidumapdemo.DemoApplication;
import com.example.baidumapdemo.MainActivity;
import com.example.baidumapdemo.R;
import com.example.baidumapdemo.data.PositioningInfo;
import com.example.baidumapdemo.db.DbManage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图添加位置页
 */
public class AddMapActivity extends Activity{
    //需要搜索的关键词
    @BindView(R.id.etName)
    EditText etName;

    private DbManage dbManage;
    private LocationClient mClient;
    private boolean isFirstLoc = true;//是否第一次获取位置

    public static double mLongitude;//地图的经度
    public static double mLatitude;//地图的维度
    public static String city;//定位地址的名
    public static String editName;//搜索位置名字
    private static String province;//定位城市

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place_activity);
        ButterKnife.bind(this); //初始化数据库
        dbManage = DbManage.getInstance();
        dbManage.init(this);
        baiduInit();
    }
    //初始化百度地图的信息
    private void baiduInit() {
        mClient = new LocationClient(this);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        mClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mClient.start();
    }


    @OnClick({R.id.tvLocation, R.id.showMap,R.id.tvSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLocation: // ========搜索定位===========
                if (TextUtils.isEmpty(etName.getText().toString())){
                    Toast.makeText(AddMapActivity.this,"Input Place Name",Toast.LENGTH_LONG).show();
                    return;
                }
                editName = etName.getText().toString();
                Intent intent = new Intent(AddMapActivity.this, PoiCitySearchActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.showMap:    // =====显示当前位置======
                Intent mIntent = new Intent(AddMapActivity.this,CurrentMapActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tvSave:
                if (mLongitude == 0.0 || mLatitude == 0.0 || TextUtils.isEmpty(etName.getText().toString())){
                    Toast.makeText(AddMapActivity.this, "Location Can not be empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                PositioningInfo data = new PositioningInfo();
                data.setLatitude(mLatitude);
                data.setLongitude(mLongitude);
                data.setName(etName.getText().toString());
                data.setProvince(province);
                dbManage.addRecording(data);
                finish();
                Toast.makeText(AddMapActivity.this, "Saved successfully!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || !isFirstLoc)  {
                return;
            }
            //获取到定位的信息
            isFirstLoc = false;
            mLongitude = location.getLongitude();
            mLatitude = location.getLatitude();
            city = location.getCity();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                if (data == null){
                    return;
                }
                mLongitude = data.getDoubleExtra("longitude",0);
                mLatitude = data.getDoubleExtra("latitude",0);
                editName = data.getStringExtra("name");
                province = data.getStringExtra("province");
                etName.setText(editName);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        mClient.stop();
        super.onDestroy();
    }
}
