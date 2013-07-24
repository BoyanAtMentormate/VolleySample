package com.example.volleysimplesample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.volleysimplesample.cmn.Result;
import com.example.volleysimplesample.cmn.SearchResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {
	private Context mContext;
	private List<Result> mOriginalList;
    private boolean mUseNetworkImageView = true;
    private static final int MAX_IMAGES_PER_LOAD = 3;
    private int start = 0;
    private static final String base_url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android";

    public GalleryAdapter(Context context, List<Result> list) {
		mOriginalList = new ArrayList<Result>();
		mOriginalList.addAll(list);
		
		mContext = context;
	}

    public void loadMoreData() {
        start += 4;
        String url = base_url + "&start=" + start;
        VolleyApp.getRequestQueue().add(new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SearchResponse responseObj = VolleyApp.getGsonInstance().fromJson(response.toString(), SearchResponse.class);
                if(responseObj != null && responseObj.responseData != null) {
                   // append results to original list
                   mOriginalList.addAll(responseObj.responseData.results);
                   notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // wohoo there something went wrong
                error.printStackTrace();
            }
        }));
    }
	
	public Context getContext() {
		return mContext;
	}

    public void clear() {
        mOriginalList.clear();
        notifyDataSetChanged();
    }

	@Override
	public int getCount() {
		if (mOriginalList == null) {
			return 0;
		} else {
			return mOriginalList.size();
		}
	}

	@Override
	public Result getItem(int position) {
		if (mOriginalList == null) {
			return null;
		} else {
			return mOriginalList.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		if (mOriginalList == null) {
			return -1;
		} else {
			return position;
		}
	}

    @Override
    public boolean hasStableIds() {
        return true;
    }

    static class ViewHolder{
		NetworkImageView thumbnail;
		TextView description;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder viewHolder;

		if(v == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item_photo_gallery, null, false);

			viewHolder = new ViewHolder();
            if (v != null) {
                viewHolder.thumbnail = (NetworkImageView) v.findViewById(R.id.thumbnail);
                viewHolder.description = (TextView) v.findViewById(R.id.description);
                v.setTag(viewHolder);
            }
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

        Result result = mOriginalList.get(position);

		if(result != null && mUseNetworkImageView){
			viewHolder.thumbnail.setImageUrl(result.tbUrl, VolleyApp.getImageLoader());
			viewHolder.description.setText(result.titleNoFormatting);
		} else {
            ((VolleyActivity) getContext()).requestImage(viewHolder.thumbnail, result.tbUrl, mOriginalList.size());
        }

        if(closeToEnd(position)) {
            loadMoreData();
        }

		return v;
	}

    private boolean shouldLoadData(long position) {
        if(mOriginalList.size() - 1 >= position)
            return true;
        else
            return false;
    }

    private boolean closeToEnd(long position) {
        int max = start < 1 ? MAX_IMAGES_PER_LOAD : start;
        if(position == max && shouldLoadData(position)) {
            return true;
        } else {
            return false;
        }
    }
}
