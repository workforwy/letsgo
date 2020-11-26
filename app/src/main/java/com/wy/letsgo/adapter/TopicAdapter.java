package com.wy.letsgo.adapter;

import java.util.ArrayList;

import com.wy.xutils.BitmapUtils;
import com.wy.letsgo.TApplication;
import com.wy.letsgo.entity.TopicEntity;
import com.wy.letsgo.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicAdapter extends BaseAdapter {
    Context context;
    ArrayList<TopicEntity> list;
    BitmapUtils bitmapUtils;

    public TopicAdapter(Context context, ArrayList<TopicEntity> list,
                        BitmapUtils bitmapUtils) {
        super();
        this.context = context;
        if (list == null) {
            this.list = new ArrayList<TopicEntity>();
        } else {
            this.list = list;
        }
        this.bitmapUtils = bitmapUtils;
    }

    public void updateData(ArrayList<TopicEntity> list) {
        if (list != null) {
            this.list = list;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        int size = 0;
        try {
            size = list.size();
        } catch (Exception e) {
        }
        return size;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.topic_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvUsername = (TextView) convertView
                    .findViewById(R.id.tv_topic_item_username);
            viewHolder.tvBody = (TextView) convertView
                    .findViewById(R.id.tv_topic_item_body);
            viewHolder.tvAddress = (TextView) convertView
                    .findViewById(R.id.tv_topic_item_address);

            viewHolder.ivImage = (ImageView) convertView
                    .findViewById(R.id.iv_topic_item_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 取数据
        TopicEntity topicEntity = list.get(position);

        viewHolder.tvUsername.setText(topicEntity.getUsername());
        viewHolder.tvBody.setText(topicEntity.getBody());
        viewHolder.tvAddress.setText(topicEntity.getLocationAddress());

        // 显示图片
        String imageUrl = topicEntity.getImageAddress();

        imageUrl = "http://" + TApplication.host + ":8080" + imageUrl;
        bitmapUtils.display(viewHolder.ivImage, imageUrl);

        return convertView;
    }

    class ViewHolder {
        TextView tvUsername, tvBody, tvTime, tvAddress;
        ImageView ivImage;
    }

}
