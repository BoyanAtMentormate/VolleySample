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
import java.io.File;

public class VolleyApp extends Application {

    private static Gson sGson;
    private static RequestQueue sRequestQueue;
    private static ImageLoader sImageLoader;

    private static final int DEFAULT_MAX_CACHE_LIMIT = 5 * 1024 * 1024;

    private ImageLoader.ImageCache mImageCache;
    private ImageLoader.ImageCache mDiskCache;

    private boolean mUseDiskCache = false;

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

        File cacheDir = getCacheDir();
        if(!cacheDir.exists() && !cacheDir.canWrite() && !cacheDir.canRead()) {
            // oops, can't use disk cache fallback to memory
            mUseDiskCache = false;
        }

        mDiskCache = new ImageLoader.ImageCache() {
            private final DiskBasedCache mCache = new DiskBasedCache(getCacheDir(), DEFAULT_MAX_CACHE_LIMIT);
            private final Bitmap.CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
            private final int COMPRESS_QUALITY = 70;

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                Cache.Entry entry = new Cache.Entry();
                entry.data = getImageBytes(bitmap);
                entry.softTtl = 3 * 60 * 1000; // 3min
                entry.ttl = 5 * 60 * 1000; // 5 min
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
                bitmap.compress(COMPRESS_FORMAT, COMPRESS_QUALITY, stream);
                return stream.toByteArray();
            }

            private Bitmap getBitmapFromBytes(byte[] imageBytes) {
                return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
        };

        // can't be simpler than this, really I tried
        sImageLoader = new ImageLoader(sRequestQueue, mUseDiskCache ? mDiskCache : mImageCache);
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
