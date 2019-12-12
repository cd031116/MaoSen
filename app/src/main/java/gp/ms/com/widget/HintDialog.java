package gp.ms.com.widget;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import gp.ms.com.R;
import gp.ms.com.base.BaseDialog;

public class HintDialog extends BaseDialog<HintDialog> {
    private Activity context;
    private TextView submit;
    private TextView cancel;
    private TextView title_t;
    private TextView edit_e;
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
        edit_e=inflate.findViewById(R.id.edit_e);
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

    public void settCotent(String tNames) {
        edit_e.setText(tNames);
    }

    public void settSureTitle(String tNames) {
        submit.setText(tNames);
    }

}
