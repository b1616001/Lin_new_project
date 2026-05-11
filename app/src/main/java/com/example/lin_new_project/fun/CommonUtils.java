package com.example.lin_new_project.fun;


import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;


import static com.example.lin_new_project.MyApplication.getInstance;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.LocaleList;
import android.os.PowerManager;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CommonUtils {
    private static final int MIN_DELAY_TIME = 500;  // 兩次點選間隔不能少於1000ms
    private static long lastClickTime;

    public static boolean checkStr(String str) {
        return !"".equals(str) && str != null;
    }



    /**
     * 判斷網絡是否連接
     *
     * @param context context
     * @return 判斷網絡是否連接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        @SuppressLint("MissingPermission")
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }


    //
    public static Drawable getDrawableByVERSION(int id) {
        //會根據Android 版本來抓取圖片-----------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getInstance().getResources().getDrawable(id, getInstance().getTheme());
        } else {
            return getInstance().getResources().getDrawable(id);
        }
    }

    public static int getColorByVERSION(int id) {
        //會根據Android 版本來抓取顏色-----------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getInstance().getResources().getColor(id, getInstance().getTheme());
        } else {
            return getInstance().getResources().getColor(id);
        }
    }

    public static String getResourcesString(int strid) {
//        Resources res = getInstance().getResources();
//        Configuration conf = res.getConfiguration();
//        conf.setLocale(Locale.SIMPLIFIED_CHINESE);
//        Context context = getInstance().createConfigurationContext(conf);
//        Resources resources = context.getResources();
//        return resources.getString(strid);
        return getInstance().getResources().getString(strid);
    }

    public static int getResourcesIdentifier_drawable(String id) {
        int resId = getInstance().getResources().getIdentifier(
                id, "drawable", getInstance().getPackageName());
        return resId;
    }




    public static ArrayList<String> getListIntRange(int startIndex, int endIndex) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            arrayList.add(String.valueOf(i));
        }
        return arrayList;
    }



    /**
     * 保留小數，不四捨五入
     *
     * @param value 數值
     * @param keep  保留位數
     * @return string
     */
    public static String formatDecimal(double value, int keep) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        final DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(symbols);
        format.setMaximumFractionDigits(keep);
        format.setGroupingSize(0);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(value);
    }
    /**
     * 保留小數，不四捨五入
     *
     * @param Svalue 數值
     * @param keep   保留位數
     * @return string
     */
    public static String formatDecimal(String Svalue, int keep) {
        double value = Double.parseDouble(Svalue);
        final DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(keep);
        format.setGroupingSize(0);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(value);
    }
    public static String formatDecimal2(double value, int keep) {
        final DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(keep);
        format.setGroupingSize(0);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(value);
    }






    public static void changeLanguageSetting(Locale language/*Locale.GERMAN*/) {
        try {

            Class<?> activityManagerNative = Class.forName("android.app.ActivityManager");
            Object am = activityManagerNative.getMethod("getService").invoke(activityManagerNative);
            Configuration config = (Configuration) am.getClass().getMethod("getConfiguration").invoke(am);

//            config.setLocale(language);
            config.setLocales(new LocaleList(language, new Locale("en", "US")));   // <<<<<<<<<<<<<<< 切換語言時  ,加入第二語言 英文，避免netflix變中文

            config.getClass().getDeclaredField("userSetLocale").setBoolean(config, true);
            am.getClass().getMethod("updatePersistentConfiguration", Configuration.class).invoke(am, config);
            BackupManager.dataChanged("com.android.providers.settings");

            Log.d("多國語系", "success!" + language);
            //PS 如果重開機後語言回歸 ，可以使用updateLanguage


        } catch (Exception e) {
            Log.d("多國語系", "error-->:" + new Gson().toJson(e));

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public long getLocalVersionCode() {
        long localVersion = 0;
        try {
            PackageInfo packageInfo = getInstance()
                    .getPackageManager()
                    .getPackageInfo(getInstance().getPackageName(), 0);
            localVersion = packageInfo.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }



    /**
     * 獲取apk的包名
     */
    public String getApkPackageName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, 0);
        if (info != null) {
            return info.packageName;
        } else {
            return null;
        }
    }

}
