package gp.ms.com.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gp.ms.com.R;
import gp.ms.com.base.BaseDialog;

public class HintDialog extends BaseDialog<HintDialog> {
    private Activity context;
    private TextView submit;
    private TextView cancel;
    private TextView title_t;
    private ImageView  image_g;
    private OnclickCallBack onclickCallBack;

    public HintDialog(Activity context) {
        super(context);
        this.context = context;
    }


    @Override
    public View onCreateView() {
        widthScale(0.75f);
        dimEnabled(true);
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.dialog_hint, null);
        submit = inflate.findViewById(R.id.submit);
        cancel = inflate.findViewById(R.id.cancel);
        title_t=inflate.findViewById(R.id.title_t);
        image_g=inflate.findViewById(R.id.image_g);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickCallBack.onCallBack();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setOnclickCallBack(OnclickCallBack onCallBack) {
        this.onclickCallBack = onCallBack;
    }

    public interface OnclickCallBack {
        void onCallBack();
    }

    public void settName(String tNames) {
        title_t.setText(tNames);
    }

    public void settCotent(Context cotent,String path) {
        Glide.with(context).load(path).into(image_g);
    }

    public void settSureTitle(String tNames) {
        submit.setText(tNames);
    }

}
