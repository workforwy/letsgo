package com.wy.letsgo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.wy.letsgo.R;
import com.wy.letsgo.TApplication;
import com.wy.letsgo.adapter.NearbyUserAdapter;
import com.wy.letsgo.biz.implAsmack.NearbyUserBiz;
import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.util.Const;
import com.wy.letsgo.util.ExceptionUtil;
import com.wy.letsgo.util.LogUtil;
import com.wy.letsgo.util.Tools;
import com.wy.pulltorefresh2.pulltorefreshswipemenulistviewsample.PullToRefreshSwipeMenuListView;
import com.wy.pulltorefresh2.swipemenulistview.SwipeMenu;
import com.wy.pulltorefresh2.swipemenulistview.SwipeMenuCreator;
import com.wy.pulltorefresh2.swipemenulistview.SwipeMenuItem;
import com.wy.xutils.BitmapUtils;
import com.wy.xutils.bitmap.PauseOnScrollListener;

import java.util.ArrayList;

import com.wy.pulltorefresh2.pulltorefreshswipemenulistviewsample.PullToRefreshSwipeMenuListView.IXListViewListener;
import com.wy.pulltorefresh2.pulltorefreshswipemenulistviewsample.PullToRefreshSwipeMenuListView.OnMenuItemClickListener;
import com.wy.pulltorefresh2.pulltorefreshswipemenulistviewsample.PullToRefreshSwipeMenuListView.OnSwipeListener;

public class NearbyUserActivity extends BaseActivity {
	PullToRefreshSwipeMenuListView mListView;
	NearbyUserAdapter nearbyUserAdatper;
	BitmapUtils bitmapUtils = null;
	ShowNearbyUserReceiver showNearbyUserReceiver;
	ArrayList<UserEntity> list;
	int pageIndex = 1;
	int rowNum = 2;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == 200) {
			// 说明data来之addFriendBiz不是来之系统图库的图片
			int status = data.getIntExtra(Const.KEY_DATA, -1);
			if (status == 0) {
				Tools.showInfo(this, "添加好友信息发送成功，等待好友处理");
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.nearby_user);
			setupView();
			addListener();

			showNearbyUserReceiver = new ShowNearbyUserReceiver();
			this.registerReceiver(showNearbyUserReceiver, new IntentFilter(
					Const.ACTION_GET_NEARBY_USER));
			// NearbyUserBiz nearbyUserBiz = new NearbyUserBiz();
			NearbyUserBiz.query(TApplication.currentUser,
					String.valueOf(pageIndex), String.valueOf(rowNum));
			Tools.showInfo(this, "正在查询");
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(showNearbyUserReceiver);
	}

	private void addListener() {
	}

	private void setupView() {
		mListView = (PullToRefreshSwipeMenuListView) findViewById(R.id.listView);
		bitmapUtils = new BitmapUtils(this);
		nearbyUserAdatper = new NearbyUserAdapter(this, null, bitmapUtils);
		mListView.setAdapter(nearbyUserAdatper);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {}

			@Override
			public void onLoadMore() {
				new Thread() {
					public void run() {
						try {
							this.sleep(2000);

							pageIndex++;
							NearbyUserBiz nearbyUserBiz = new NearbyUserBiz();
							nearbyUserBiz.query(TApplication.currentUser,
									String.valueOf(pageIndex),
									String.valueOf(rowNum));
						} catch (Exception e) {
							// TODO: handle exception
						}
					};
				}.start();

			}
		});

		SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				int px = Tools.dp2px(NearbyUserActivity.this, 90);
				openItem.setWidth(px);
				// set item title
				openItem.setTitle("delete");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);
			}
		};

		mListView.setMenuCreator(swipeMenuCreator);
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				if (index == 0) {
					list.remove(position);
					nearbyUserAdatper.update(list);
				}

			}
		});

		mListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
		mListView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
				false, true));
	}

	class ShowNearbyUserReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				Tools.closeProgressDialog();
				if (pageIndex == 1) {
					list = (ArrayList<UserEntity>) intent
							.getSerializableExtra(Const.KEY_DATA);
					nearbyUserAdatper.update(list);
					LogUtil.i("分页", pageIndex+",size="+list.size());
				} else if (pageIndex > 1) {
					ArrayList newList = (ArrayList<UserEntity>) intent
							.getSerializableExtra(Const.KEY_DATA);
					list.addAll(newList);
					nearbyUserAdatper.update(list);
					mListView.stopLoadMore();
					LogUtil.i("分页", "刷新"+pageIndex+",size="+list.size());

				}
			} catch (Exception e) {
			}

		}
	}
}
