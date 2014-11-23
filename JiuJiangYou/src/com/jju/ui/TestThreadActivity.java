package com.jju.ui;


import java.io.IOException;

import com.jju.net.HttpUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.AsyncTask;
import android.os.Bundle;

public class TestThreadActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    new GetHtmlTask().execute("http://www.baidu.com/");
	}
	
	
	public void showDialog(String result) {
		AlertDialog builder = new Builder(getBaseContext()).setMessage(result)
				.setTitle("结果").setNegativeButton("确认", null).show();
	}
	
	class GetHtmlTask extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			String htmlStr = null;
			try {
				htmlStr = HttpUtil.requestByConnect("http://www.baidu.com");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return htmlStr;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			showDialog(result);
		}
	}

}
