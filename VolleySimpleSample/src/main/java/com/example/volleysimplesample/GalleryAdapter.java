package com.example.volleysimplesample;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
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
    private int mStartAtResultPage = 0;

    public GalleryAdapter(Context context, List<Result> list) {
		mOriginalList = new ArrayList<Result>();
		mOriginalList.addAll(list);
		
		mContext = context;
	}

    public void loadMoreData() {
        mStartAtResultPage += 4;
        String url = Constants.BASE_URL + "&start=" + mStartAtResultPage;
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

        viewHolder.thumbnail.setDefaultImageResId(android.R.drawable.ic_menu_gallery);
        viewHolder.thumbnail.setErrorImageResId(android.R.drawable.ic_menu_delete);

        if (result != null) {
            if (Constants.USER_NETWORK_IMAGE_VIEWS) {
                viewHolder.thumbnail.setImageUrl(result.tbUrl, VolleyApp.getImageLoader());
            } else {
                requestImage(viewHolder.thumbnail, result.tbUrl);
            }
        }

        viewHolder.description.setText(result.titleNoFormatting);

        if(closeToEnd(position)) {
            loadMoreData();
        }

		return v;
	}

    public void requestImage(final ImageView niv, final String imgUrl) {
        ImageRequest request = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bm) {
                niv.setImageBitmap(bm);
                niv.invalidate();
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }
        );

        request.setTag(Constants.IMAGE_TAG);
        VolleyApp.getRequestQueue().add(request);
    }

    private boolean shouldLoadData(long position) {
        if(mOriginalList.size() - 1 >= position)
            return true;
        else
            return false;
    }

    private boolean closeToEnd(long position) {
        int max = mStartAtResultPage < 1 ? Constants.MAX_IMAGES_PER_LOAD : mStartAtResultPage;
        if(position == max && shouldLoadData(position)) {
            return true;
        } else {
            return false;
        }
    }
}
