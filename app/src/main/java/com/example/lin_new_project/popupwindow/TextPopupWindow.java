package com.example.lin_new_project.popupwindow;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.lin_new_project.General;
import com.example.lin_new_project.databinding.PopupWindowTextBinding;
import com.example.lin_new_project.viewBinding.BasePopupWindow;

public class TextPopupWindow extends BasePopupWindow<PopupWindowTextBinding> {
    @Override
    protected PopupWindowTextBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return PopupWindowTextBinding.inflate(layoutInflater);
    }
    public TextPopupWindow(Context context) {
        super(context,500,500,300, General.FADE, false,true,false,true);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        Looper.myQueue().addIdleHandler(() -> {
            initView();
            return false;
        });
    }

    private void initView() {
        getBinding().imgClosed.setOnClickListener(view -> dismiss());
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
