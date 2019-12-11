package gp.ms.com.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import gp.ms.com.R;
import gp.ms.com.base.BaseActivity;
import gp.ms.com.utils.DataUtils;
import gp.ms.com.utils.StatusBarUtil;
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

    private TextView time_t;

    //是否已经授权
    private boolean isAllowPermissions = false;

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
        time_t.setText(DataUtils.getNYR());

        top_left.setOnClickListener(this);
        top_image.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        top_title.setText("茂深集团");
        showDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left:
            case R.id.top_image:
                PunchActivity.this.finish();

                break;


        }
    }

    private void showDialog() {
        PunchActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(PunchActivity.this);
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void requestPermission() {

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
}
