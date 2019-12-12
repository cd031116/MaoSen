package gp.ms.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.utils.StatusBarUtil;

public class StartUpActivity extends BaseActivity {
    private LinearLayout main_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucent(StartUpActivity.this,0);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setStatusBar() {

        StatusBarUtil.setTransparent(this);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_start_up;
    }

    @Override
    protected void initView() {
        main_line = findViewById(R.id.main_line);
    }

    @Override
    protected void initData() {
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(2000);
        main_line.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });
    }
}
