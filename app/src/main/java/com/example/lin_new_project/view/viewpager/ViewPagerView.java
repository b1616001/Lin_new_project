package com.example.lin_new_project.view.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.example.lin_new_project.fun.ToastMethod;


public abstract class ViewPagerView<VB extends ViewBinding> extends LinearLayout {
    private VB binding;
    private Context context;

    public ViewPagerView(@NonNull Context context) {
        super(context);
        this.context=context;
        binding = onCreateViewBinding(LayoutInflater.from(context));


    }

    protected abstract VB onCreateViewBinding(@NonNull LayoutInflater layoutInflater);
    public abstract void dismiss();
    public abstract void initData();

    public VB getBinding() {
        return binding;
    }
    public  void showToast( String msg) {
        ToastMethod.showToast(context, msg);
    }

}
