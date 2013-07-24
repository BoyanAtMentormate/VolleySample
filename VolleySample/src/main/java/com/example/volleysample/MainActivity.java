package com.example.volleysample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.volleysample.common.PhotoGallery;
import com.example.volleysample.common.RequestManager;
import com.example.volleysample.request.CustomRetryPolicy;
import com.example.volleysample.request.HeaderRequest;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends Activity {
	protected static final String TAG = MainActivity.class.getCanonicalName();
	protected GalleryAdapter mAdapter;
    private HeaderRequest mGetGalleriesRequest;
    private ListView mListView;
    private ProgressBar mLoadingBar;
    private static final String sUrl = "https://api.stage.ngin-staging.com/photo_galleries/590606";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(android.R.id.list);
		mLoadingBar = (ProgressBar) findViewById(R.id.progressBar1);

		refresh(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                mListView.setAdapter(null);
                mLoadingBar.setVisibility(View.VISIBLE);

                // notify user
                Toast.makeText(this, getString(R.string.toast_refresh), Toast.LENGTH_SHORT).show();

                refresh(false);
            }
            break;
            case R.id.action_refresh_cache: {
                mListView.setAdapter(null);
                mLoadingBar.setVisibility(View.VISIBLE);

                // notify user
                Toast.makeText(this, getString(R.string.toast_refresh_cache), Toast.LENGTH_SHORT).show();

                refresh(true);
                //RequestManager.getRequestQueue().start();
            }
            break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh(boolean shouldCache) {
        // Custom Request
        mGetGalleriesRequest = new HeaderRequest(sUrl, new Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // GSON response
                List<PhotoGallery> photoGalleryList = VolleyApp.getGsonInstance().fromJson(
                        response.toString(), PhotoGallery.getJsonArrayType());

                mAdapter = new GalleryAdapter(MainActivity.this, photoGalleryList);
                mListView.setAdapter(mAdapter);

                mLoadingBar.setVisibility(View.GONE);

                // Show the cache
                Cache.Entry cacheEntry = mGetGalleriesRequest.getCacheEntry();
                if(cacheEntry != null) {
                    Log.d(TAG, mGetGalleriesRequest.getCacheKey());
                    Log.d(TAG, cacheEntry.toString());
                }
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                Log.e(TAG, error.getLocalizedMessage());
                mLoadingBar.setVisibility(View.GONE);
            }
        });

        // Custom Policy
        mGetGalleriesRequest.setRetryPolicy(new CustomRetryPolicy());
        // Should we cache?
        mGetGalleriesRequest.setShouldCache(shouldCache);

        // finally add to queue
        RequestManager.getRequestQueue().add(mGetGalleriesRequest);
    }
}
