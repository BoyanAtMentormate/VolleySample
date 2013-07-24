package com.example.volleysample;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.example.volleysample.common.RequestManager;
import com.example.volleysample.images.ImageCacheManager;
import com.google.gson.Gson;

public class VolleyApp extends Application {

	private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided

	private static Gson sGson = new Gson();
	
	@Override
	public void onCreate() {
		super.onCreate();

		init();
	}

	/**
	 * Intialize the request manager and the image cache 
	 */
	private void init() {
		RequestManager.init(this);
		createImageCache();
	}
	

	
	public static Gson getGsonInstance() {
		if(sGson == null) {
			sGson = new Gson();
		}
		
		return sGson;
	}
	
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.  
	 */
	private void createImageCache(){
		ImageCacheManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, ImageCacheManager.CacheType.MEMORY);
	}
}
