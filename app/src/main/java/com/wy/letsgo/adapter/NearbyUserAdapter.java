package com.wy.letsgo.adapter;

import java.util.ArrayList;

import com.wy.xutils.BitmapUtils;
import com.wy.letsgo.R;
import com.wy.letsgo.TApplication;
import com.wy.letsgo.service.AddFriendService;
import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.config.Const;
import com.wy.letsgo.util.ExceptionUtil;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NearbyUserAdapter extends BaseAdapter {
	Context context;
	ArrayList<UserEntity> list;
	BitmapUtils bitmapUtils;

	public NearbyUserAdapter(Context context, ArrayList<UserEntity> list,
			BitmapUtils bitmapUtils) {
		super();
		this.context = context;
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<UserEntity>();
		}
		this.bitmapUtils = bitmapUtils;
	}

	@Override
	public int getCount() {

		return this.list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.nearby_user_item,
						null);
				viewHolder = new ViewHolder();
				viewHolder.ivUserIcon = (ImageView) convertView
						.findViewById(R.id.iv_nearby_use_item_userIcon);
				viewHolder.tvName = (TextView) convertView
						.findViewById(R.id.tv_nearby_user_item_name);
				viewHolder.btnAddFriend = (Button) convertView
						.findViewById(R.id.btn_nearby_user_item_addFriend);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final UserEntity userEntity = list.get(position);
			// 实际工作中，服务器返回的url要包含域名
			String imageUrl = "http://" + TApplication.host + ":8080"
					+ userEntity.getIconUrl();

			viewHolder.tvName.setText(userEntity.getName());
			try {
				// bitmapUtils.display(viewHolder.ivUserIcon, imageUrl);
				bitmapUtils.display(viewHolder.ivUserIcon, imageUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean isAdded = userEntity.isAdded();
			if (isAdded) {
				viewHolder.btnAddFriend.setText("已添加");
			} else {
				viewHolder.btnAddFriend.setText("添加为好友");
			}
			// 添加好友
			viewHolder.btnAddFriend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Button btn = (Button) v;
						btn.setText("已添加");
						userEntity.setAdded(true);
						// 调addFriendBiz
						userEntity.setGroup("allRun");
						userEntity.setUser(userEntity.getUsername() + "@"
								+ TApplication.serviceName);

						Intent intentStartService = new Intent(context,
								AddFriendService.class);
						intentStartService.putExtra(Const.KEY_DATA, userEntity);

						Intent intentToService = new Intent();
						Activity activity = (Activity) context;
						PendingIntent pendingIntent = activity
								.createPendingResult(100, intentToService, 0);
						intentStartService.putExtra("pendingIntent",
								pendingIntent);

						context.startService(intentStartService);
					} catch (Exception e) {
						ExceptionUtil.handleException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView ivUserIcon;
		TextView tvName;
		Button btnAddFriend;
	}

	public void update(ArrayList<UserEntity> list2) {
		int threadId = (int) Thread.currentThread().getId();
		this.list = list2;
		this.notifyDataSetChanged();
	}

}
