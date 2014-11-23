package com.jju.net;


import android.os.AsyncTask;

public class AibangData {
	
	
	public static void executeWithAPI(Parameters param,DataCallBack callBack){
	    DataGetTask	task= new DataGetTask(param, callBack);
	    task.execute();
	}
	
	
	
	public interface  DataCallBack{
		public void resultDataLoad(String result);
	}
	
	static class DataGetTask extends AsyncTask<Integer	, Integer, String>{

		Parameters param;
		DataCallBack callBack;
		
		public DataGetTask(Parameters param,DataCallBack callBack) {
			this.param = param;
			this.callBack = callBack;
		}
		
		@Override
		protected String doInBackground(Integer... params) {
			String s = null;
			try {
				s = HttpUtil.storeSearch( param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		
		@Override
		protected void onPostExecute(String result) {
			callBack.resultDataLoad(HttpUtil.decodeUnicode(result));
		}
	}
 
}
