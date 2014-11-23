package com.jju.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button button1 = null;
	Button button2 = null;
	Button button4 = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button4 = (Button)findViewById(R.id.button3);
        
        button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this , GprsActivity.class);
				startActivity(intent);
			}
		});
        button2.setOnClickListener(new OnClickListener() {
			
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				Intent intent = new Intent();
   				intent.setClass(MainActivity.this , MapShowActivity.class);
   				startActivity(intent);
   			}
   		});
      
        button4.setOnClickListener(new OnClickListener() {
			
     			public void onClick(View v) {
     				// TODO Auto-generated method stub
     				Intent intent = new Intent();
     				intent.setClass(MainActivity.this , WeatherReportActivity.class);
     				startActivity(intent);
     			}
     		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
