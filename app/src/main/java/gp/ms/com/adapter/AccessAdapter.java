package gp.ms.com.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import gp.ms.com.R;
import gp.ms.com.bean.LocalInfo;

public class AccessAdapter  extends BaseQuickAdapter<LocalInfo, BaseViewHolder> {

    private Context mContext;

    public AccessAdapter(Context context, int layoutResId) {
        super(layoutResId);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalInfo item) {
        ImageView imageView = helper.getView(R.id.image_v);
        Glide.with(mContext).load(item.getBackRouse()).into(imageView);
        helper.setText(R.id.tName,item.gettName());


    }
}
