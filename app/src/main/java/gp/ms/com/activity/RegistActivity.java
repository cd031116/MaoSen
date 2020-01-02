package gp.ms.com.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.utils.StatusBarUtil;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;

public class RegistActivity extends BaseActivity implements View.OnClickListener {
    TextView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView() {
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, Color.parseColor("#ff7767"),0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                startActivity(new Intent(RegistActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}
