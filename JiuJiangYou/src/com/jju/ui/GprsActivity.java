package com.jju.ui;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;


//  位置信息
public class GprsActivity extends Activity implements  AMapLocationListener,OnGeocodeSearchListener{
	
	private TextView t1;
	private TextView t2;
	private TextView t3;
	private TextView t4;
	private TextView t5;
	private TextView t6;
	private TextView t7;
	private Button button1;
	private ImageView image1;
	private LocationManagerProxy mLocationManagerProxy;

	  @Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_position);
		
		t1 = (TextView)findViewById(R.id.T1);
		t2 = (TextView)findViewById(R.id.T2);
		t3 = (TextView)findViewById(R.id.T3);
		t4 = (TextView)findViewById(R.id.T4);
		t5 = (TextView)findViewById(R.id.T5);
		t6 = (TextView)findViewById(R.id.T6);
		t7 = (TextView)findViewById(R.id.T7);
		button1 = (Button)findViewById(R.id.get);
		image1 = (ImageView)findViewById(R.id.imageView1);
		
		
		
		
		
//		locationManager = (LocationManager)GprsActivity.this.getSystemService(Context.LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(
//				LocationManagerProxy.NETWORK_PROVIDER, 100, 0, this);
//		
//		geocoderSearch = new GeocodeSearch(this);
//		geocoderSearch.setOnGeocodeSearchListener(this);
	init();

	}
	  private void init(){
		  
		  mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		  mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,  60*1000, 5, this);
		  mLocationManagerProxy.setGpsEnable(false);
	  }
	public void onLocationChanged(Location arg0) {
		// TODO 自动生成的方法存根
		
	}
	public void onProviderDisabled(String arg0) {
		// TODO 自动生成的方法存根
		
	}
	public void onProviderEnabled(String arg0) {
		// TODO 自动生成的方法存根
		
	}
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO 自动生成的方法存根
		
	}
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO 自动生成的方法存根
		
	}
	public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {
		// TODO 自动生成的方法存根
		
	}
	public void onLocationChanged(AMapLocation arg0) {
		// TODO 自动生成的方法存根
		if(arg0 != null && arg0.getAMapException().getErrorCode()==0){
			Double geoLat = arg0.getLatitude();
			Double geoLng = arg0.getLongitude();
			String Address = arg0.getAddress();
			arg0.getCity();
			t2.setText("纬度:" +String.valueOf(geoLat));
			t3.setText("经度:"+ String.valueOf(geoLng));
			t4.setText("位置:"+ Address);
			t1.setText("城市:" + arg0.getCity());
			t5.setText("海拔:" + arg0.getAccuracy() );
			t7.setText("城市编码："+ arg0.getCityCode());
			t6.setText("速度：" + arg0.getSpeed());
		}
		
	}




	




		
		
	

}
