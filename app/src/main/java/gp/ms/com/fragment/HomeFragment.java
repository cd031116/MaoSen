package gp.ms.com.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import gp.ms.com.R;
import gp.ms.com.activity.PunchActivity;
import gp.ms.com.adapter.GlideImageLoader;
import gp.ms.com.base.BaseFragment;
import gp.ms.com.utils.StatusBarUtil;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private ImageView to_sign;
    private Banner banner;
    private List<String> imageUrl = new ArrayList<>();
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setFmView() {
        return R.layout.fm_home;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        to_sign=view.findViewById(R.id.to_sign);
        banner=view.findViewById(R.id.banner);
        to_sign.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_sign:
                Intent intent=new Intent(getActivity(), PunchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void startBanner() {
        if (imageUrl.size()<=0){
            return;
        }
        //设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居右）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageUrl);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {


            }
        });
    }

}
