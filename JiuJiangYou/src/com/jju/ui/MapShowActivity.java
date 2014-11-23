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

// ���ص�ͼ
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
	//λ��
	 private void setUpMap() {
	         aMap.setLocationSource(this);// ���ö�λ����
	        aMap.getUiSettings().setMyLocationButtonEnabled(true);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
	        aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
	        // ���ö�λ������Ϊ��λģʽ����λ��AMap.LOCATION_TYPE_LOCATE�������棨AMap.LOCATION_TYPE_MAP_FOLLOW��
	        // ��ͼ������������ת��AMap.LOCATION_TYPE_MAP_ROTATE������ģʽ
	 }

    /**
     * �˷��������
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
 
    /**
     * �˷��������
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }
 
    /**
     * �˷��������
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
               
            	mListener.onLocationChanged(amapLocation);// ��ʾϵͳС����
            	marker.setPosition(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
            	float bearing = aMap.getCameraPosition().bearing;
            	aMap.setMyLocationRotateAngle(bearing);//��ת�Ƕ�
              z = amapLocation.getLatitude();
              y = amapLocation.getLongitude();
               
              
            }
          
        }
    
	
		
    }
	//�������
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
        	mAMapLocationManager = LocationManagerProxy.getInstance(this);
            //�˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
            //ע�����ú��ʵĶ�λʱ��ļ���������ں���ʱ�����removeUpdates()������ȡ����λ����
            //�ڶ�λ�������ں��ʵ��������ڵ���destroy()����     
            //����������ʱ��Ϊ-1����λֻ��һ��
        	mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork , 1000, 10, this);
        }
    }
    //ͣ������
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
