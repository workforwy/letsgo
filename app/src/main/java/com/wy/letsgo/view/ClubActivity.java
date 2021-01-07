package com.wy.letsgo.view;

import com.wy.letsgo.base.BaseActivity;
import com.wy.letsgo.service.JoinIntentService;
import com.wy.letsgo.config.Const;
import com.wy.letsgo.util.Tools;
import com.wy.letsgo.R;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ClubActivity extends BaseActivity {
	EditText etRoomName, etName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.input_club_name);
			setupView();
			addListener();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void addListener() {
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==200 && requestCode==100)
		{
			int status=data.getIntExtra(Const.KEY_DATA, -1);
			if (status==Const.STATUS_OK)
			{
				startActivity(new Intent(this, ChatActivity.class));
			}else
			{
				Tools.showInfo(this, "Ê§°Ü");
			}
		}
	}
	public void submit(View view)
	{

		try {
			String roomName = etRoomName.getText().toString();
			String name = etName.getText().toString();
			
			Intent intentStartService=new Intent(this, JoinIntentService.class);
			intentStartService.putExtra("roomName", roomName);
			intentStartService.putExtra("name", name);

			Intent intentToService=new Intent();
			PendingIntent pendingIntent=
					createPendingResult(100, intentToService, 0);
			
			intentStartService.putExtra("pendingIntent", pendingIntent);
			
			startService(intentStartService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	private void setupView() {

		etRoomName = (EditText) findViewById(R.id.et_input_room_roomName);
		etName = (EditText) findViewById(R.id.et_input_room_name);
	}

}
