package gp.ms.com.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gp.ms.com.R;

public abstract class TitleBaseActivity extends BaseActivity {
    protected LayoutInflater mInflater;
    protected FrameLayout activity_content;
    protected RelativeLayout empty_view;
    private TextView message;
    private TextView top_title;
    private LinearLayout top_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mInflater = this.getLayoutInflater();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_title_base;
    }

    @Override
    protected void initView() {
        activity_content = findViewById(R.id.activity_base_content);
        empty_view = findViewById(R.id.view_empty);
        message= findViewById(R.id.message);
        top_left=findViewById(R.id.top_left);
        top_title=findViewById(R.id.top_title);
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initActivityContent();
    }

    @Override
    protected void initData() {
        initActivityData();
    }

    abstract protected void initActivityContent();

    abstract protected void initActivityData();

    public void setActivityContent(int layoutId) {
        View view = mInflater.inflate(layoutId, null);
        setActivityContent(view);
    }

    public void setActivityContent(View view) {
        if (null != activity_content) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            activity_content.addView(view, params);
        }
    }


    public void AddFragmentToThis(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.activity_base_content, fragment);
        transaction.commitAllowingStateLoss();
    }


    public void setNoData(boolean noData) {
        if (noData) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }

    }


    public void setMessages(String strs){
        if (!TextUtils.isEmpty(strs)){
            message.setText(strs);
        }
    }

    public void setTitle(String strs){
        if (!TextUtils.isEmpty(strs)){
            top_title.setText(strs);
        }
    }
}
