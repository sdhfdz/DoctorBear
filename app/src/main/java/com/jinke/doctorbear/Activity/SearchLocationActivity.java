package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.jinke.doctorbear.Adapter.AdpLocNearAddress;
import com.jinke.doctorbear.Model.FgSearchLocationModel;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.MyPoiOverlay;
import com.jinke.doctorbear.Utils.PoiOverlay;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SearchLocationActivity extends Activity {

    private static final String LTAG = SearchLocationActivity.class.getSimpleName();
    private MapView mMapView;
    private ListView lv_PoiList;
    private ImageView iv_Back;
    public LocationClient mLocationClient = null;
    private static final String TAG = SearchLocationActivity.class.getSimpleName();

    AdpLocNearAddress adpLocNearAddress;
    private ArrayList<FgSearchLocationModel> listPoi;
    private  PoiSearch mPoiSManager = null;
    private  LocationClient mLocClient = null;
    private  LatLng mLatLng = null;
    private  Handler mHandler = null;
    private  PoiInfo info;
    private  BaiduMap baiduMap;
    private String address = "";


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_fg_search_location);
        init(this);


}

    private void init(Context context) {
        initView();
        initListener();
        initData(context);

    }

    private void initListener() {
        iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //listviewitem的点击事件,移动地图中心点,开启百度地图APP
        lv_PoiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                adpLocNearAddress.notifyDataSetChanged();
                FgSearchLocationModel fgSearchLocationModel = (FgSearchLocationModel) adpLocNearAddress.getItem(position);

                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(fgSearchLocationModel.getLocation())
                        .zoom(18)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                baiduMap .setMapStatus(mMapStatusUpdate);

                //开启百度地图APP
                String location = fgSearchLocationModel.getLocation().latitude+","+fgSearchLocationModel.getLocation().longitude;
                String title = fgSearchLocationModel.getLocationName();
                try {
                    //移动APP调起Android百度地图方式举例
                    Intent intent = Intent.getIntent("intent://map/marker?location="+location+"&title="+title+
                            "&content =&src=yourCompanyName|DoctorBear#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    startActivity(intent); //启动调用
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.activity_fg_search_location_mapview_location);
        lv_PoiList = (ListView) findViewById(R.id.activity_fg_search_location_lv_location_nearby);
        iv_Back = (ImageView) findViewById(R.id.activity_fg_search_location_iv_location_back);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类

    }

    /**
     * 初始化数据
     * @param context
     */
    private void initData(Context context) {

        baiduMap = mMapView.getMap();

        mHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 2)
                    goPoiSearch(mLatLng);
            }

        };
        goStartPoi();
        goGetLocation(this);

    }

    /**
     * 开始poi检索
     */
    private void goStartPoi() {
        // 获取本地位置--上传位置信息--等待回调--回调成功--开始查询--等待回调--回调中打印结果
        mPoiSManager = PoiSearch.newInstance();
        mPoiSManager.setOnGetPoiSearchResultListener(mPoiResultListener);
    }

    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    /**
     * poi检索接口的实现
     */
    private  OnGetPoiSearchResultListener mPoiResultListener = new OnGetPoiSearchResultListener() {

        /**
         * POI检索结果处理函数
         */
        @Override
        public void onGetPoiResult(PoiResult result) {
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                Log.i(TAG, "GetPoiResult OK");

//				totalNum:45
//				totalPageNum:5
//				pageIndex:0
//				pageCapacity:10
//				isHasAddrInfo:false
//				poi.size:10
//				info address:北京市东城区王府井大街277号(近好友世界商场)
//				info city:北京市
//				info name:酷时代游艺世界
//				info phoneNum:(010)65225696
//				info postCode:null
//				info uid:34059f97e013925471455bce
//				info hasCaterDetails:false
//				info isPano:true
//				info location:latitude: 39.91824597245212, longitude: 116.41649542740966
//				info type:POINT

                Log.i(TAG, "totalNum:" + result.getTotalPoiNum());	// 总结果个数
                Log.i(TAG, "totalPageNum:" + result.getTotalPageNum());	// 总页数
                Log.i(TAG, "pageIndex:" + result.getCurrentPageNum());	// 页码
                Log.i(TAG, "pageCapacity:" + result.getCurrentPageCapacity());	// 本页的结果数，可能小于本页可显示的结果数
                Log.i(TAG, "isHasAddrInfo:" + result.isHasAddrInfo());

                Log.i(TAG, "poi.size:" + result.getAllPoi().size());
                if (result.isHasAddrInfo()) {	// 如果为false，则getAllAddr为空
                    Log.i(TAG, "addr.size:" + result.getAllAddr().size());
                }
                for (int i = 0; i < result.getAllPoi().size(); i++) {
                    info = result.getAllPoi().get(i);
                    Log.i(TAG, "info address:" + info.address);	// 地址
                    Log.i(TAG, "info city:" + info.city);	// 城市
                    Log.i(TAG, "info name:" + info.name);	// 名称
                    Log.i(TAG, "info phoneNum:" + info.phoneNum);	// 电话
                    Log.i(TAG, "info postCode:" + info.postCode);	// 邮编
                    Log.i(TAG, "info uid:" + info.uid);	// uid，用于后面查询DetailResult
                    Log.i(TAG, "info hasCaterDetails:" + info.hasCaterDetails);	// 是否有详细信息
                    Log.i(TAG, "info isPano:" + info.isPano);	// 是否有全景
                    Log.i(TAG, "info location:" + info.location);	// 经纬度
                    Log.i(TAG, "info type:" + info.type);	// 类型

                }
                //显示poi
                showPoi(result);

                //设置listview
                setListView(result.getAllPoi());

                return;

            } else {
                Log.i(TAG, "GetPoiResult err:" + result.error);
            }


        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult result) {
        }
    };

    /**
     * 设置listview里的数据,显示poi列表
     */
    private void setListView(List<PoiInfo> infos) {
        listPoi = new ArrayList<FgSearchLocationModel>();
        for (int i = 0; i < infos.size(); i++) {
            FgSearchLocationModel fgSearchLocationModel = new FgSearchLocationModel(i+1+"",infos.get(i).name,infos.get(i).address,infos.get(i).location);
            listPoi.add(fgSearchLocationModel);
        }
        adpLocNearAddress = new AdpLocNearAddress(this,listPoi);
        lv_PoiList.setAdapter(adpLocNearAddress);
    }

    /**
     * 定位函数,同时设置地图显示中心点为当前坐标,移动地图位置
     * @param context
     */
    private  void goGetLocation(Context context) {
        Log.i(TAG, "goGetLocation");

        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setIgnoreKillProcess(false);
        locationClientOption.setEnableSimulateGps(true);
        locationClientOption.setCoorType("bd09ll");

        mLocClient = new LocationClient(context);
        mLocClient.setLocOption(locationClientOption);

        mLocClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                Log.i(TAG, "onReceiveLocation");

                mLatLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                mHandler.sendEmptyMessage(2);


                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(mLatLng)
                        .zoom(18)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                baiduMap .setMapStatus(mMapStatusUpdate);
                // 定位成功后销毁
                mLocClient.stop();
            }
        });
        mLocClient.start();
    }

    /**
     * 发起poi周边搜索,搜索附近药店
     * @param ll
     */
    private  void goPoiSearch(LatLng ll) {
        Log.i(TAG, "goPoiSearch");

        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.location(ll);
        option.radius(3000);
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.keyword("药店");
        option.pageCapacity(10);	// 每页最多含多少条结果
        option.pageNum(0);	// 查询的页码

        mPoiSManager.searchNearby(option);
    }

    /**
     * 显示poi在地图上
     * @param result
     */
    public  void showPoi(PoiResult result){
        baiduMap.clear();
        //创建PoiOverlay
        PoiOverlay overlay = new MyPoiOverlay(baiduMap);
        //设置overlay可以处理标注点击事件
        baiduMap.setOnMarkerClickListener(overlay);
        //设置PoiOverlay数据
        overlay.setData(result);
        //添加PoiOverlay到地图中
        overlay.addToMap();
        overlay.zoomToSpan();


    }

}

