package com.example.baidumapdemo;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.lljjcoder.style.citylist.utils.CityListLoader;

public class DemoApplication extends Application {
    private static DemoApplication demoApplication;

    public static DemoApplication getInstance() {
        if(demoApplication == null){
            demoApplication = new DemoApplication();
        }
        return demoApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        // 默认本地个性化地图初始化方法
        SDKInitializer.initialize(this);

        // 自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        // 包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        /**
         * 预先加载一级列表显示 全国所有城市市的数据
         */
        CityListLoader.getInstance().loadCityData(this);
    }
}
