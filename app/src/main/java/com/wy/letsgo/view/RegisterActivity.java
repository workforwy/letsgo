package com.wy.letsgo.view;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.wy.letsgo.R;
import com.wy.letsgo.biz.implAsmack.RegisterBiz;
import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.util.Const;
import com.wy.letsgo.util.ImageCompress;
import com.wy.letsgo.util.ImageCompress.CompressOptions;
import com.wy.letsgo.util.Tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends Activity {

    @ViewById
    EditText et_register_username,
            et_register_password,
            et_register_confirm_password,
            et_register_name;
    @ViewById
    Button btn_register_submit;

    @ViewById
    ImageView iv_register_selectIcon;

    private Bitmap bitmap;
    String username, pwd;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent data = new Intent();
            data.putExtra("username", username);
            data.putExtra("pwd", pwd);
            //finish�����ں���
            setResult(0, data);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ��ϵͳͼ�⣬ѡ��ͼƬ
     *
     * @param view
     */
    public void selectIcon(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 200);
    }

    @Click(R.id.btn_register_submit)
    public void btn_register_submit() {
        try {
            btn_register_submit.setEnabled(false);
            Tools.showProgressDialog(RegisterActivity.this, "�ף�����Ϊ��ע��");
            username = et_register_username.getText().toString();
            pwd = et_register_password.getText().toString();
            String name = et_register_name.getText().toString();
            // ���ѧԱ���

            // ������
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(pwd);
            userEntity.setName(name);

            // ��intent����ֱ�Ӵ�ͼƬ����byte[]
//			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			bitmap.compress(CompressFormat.PNG, 10, byteArrayOutputStream);
//			byte[] imageData = byteArrayOutputStream.toByteArray();
            callBiz(userEntity);
        } catch (Exception e) {
        }
    }

    @Background
    public void callBiz(UserEntity userEntity) {
        RegisterBiz registerBiz = new RegisterBiz();
        registerBiz.register(this, userEntity);
    }

    /**
     * ִ��startActivityForResult����ϵͳͼ�⣬ѡ����ͼƬ��ִ��onActivityResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 200) {
                // bitmap = MediaStore.Images.Media.getBitmap(
                // getContentResolver(), data.getData());
                // ivSelectIcon.setImageBitmap(bitmap);

                ImageCompress.CompressOptions options = new CompressOptions();
                options.maxHeight = 40;
                options.maxWidth = 40;
                options.uri = data.getData();

                ImageCompress imageCompress = new ImageCompress();
                bitmap = imageCompress.compressFromUri(this, options);
                iv_register_selectIcon.setImageBitmap(bitmap);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @UiThread
    public void handleResult(int status) {
        int threadId = (int) Thread.currentThread().getId();

        Tools.closeProgressDialog();
        btn_register_submit.setEnabled(true);
        if (status == Const.STATUS_OK) {
            Toast.makeText(this, "ע��ɹ�", 2000).show();
        } else {
            Toast.makeText(this, "ע��ʧ��", 2000).show();
        }
    }

}
