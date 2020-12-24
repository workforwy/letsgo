package com.wy.letsgo.adapter;

import java.io.IOException;

import com.wy.letsgo.R;
import com.wy.showgif.GifDrawable;
import com.wy.showgif.GifImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FaceAdapter extends BaseAdapter {
	Context context;
	/**
	 * ±íÇéµÄid
	 */
	String[] faceFileName;

	public FaceAdapter(Context context, String[] faceFileName) {
		super();
		this.context = context;
		this.faceFileName=faceFileName;
	}

	@Override
	public int getCount() {

		return faceFileName.length;
	}

	@Override
	public Object getItem(int position) {

		return faceFileName[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		try {
			if (convertView == null) {
				convertView=View.inflate(context, R.layout.gridview_item, null);
				viewHolder=new ViewHolder();
				viewHolder.imageView = (GifImageView) convertView.findViewById(R.id.imageView1);;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			GifDrawable gifDrawable=new GifDrawable(context.getAssets(), faceFileName[position]);
			viewHolder.imageView.setBackgroundDrawable(gifDrawable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//viewHolder.imageView.setBackground(gifDrawable);
		return convertView;
	}

	class ViewHolder {
		GifImageView imageView;
	}

}
