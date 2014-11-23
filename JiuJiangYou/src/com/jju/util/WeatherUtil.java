
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
 * 1.�ù���������ʵ���������ݷ��ʣ��������ݣ�
 * 2.ʵ��XML�ļ��� ����
 * @author С��
 *
 */
public class WeatherUtil {
	
	private static String URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
/**
 * ʵ�ַ��� �������������ݲ�����
 * @param city
 * @return
 * @throws RuntimeException
 */
	public static String getWeather(String city)throws RuntimeException{
		String xml = null;
		try{
		//����һ��Post����
		HttpPost request = new HttpPost(URL);
		//��װ�������
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("theCityName",city));
		HttpEntity entity = new UrlEncodedFormEntity(params);
		request.setEntity(entity);
		//��������
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(request);
		//�ж�Http���󷵻ص�״̬�Ƿ���ȷ��200��
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			//�ѷŻص�Entity�Ķ���ת�����ַ���
			xml=EntityUtils.toString(response.getEntity());
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return xml;
	}
	/**
	 * ���������Ϸ��ص�XML����
	 * @param xml
	 * @return
	 * @throws RuntimeException
	 */
	public static  List<String> parseXML(String xml)throws RuntimeException{
		List<String> weatherDatas = new ArrayList<String>();
		
		//��������XML�ļ��Ľ�����
	  XmlPullParser pull = Xml.newPullParser();
	  StringReader in = new StringReader(xml);
	  try{
	     pull.setInput(in);
	     //��ȡ�¼�����
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
	    	 eventType = pull.next();//��ȡ��һ���¼�����
	     }
	     
	     
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	return weatherDatas; 
	}
	
}
