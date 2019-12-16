package gp.ms.com.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import gp.ms.com.R;
import gp.ms.com.activity.PunchActivity;
import gp.ms.com.adapter.AccessAdapter;
import gp.ms.com.adapter.GlideImageLoader;
import gp.ms.com.base.BaseFragment;
import gp.ms.com.bean.LocalInfo;
import gp.ms.com.http.utils.ToastUtils;
import gp.ms.com.interfaces.FunctionId;
import gp.ms.com.utils.IntentUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private Banner banner;
    private List<Integer> imageUrl = new ArrayList<>();
    private RecyclerView recyclerView;
    private AccessAdapter mAdapter;
    private List<LocalInfo> oList=new ArrayList<>();

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
        banner = view.findViewById(R.id.banner);
        recyclerView=view.findViewById(R.id.recyclerView);
    }

    @Override
    public void initData() {
        mAdapter=new AccessAdapter(getActivity(),R.layout.home_item);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 1, Color.parseColor("#B0B0B0")));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int index=oList.get(position).gettId();

                switch (index){
                    case FunctionId.SIGN_DAKA:
                        HomeFragmentPermissionsDispatcher.needLocationWithPermissionCheck(HomeFragment.this);
                        break;

                }
            }
        });
        oList.add(new LocalInfo(FunctionId.SIGN_DAKA,"考勤打卡",R.mipmap.sign_icon));
        mAdapter.setNewData(oList);

        imageUrl.add(R.mipmap.banner_1);
        imageUrl.add(R.mipmap.banner_2);
        startBanner();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void startBanner() {
        if (imageUrl.size() <= 0) {
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


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void needLocation() {
        Intent intent = new Intent(getActivity(), PunchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void onShowRation(final PermissionRequest request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("茂深集团");
        builder.setMessage("考勤签到,需要GPS权限,请允许!");
        builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                request.proceed();
                dialog.dismiss();
            }
        })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        request.cancel();
                        dialog.dismiss();
                    }
                })
                .create();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void onDenied() {
        ToastUtils.showToast("打卡签到需要,拍照权限和图片存储权限才能正常工作");
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void onAskgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("茂深集团");
        builder.setMessage("考勤签到,需要拍照权限和图片存储权限,您已拒绝,请前往设置!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                IntentUtils.gotoPermission(getActivity());
                dialog.dismiss();
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

}
