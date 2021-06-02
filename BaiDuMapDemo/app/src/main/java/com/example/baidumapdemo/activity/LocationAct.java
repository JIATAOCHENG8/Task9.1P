package com.example.baidumapdemo.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.baidumapdemo.R;
import com.example.baidumapdemo.data.PositioningInfo;
import com.example.baidumapdemo.db.DbManage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示历史记录
 */
public class LocationAct extends AppCompatActivity implements BaiduMap.OnMapClickListener {
    @BindView(R.id.bmapView)
    MapView myShow;

    private DbManage dbManage;
    private BaiduMap mBaiduMap = null;
    private BitmapDescriptor mBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.tra_lyxl);

    private List<PositioningInfo> dataList;//历史记录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        mBaiduMap = myShow.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        //初始化数据库
        dbManage = DbManage.getInstance();
        dbManage.init(this);
        dataList = dbManage.getPositioningInfo();
        List<InfoWindow> infoWindowList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (this.mBitmapDescriptor != null) {
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.top);//地理位置背景图
                button.setText(dataList.get(i).getName());
                button.setTextColor(-1);//
                button.setPadding(5, 5, 5, 5);//边距
                button.setTextSize(10.0f);//文字大小 字节
                button.setWidth(300);//宽度
                LatLng point = new LatLng(dataList.get(i).getLatitude(), dataList.get(i).getLongitude());
                infoWindowList.add(new InfoWindow(BitmapDescriptorFactory.fromView(button), point, -95, null));
                this.mBaiduMap.addOverlay(new MarkerOptions().icon(this.mBitmapDescriptor).position(point));//添加图片
            }
        }
        this.mBaiduMap.showInfoWindows(infoWindowList);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(20.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            //地图移动到指定位置
//            try {
//                if (location != null) {
//                    LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
//                    // 构建Marker图标
//                    BitmapDescriptor bitmap= BitmapDescriptorFactory.fromResource(R.mipmap.icon_openmap_focuse_mark); // 推算结果;
//
//                    // 构建MarkerOption，用于在地图上添加Marker
//                    OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
//                    // 在地图上添加Marker，并显示
//                    mBaiduMap.addOverlay(option);
//                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
        }
    }

}
