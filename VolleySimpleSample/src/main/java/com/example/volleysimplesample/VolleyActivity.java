package com.example.volleysimplesample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.volleysimplesample.cmn.SearchResponse;

import org.json.JSONObject;

public class VolleyActivity extends Activity {

    private static final String LOG = "LOGGY";
    public static final Object IMAGE_TAG = new Object();

    private ListView mListView;
    protected GalleryAdapter mAdapter;
    private MenuItem mRefreshMenu;
    private RequestQueue mQueue;
    private int mTotalLoadedImgs;

    private void startLoadingAnim() {
        if (mRefreshMenu != null) {
            Log.i(LOG, "===== start loading");
            ImageView iv = (ImageView) mRefreshMenu.getActionView();
            Animation rotation = AnimationUtils.loadAnimation(this,
                    R.anim.refresh_rotate);
            rotation.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotation);
        }
    }

    private void stopLoadingAnim() {
        if (mRefreshMenu != null) {
            Log.i(LOG, "===== stop loading");
            ImageView iv = (ImageView) mRefreshMenu.getActionView();
            iv.setImageResource(R.drawable.ic_action_refresh);
            iv.clearAnimation();
        }
    }

    private void toast(int id) {
        String text = getResources().getString(id);
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        mQueue = VolleyApp.getRequestQueue();
        mListView = (ListView) findViewById(android.R.id.list);

        startLoadingAnim();
        refreshData();
    }

    private void refreshData() {
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SearchResponse responseObj = VolleyApp.getGsonInstance().fromJson(response.toString(), SearchResponse.class);
                if(responseObj != null && responseObj.responseData != null) {
                    mAdapter = new GalleryAdapter(VolleyActivity.this, responseObj.responseData.results);
                    mListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    stopLoadingAnim();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast(R.string.connection_error);
                stopLoadingAnim();
            }
        });
        jsonRequest.setTag(IMAGE_TAG);
        mQueue.add(jsonRequest);
    }

    public void onStop() {
        super.onStop();
        mQueue.cancelAll(IMAGE_TAG);
        stopLoadingAnim();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.volley, menu);
        return true;
    }



    public void requestImage(final ImageView niv, final String imgUrl, final int len) {
        ImageRequest request = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bm) {
                niv.setImageBitmap(bm);
                mTotalLoadedImgs++;
                if (len == mTotalLoadedImgs) {
                    stopLoadingAnim();
                    mTotalLoadedImgs = 0;
                }
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError arg0) {
                arg0.printStackTrace();
                mTotalLoadedImgs++;
                if (len == mTotalLoadedImgs) {
                    stopLoadingAnim();
                    mTotalLoadedImgs = 0;
                }
            }
        });
        request.setTag(IMAGE_TAG);
        VolleyApp.getRequestQueue().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
