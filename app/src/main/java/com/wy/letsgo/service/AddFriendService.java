package com.wy.letsgo.service;

import org.jivesoftware.smack.Roster;

import com.wy.letsgo.TApplication;
import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.config.Const;
import com.wy.letsgo.util.ExceptionUtil;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

public class AddFriendService extends IntentService {

    public AddFriendService() {
        super("AddFrindBiz");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int status = -1;
        try {
            UserEntity userEntity = (UserEntity) intent.getSerializableExtra(Const.KEY_DATA);

            String[] groups = new String[]{userEntity.getGroup()};

            //花名册,放的是好友分组和好友信息
            Roster roster = TApplication.xmppConnection.getRoster();
            roster.createEntry(userEntity.getUser(), userEntity.getName(), groups);
            status = Const.STATUS_OK;
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        } finally {
            try {
                //面试题：在service发结果发给activity
                //1,用发广播
                //2,用pendingintent
                PendingIntent pendingIntent = intent.getParcelableExtra("pendingIntent");
                Intent intentToActivity = new Intent();
                intentToActivity.putExtra(Const.KEY_DATA, status);
                pendingIntent.send(this, 200, intentToActivity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


}
