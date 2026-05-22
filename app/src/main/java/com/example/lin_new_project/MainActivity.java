package com.example.lin_new_project;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import static com.example.lin_new_project.MyApplication.nfc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.lin_new_project.databinding.ActivityMainBinding;
import com.example.lin_new_project.fun.LanguageUtils;
import com.example.lin_new_project.fun.MsgEvent;
import com.example.lin_new_project.fun.RxBus;
import com.example.lin_new_project.viewBinding.BaseBindingActivity;
import com.example.lin_new_project.webService.WebApi;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> {//416370
    public static MainActivity mainActivity;
    public NavController navController;
    public String currentFragment="HomeFragment";
    @Override
    protected ActivityMainBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageUtils.attachBaseContext(base));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        WebApi.initialization(this);
        mainActivity=this;
        requestPermission();
        initView();
    }


    private void initView() {
        navController = Navigation.findNavController(this, R.id.mainNavigation);
    }
    public void pageChangeNavController(NavDirections directions, int tag) {
        try {
            Log.d("切換Fragment", "TAG:" + tag + "\naaaa:");
            mainActivity.navController.navigate(directions);
        } catch (Exception e) {
            Log.d("切換Fragment", "TAG:" + tag + "\nErr:" + e);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!currentFragment.equals("HomeFragment")){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                navController.popBackStack();
                return false; // 返回false让事件继续传递到onBackPressed
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (currentFragment.equals("NfcFragment")){
            RxBus.getInstance().post(new MsgEvent(nfc,  intent));
        }
    }
    public void requestPermission() {
        // 如果裝置版本是6.0（包含）以上


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission1 = checkSelfPermission(
                    Manifest.permission.CAMERA);


            int hasPermission2 = checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            int hasPermission3 = checkSelfPermission(
                    Manifest.permission.INTERNET);

            int hasPermission4 = checkSelfPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION);

            int hasPermission5 = checkSelfPermission(
                    Manifest.permission.GET_ACCOUNTS);

            int hasPermission6 = checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            int hasPermission7 = checkSelfPermission(
                    Manifest.permission.ACCESS_NETWORK_STATE);

            int hasPermission8 = checkSelfPermission(
                    Manifest.permission.ACCESS_WIFI_STATE);

            int hasPermission9 = checkSelfPermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
            int hasPermission10 = checkSelfPermission(
                    Manifest.permission.NFC);
            int hasPermission11 = checkSelfPermission(
                    Manifest.permission.RECORD_AUDIO);
            int hasPermission12 = checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasPermission13 = checkSelfPermission(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            int hasPermission14 = checkSelfPermission(
                    Manifest.permission.BLUETOOTH_SCAN);
            int hasPermission15 = checkSelfPermission(
                    Manifest.permission.BLUETOOTH_ADVERTISE);
            int hasPermission16 = checkSelfPermission(
                    Manifest.permission.BLUETOOTH_CONNECT);
            int hasPermission17 = checkSelfPermission(
                    Manifest.permission.BLUETOOTH);
            int hasPermission18 = checkSelfPermission(
                    Manifest.permission.REQUEST_INSTALL_PACKAGES);
            // 如果未授權
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission2 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission3 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission4 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission5 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission6 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission7 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission8 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission9 != PackageManager.PERMISSION_GRANTED ||

                    hasPermission10 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission11 != PackageManager.PERMISSION_GRANTED ||

                    hasPermission12 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission13 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission14 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission15 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission16 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission17 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission18 != PackageManager.PERMISSION_GRANTED


            ) {
                // 請求授權
                // 第一個參數是請求授權的名稱
                // 第二個參數是請求代碼

                requestPermissions(
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.GET_ACCOUNTS,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                                Manifest.permission.NFC,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.BLUETOOTH_ADVERTISE,
                                Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.BLUETOOTH,
                                Manifest.permission.REQUEST_INSTALL_PACKAGES,
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE

                        },
                        100);
            }
        }
    }

}