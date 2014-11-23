package com.jju.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class CacheUtil {

	private DiskLruCache mDiskLruCache = null;
	private Context context;

	public CacheUtil(Context context) {
		this.context = context;
		initDiskCache();
	}

	private void initDiskCache() {
		try {
			File cacheFile = getDiskCacheDir(context, "bitmap");
			if (!cacheFile.exists()) {
				cacheFile.mkdir();
			}
			mDiskLruCache = DiskLruCache.open(cacheFile,
					getAppVersion(context), 1, 10 * 1024 * 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 *  将 imageUrl对应图片保存到SD卡中
	 * @param imageUrl
	 */
	public void urlSaveDisk(String imageUrl) {
		String key = CacheUtil.hashKeyForDisk(imageUrl);
		try {
			DiskLruCache.Editor editor = mDiskLruCache.edit(key);
			OutputStream outputStream = editor.newOutputStream(0);
			if (downloadUrlToStream(imageUrl, outputStream)) {
				editor.commit();
				Log.i("test", "+++commit成功");
			} else {
				editor.abort();
			}
			mDiskLruCache.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 从SD卡中  imageUrl 对应的图片
	 * @param imageUrl
	 * @return
	 */
	public Bitmap getFromDisk(String imageUrl) {
		Bitmap bitmap = null;
		try {
			String key = CacheUtil.hashKeyForDisk(imageUrl);
			DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
			if (snapshot != null) {
				InputStream is = snapshot.getInputStream(0);
				bitmap = BitmapFactory.decodeStream(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	// 获取缓存目录 文件
	@SuppressLint("NewApi")
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	public static int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 将url 所对应图片通过outputStream 写入
	 * 
	 * @param urlString
	 * @param outputStream
	 * @return
	 */
	public static boolean downloadUrlToStream(String urlString,
			OutputStream outputStream) {
		HttpURLConnection urlConnection = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			final URL url = new URL(urlString);
			Log.i("test","+++url"+url);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			out = new BufferedOutputStream(outputStream);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static String hashKeyForDisk(String key) {
		String cacheKey = null;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			Log.i("test", "++++key"+key);
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Log.i("test", "+++cacheKey"+cacheKey);
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

}
