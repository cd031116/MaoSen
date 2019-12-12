package gp.ms.com.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import gp.ms.com.R;
import gp.ms.com.base.BaseDialog;

public class SureDialog extends BaseDialog<SureDialog> {
    private Activity context;
    private TextView submit;
    private TextView cancel;
    private TextView title_t;
    private EditText edit_e;
    private OnclickCallBack onclickCallBack;

    public SureDialog(Activity context) {
        super(context);
        this.context = context;
    }


    @Override
    public View onCreateView() {
        widthScale(0.75f);
        dimEnabled(true);
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.sure_dialog, null);
        submit = inflate.findViewById(R.id.submit);
        cancel = inflate.findViewById(R.id.cancel);
        title_t=inflate.findViewById(R.id.title_t);
        edit_e=inflate.findViewById(R.id.edit_e);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strs=edit_e.getText().toString().trim();
                if (!TextUtils.isEmpty(strs)){
                    onclickCallBack.onCallBack(strs);
                }else {
                    onclickCallBack.onCallBack("");
                }
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
        void onCallBack(String strs);
    }

    public void settName(String tNames) {
        title_t.setText(tNames);
    }
}
