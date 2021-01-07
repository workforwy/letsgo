package com.wy.letsgo.biz.implAsmack;

import java.util.ArrayList;

import android.content.Intent;

import com.wy.xutils.HttpUtils;
import com.wy.xutils.exception.HttpException;
import com.wy.xutils.http.RequestParams;
import com.wy.xutils.http.ResponseInfo;
import com.wy.xutils.http.callback.RequestCallBack;
import com.wy.xutils.http.client.HttpRequest.HttpMethod;
import com.wy.letsgo.TApplication;
import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.parser.NearbyParser;
import com.wy.letsgo.config.Const;
import com.wy.letsgo.util.LogUtil;
import com.wy.letsgo.util.MD5Util;

public class NearbyUserBiz {
	public static void query(final UserEntity userEntity,String pageIndex,String rowNum) {
		String url = "http://" + TApplication.host
				+ ":8080/allRunServer/queryNearbyUser.jsp";
		HttpUtils httpUtils = new HttpUtils(60000);
		RequestParams requestParams = new RequestParams();
		requestParams.addQueryStringParameter("username",
				userEntity.getUsername());
		String pwd = MD5Util.getStringMD5(userEntity.getPassword());
		requestParams.addQueryStringParameter("md5password", pwd);
		requestParams.addQueryStringParameter("pageIndex", pageIndex);
		requestParams.addQueryStringParameter("rowNum", rowNum);

		requestParams.addQueryStringParameter("latitude", "0.000000");
		requestParams.addQueryStringParameter("longitude", "0.000000");

		httpUtils.send(HttpMethod.POST, url, requestParams,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ArrayList<UserEntity> list = null;
						try {
							// 测试服务器是否正确返回数据，必须打印服务器返回的结果
							LogUtil.i("NearbyUserBiz", responseInfo.result);
							list = NearbyParser.parser(responseInfo.result);
						} catch (Exception e) {

						} finally {
							Intent intent = new Intent(
									Const.ACTION_GET_NEARBY_USER);
							intent.putExtra(Const.KEY_DATA, list);
							TApplication.instance.sendBroadcast(intent);
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {

						Intent intent = new Intent(Const.ACTION_GET_NEARBY_USER);
						TApplication.instance.sendBroadcast(intent);
					}
				});
	}

}
