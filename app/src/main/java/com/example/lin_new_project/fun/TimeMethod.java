package com.example.lin_new_project.fun;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.example.lin_new_project.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeMethod {
    public static final String formatType="yyyy-MM-dd HH:mm:ss";
    public static final String formatType2="yyyy年-MM月";
    public static final String formatType3="yyyy-MM-dd";
    public static final String formatType4="MM-dd";
    public static final String formatType5="yyyy年MM月";
    public static final String formatType6="yyyy年MM月dd日";

    public static final String zone_UTC="UTC";
    public static final String zone_GMT_8="UTC+8";
    public static final String zone_GMT_9="UTC+9";

    public static String getTime_zone(String formatType,String zone) {
        //取得目前年月日時間
        SimpleDateFormat formatter = new SimpleDateFormat(formatType,Locale.getDefault());
        TimeZone timeZone = TimeZone.getTimeZone(zone);
        formatter.setTimeZone(timeZone);
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        return formatter.format(curDate);
    }
    public static String getTime(String formatType) {
        //取得目前年月日時間
        SimpleDateFormat formatter = new SimpleDateFormat(formatType, Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        return formatter.format(curDate);
    }
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        //date類型轉換為String類型
        return new SimpleDateFormat(formatType,Locale.getDefault()).format(data);
    }


    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        //long類型轉換為String類型
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        return dateToString(date, formatType); // date类型转成String
    }

    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        //string類型轉換為date類型
        SimpleDateFormat formatter = new SimpleDateFormat(formatType,Locale.getDefault());
        return formatter.parse(strTime);
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        //long轉換為Date類型
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        //String類型轉換為long類型
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            return dateToLong(date); // date类型转成long类型
        }
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        //date類型轉換為long類型
        return date.getTime();
    }

    public static String addDayMINUTE(String strTime, int days, String formatType) throws ParseException {
        Date date = stringToDate(strTime, formatType);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + days);
        return dateToString(calendar.getTime(),formatType);
    }
    public static String reduceDayMINUTE(String strTime, int days, String formatType){
        try {
            Date date = stringToDate(strTime, formatType);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
            return dateToString(calendar.getTime(), formatType);
        }catch (Exception e){
            return strTime;
        }

    }
    private static Date str3Date(String str, String formatType) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(formatType);
            return df.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String setTimDisplay(Resources resources, String time, String formatType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str3Date(time,formatType));
        int ii = time.indexOf("年");

        String MM=time.substring(ii+1);
        MM =MM.replace("月","-");
        MM =MM.replace("日","");
        int firstIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String qq = "";
        switch (firstIndex) {
            case 1:
                qq =resources.getString(R.string.monday);
                break;
            case 2:
                qq =resources.getString(R.string.tuesday);
                break;
            case 3:
                qq =resources.getString(R.string.wednesday);
                break;
            case 4:
                qq =resources.getString(R.string.thursday);
                break;
            case 5:
                qq =resources.getString(R.string.friday);
                break;
            case 6:
                qq =resources.getString(R.string.saturday);
                break;
            case 0:
                qq =resources.getString(R.string.sunday);
                break;
        }
        return MM+"\n"+qq;
    }
    public static void datePicker(Context context, String time, String formatType,
                                  OnDateSetListener onDateSetListener) {
        if (time.trim().length()==0){
            time=TimeMethod.getTime(formatType);
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(TimeMethod.stringToDate(time,formatType));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                Date curDate = calendar.getTime();
                String time=TimeMethod.dateToString(curDate,formatType);
                onDateSetListener.onDateSet(time);
            }
        };

        new DatePickerDialog(context,dateSetListener,yy,mm,dd).show();
    }
    public static void datePicker_years(Context context, String time, String formatType,
                                        OnDateSetListener onDateSetListener) {
        if (time.trim().length()==0){
            time=TimeMethod.getTime(formatType);
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(TimeMethod.stringToDate(time,formatType));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                DecimalFormat decimalFormat = new DecimalFormat("00");//十進制

                onDateSetListener.onDateSet(decimalFormat.format(year) + "-" + decimalFormat.format(month));
            }
        };
        DatePickerDialog datePickerDialog= new DatePickerDialog(new ContextThemeWrapper(context,android.R.style.Theme_Holo_Light_Dialog_NoActionBar),dateSetListener,yy,mm,12);
        ((ViewGroup) ((ViewGroup) datePickerDialog.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
        datePickerDialog.show();
    }
    public static void datePicker_TimePickerDialog(Context context, String time, String formatType,
                                                   onTimeSetListener onTimeSetListener) {

        if (time.trim().length()==0){
            time=TimeMethod.getTime(formatType);
        }
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(TimeMethod.stringToDate(time,formatType));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog=   new TimePickerDialog(context, R.style.abirStyle, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                DecimalFormat decimalFormat = new DecimalFormat("00");//十進制
                onTimeSetListener.onTimeSet(decimalFormat.format(hourOfDay) + ":" + decimalFormat.format(minute));
            }
        },hh,mm,true);
//        timePickerDialog.setTitle("pick");
        timePickerDialog.show();
    }
    public interface OnDateSetListener {
        /**
         * 介面中的點選每一項的實現方法，引數自己定義
         * @param time 點選的時間
         */
        public void onDateSet(String time);

    }
    public interface onTimeSetListener {
        /**
         * 介面中的點選每一項的實現方法，引數自己定義
         * @param time 點選的時間
         */
        public void onTimeSet(String time);

    }
}
