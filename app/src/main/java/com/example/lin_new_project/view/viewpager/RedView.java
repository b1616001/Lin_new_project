package com.example.lin_new_project.view.viewpager;

import android.content.Context;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;


import com.example.lin_new_project.databinding.ViewRedBinding;


public class RedView extends ViewPagerView<ViewRedBinding> {
    private Context context;
    @Override
    public ViewRedBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return ViewRedBinding.inflate(layoutInflater);
    }

    @Override
    public void dismiss() {

    }

    @Override
    public void initData() {

    }

    public RedView(@NonNull Context context) {
        super(context);
        this.context=context;
        getBinding().imageView.setOnClickListener(view -> showToast("紅色"));
    }
}
