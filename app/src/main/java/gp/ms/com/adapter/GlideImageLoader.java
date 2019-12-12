package gp.ms.com.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;


/**
 * @author lyj
 * @description:
 * @date :2019/3/12 0012 上午 9:31
 */
public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_loading).error(R.mipmap.default_img_failed);
        Glide.with(context).load(path).into(imageView);
    }
}
