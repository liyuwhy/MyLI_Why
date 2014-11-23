package com.jju.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.jju.net.AibangData.DataCallBack;

import android.util.Log;

public class HttpUtil {

	public static String requestByConnect(String url) throws IOException {
		
		URL urlNet =  new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlNet
				.openConnection();
		connection.connect();
		Log.i("test", "+++url" + url);
		int responseCode = connection.getResponseCode();
		Log.i("test", "++++code" + responseCode);
		String message = connection.getResponseMessage();
		Log.i("test", "++++message" + message);

		/*BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		StringBuilder sb = new StringBuilder("");
		String aLine;
		while ((aLine = reader.readLine()) != null) {
			sb.append(aLine);
		}
		reader.close();*/
		
		InputStream in = new BufferedInputStream(connection.getInputStream());
		byte[] buffer = new byte[1024];
		int count;
		StringBuilder sb = new StringBuilder("");
		while( (count = in.read(buffer))>0 ){
			sb.append( new String(buffer, 0, count));
		}
		connection.disconnect();
		Log.i("test", "+++result" + sb.toString());
		return sb.toString();
	}

	// HttpGet方式请求
	public static String requestByHttpGet(String url) throws Exception {
		// 新建HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
		// 获取HttpResponse实例
		HttpResponse httpResp = httpClient.execute(httpGet);
		// 判断是够请求成功
		if (httpResp.getStatusLine().getStatusCode() == 200) {
			// 获取返回的数据
			String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
			return result;
		} else {
			return "请求失败";
		}
	}

	// HttpPost方式请求
	public static String requestByHttpPost(String url,
			List<NameValuePair> params) throws ParseException, IOException {
		// 新建HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		// Post参数
		/*
		 * List<NameValuePair> params = new ArrayList<NameValuePair>();
		 * params.add(new BasicNameValuePair("id", "helloworld"));
		 * params.add(new BasicNameValuePair("pwd", "android"));
		 */
		// 设置字符集
		HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		// 设置参数实体
		httpPost.setEntity(entity);
		// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
		// 获取HttpResponse实例
		HttpResponse httpResp = httpClient.execute(httpPost);
		// 判断是够请求成功
		if (httpResp.getStatusLine().getStatusCode() == 200) {
			// 获取返回的数据
			String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
			return result;
		} else {
			return "请求失败";
		}
	}

	// 商户搜索
	// city a 地址 lng经度 lat纬度 q关键字
	// cate 类别 rc 排序方法 as 距离限制
	// from 返回结果开始位置 to
	public static String storeSearch(Parameters parameters) throws Exception {
		
		String url = "http://openapi.aibang.com/search?app_key=a35c4781ad5f11795b4ee396715e0728"
				+  parameters.toMyUrl() ;

		Log.i("test", "++" + url);
		String result = requestByConnect(url);
		Log.i("test", "得到结果");
		Log.i("test", "++testreuslt" + result);
		return result;

	}

