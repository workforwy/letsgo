package com.wy.letsgo.biz.implAsmack;

import java.util.HashMap;

import org.jivesoftware.smack.AccountManager;

import com.wy.xutils.HttpUtils;
import com.wy.xutils.exception.HttpException;
import com.wy.xutils.http.RequestParams;
import com.wy.xutils.http.ResponseInfo;
import com.wy.xutils.http.callback.RequestCallBack;
import com.wy.xutils.http.client.HttpRequest.HttpMethod;
import com.wy.letsgo.TApplication;
import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.util.Const;
import com.wy.letsgo.view.RegisterActivity;

public class RegisterBiz  {
	int status = Const.STATUS_OK;

	


	public void register(final RegisterActivity registerActivity,UserEntity userEntity ) {
		try {
			int threadId = (int) Thread.currentThread().getId();
			
			// ��ע����Ϣ����openfire
			AccountManager accountManager = TApplication.xmppConnection
					.getAccountManager();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", userEntity.getName());
			accountManager.createAccount(userEntity.getUsername(),
					userEntity.getPassword(), map);

			// ��ע����Ϣ����tomcat
			// 1,�ύ��˭
//			String url = "http://" + TApplication.host
//					+ ":8080/allRunServer/register.jsp";
//			// 2,��ʲô����
//			// httpClient,asyncHttpClient,java.net.HttpUrlConnection
//			HttpUtils httpUtils = new HttpUtils(60000);
//			RequestParams requestEntity = new RequestParams();
//			requestEntity
//					.addBodyParameter("username", userEntity.getUsername());
//			String pwd = userEntity.getPassword();
//			pwd = MD5Util.getStringMD5(pwd);
//			requestEntity.addBodyParameter("md5password", pwd);
//			requestEntity.addBodyParameter("nickname", userEntity.getName());
//
//			// ���ļ�
//			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
//					imageData);
//			String fileName = UUID.randomUUID().toString() + ".png";
//			requestEntity.addBodyParameter("file", byteArrayInputStream,
//					byteArrayInputStream.available(), fileName, "image/png");
//
//			httpUtils.send(HttpMethod.POST, url, requestEntity,
//					new RequestCallBack<String>() {
//
//						@Override
//						public void onSuccess(ResponseInfo<String> responseInfo) {
//							int threadId = (int) Thread.currentThread().getId();
//
//							String jsonString = responseInfo.result;
//							Log.i("ע��", jsonString);
//							try {
//								JSONObject jsonObject = new JSONObject(jsonString);
//								String strStatus = jsonObject
//										.getString("status");
//								if ("0".equals(strStatus)) {
//									status = Integer.parseInt(strStatus);
//								}
//								registerActivity.handleResult(Const.STATUS_OK);
//
//							} catch (Exception e) {
//								e.printStackTrace();
//							} finally {
//								
//							}
//
//						}
//
//						@Override
//						public void onFailure(HttpException error, String msg) {
//							// ���㲥
//							Log.i("","");
//							registerActivity.handleResult(Const.STATUS_REGISTER_FAILURE);
//
//						}
//					});

			// 3,�������������ݴ���
		} catch (Exception e) {
			registerActivity.handleResult(Const.STATUS_OK);

		} finally {
			registerActivity.handleResult(Const.STATUS_OK);


		}

	}

}
