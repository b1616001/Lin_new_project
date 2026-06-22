package com.example.lin_new_project.popupwindow;



import static com.example.lin_new_project.MainActivity.mainActivity;
import static com.example.lin_new_project.fun.CommonUtils.getColorByVERSION;
import static com.example.lin_new_project.fun.CommonUtils.getDrawableByVERSION;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.lin_new_project.General;
import com.example.lin_new_project.R;
import com.example.lin_new_project.databinding.PopuWindowBackgroundBinding;
import com.example.lin_new_project.viewBinding.BasePopupWindow;


public class BackgroundWindow extends BasePopupWindow<PopuWindowBackgroundBinding> {

    public BackgroundWindow(Context context, boolean isFloating, boolean isBackground) {
        super(context, 0, 0, 0, General.TRANSLATION_Y, false, false, isFloating, false);
        if (isBackground){
            getBinding().bg.setAlpha(0.8f);
            getBinding().bg.setBackgroundColor(getColorByVERSION(R.color.black));
        }else {
            getBinding().bg.setAlpha(1f);
            getBinding().bg.setBackgroundColor(getColorByVERSION(R.color.white));
        }
//        getBinding().bg.setBackgroundColor(getColorByVERSION(R.color.black));
//        getBinding().bg.setBackground(blurBitmap(10));
//        getBinding().bg.setBackground(blur(context,R.drawable.bg_blue)) ;
    }

    @Override
    protected PopuWindowBackgroundBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return PopuWindowBackgroundBinding.inflate(layoutInflater);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
//        Looper.myQueue().addIdleHandler(() -> {
//
//            return false;
//        });

    }




}
