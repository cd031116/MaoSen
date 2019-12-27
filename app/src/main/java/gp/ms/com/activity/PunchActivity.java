package gp.ms.com.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.http.utils.ToastUtils;
import gp.ms.com.utils.DataUtils;
import gp.ms.com.utils.IntentUtils;
import gp.ms.com.utils.StatusBarUtil;
import gp.ms.com.utils.Utils;
import gp.ms.com.widget.CalendarDialog;
import gp.ms.com.widget.HintDialog;
import gp.ms.com.widget.SureDialog;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PunchActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout top_left;
    private ImageView top_image;
    private TextView top_title;
    private LinearLayout daka_l;
    private TextView time_t;
    private TextClock clock_x;
    private LinearLayout daka_x;
    private LinearLayout hint_one;
    private LinearLayout hint_two;
    private TextView look_location;
    private TextView look_location_x;
    private LinearLayout xiaban_l;
    private LinearLayout shangb_l;
    private TextView no_pinch;
    private TextView no_pinch_t;
    private TextView time_s;
    private TextView time_x;
    private TextView gps_t;
    private TextView gps_s;
    private SwipeRefreshLayout refreshLayout;
    private TextView daka_t;//上班打卡文字
    private TextView daka_xt;//下班打卡文字
    private boolean sCan = false;
    private boolean xCan = false;
    private boolean isLocation = false;
    //是否外勤
    private boolean isWaiqin = true;
    private List<LocalMedia> selectList = new ArrayList<>();
    //--------------------
    private LatLng mDestinationPoint;//目的地坐标点
    private LocationClient client;//定位监听
    private LocationClientOption mOption;//定位属性
    private MyLocationData locData;//定位坐标
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private double mDistance = 0;
    private Double mLatitude;
    private Double mLongitude;

    /**
     * 规定到达距离范围距离
     */
    private int DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, Color.parseColor("#d81e06"), 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_punch;
    }

    @Override
    protected void initView() {
        time_t = findViewById(R.id.time_t);
        top_title = findViewById(R.id.top_title);
        top_left = findViewById(R.id.top_left);
        top_image = findViewById(R.id.top_image);
        daka_l = findViewById(R.id.daka_l);
        hint_one = findViewById(R.id.hint_one);
        hint_two = findViewById(R.id.hint_two);
        daka_x = findViewById(R.id.daka_x);
        clock_x = findViewById(R.id.clock_x);
        look_location_x = findViewById(R.id.look_location_x);
        look_location = findViewById(R.id.look_location);
        xiaban_l = findViewById(R.id.xiaban_l);
        no_pinch = findViewById(R.id.no_pinch);
        time_s = findViewById(R.id.time_s);
        no_pinch_t = findViewById(R.id.no_pinch_t);
        time_x = findViewById(R.id.time_x);
        gps_t = findViewById(R.id.gps_t);
        refreshLayout = findViewById(R.id.swipe);
        shangb_l = findViewById(R.id.shangb_l);
        gps_s = findViewById(R.id.gps_s);
        daka_t = findViewById(R.id.daka_t);
        daka_xt = findViewById(R.id.daka_xt);
        time_t.setOnClickListener(this);
        daka_t.setOnClickListener(this);
        top_left.setOnClickListener(this);
        top_image.setOnClickListener(this);
        daka_l.setOnClickListener(this);
        daka_x.setOnClickListener(this);
        look_location.setOnClickListener(this);
        look_location_x.setOnClickListener(this);
        gps_t.setOnClickListener(this);
        refreshLayout.setColorSchemeColors(Color.parseColor("#BF3183"), Color.parseColor("#65BD32"), Color.parseColor("#2C68C8"), Color.parseColor("#C6492B"));
    }

    @Override
    protected void initData() {
        top_title.setText(R.string.app_name);
        time_t.setText(DataUtils.getNYR());
        mLatitude = 28.160197;
        mLongitude = 113.620241;
        mDestinationPoint = new LatLng(mLatitude, mLongitude);//假设公司坐标

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left:
            case R.id.top_image:
                PunchActivity.this.finish();
                break;
            case R.id.daka_l:
                if (sCan) {
                    PunchActivityPermissionsDispatcher.needCameraWithPermissionCheck(this);
                    PunchActivityPermissionsDispatcher.needStorageWithPermissionCheck(this);
                } else {
                    showdialog();
                }
                break;
            case R.id.daka_x:
                if (xCan) {
                    PunchActivityPermissionsDispatcher.needCameraWithPermissionCheck(this);
                    PunchActivityPermissionsDispatcher.needStorageWithPermissionCheck(this);
                } else {
                    showdialog();
                }
                break;
            case R.id.look_location:
                startActivity(new Intent(PunchActivity.this, PunchMapActivity.class));
                break;
            case R.id.look_location_x:
                startActivity(new Intent(PunchActivity.this, PunchMapActivity.class));
                break;
            case R.id.time_t:
                goCheckData();
                break;
        }
    }


    /**
     * 日历选择
     */
    private void goCheckData() {
        final CalendarDialog dialog = new CalendarDialog(PunchActivity.this);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCallBack(onCallBack);
    }


    CalendarDialog.OnCallBack onCallBack = new CalendarDialog.OnCallBack() {
        @Override
        public void sure(String strs) {
            time_t.setText(strs);
        }
    };


    /**
     * 拍照权限
     */
    @NeedsPermission(Manifest.permission.CAMERA)
    public void needCamera() {
        takePhoto();
    }


    /**
     * 存储权限
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void needStorage() {


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PunchActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    /**
     * 注解在用于向用户解释为什么需要调用该权限的方法上，只有当第一次请求权限被用户拒绝，下次请求权限之前会调用
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void showRationStore(final PermissionRequest request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("茂深集团");
        builder.setMessage("考勤签到,需要图片存储权限,请允许!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                request.proceed();
                dialog.dismiss();
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        request.cancel();
                        dialog.dismiss();
                    }
                })
                .create();

    }

    @OnShowRationale(Manifest.permission.CAMERA)
    public void showRationCamera(final PermissionRequest request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("茂深集团");
        builder.setMessage("考勤签到,需要拍照权限,请允许!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                request.proceed();
                dialog.dismiss();
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        request.cancel();
                        dialog.dismiss();
                    }
                })
                .create();
    }


    /**
     * 注解在当用户拒绝了权限请求时需要调用的方法上
     */
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void OnDenied() {
        ToastUtils.showToast("考勤打卡需要GPS权限才能正常工作");
    }


    /**
     * 注解在当用户拒绝了权限请求时需要调用的方法上
     */
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void OnDeniedCamera() {
        ToastUtils.showToast("打卡签到需要,拍照权限和图片存储权限才能正常工作");
    }


    /**
     * 注解在当用户选中了授权窗口中的不再询问复选框后并拒绝了权限请求时需要调用的方法，一般可以向用户解释为何申请此权限，并根据实际需求决定是否再次弹出权限请求对话框
     */
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void OnAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("茂深集团");
        builder.setMessage("考勤签到,需要拍照权限和图片存储权限,您已拒绝,请前往设置!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                IntentUtils.gotoPermission(PunchActivity.this);
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


    private void showdialog() {
        final SureDialog dialog = new SureDialog(PunchActivity.this);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnclickCallBack(new SureDialog.OnclickCallBack() {
            @Override
            public void onCallBack(String strs) {


            }
        });
    }

    private void takePhoto() {
        // 单独拍照
        PictureSelector.create(PunchActivity.this)
                .openCamera(PictureMimeType.ofImage())
                .compress(true)
                .forResult(PictureConfig.REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.REQUEST_CAMERA:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getCompressPath());
                    }
                    submitDaka(selectList.get(0).getCompressPath());
                    break;
            }
        }
    }


    /***
     * 定位选项设置
     * @return
     */
    public void getLocationClientOption() {
        daka_l.setEnabled(true);
        daka_x.setEnabled(true);
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(2000);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setOpenGps(true);//可选，默认false，设置是否开启Gps定位
        mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        client = new LocationClient(this);
        client.setLocOption(mOption);
        client.registerLocationListener(BDAblistener);
        client.start();
    }

    /***
     * 接收定位结果消息，并显示在地图上
     */
    private BDAbstractLocationListener BDAblistener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //定位方向
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            //更改UI
            Message message = new Message();
            message.obj = location;
            mHandler.sendMessage(message);
        }
    };

    /**
     * 处理连续定位的地图UI变化
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BDLocation location = (BDLocation) msg.obj;
            LatLng LocationPoint = new LatLng(location.getLatitude(), location.getLongitude());
            //计算两点距离,单位：米
            mDistance = DistanceUtil.getDistance(mDestinationPoint, LocationPoint);
            if (mDistance <= DISTANCE) {
                //显示文字 "您已在签到范围内"
                sCan = true;
                xCan = true;
                client.stop();
                daka_t.setText("考勤打卡");
                daka_xt.setText("考勤打卡");
                daka_l.setBackgroundResource(R.drawable.green_circel);
                daka_x.setBackgroundResource(R.drawable.green_circel);
                hint_one.setVisibility(View.GONE);
                hint_two.setVisibility(View.GONE);
            } else {
                hint_one.setVisibility(View.VISIBLE);
                hint_two.setVisibility(View.VISIBLE);
                daka_l.setBackgroundResource(R.drawable.red_circel);
                daka_x.setBackgroundResource(R.drawable.red_circel);
                daka_t.setText("外勤");
                daka_xt.setText("外勤");
            }

        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        //取消注册传感器监听
        if (client != null && client.isStarted()) {
            client.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showYesOrNoGps();
        if (client != null) {
            client.start();
        }
    }


    private void showNoGps() {
        daka_t.setText("无法打卡");
        daka_xt.setText("无法打卡");
        gps_s.setVisibility(View.VISIBLE);
        gps_t.setVisibility(View.GONE);
        hint_one.setVisibility(View.GONE);
        hint_two.setVisibility(View.GONE);
        daka_l.setBackgroundResource(R.drawable.gray_cirel);
        daka_x.setBackgroundResource(R.drawable.gray_cirel);
        daka_l.setEnabled(false);
        daka_x.setEnabled(false);

    }


    /**
     * 上班打卡之前显示
     */
    private void ShowOne() {
        xiaban_l.setVisibility(View.GONE);
        no_pinch.setVisibility(View.GONE);
        time_s.setVisibility(View.GONE);
        //进入考勤范围
        daka_l.setBackgroundResource(R.drawable.green_circel);
        hint_one.setVisibility(View.GONE);
        //未进入
        daka_l.setBackgroundResource(R.drawable.red_circel);
        hint_one.setVisibility(View.VISIBLE);

    }

    /**
     * 下班之前
     */
    private void ShowTwo(boolean daka) {
        xiaban_l.setVisibility(View.VISIBLE);
        daka_l.setVisibility(View.GONE);
        if (daka) {
            time_s.setVisibility(View.VISIBLE);
            time_s.setText("2019-12-25");
            no_pinch.setVisibility(View.GONE);
        } else {
            time_s.setVisibility(View.GONE);
            no_pinch.setVisibility(View.VISIBLE);
        }
        //未进入
        daka_x.setBackgroundResource(R.drawable.red_circel);
        hint_two.setVisibility(View.VISIBLE);
    }

    /**
     * 下班后
     */
    private void showOver(boolean sdaka, boolean xdaka) {
        daka_l.setVisibility(View.GONE);
        xiaban_l.setVisibility(View.VISIBLE);
        daka_x.setVisibility(View.GONE);
        if (sdaka) {
            time_s.setVisibility(View.VISIBLE);
            no_pinch.setVisibility(View.GONE);
            time_s.setText("2019-12-25");
        } else {
            time_s.setVisibility(View.GONE);
            no_pinch.setVisibility(View.VISIBLE);
        }
        //--
        if (xdaka) {
            no_pinch_t.setVisibility(View.GONE);
            time_x.setVisibility(View.VISIBLE);
            time_x.setText("2019-12-25");
        } else {
            no_pinch_t.setVisibility(View.VISIBLE);
            time_x.setVisibility(View.GONE);
        }
    }


    /**
     * gps是否打开
     */
    private void showYesOrNoGps() {
        if (Utils.GpsIsOPen(PunchActivity.this)) {
            gps_t.setVisibility(View.GONE);
            gps_s.setVisibility(View.GONE);
            hint_one.setVisibility(View.GONE);
            hint_two.setVisibility(View.GONE);
            daka_t.setText("正在定位");
            daka_xt.setText("正在定位");
            if (!isLocation) {
                getLocationClientOption();
            }
        } else {
            showNoGps();
        }
    }


    /**
     * 打卡上传照片
     */
    private void submitDaka(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        final HintDialog dialog = new HintDialog(PunchActivity.this);
        dialog.show();
        dialog.settCotent(PunchActivity.this, path);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnclickCallBack(new HintDialog.OnclickCallBack() {
            @Override
            public void onCallBack() {


            }
        });
    }


}
