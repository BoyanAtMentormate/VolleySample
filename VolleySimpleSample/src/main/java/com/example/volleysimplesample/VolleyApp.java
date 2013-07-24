package com.example.volleysimplesample;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

public class VolleyApp extends Application {

    private static Gson sGson;
    private static RequestQueue sRequestQueue;
    private static ImageLoader sImageLoader;

    private ImageLoader.ImageCache mImageCache;
    private ImageLoader.ImageCache mDiskCache;

    @Override
    public void onCreate() {
        super.onCreate();

        // init everything
        sRequestQueue = Volley.newRequestQueue(getApplicationContext());
        sGson = new Gson();

        // super simple image cache
        mImageCache = new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            @Override
            public Bitmap getBitmap(String url) {
                Log.d("MemoryCache", "Memory cache hit for image : " + url);
                return mCache.get(url);
            }
        };

        mDiskCache = new ImageLoader.ImageCache() {
            private final DiskBasedCache mCache = new DiskBasedCache(getCacheDir(), Constants.DEFAULT_MAX_CACHE_LIMIT);

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                Cache.Entry entry = new Cache.Entry();
                entry.data = getImageBytes(bitmap);
                entry.softTtl = Constants.DISK_CACHE_SOFT_TTL; // 3min
                entry.ttl = Constants.DISK_CACHE_TTL; // 5 min
                entry.etag = url;

                mCache.put(url, entry);
            }

            @Override
            public Bitmap getBitmap(String url) {
                Cache.Entry entry = mCache.get(url);
                if(entry == null) {
                    return null;
                } else {
                    Log.d("DiskCache", "Disk cache hit for image : " + url);
                }

                return getBitmapFromBytes(entry.data);
            }

            private byte[] getImageBytes(Bitmap bitmap) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Constants.COMPRESS_FORMAT, Constants.COMPRESS_QUALITY, stream);
                return stream.toByteArray();
            }

            private Bitmap getBitmapFromBytes(byte[] imageBytes) {
                return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
        };

        // can't be simpler than this, really I tried
        sImageLoader = new ImageLoader(sRequestQueue, Constants.USE_DISK_CACHE ? mDiskCache : mImageCache);
    }

    public static ImageLoader getImageLoader() {
        if(sImageLoader != null) {
            return sImageLoader;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    public static RequestQueue getRequestQueue() {
        if(sRequestQueue != null) {
            return sRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    public static Gson getGsonInstance() {
        if(sGson != null) {
            return sGson;
        }else {
            throw new IllegalStateException("Not initialized");
        }
    }
}
