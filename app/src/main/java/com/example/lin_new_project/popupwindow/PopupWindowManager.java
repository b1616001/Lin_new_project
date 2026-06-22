package com.example.lin_new_project.popupwindow;

import static com.example.lin_new_project.MainActivity.mainActivity;
import static com.example.lin_new_project.MyApplication.getInstance;

import android.util.Log;
import android.view.Gravity;

import com.example.lin_new_project.window.MediaMenuWindow;

public class PopupWindowManager {
    private static TextPopupWindow textPopupWindow;
    private static BackgroundWindow backgroundWindow;
    public static MediaMenuWindow mediaMenuWindow;
    public static  void showTextPopupWindow(){
        if (textPopupWindow != null) return;
        showBg(true);
        textPopupWindow = new TextPopupWindow(getInstance().getApplicationContext());
        textPopupWindow.showAtLocation(mainActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        textPopupWindow.setOnCustomDismissListener(new TextPopupWindow.OnCustomDismissListener() {
            @Override
            public void onStartDismiss() {
            }

            @Override
            public void onDismiss() {
                textPopupWindow = null;
                showBg(false);
            }
        });
    }
    public static void showBg(boolean isShow) {
        if (isShow) {
            if (backgroundWindow == null) {
                backgroundWindow = new BackgroundWindow(getInstance(), false , true);
            }
            if (backgroundWindow.isShowing()) return;
            backgroundWindow.showAtLocation(mainActivity.getWindow().getDecorView(), Gravity.END | Gravity.BOTTOM, 0, 0);
        } else {
            if (backgroundWindow != null) {
                backgroundWindow.dismiss();
            }
        }
    }
    public static void showMediaMenu(boolean isShow) {
        if (isShow) {
            if (mediaMenuWindow != null) return;
            mediaMenuWindow = new MediaMenuWindow(mainActivity);
            mediaMenuWindow.setOnCustomDismissListener(() -> mediaMenuWindow = null);
        }else {
            if (mediaMenuWindow != null) {
                mediaMenuWindow.dismiss();
                mediaMenuWindow = null;
            }
        }
    }
}
