package com.wy.letsgo.view.Fragment;

import java.io.File;
import com.wy.letsgo.R;
import com.wy.letsgo.TApplication;
import com.wy.letsgo.biz.implAsmack.UpdateBiz;
import com.wy.letsgo.entity.VersionEntity;
import com.wy.letsgo.config.Const;
import com.wy.letsgo.util.ExceptionUtil;
import com.wy.letsgo.util.LogUtil;
import com.wy.letsgo.util.Tools;
import com.wy.progressbutton.CircularProgressButton;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MeFragment extends Fragment {
	public static final int SHOW_VERSION_INFO = 1;
	public static final int INSTALL = 2;
	public static final int ERROR = 3;
	Button  btnExit,btnRecommend;
	CircularProgressButton btnUpdate;
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				Bundle bundle = msg.getData();
				switch (msg.what) {
				case INSTALL:
					String apkPath = bundle.getString(Const.KEY_DATA);
					File file = new File(apkPath);
					Uri uri = Uri.fromFile(file);

					Intent intent = new Intent(Intent.ACTION_VIEW);
					// type��mime
					String type = "application/vnd.android.package-archive";
					intent.setDataAndType(uri, type);
					getActivity().startActivity(intent);
					break;

				case SHOW_VERSION_INFO:
					btnUpdate.setProgress(100);
					VersionEntity versionEntity = (VersionEntity) bundle
							.getSerializable(Const.KEY_DATA);
					LogUtil.i("����", versionEntity.getApkUrl());

					showDialog(versionEntity);
					break;
				case ERROR:
					btnUpdate.setProgress(-1);
					btnUpdate.setText("����ʧ��");

					//Tools.showInfo(getActivity(), "����ʧ��");
					break;
				}
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			}
		}

		private void showDialog(final VersionEntity versionEntity)
				throws Exception {

			// �жϰ汾���ǲ������µ�
			String currentVersion = Tools.getCurrentVersion(getActivity());
			if (Double.parseDouble(currentVersion) < Double
					.parseDouble(versionEntity.getVersion())) {
				// showDialog
				AlertDialog.Builder dialog = new Builder(getActivity());
				dialog.setMessage(versionEntity.getVersion() + "\n"
						+ versionEntity.getChangeLog());
				dialog.setPositiveButton("����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									UpdateBiz.downloadApk(handler,
											versionEntity.getApkUrl());
									dialog.cancel();
								} catch (Exception e) {
									// TODO: handle exception
								}

							}
						});

				dialog.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();

							}
						});
				dialog.show();
			} else {
				Tools.showInfo(getActivity(), "�ף���İ汾�Ѿ������µ�");
			}
		};
	};
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = View.inflate(getActivity(), R.layout.fragment_me, null);
			setupView();
			addListener();
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		return view;
	}

	private void addListener() {
		btnRecommend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {

					// ��ʾ����ǽ��
					//��ʾ��һ���µ�activity�У�
					//activity��jar
					// youmi sdk ��һ��activity,.xml,��������������ʾ
//					AdManager.getInstance(getActivity()).init(
//							"4648c5afc044b64e", "d73d40f04826354b", false);
//					// ���ʹ�û��ֹ�棬����ص��û��ֹ��ĳ�ʼ���ӿ�:
//					OffersManager.getInstance(getActivity())
//							.onAppLaunch();
//					OffersManager.getInstance(getActivity())
//							.showOffersWall();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					TApplication.instance.exit();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					btnUpdate.setProgress(50);
					UpdateBiz updateBiz = new UpdateBiz();
					updateBiz.getNewVersionInfo(handler, "zhangjiujun");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private void setupView() {

		btnUpdate = (CircularProgressButton) view.findViewById(R.id.btn_me_update);
		btnUpdate.setProgress(0);
		btnUpdate.setIndeterminateProgressMode(true);
		btnExit = (Button) view.findViewById(R.id.btn_me_exit);
		btnRecommend = (Button) view.findViewById(R.id.btn_me_recommend);
	}

}
