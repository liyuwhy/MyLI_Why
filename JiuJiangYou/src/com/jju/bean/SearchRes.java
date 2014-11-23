package com.jju.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import com.jju.bean.FeedReadContract.FeedSearchResult;
import com.jju.bean.FeedReadContract.FeedSearchResult.FeedShop;

import android.util.JsonReader;
import android.util.Log;

public class SearchRes {
  
	private String indexNum;  //  總結果數
	private String total;  // 查詢到的記錄
	private String resultNum;  // 当前返回的结果
	private String webUrl;
	private String wapUrl;
	
	private List<ShopBean> listShop;

	public String getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getResultNum() {
		return resultNum;
	}

	public void setResultNum(String resultNum) {
		this.resultNum = resultNum;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getWapUrl() {
		return wapUrl;
	}

	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}

	public List<ShopBean> getListShop() {
		return listShop;
	}

	public void setListShop(List<ShopBean> listShop) {
		this.listShop = listShop;
	}
	
	public static  SearchRes parseFromJson(String s) throws JSONException{
		SearchRes result = new SearchRes();
		
		JSONTokener  jsonTokener =  new JSONTokener(s);
		JSONObject jsonObject =  (JSONObject) jsonTokener.nextValue();
		 
		
	    if( jsonObject.get(FeedSearchResult.COLUMN_1_index) == null)
	    	throw new JSONException("没有这个字段");
	    result.setIndexNum(jsonObject.getString(FeedSearchResult.COLUMN_1_index));
	    result.setTotal(jsonObject.getString(FeedSearchResult.COLUMN_2_total));
	    result.setResultNum(jsonObject.getString(FeedSearchResult.COLUMN_3_num));
	    
	    JSONObject shopJsonObj = jsonObject.getJSONObject(FeedSearchResult.COLUMN_6_bizs);
	   
	    if( shopJsonObj !=null){
	    	 JSONArray shopJson = shopJsonObj.getJSONArray("biz");
	    	 List<ShopBean> listShop = new ArrayList<ShopBean>();
	    	for(int i=0;i<shopJson.length();i++){
	    		ShopBean shopBean = new ShopBean();
	    		JSONObject aShopJson = shopJson.getJSONObject(i);
	    		shopBean.setId(aShopJson.getString(FeedShop.COLUMN_1_id));
	    		shopBean.setName(aShopJson.getString(FeedShop.COLUMN_2_name));
	    		shopBean.setAddr(aShopJson.getString(FeedShop.COLUMN_3_addr));
	    		shopBean.setTel(aShopJson.getString(FeedShop.COLUMN_4_tel));
	    		listShop.add(shopBean);
	    	}
	    	result.setListShop(listShop);
	    }
	    return result;
	    
	    
	   
	    
	    
		 
		 
		
	}
	
}
