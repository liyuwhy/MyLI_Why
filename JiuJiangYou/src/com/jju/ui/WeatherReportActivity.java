package com.jju.ui;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocalDayWeatherForecast;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;


// 天气预备
public class WeatherReportActivity extends Activity implements AMapLocalWeatherListener,AMapLocationListener{
	private LocationManagerProxy mLocationManagerProxy;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
	private Button button1;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_weather_report);
		tv1=(TextView) findViewById(R.id.T1);
		tv2=(TextView) findViewById(R.id.T2);
		tv3=(TextView) findViewById(R.id.T3);
		tv4=(TextView) findViewById(R.id.T4);
		tv5=(TextView) findViewById(R.id.T5);
		tv6=(TextView) findViewById(R.id.T6);
		tv7=(TextView) findViewById(R.id.T7);
		button1=(Button) findViewById(R.id.button1);
		
		button1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				init();
			}
		});
		button1.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO 自动生成的方法存根
				button1.setText("上传数据");
				return false;
			}
		});
		
		
		//打开数据库
	  db =openOrCreateDatabase("person", Context.MODE_PRIVATE, null);
	  db.execSQL("DROP TABLE IF EXISTS person");
	  ContentValues cv = new ContentValues();
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void init()
	{
//		Toast.makeText(this,"2321", 0).show();
//		System.out.println("获2321位置：");
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 500, 0, this);
//        mLocationManagerProxy.setGpsEnable(false);
       
        


        
	}

	public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {
		// TODO Auto-generated method stub
		
		     
			    if(aMapLocalWeatherForecast != null && aMapLocalWeatherForecast.getAMapException().getErrorCode() == 0){
			 
			        List<AMapLocalDayWeatherForecast> forcasts = aMapLocalWeatherForecast
			                .getWeatherForecast();
			        for (int i = 0; i < forcasts.size(); i++) {
			            AMapLocalDayWeatherForecast forcast = forcasts.get(i);
			            switch (i) {
			            //今天天气
			            case 0:
			                                    //城市
			                String city = forcast.getCity();
			                                    String today = "今天 ( "+ forcast.getDate() + " )";
			                String todayWeather = forcast.getDayWeather() + "    "
			                        + forcast.getDayTemp() + "/" + forcast.getNightTemp()
			                        + "    " + forcast.getDayWindPower();
			                break;
			            //明天天气
			            case 1:
			                 
			                String tomorrow = "明天 ( "+ forcast.getDate() + " )";
			                String tomorrowWeather = forcast.getDayWeather() + "    "
			                        + forcast.getDayTemp() + "/" + forcast.getNightTemp()
			                        + "    " + forcast.getDayWindPower();
			                break;
			            //后天天气
			            case 2:
			                 
			                String aftertomorrow = "后天( "+ forcast.getDate() + " )";
			                String aftertomorrowWeather = forcast.getDayWeather() + "    "
			                        + forcast.getDayTemp() + "/" + forcast.getNightTemp()
			                        + "    " + forcast.getDayWindPower();
			                break;
			            }
			          
			        }
			        
			    }else{
			        // 获取天气预报失败
			        Toast.makeText(this,"获取天气预报失败:"+ aMapLocalWeatherForecast.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT).show();
			    }
			    
	}

	public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
//		Toast.makeText(this,"获取天气:", 0).show();
		System.out.print("错误："+ aMapLocalWeatherLive.getAMapException().getErrorCode());
		// TODO Auto-generated method stub
		if(aMapLocalWeatherLive!=null && aMapLocalWeatherLive.getAMapException().getErrorCode() == 0){
			String city = aMapLocalWeatherLive.getCity();//城市
			tv1.setText("城市："+ city);
			String temp = aMapLocalWeatherLive.getTemperature();
			tv2.setText("现在温度："+temp+"°C")
			;
			String weather = aMapLocalWeatherLive.getWeather();//天气情况
			tv3.setText("天气："+ weather);
			String windDir = aMapLocalWeatherLive.getWindDir();//风向
			tv4.setText("风向："+ windDir);
			String windPower = aMapLocalWeatherLive.getWindPower();//风力
			tv5.setText("风力："+ windPower);
			String humidity = aMapLocalWeatherLive.getHumidity();//空气湿度
			tv6.setText("空气湿度："+ humidity );
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
			String   date   =   sDateFormat.format(new   java.util.Date());  
			tv7.setText(date);
			db.execSQL("INSERT INTO person VALUES (NULL, city, tenp , weather , date)");
			
			}
		else{
				
			Toast.makeText(this,"获取天气预报失败:"+aMapLocalWeatherLive.getAMapException().getErrorCode(), 0).show();
			}	
	}

	public void onLocationChanged(Location arg0) {
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

	public void onLocationChanged(AMapLocation amapLocation) {
		//Toast.makeText(this,"获取位置:", 0).show();
		System.out.println("错误："+ amapLocation.getAMapException().getErrorCode());
//		Toast.makeText(this, "获取位置000000000000000000000000000000000000000000000000000000000000000000000:"+amapLocation.getAMapException().getErrorCode(), 0);
        if(amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0){
            //获取位置信息
            Double geoLat = amapLocation.getLatitude();
            Double geoLng = amapLocation.getLongitude();
//            Toast.makeText(this,geoLat.toString(), 0).show();
            System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"+geoLat);
        }
        
        mLocationManagerProxy.requestWeatherUpdates(LocationManagerProxy.WEATHER_TYPE_LIVE, this);
	}
}
