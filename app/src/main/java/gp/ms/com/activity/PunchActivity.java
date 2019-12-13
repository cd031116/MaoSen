package gp.ms.com.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.utils.DataUtils;
import gp.ms.com.utils.StatusBarUtil;
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
    //是否已经授权
    private boolean isAllowPermissions = false;

    //是否外勤
    private boolean isWaiqin=true;

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
        StatusBarUtil.setColor(this, Color.parseColor("#d81e06"),0);
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
        daka_l=findViewById(R.id.daka_l);
        hint_one=findViewById(R.id.hint_one);
        hint_two=findViewById(R.id.hint_two);
        daka_x=findViewById(R.id.daka_x);
        clock_x=findViewById(R.id.clock_x);
        look_location_x=findViewById(R.id.look_location_x);
        look_location=findViewById(R.id.look_location);
        top_left.setOnClickListener(this);
        top_image.setOnClickListener(this);
        daka_l.setOnClickListener(this);
        look_location.setOnClickListener(this);
        look_location_x.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        top_title.setText(R.string.app_name);
        time_t.setText(DataUtils.getNYR());
        showDialog();
        mLatitude=28.160197;
        mLongitude=113.620241;
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
                if (isWaiqin){
                    showdialog();
                }else {

                }
                break;
            case R.id.look_location:
                startActivity(new Intent(PunchActivity.this,PunchMapActivity.class));
                break;
            case R.id.look_location_x:
                startActivity(new Intent(PunchActivity.this,PunchMapActivity.class));
                break;
        }
    }

    private void showDialog() {
        PunchActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(PunchActivity.this);
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void requestPermission() {
        getLocationClientOption();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PunchActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    public void showRation(final PermissionRequest request){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("茂深集团");
        builder.setMessage("考勤签到,需要GPS权限");
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

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    public void OnDenied() {

    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    public void OnAgain() {
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


    /***
     * 定位选项设置
     * @return
     */
    public void getLocationClientOption() {
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
                Toast.makeText(PunchActivity.this,"已进入考勤范围",Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(PunchActivity.this,"未进入考勤范围",Toast.LENGTH_SHORT).show();

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
        if (client != null) {
            client.start();
        }
    }
}
