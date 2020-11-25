package com.wy.letsgo.biz.implAsmack;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

import com.wy.letsgo.TApplication;
import com.wy.letsgo.entity.PrivateChatEntity;
import com.wy.letsgo.util.Const;

import android.content.Intent;

public class PrivateChatBiz {
	/**
	 * 
	 * @param friendUser
	 * @param body
	 */
	public static void sendMessage(final String friendUser,final String body) {
		new Thread() {
			public void run() {
				int status=0;
				try {
					Message message = new Message();
					message.setTo(friendUser);
					String from = TApplication.currentUser.getUser();
					message.setFrom(from);

					message.setBody(body);
					message.setType(Type.chat);
					// ����Ϣ���浽ʵ����
					PrivateChatEntity.addMessage(friendUser, message);
					// ���㲥
					Intent intent = new Intent(
							Const.ACTION_SHOW_GROUP_CHAT_MESSAGE);

					TApplication.instance.sendBroadcast(intent);
					
					//������������
					TApplication.xmppConnection.sendPacket(message);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			
		}.start();
	}

	

}
