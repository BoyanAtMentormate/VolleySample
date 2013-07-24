package com.example.volleysample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.volleysample.common.PhotoGallery;
import com.example.volleysample.images.ImageCacheManager;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {
	private Context mContext;
	private List<PhotoGallery> mOriginalList;

	public GalleryAdapter(Context context, List<PhotoGallery> list) {
		mOriginalList = new ArrayList<PhotoGallery>();
		mOriginalList.addAll(list);
		
		mContext = context;
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
	public PhotoGallery getItem(int position) {
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
			return mOriginalList.get(position).getId();
		}
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

		PhotoGallery gallery = mOriginalList.get(position);
		if(gallery != null){
			viewHolder.thumbnail.setImageUrl(gallery.getThumbnails().getThumb(), ImageCacheManager.getInstance().getImageLoader());
			viewHolder.description.setText(gallery.getDescription());
		}

		return v;
	}
}
