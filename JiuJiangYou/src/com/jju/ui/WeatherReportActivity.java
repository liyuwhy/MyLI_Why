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


// ����Ԥ��
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
				// TODO �Զ����ɵķ������
				init();
			}
		});
		button1.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO �Զ����ɵķ������
				button1.setText("�ϴ�����");
				return false;
			}
		});
		
		
		//�����ݿ�
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
//		System.out.println("��2321λ�ã�");
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
			            //��������
			            case 0:
			                                    //����
			                String city = forcast.getCity();
			                                    String today = "���� ( "+ forcast.getDate() + " )";
			                String todayWeather = forcast.getDayWeather() + "    "
			                        + forcast.getDayTemp() + "/" + forcast.getNightTemp()
			                        + "    " + forcast.getDayWindPower();
			                break;
			            //��������
			            case 1:
			                 
			                String tomorrow = "���� ( "+ forcast.getDate() + " )";
			                String tomorrowWeather = forcast.getDayWeather() + "    "
			                        + forcast.getDayTemp() + "/" + forcast.getNightTemp()
			                        + "    " + forcast.getDayWindPower();
			                break;
			            //��������
			            case 2:
			                 
			                String aftertomorrow = "����( "+ forcast.getDate() + " )";
			                String aftertomorrowWeather = forcast.getDayWeather() + "    "
			                        + forcast.getDayTemp() + "/" + forcast.getNightTemp()
			                        + "    " + forcast.getDayWindPower();
			                break;
			            }
			          
			        }
			        
			    }else{
			        // ��ȡ����Ԥ��ʧ��
			        Toast.makeText(this,"��ȡ����Ԥ��ʧ��:"+ aMapLocalWeatherForecast.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT).show();
			    }
			    
	}

	public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
//		Toast.makeText(this,"��ȡ����:", 0).show();
		System.out.print("����"+ aMapLocalWeatherLive.getAMapException().getErrorCode());
		// TODO Auto-generated method stub
		if(aMapLocalWeatherLive!=null && aMapLocalWeatherLive.getAMapException().getErrorCode() == 0){
			String city = aMapLocalWeatherLive.getCity();//����
			tv1.setText("���У�"+ city);
			String temp = aMapLocalWeatherLive.getTemperature();
			tv2.setText("�����¶ȣ�"+temp+"��C")
			;
			String weather = aMapLocalWeatherLive.getWeather();//�������
			tv3.setText("������"+ weather);
			String windDir = aMapLocalWeatherLive.getWindDir();//����
			tv4.setText("����"+ windDir);
			String windPower = aMapLocalWeatherLive.getWindPower();//����
			tv5.setText("������"+ windPower);
			String humidity = aMapLocalWeatherLive.getHumidity();//����ʪ��
			tv6.setText("����ʪ�ȣ�"+ humidity );
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
			String   date   =   sDateFormat.format(new   java.util.Date());  
			tv7.setText(date);
			db.execSQL("INSERT INTO person VALUES (NULL, city, tenp , weather , date)");
			
			}
		else{
				
			Toast.makeText(this,"��ȡ����Ԥ��ʧ��:"+aMapLocalWeatherLive.getAMapException().getErrorCode(), 0).show();
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
		//Toast.makeText(this,"��ȡλ��:", 0).show();
		System.out.println("����"+ amapLocation.getAMapException().getErrorCode());
//		Toast.makeText(this, "��ȡλ��000000000000000000000000000000000000000000000000000000000000000000000:"+amapLocation.getAMapException().getErrorCode(), 0);
        if(amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0){
            //��ȡλ����Ϣ
            Double geoLat = amapLocation.getLatitude();
            Double geoLng = amapLocation.getLongitude();
//            Toast.makeText(this,geoLat.toString(), 0).show();
            System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"+geoLat);
        }
        
        mLocationManagerProxy.requestWeatherUpdates(LocationManagerProxy.WEATHER_TYPE_LIVE, this);
	}
}
