
package com.jju.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

/**
 * 1.该工具类用来实现网络数据访问（天气数据）
 * 2.实现XML文件的 解析
 * @author 小白
 *
 */
public class WeatherUtil {
	
	private static String URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
/**
 * 实现访问 网络天气的数据并返回
 * @param city
 * @return
 * @throws RuntimeException
 */
	public static String getWeather(String city)throws RuntimeException{
		String xml = null;
		try{
		//创建一个Post请求
		HttpPost request = new HttpPost(URL);
		//封装请求参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("theCityName",city));
		HttpEntity entity = new UrlEncodedFormEntity(params);
		request.setEntity(entity);
		//发送请求
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(request);
		//判断Http请求返回的状态是否正确（200）
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			//把放回的Entity的对象转换成字符串
			xml=EntityUtils.toString(response.getEntity());
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return xml;
	}
	/**
	 * 解析网络上返回的XML数据
	 * @param xml
	 * @return
	 * @throws RuntimeException
	 */
	public static  List<String> parseXML(String xml)throws RuntimeException{
		List<String> weatherDatas = new ArrayList<String>();
		
		//创建解析XML文件的解析器
	  XmlPullParser pull = Xml.newPullParser();
	  StringReader in = new StringReader(xml);
	  try{
	     pull.setInput(in);
	     //获取事件类型
	     int eventType = pull.getEventType();
	     while(eventType!=XmlPullParser.END_DOCUMENT){
	    	 
	    	 switch (eventType) {
			case  XmlPullParser.START_TAG:
				String tag = pull.getName();
				if("string".equalsIgnoreCase(tag)){
					weatherDatas.add(pull.nextText());
				}
				
				break;
			default:
				break;
			}
	    	 eventType = pull.next();//获取下一个事件类型
	     }
	     
	     
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	return weatherDatas; 
	}
	
}
