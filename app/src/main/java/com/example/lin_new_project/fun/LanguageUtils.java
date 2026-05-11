package com.example.lin_new_project.fun;

import static com.example.lin_new_project.MainActivity.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.Log;


import androidx.core.app.ActivityCompat;

import com.example.lin_new_project.MainActivity;
import com.example.lin_new_project.MyApplication;
import com.example.lin_new_project.enums.LanguageEnum;
import com.google.gson.Gson;

import java.util.Locale;

public class LanguageUtils {
    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context);
        } else {
            return context;
        }
    }

    private static Context updateResources(Context context) {
        Resources resources = context.getResources();
        LanguageEnum languageEnum = LanguageEnum.getLanguageEnum(MmkvUtils.getLanguageCountry(), false);
        Locale locale = new Locale(languageEnum.getLanguageCode(), languageEnum.getCountry());
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    public static void setLanguage(Activity activity, LanguageEnum languageEnum) {
        Log.d("多國語系", "setLanguage:" + new Gson().toJson(languageEnum));
        Configuration newConfig = new Configuration();
        newConfig.setLocales(new LocaleList(Locale.FRENCH));
        activity.createConfigurationContext(newConfig);
    }

    public static void refreshApp(Activity activity, LanguageEnum languageEnum) {
        MmkvUtils.setLanguageCountry(languageEnum.getCountry());
        LanguageUtils.setLanguage(mainActivity, languageEnum);
        ActivityCompat.recreate(activity);//當前頁面直接刷新無須重啟Activity

        //刷新Activity
//        Intent intent = new Intent(activity, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        activity.startActivity(intent);
    }

    public static void saveLanguage(LanguageEnum languageEnum) {
        MmkvUtils.setLanguageCountry(languageEnum.getCountry());
    }

    public static void updateAppLanguage(LanguageEnum languageEnum) {
        Configuration config = MyApplication.instance.getResources().getConfiguration();
        config.locale = languageEnum.getLocale();
        MyApplication.instance.getResources().updateConfiguration(config, MyApplication.instance.getResources().getDisplayMetrics());

        LanguageUtils.saveLanguage(languageEnum);
    }
}
