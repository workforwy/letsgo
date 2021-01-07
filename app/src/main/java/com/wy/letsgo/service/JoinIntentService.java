package com.wy.letsgo.service;

import org.jivesoftware.smackx.muc.MultiUserChat;

import com.wy.letsgo.TApplication;
import com.wy.letsgo.config.Const;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;

public class JoinIntentService extends IntentService {


    public JoinIntentService() {
        super("JoinIntentService");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int status = 0;

        try {
            String roomName = intent.getStringExtra("roomName");
            String name = intent.getStringExtra("name");

            String room = roomName + "@conference."
                    + TApplication.serviceName;
            MultiUserChat multiUserChat = new MultiUserChat(
                    TApplication.xmppConnection, room);
            multiUserChat.join(name);
            TApplication.currentUser.setName(name);
            // �ڱ�ĵط����õ�������ȫ�ֱ���
            TApplication.multiUserChat = multiUserChat;
            status = Const.STATUS_OK;

        } catch (Exception e) {
            e.printStackTrace();
            status = Const.STATUS_FAILURE;

        } finally {
            try {
                PendingIntent pendingIntent = intent.getParcelableExtra("pendingIntent");
                Intent intentToActivity = new Intent();
                intentToActivity.putExtra(Const.KEY_DATA, status);
                pendingIntent.send(this, 200, intentToActivity);
            } catch (CanceledException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

}
