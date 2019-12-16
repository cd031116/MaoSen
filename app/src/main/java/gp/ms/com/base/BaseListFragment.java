package gp.ms.com.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gp.ms.com.R;

public abstract class BaseListFragment  extends BaseFragment{
    private FrameLayout content_t;
    private SwipeRefreshLayout refreshLayout;
    private RelativeLayout progress;
    protected LayoutInflater mInflater;
    private TextView top_title;
    private TextView name_t;
    private RelativeLayout top_left;
    @Override
    public int setFmView() {
        return R.layout.fm_bade_list;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        mInflater = getActivity().getLayoutInflater();
        content_t=view.findViewById(R.id.content_t);
        refreshLayout=view.findViewById(R.id.refresh);
        top_left=view.findViewById(R.id.top_left);
        top_title=view.findViewById(R.id.top_title);
        progress=view.findViewById(R.id.progress);
        name_t=view.findViewById(R.id.name_t);
        refreshLayout.setColorSchemeColors(Color.parseColor("#BF3183"),Color.parseColor("#65BD32"),Color.parseColor("#2C68C8"), Color.parseColor("#C6492B"), Color.parseColor("#31C08B"));
        initContentView(view,savedInstanceState);
    }

    @Override
    public void initData() {
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        initContentData();
    }

    /**
     * 绑定控件等
     */
    public abstract void initContentView(View view,Bundle savedInstanceState );

    /**
     * 初始化数据, 子类可以不实现
     */
    public abstract void initContentData();

    public void setFragmentView(int layoutId) {
        View view = mInflater.inflate(layoutId, null);
        setFragmentContent(view);
    }

    public void setFragmentContent(View view) {
        if (null != content_t) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            content_t.addView(view, params);
        }
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }


    /**
     * 显示加载图标
     * @param txt
     */
    public void showDialog(String txt){
        if(!"".equals(txt)&&txt!=null){
            progress.setVisibility(View.VISIBLE);
            name_t.setText(txt);
        }
    }

    /**
     * 关闭加载图标
     */
    public void dismissDialog(){
        progress.setVisibility(View.GONE);
    }

    public void SetTitle(String string){
        if (!TextUtils.isEmpty(string)){
            top_title.setText(string);
        }
    }
}
