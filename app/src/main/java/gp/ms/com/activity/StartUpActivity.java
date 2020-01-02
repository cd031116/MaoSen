package gp.ms.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.StatusBarUtil;
import com.hyphenate.util.EasyUtils;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.chat.ChatHelper;
import gp.ms.com.chat.ui.VideoCallActivity;
import gp.ms.com.chat.ui.VoiceCallActivity;

public class StartUpActivity extends BaseActivity {
    private LinearLayout main_line;
    private static final int sleepTime = 2000;
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
        ChatHelper.getInstance().initHandler(this.getMainLooper());
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
                toLogin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });
    }


    private void  toLogin(){
            new Thread(new Runnable() {
                public void run() {
                    if (ChatHelper.getInstance().isLoggedIn()) {
                        // auto login mode, make sure all group and conversation is loaed before enter the main screen
                        long start = System.currentTimeMillis();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        EMClient.getInstance().groupManager().loadAllGroups();
                        long costTime = System.currentTimeMillis() - start;
                        //wait
                        if (sleepTime - costTime > 0) {
                            try {
                                Thread.sleep(sleepTime - costTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
                        if (topActivityName != null && (topActivityName.equals(VideoCallActivity.class.getName()) || topActivityName.equals(VoiceCallActivity.class.getName()))) {
                            // nop
                            // avoid main screen overlap Calling Activity
                        } else {
                            //enter main screen
                            startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                        }
                        finish();
                    }else {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                        startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }).start();
    }


}
