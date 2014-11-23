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

	// HttpGet��ʽ����
	public static String requestByHttpGet(String url) throws Exception {
		// �½�HttpGet����
		HttpGet httpGet = new HttpGet(url);
		// ��ȡHttpClient����
		HttpClient httpClient = new DefaultHttpClient();
		// ��ȡHttpResponseʵ��
		HttpResponse httpResp = httpClient.execute(httpGet);
		// �ж��ǹ�����ɹ�
		if (httpResp.getStatusLine().getStatusCode() == 200) {
			// ��ȡ���ص�����
			String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
			return result;
		} else {
			return "����ʧ��";
		}
	}

	// HttpPost��ʽ����
	public static String requestByHttpPost(String url,
			List<NameValuePair> params) throws ParseException, IOException {
		// �½�HttpPost����
		HttpPost httpPost = new HttpPost(url);
		// Post����
		/*
		 * List<NameValuePair> params = new ArrayList<NameValuePair>();
		 * params.add(new BasicNameValuePair("id", "helloworld"));
		 * params.add(new BasicNameValuePair("pwd", "android"));
		 */
		// �����ַ���
		HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		// ���ò���ʵ��
		httpPost.setEntity(entity);
		// ��ȡHttpClient����
		HttpClient httpClient = new DefaultHttpClient();
		// ��ȡHttpResponseʵ��
		HttpResponse httpResp = httpClient.execute(httpPost);
		// �ж��ǹ�����ɹ�
		if (httpResp.getStatusLine().getStatusCode() == 200) {
			// ��ȡ���ص�����
			String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
			return result;
		} else {
			return "����ʧ��";
		}
	}

	// �̻�����
	// city a ��ַ lng���� latγ�� q�ؼ���
	// cate ��� rc ���򷽷� as ��������
	// from ���ؽ����ʼλ�� to
	public static String storeSearch(Parameters parameters) throws Exception {
		
		String url = "http://openapi.aibang.com/search?app_key=a35c4781ad5f11795b4ee396715e0728"
				+  parameters.toMyUrl() ;

		Log.i("test", "++" + url);
		String result = requestByConnect(url);
		Log.i("test", "�õ����");
		Log.i("test", "++testreuslt" + result);
		return result;

	}

	/*
	 * // ��¼
	 *//**
	 * 
	 * @param login_id
	 *            ��¼��
	 * @param login_pass
	 *            ��¼����
	 * @return
	 * @throws Exception
	 *             ��������json����
	 */
	/*
	 * public static boolean loginPost(String login_id,String login_pass) throws
	 * Exception { String url = ConstantForAll.makeUrl("login", sessionID);
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // login_id
	 * ��ԱID�����ֻ��� login_pass ��Ա���� step: �̶�ֵ1 params.add(new
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
	 * //TODO �ɹ����Խ�����ȥ } } return false; }
	 * 
	 * // ע��
	 *//**
	 * 
	 * @param login_id
	 *            ��¼��
	 * @param check_code
	 *            6λ��֤��
	 * @param real_code
	 *            ������ -- ��Ϊ��
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	/*
	 * public static String registerPost(String login_id,String
	 * check_code,String real_code) throws ParseException, IOException {
	 * Log.i("test", "ע��");
	 * 
	 * String url = ConstantForAll.makeUrl("register",sessionID );
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // login_id
	 * ����ע���ID�����ֻ��� // step �̶�ֵ1 // check_code 4λ��֤�� //��rel_m_id
	 * �����˵Ļ�ԱID���������˵��ֻ��� params.add(new BasicNameValuePair("login_id",
	 * login_id)); params.add(new BasicNameValuePair("step", "1"));
	 * params.add(new BasicNameValuePair("check_code", check_code));
	 * params.add(new BasicNameValuePair("rel_m_id", real_code)); String result
	 * = null; result = requestByHttpPost(url, params);
	 * 
	 * 
	 * 
	 * return result; }
	 * 
	 * // ͼƬ�ϴ� public static String shopUpload(String url) throws
	 * ClientProtocolException, IOException { url =
	 * ConstantForAll.makeUrl("shopreg", sessionID); HttpPost post = new
	 * HttpPost(url); File file = new File(PicSolve.IMG_PATH, "pic_one.jpg");
	 * MultipartEntity multipart = new MultipartEntity();
	 * 
	 * // step ȡֵ�ֱ�Ϊ1/2/3/4 m_id �����Ա��id�����ֻ��� member_image ��Աͷ��ͼƬ�ļ�������1ʱ�ϴ� //
	 * up_id_card ��Աչʾ֤ͼƬ�ļ�������2ʱ�ϴ� up_licence ��Ա�ʸ�֤ͼƬ�ļ�������3ʱ�ϴ� // member_name
	 * ��Ա����������4ʱ�ύ province ��Ա���ڵ���ʡ�ݣ�����4ʱ�ύ // city ��Ա���ڵ�������������4ʱ�ύ county
	 * ��Ա���ڵ����أ�����4ʱ�ύ
	 * 
	 * multipart.addPart("m_id", new StringBody("13902432043"));
	 * multipart.addPart("step", new StringBody("1"));
	 * multipart.addPart("member_image", new FileBody(file)); HttpClient client
	 * = new DefaultHttpClient(); post.setEntity(multipart); HttpResponse
	 * response = client.execute(post);
	 * 
	 * // �ж��ǹ�����ɹ� if (response.getStatusLine().getStatusCode() == 200) { //
	 * ��ȡ���ص����� String result = EntityUtils.toString(response.getEntity(),
	 * "UTF-8"); return result; } else { return "����ʧ��"; }
	 * 
	 * }
	 * 
	 * // ��Ա��Ϣ�ϴ� public static String memberInfoUpload(String url) {
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // province
	 * ʡ������ // city �м����� // county �ؼ����� params.add(new
	 * BasicNameValuePair("step", "4")); params.add(new
	 * BasicNameValuePair("m_id", "12345678912")); params.add(new
	 * BasicNameValuePair("member_name", "testFor123")); params.add(new
	 * BasicNameValuePair("province", "����ʡ")); params.add(new
	 * BasicNameValuePair("city", "�Ϸ���")); params.add(new
	 * BasicNameValuePair("province", "��ɽ��")); String result = null; try {
	 * result = requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 * 
	 * // ���� public static String AreaPost(String url) { List<NameValuePair>
	 * params = new ArrayList<NameValuePair>(); // province ʡ������ // city �м����� //
	 * county �ؼ����� params.add(new BasicNameValuePair("province", "����"));
	 * params.add(new BasicNameValuePair("city", "")); params.add(new
	 * BasicNameValuePair("county", "")); String result = null; try { result =
	 * requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 * 
	 * // ��Ʒ����
	 * 
	 * �ӿ�URL��http(s)://����/mobile.php?m=prod_search&v=v1&sessionID=��Ӧ��sessionID
	 * POST������company Ҫ�����Ĺ�˾���� p ��ǰ��ҳ��
	 * 
	 * 
	 * public static String productSearch(String url, String company, int p) {
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); // province
	 * ʡ������ // city �м����� // county �ؼ����� params.add(new
	 * BasicNameValuePair("company", company)); params.add(new
	 * BasicNameValuePair("p", String.valueOf(1))); String result = null; try {
	 * result = requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result;
	 * 
	 * }
	 * 
	 * // ��Ʒϸ��
	 * 
	 * �ӿ�URL��http(s)://����/mobile.php?m=prod_detail&v=v1&sessionID=��Ӧ��sessionID
	 * POST������uid ��Ʒ��Ψһͳ������
	 * 
	 * 
	 * public static String productDatil(String url) { List<NameValuePair>
	 * params = new ArrayList<NameValuePair>(); params.add(new
	 * BasicNameValuePair("uid", "2")); String result = null; try { result =
	 * requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 * 
	 * // ��Ʒ�ϼ�
	 * 
	 * �ӿ�URL��http(s)://����/mobile.php?m=prod_upv&v=v1&sessionID=��Ӧ��sessionID
	 * POST������uids Ҫ�ϼܵĲ�Ʒ���룬����ж�����룬�԰�Ƕ��š�,���ָ�
	 * 
	 * public static String productUp(String url) { List<NameValuePair> params =
	 * new ArrayList<NameValuePair>(); params.add(new BasicNameValuePair("uids",
	 * "2")); String result = null; try { result = requestByHttpPost(url,
	 * params); } catch (Exception e) { e.printStackTrace(); } return result; }
	 * 
	 * // ͼƬ ����
	 * 
	 * �ӿ�URL��http(s)://����/mobile.php?m=photo_search&v=v1&sessionID=��Ӧ��sessionID
	 * POST������p ��ǰҳ�� uid ����� pic thumbnail / photo
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
	 * // ���ص��̵�ַ
	 * 
	 * �ӿ�URL��http(s)://����/mobile.php?m=qrcode&v=v1&sessionID=��Ӧ��sessionID
	 * POST������m_id ��ԱID�����ֻ���
	 * 
	 * public static String qrcodeGet(String url, String m_id) {
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("m_id", m_id)); String result = null;
	 * try { result = requestByHttpPost(url, params); } catch (Exception e) {
	 * e.printStackTrace(); } return result; }
	 */
	// ���ַ�����unicode���� ת��Ϊ ����

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
