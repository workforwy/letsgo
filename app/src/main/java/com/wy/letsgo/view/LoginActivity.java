package com.wy.letsgo.view;

import com.wy.letsgo.TApplication;
import com.wy.letsgo.base.BaseActivity;
import com.wy.letsgo.biz.Factory;
import com.wy.letsgo.biz.ILoginBiz;
import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.config.Const;
import com.wy.letsgo.util.NetworkUtil;
import com.wy.letsgo.util.Tools;
import com.wy.letsgo.R;
import com.wy.progressbutton.CircularProgressButton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    EditText etUserName, etPassword;
    Button tvToRegister;
    CircularProgressButton btn;
    LoginReceiver loginReceiver;
    String username, password;
    UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NetworkUtil.checkNetworkState(this);
        setView();
        addListener();
        loginReceiver = new LoginReceiver();
        this.registerReceiver(loginReceiver, new IntentFilter(Const.ACTION_LOGIN));
        for (int i = 0; i < 10000; i++) {
            TApplication.listUserEntity.add(new UserEntity());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(loginReceiver);
    }

    private void addListener() {
        MyListener myListener = new MyListener();
        btn.setOnClickListener(myListener);
        tvToRegister.setOnClickListener(myListener);
    }

    private void setView() {
        etUserName = (EditText) findViewById(R.id.et_login_username);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btn = (CircularProgressButton) findViewById(R.id.btn_login_submit);
        btn.setIndeterminateProgressMode(true);
        btn.setProgress(0);
        tvToRegister = (Button) findViewById(R.id.btn_login_register);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100 && resultCode == 0) {
                String username = data.getStringExtra("username");
                String pwd = data.getStringExtra("pwd");
                etUserName.setText(username);
                etPassword.setText(pwd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.btn_login_register) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 100);
            }
            if (v.getId() == R.id.btn_login_submit) {

                username = etUserName.getText().toString();
                password = etPassword.getText().toString();
                StringBuilder builder = new StringBuilder();
                if (TextUtils.isEmpty(username)) {
                    builder.append("用户名为空\n");
                }
                if (TextUtils.isEmpty(password)) {
                    builder.append("密码为空\n");
                }
                if (!TextUtils.isEmpty(builder.toString())) {
                    Toast.makeText(LoginActivity.this, builder.toString(),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                btn.setProgress(50);
                // Tools.showProgressDialog(LoginActivity.this, "亲，正在努力为你登录");

                userEntity = new UserEntity();
                userEntity.setUsername(username);
                userEntity.setPassword(password);
                // 直接从下一层创建对象，耦合度高
                // LoginBiz loginBiz = new LoginBiz();
                // loginBiz.login(userEntity);

                // ioc
                ILoginBiz iLoginBiz = Factory.getLoginBizInstance();
                iLoginBiz.login(userEntity);

                // 单击不执行onClick()
                btn.setEnabled(false);

            }
        }
    }

    class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            btn.setEnabled(true);
            Tools.closeProgressDialog();
            // 给用户一个详细的提示
            // 成功，连接服务器失败，密码错误，
            int status = intent.getIntExtra(Const.KEY_DATA, -1);
            if (status == Const.STATUS_OK) {
                btn.setProgress(100);
                userEntity.setUser(username + "@" + TApplication.serviceName);
                TApplication.currentUser = userEntity;
                //				Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_LONG)
                //						.show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        btn.setProgress(0);
                        startActivity(new Intent(context, MainFragmentActivity.class));


                    }
                }, 1000);
            }

            if (status == Const.STATUS_PASSWORD_ERROR) {
                btn.setProgress(-1);

                Tools.showInfo(context, "密码错误");
                // startActivity(new Intent(context,
                // MainFragmentActivity.class));
            }
            if (status == Const.STATUS_CONNECT_FAILURE) {
                Tools.showInfo(context, "连接失败");
                // startActivity(new Intent(context,
                // MainFragmentActivity.class));
                btn.setProgress(-1);

            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn.setProgress(0);
                }
            }, 1000);
        }

    }
}
