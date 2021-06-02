package com.example.baidumapdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.baidumapdemo.R;

/**
 * 当前位置
 */
public class CurrentMapActivity extends Activity {
    private MapView myMap;

    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient;

    private double mLongitude;//经度
    private double mLatitude;//维度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        myMap = findViewById(R.id.map);

        mBaiduMap = myMap.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        //定位初始化
        mLocationClient = new LocationClient(this);
        Log.e("TAG","mLongitude2------->"+AddMapActivity.mLongitude);
        mLongitude = AddMapActivity.mLongitude == 0.0 ? 116.38 : AddMapActivity.mLongitude;
        mLatitude = AddMapActivity.mLatitude == 0.0 ? 39.9 : AddMapActivity.mLatitude;
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        // 设置初始中心点
        LatLng center = new LatLng(mLatitude, mLongitude);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(center, 12);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.openmap); // 推算结果;
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions layOption = new MarkerOptions().position(center).icon(bitmap);
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(layOption);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //开启地图定位图层
        mLocationClient.start();
        //LatLng ll = new LatLng(mLatitude, mLongitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(center).zoom(20.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void onResume() {
        myMap.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        myMap.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        myMap.onDestroy();
        myMap = null;
        super.onDestroy();
    }

}
