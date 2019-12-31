package gp.ms.com.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import gp.ms.com.R;
import gp.ms.com.base.TitleBaseActivity;
import gp.ms.com.editpic.DoodleActivity;
import gp.ms.com.editpic.DoodleParams;
import gp.ms.com.editpic.DoodleView;

public class PicOrderActivity extends TitleBaseActivity implements View.OnClickListener {
    private TextView submit_t;
    private TextView select_t;
    private ImageView image_c;
    private  String mPath="";
    private String edtPatn="";
    public static final int REQ_CODE_DOODLE = 101;

    @Override
    protected void initActivityContent() {
        setActivityContent(R.layout.activity_pic_order);
        submit_t=findViewById(R.id.submit_t);
        select_t=findViewById(R.id.select_t);
        image_c= findViewById(R.id.image_c);
        submit_t.setOnClickListener(this);
        select_t.setOnClickListener(this);
    }

    @Override
    protected void initActivityData() {
        setTitle("图片下单");
        choseImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_t:
                choseImage();
                break;
            case R.id.submit_t:

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                     List<LocalMedia> selectList = new ArrayList<>();
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    if (selectList.size()>0){
                        LocalMedia media=selectList.get(0);

                        if (media.isCompressed()&&!TextUtils.isEmpty(media.getCompressPath())){
                            mPath=media.getCompressPath();
                        }else {
                            mPath=media.getPath();
                        }
                        // 涂鸦参数
                        DoodleParams params = new DoodleParams();
                        params.mIsFullScreen = true;
                        // 图片路径
                        params.mImagePath = mPath;
                        // 初始画笔大小
                        params.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
                        // 画笔颜色
                        params.mPaintColor = Color.RED;
                        // 是否支持缩放item
                        params.mSupportScaleItem = true;
                        // 启动涂鸦页面
                        DoodleActivity.startActivityForResult(PicOrderActivity.this, params, REQ_CODE_DOODLE);
                    }
                    break;
                case  REQ_CODE_DOODLE:
                    if (data == null) {
                        return;
                    }
                    if (resultCode == DoodleActivity.RESULT_OK) {
                         edtPatn = data.getStringExtra(DoodleActivity.KEY_IMAGE_PATH);
                        if (TextUtils.isEmpty(edtPatn)) {
                            return;
                        }
                        Glide.with(PicOrderActivity.this).load(edtPatn).into(image_c);
//                        ImageLoader.getInstance(this).display(image_c, path);
                    } else if (resultCode == DoodleActivity.RESULT_ERROR) {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }







    private void choseImage(){
        PictureSelector.create(PicOrderActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(com.hyphenate.easeui.R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选（ PictureConfig.SINGLE）
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(false)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
//                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(200)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        edtPatn=null;
        mPath=null;
    }
}
