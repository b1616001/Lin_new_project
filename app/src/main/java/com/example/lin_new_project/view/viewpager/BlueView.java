package com.example.lin_new_project.view.viewpager;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.lin_new_project.databinding.ViewBlueBinding;


public class BlueView extends ViewPagerView<ViewBlueBinding> {
    private Context context;
    @Override
    public ViewBlueBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return ViewBlueBinding.inflate(layoutInflater);
    }

    @Override
    public void dismiss() {

    }

    @Override
    public void initData() {

    }

    public BlueView(@NonNull Context context) {
        super(context);
        this.context=context;
        getBinding().imageView.setOnClickListener(view -> showToast("藍色"));
    }
}
