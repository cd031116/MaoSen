package gp.ms.com.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.chat.ChatHelper;
import gp.ms.com.http.utils.ToastUtils;
import gp.ms.com.utils.StatusBarUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private Button login;
    private TextView no_acount;
    private TextView to_acount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        login=findViewById(R.id.login);
        no_acount=findViewById(R.id.no_acount);
        to_acount=findViewById(R.id.to_acount);
        to_acount.setOnClickListener(this);
        no_acount.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this,Color.parseColor("#ff7767"),0);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.no_acount:
            case R.id.to_acount:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                finish();
                break;
        }
    }

    private void  ImLogin(String userName,String password){
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            ToastUtils.showToast("网络不可用");
            return;
        }
        if (ChatHelper.getInstance().isLoggedIn()) {

            return;
        }
        EMClient.getInstance().login(userName,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }

}
