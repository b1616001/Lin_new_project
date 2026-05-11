package com.example.lin_new_project.view.viewpager;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.lin_new_project.databinding.ViewBlackBinding;
import com.example.lin_new_project.databinding.ViewBlueBinding;


public class BlackView extends ViewPagerView<ViewBlackBinding> {
    private Context context;
    @Override
    public ViewBlackBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return ViewBlackBinding.inflate(layoutInflater);
    }

    @Override
    public void dismiss() {

    }

    @Override
    public void initData() {

    }

    public BlackView(@NonNull Context context) {
        super(context);
        this.context=context;
        getBinding().imageView.setOnClickListener(view -> showToast("黑色"));
    }
}
