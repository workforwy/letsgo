package com.wy.letsgo.view.Fragment;

import com.wy.letsgo.R;
import com.wy.letsgo.util.ExceptionUtil;
import com.wy.letsgo.view.ClubActivity;
import com.wy.letsgo.view.MallActivity;
import com.wy.letsgo.view.MyFriendActivity;
import com.wy.letsgo.view.NearbyUserActivity;
import com.wy.letsgo.view.TopicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class DiscoverFragment extends Fragment {
    View view;
    LinearLayout llSportGroup, llNearby, llClub, llMall, llFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = View.inflate(getActivity(), R.layout.fragment_discover, null);
            setupView();
            addListener();
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
        return view;
    }

    private void addListener() {
        llClub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ClubActivity.class));
            }
        });
        llNearby.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), NearbyUserActivity.class));
            }
        });
        llMall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MallActivity.class));
            }
        });
        llFriend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyFriendActivity.class));
            }
        });
        llSportGroup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), TopicActivity.class));
            }
        });
    }

    private void setupView() {

        llNearby = (LinearLayout) view.findViewById(R.id.ll_discover_nearby);
        llClub = (LinearLayout) view.findViewById(R.id.ll_discover_club);
        llMall = (LinearLayout) view.findViewById(R.id.ll_discover_mall);
        llFriend = (LinearLayout) view.findViewById(R.id.ll_discover_friend);
        llSportGroup = (LinearLayout) view.findViewById(R.id.ll_discover_sportGroup);
    }

}