	/*
	 * // 登录
	 *//**
	 * 
	 * @param login_id
	 *            登录名
	 * @param login_pass
	 *            登录密码
	 * @return
	 * @throws Exception
	 *             网络错误或json解析
	 */
	/*
	 * public static boolean loginPost(String login_id,String login_pass) throws
	 * Exception { String url = ConstantForAll.makeUrl("login", sessionID);
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // login_id
	 * 会员ID，即手机号 login_pass 会员密码 step: 固定值1 params.add(new
	 * BasicNameValuePair("login_id", "13902432043")); params.add(new
	 * BasicNameValuePair("login_pass", "123456")); params.add(new
	 * BasicNameValuePair("step", "1")); String result = null;
	 * 
	 * result = requestByHttpPost(url, params);
	 * 
	 * if( result!= null){ JSONTokener tokener = new JSONTokener(result);
	 * JSONObject jsonObj = (JSONObject) tokener.nextValue(); String modelName =
	 * jsonObj.getString("str_model"); if( modelName == "login"){ int isSuccess
	 * = jsonObj.getInt("int_success"); return isSuccess == 1 ? true:false;
	 * //TODO 成功可以解析下去 } } return false; }
	 * 
	 * // 注册
	 *//**
	 * 
	 * @param login_id
	 *            登录名
	 * @param check_code
	 *            6位验证码
	 * @param real_code
	 *            邀请码 -- 可为空
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	/*
	 * public static String registerPost(String login_id,String
	 * check_code,String real_code) throws ParseException, IOException {
	 * Log.i("test", "注册");
	 * 
	 * String url = ConstantForAll.makeUrl("register",sessionID );
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // login_id
	 * 用于注册的ID，即手机号 // step 固定值1 // check_code 4位验证号 //　rel_m_id
	 * 邀请人的会员ID，即邀请人的手机号 params.add(new BasicNameValuePair("login_id",
	 * login_id)); params.add(new BasicNameValuePair("step", "1"));
	 * params.add(new BasicNameValuePair("check_code", check_code));
	 * params.add(new BasicNameValuePair("rel_m_id", real_code)); String result
	 * = null; result = requestByHttpPost(url, params);
	 * 
	 * 
	 * 
	 * return result; }
	 * 
	 * // 图片上传 public static String shopUpload(String url) throws
	 * ClientProtocolException, IOException { url =
	 * ConstantForAll.makeUrl("shopreg", sessionID); HttpPost post = new
	 * HttpPost(url); File file = new File(PicSolve.IMG_PATH, "pic_one.jpg");
	 * MultipartEntity multipart = new MultipartEntity();
	 * 
	 * // step 取值分别为1/2/3/4 m_id 开店会员的id，即手机号 member_image 会员头像图片文件，步骤1时上传 //
	 * up_id_card 会员展示证图片文件，步骤2时上传 up_licence 会员资格证图片文件，步骤3时上传 // member_name
	 * 会员姓名，步骤4时提交 province 会员所在地区省份，步骤4时提交 // city 会员所在地区市区，步骤4时提交 county
	 * 会员所在地区县，步骤4时提交
	 * 
	 * multipart.addPart("m_id", new StringBody("13902432043"));
	 * multipart.addPart("step", new StringBody("1"));
	 * multipart.addPart("member_image", new FileBody(file)); HttpClient client
	 * = new DefaultHttpClient(); post.setEntity(multipart); HttpResponse
	 * response = client.execute(post);
	 * 
	 * // 判断是够请求成功 if (response.getStatusLine().getStatusCode() == 200) { //
	 * 获取返回的数据 String result = EntityUtils.toString(response.getEntity(),
	 * "UTF-8"); return result; } else { return "请求失败"; }
	 * 
	 * }
	 * 
	 * // 会员信息上传 public static String memberInfoUpload(String url) {
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // province
	 * 省份名称 // city 市级名称 // county 县级名称 params.add(new
	 * BasicNameValuePair("step", "4")); params.add(new
	 * BasicNameValuePair("m_id", "12345678912")); params.add(new
	 * BasicNameValuePair("member_name", "testFor123")); params.add(new
	 * BasicNameValuePair("province", "安徽省")); params.add(new
	 * BasicNameValuePair("city", "合肥市")); params.add(new
	 * BasicNameValuePair("province", "蜀山区")); String result = null; try {
	 * result = requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 * 
	 * // 地区 public static String AreaPost(String url) { List<NameValuePair>
	 * params = new ArrayList<NameValuePair>(); // province 省份名称 // city 市级名称 //
	 * county 县级名称 params.add(new BasicNameValuePair("province", "陕西"));
	 * params.add(new BasicNameValuePair("city", "")); params.add(new
	 * BasicNameValuePair("county", "")); String result = null; try { result =
	 * requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 * 
	 * // 产品搜索
	 * 
	 * 接口URL：http(s)://域名/mobile.php?m=prod_search&v=v1&sessionID=对应的sessionID
	 * POST参数：company 要搜索的公司名称 p 当前的页码
	 * 
	 * 
	 * public static String productSearch(String url, String company, int p) {
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // province
	 * 省份名称 // city 市级名称 // county 县级名称 params.add(new
	 * BasicNameValuePair("company", company)); params.add(new
	 * BasicNameValuePair("p", String.valueOf(1))); String result = null; try {
	 * result = requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result;
	 * 
	 * }
	 * 
	 * // 产品细节
	 * 
	 * 接口URL：http(s)://域名/mobile.php?m=prod_detail&v=v1&sessionID=对应的sessionID
	 * POST参数：uid 产品的唯一统码数组
	 * 
	 * 
	 * public static String productDatil(String url) { List<NameValuePair>
	 * params = new ArrayList<NameValuePair>(); params.add(new
	 * BasicNameValuePair("uid", "2")); String result = null; try { result =
	 * requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 * 
	 * // 产品上架
	 * 
	 * 接口URL：http(s)://域名/mobile.php?m=prod_upv&v=v1&sessionID=对应的sessionID
	 * POST参数：uids 要上架的产品编码，如果有多个编码，以半角逗号“,”分隔
	 * 
	 * public static String productUp(String url) { List<NameValuePair> params =
	 * new ArrayList<NameValuePair>(); params.add(new BasicNameValuePair("uids",
	 * "2")); String result = null; try { result = requestByHttpPost(url,
	 * params); } catch (Exception e) { e.printStackTrace(); } return result; }
	 * 
	 * // 图片 检索
	 * 
	 * 接口URL：http(s)://域名/mobile.php?m=photo_search&v=v1&sessionID=对应的sessionID
	 * POST参数：p 当前页码 uid 相册编号 pic thumbnail / photo
	 * 
	 * public static String photoSearch(String url, int p, String uid, String
	 * pic) { List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("p", String.valueOf(p)));
	 * params.add(new BasicNameValuePair("uid", uid)); params.add(new
	 * BasicNameValuePair("pic", pic)); String result = null; try { result =
	 * requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result;
	 * 
	 * }
	 * 
	 * // 返回店铺地址
	 * 
	 * 接口URL：http(s)://域名/mobile.php?m=qrcode&v=v1&sessionID=对应的sessionID
	 * POST参数：m_id 会员ID，即手机号
	 * 
	 * public static String qrcodeGet(String url, String m_id) {
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("m_id", m_id)); String result = null;
	 * try { result = requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 */
	// 将字符串中unicode代码 转换为 中文

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

}
