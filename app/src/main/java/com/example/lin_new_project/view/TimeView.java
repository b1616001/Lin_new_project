package com.example.lin_new_project.view;

import static com.example.lin_new_project.fun.TimeMethod.dateToString;
import static com.example.lin_new_project.fun.TimeMethod.formatType5;
import static com.example.lin_new_project.fun.TimeMethod.formatType6;

import static com.example.lin_new_project.fun.TimeMethod.stringToDate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.adapter.TimeAdapter;
import com.example.lin_new_project.databinding.ViewTimeBinding;
import com.example.lin_new_project.viewBinding.BaseView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TimeView extends BaseView<ViewTimeBinding> implements View.OnClickListener {
    private Context context;
    public String str_time = "";//點選時間
    public String str_years = "";//年月
    private Boolean aBoolean = false;//有沒有手勢移動
    public ArrayList<String> arrayList;
    private TimeAdapter timeAdapter;
    private int lastX;
    private HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
    ;

    @Override
    public ViewTimeBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return ViewTimeBinding.inflate(layoutInflater);
    }

    public TimeView(@NonNull Context context) {
        super(context);
    }

    public TimeView(@NonNull Context context, @Nullable @NonNull AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getBinding().gvTime.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消預設點擊
    }

    public void setTime(String time) {
        str_time = time;
        int mm = str_time.indexOf("月");
        str_years = str_time.substring(0, mm + 1).trim();
        getBinding().tvLeft.setOnClickListener(this);
        getBinding().tvRight.setOnClickListener(this);
        setTimDisplay();//顯示點選時間
        monthChange(0);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(getBinding().tvLeft)) {
            monthChange(-1);
            return;
        }
        if (view.equals(getBinding().tvRight)) {
            monthChange(1);
            return;
        }
    }

    public void monthChange(int change) {
        try {
            aBoolean = false;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(stringToDate(str_years, formatType5));
            calendar.add(Calendar.MONTH, change);
            int firstIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;//0代表周日 6代表周6
            str_years = dateToString(calendar.getTime(), formatType5);
            setLog("時間格式->" + "周" + firstIndex + "," + calendar.get(Calendar.DAY_OF_MONTH) + "號,一共有" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "天,年月" + str_years);
            getBinding().tvTime.setText(str_years);
            arrayList = new ArrayList<String>();
            for (int i = 0; i < firstIndex; i++) {//週日不用加空白 周一到周六每一天追加一個空白
                arrayList.add("");
            }
            for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                if (i < 10) {
                    arrayList.add("0" + i);
                } else {
                    arrayList.add(i + "");
                }
            }
            setLog("hashMap->" +hashMap);
            timeAdapter = new TimeAdapter(context, arrayList, str_time, str_years, hashMap);
            getBinding().gvTime.setAdapter(timeAdapter);
            getBinding().gvTime.setOnTouchListener((v, event) -> {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //   Log.d(TAG, "onTouchEvent: down height="+ getHeight());
                        lastX = (int) event.getRawX();

                        break;
                    case MotionEvent.ACTION_UP:
                        int xx = lastX - (int) event.getRawX();
                        setLog("移動的值" + xx + "," + aBoolean);
                        if (!aBoolean) {
                            return false;
                        }
                        if (xx == 0 || (0 > xx && xx > -30) || (0 < xx && xx < 30)) {
                            return false;
                        } else {
                            if (xx > 0) {
                                //往左
                                setLog("移動的值" + "往左");
                                monthChange(-1);
                            } else {
                                //往右
                                setLog("移動的值" + "往右");
                                monthChange(1);
                            }
                            return true;
                        }

                    case MotionEvent.ACTION_MOVE:
                        aBoolean = true;
                        break;
                }
                return false;
            });
        } catch (Exception e) {

        }

    }

    private void setTimDisplay() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(stringToDate(str_time, formatType6));
            int firstIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            setLog("時間格式" + "周" + firstIndex + "," + calendar.get(Calendar.DAY_OF_MONTH) + "號,一共有" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "天");
            int ii = str_time.indexOf("年");
            int mm = str_time.indexOf("日");
            getBinding().tvYy.setText(str_time.substring(0, ii + 1));
            String qq = "";
            switch (firstIndex) {
                case 1:
                    qq = "  周一";
                    break;
                case 2:
                    qq = "  周二";
                    break;
                case 3:
                    qq = "  周三";
                    break;
                case 4:
                    qq = "  周四";
                    break;
                case 5:
                    qq = "  周五";
                    break;
                case 6:
                    qq = "  周六";
                    break;
                case 0:
                    qq = "  周日";
                    break;
            }
            getBinding().tvMm.setText(str_time.substring(ii + 1, mm + 1) + qq);
        } catch (Exception e) {

        }

    }
    public void setValue(String tt) {
        str_time = tt;
        timeAdapter.setStr_time(tt);
        timeAdapter.notifyDataSetChanged();
        setTimDisplay();
    }
    public void setValue(HashMap<String, Boolean> hashMap) {
        this.hashMap=hashMap;
        timeAdapter.setHashMap(hashMap);
        timeAdapter.notifyDataSetChanged();
    }
    public HashMap<String, Boolean> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Boolean> hashMap) {
        this.hashMap = hashMap;
    }

}
