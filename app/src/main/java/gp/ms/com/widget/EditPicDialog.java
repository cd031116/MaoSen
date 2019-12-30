package gp.ms.com.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import gp.ms.com.R;
import gp.ms.com.base.BaseDialog;
/**
* 编辑照片选择框
* */
public class EditPicDialog extends BaseDialog<SureDialog> {
    private Activity context;
    private TextView cancel;
    private TextView custom_t;
    private TextView pic_t;
    private OnclickCallBack onclickCallBack;

    public EditPicDialog(Activity context) {
        super(context);
        this.context = context;
    }


    @Override
    public View onCreateView() {
        widthScale(0.75f);
        dimEnabled(true);
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.sure_dialog, null);
        cancel = inflate.findViewById(R.id.cance_t);
        custom_t=inflate.findViewById(R.id.custom_t);
        pic_t=inflate.findViewById(R.id.pic_t);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        custom_t.setOnClickListener(view ->{
            if (null!=onclickCallBack){
                onclickCallBack.onCallBack(1);
            }
            dismiss();
        });
        pic_t.setOnClickListener(view ->{
            if (null!=onclickCallBack){
                onclickCallBack.onCallBack(0);
            }
            dismiss();
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
        void onCallBack(int position);
    }



}
