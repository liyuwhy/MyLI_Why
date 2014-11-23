package com.jju.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

// 本地地图
public class MapShowActivity extends Activity implements LocationSource, AMapLocationListener{

	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker marker;
	private Button button1;
	private Button button2;
	private int x = 0;
	
	private double y ;
	private double z ;
	private ProgressDialog progDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mapView = (MapView)findViewById(R.id.mapview);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		mapView.onCreate(savedInstanceState);
		button2.setOnClickListener(new OnClickListener() {
		  
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if(x==0){
				aMap.setTrafficEnabled(true);
				x+=1;
			}else{
				aMap.setTrafficEnabled(false);
				x-=1;
			}	
			
			}
			
		});
		
		
		
		init();
		
		
	}
	private void init(){
		if(aMap == null){
			aMap = mapView.getMap();
			setUpMap();
			aMap.setMapType(AMap.MAP_TYPE_NORMAL);
			MarkerOptions  markerOptions =new MarkerOptions();
	  		
	  	    markerOptions.position(new LatLng( y , z ));
	  	    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
	  		Marker marker = aMap.addMarker(markerOptions);
			

	  		
		}
	}
	//位置
	 private void setUpMap() {
	         aMap.setLocationSource(this);// 设置定位监听
	        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
	        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
	        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
	 }

    /**
     * 此方法需存在
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
 
    /**
     * 此方法需存在
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }
 
    /**
     * 此方法需存在
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
               
            	mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            	marker.setPosition(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
            	float bearing = aMap.getCameraPosition().bearing;
            	aMap.setMyLocationRotateAngle(bearing);//旋转角度
              z = amapLocation.getLatitude();
              y = amapLocation.getLongitude();
               
              
            }
          
        }
    
	
		
    }
	//激活监听
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
        	mAMapLocationManager = LocationManagerProxy.getInstance(this);
            //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
            //在定位结束后，在合适的生命周期调用destroy()方法     
            //其中如果间隔时间为-1，则定位只定一次
        	mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork , 1000, 10, this);
        }
    }
    //停机监听
     public void deactivate() {
        mListener = null;
        if (mAMapLocationManager != null) {
        	mAMapLocationManager.removeUpdates(this);
        	mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;
    }

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	
		
	}
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	

	
	
}
