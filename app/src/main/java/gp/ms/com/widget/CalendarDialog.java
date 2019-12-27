package gp.ms.com.widget;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import gp.ms.com.R;
import gp.ms.com.base.BaseDialog;
import gp.ms.com.calender.Calendar;
import gp.ms.com.calender.CalendarView;

public class CalendarDialog extends BaseDialog<SureDialog> implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener {
    private Activity context;
    private TextView submit;
    private  OnCallBack onCallBack;
    TextView mTextMonthDay;
    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    private String datas;
    public CalendarDialog(Activity context) {
        super(context);
        this.context = context;
    }
    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public View onCreateView() {
        widthScale(0.75f);
        dimEnabled(true);
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.dialog_calendar, null);
            mTextMonthDay = inflate.findViewById(R.id.tv_month_day);
            mTextYear = inflate.findViewById(R.id.tv_year);
            mTextLunar = inflate.findViewById(R.id.tv_lunar);
            mRelativeTool =inflate. findViewById(R.id.rl_tool);
            mCalendarView = inflate. findViewById(R.id.calendarView);
            mTextCurrentDay = inflate. findViewById(R.id.tv_current_day);
            submit=inflate.findViewById(R.id.submit);

            inflate.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCalendarView.scrollToCurrent();
                }
            });

            mTextMonthDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCalendarView.showYearSelectLayout(mYear);
                    mTextLunar.setVisibility(View.GONE);
                    mTextYear.setVisibility(View.GONE);
                    mTextMonthDay.setText(String.valueOf(mYear));
                }
            });

            mCalendarView.setOnCalendarSelectListener(this);
            mCalendarView.setOnYearChangeListener(this);
            mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
            mYear = mCalendarView.getCurYear();
            mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
            mTextLunar.setText("今日");
            mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));


            inflate.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                dismiss();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null!=onCallBack){
                        onCallBack.sure(datas);
                    }
                    datas="";
                    dismiss();
                }
            });
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }


    public OnCallBack getOnCallBack() {
        return onCallBack;
    }

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        datas=calendar.getYear()+"."+calendar.getMonth()+"."+calendar.getDay();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    public   interface  OnCallBack{
        void sure(String strs);
    }

}
