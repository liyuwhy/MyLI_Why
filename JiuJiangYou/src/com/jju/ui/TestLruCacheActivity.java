package com.jju.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jju.util.CacheUtil;
import com.jju.util.DiskLruCache;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class TestLruCacheActivity extends Activity{
	
	private ImageView imgView;
	private String imageUrl ="http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
	private DiskLruCache mDiskLruCache;
	private CacheUtil cacheUtil;

	
	Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			Bitmap mBitmap = cacheUtil.getFromDisk(imageUrl);
			imgView.setImageBitmap(mBitmap);
			Log.i("test", "dodo");
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imgView = new ImageView(this);
		imgView.setBackgroundResource(R.drawable.ic_launcher);
		setContentView(imgView);
		cacheUtil = new CacheUtil(this);
		new Thread(){
			public void run() {
				cacheUtil.urlSaveDisk(imageUrl);
				handler.sendEmptyMessage(0);
			};
		}.start();
		
		/*new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				return null;
			}
			
			protected void onPostExecute(Void result) {
				Bitmap mBitmap = cacheUtil.getFromDisk(imageUrl);
				imgView.setImageBitmap(mBitmap);
			};
			
		}.execute();*/
	
		
		

		
	}
	
	

}
