package com.wy.letsgo.view;

import java.util.ArrayList;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;

import com.wy.letsgo.R;
import com.wy.letsgo.TApplication;
import com.wy.letsgo.adapter.MyFriendAdapter;
import com.wy.letsgo.base.BaseActivity;
import com.wy.letsgo.util.ExceptionUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class MyFriendActivity extends BaseActivity {
	ExpandableListView expandableListView;
	MyFriendAdapter myFriendAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.my_friend);
		expandableListView = (ExpandableListView) this
				.findViewById(R.id.expandableListView1);
		Roster roster = TApplication.xmppConnection.getRoster();
		ArrayList<RosterGroup> listGroup = new ArrayList<RosterGroup>(
				roster.getGroups());
		myFriendAdapter = new MyFriendAdapter(listGroup, this);
		expandableListView.setAdapter(myFriendAdapter);
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				try {
					RosterEntry rosterEntry=(RosterEntry) myFriendAdapter.getChild(groupPosition, childPosition);
					String friendUser=rosterEntry.getUser();
					
					Intent intent = new Intent(MyFriendActivity.this, ChatActivity.class);
					intent.putExtra("friendUser", friendUser);
					startActivity(intent);
				} catch (Exception e) {
					ExceptionUtil.handleException(e);
				}
				return false;
			}
		});
	}

}
