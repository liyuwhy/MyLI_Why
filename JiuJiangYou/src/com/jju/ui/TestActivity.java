package com.jju.ui;

import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;

import com.jju.bean.SearchRes;
import com.jju.net.AibangData;
import com.jju.net.HttpUtil;
import com.jju.net.Parameters;
import com.jju.net.AibangData.DataCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Parameters param = new Parameters().add("alt", "json")
				                           .add("city", URLEncoder.encode("北京"))
				                           .add("q", URLEncoder.encode("餐馆"));
		AibangData.executeWithAPI(param, new DataCallBack() {

			@Override
			public void resultDataLoad(String result) {
					showDialog(result);
			}
		});

	}

	public void showDialog(String result) {
		AlertDialog builder = new Builder(TestActivity.this).setMessage(result)
				.setTitle("结果").setNegativeButton("确认", null).show();
	}

}
