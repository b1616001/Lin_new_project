package com.example.lin_new_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentScreenBinding;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class ScreenFragment extends BaseBindingFragment<FragmentScreenBinding> {

    @Override
    protected FragmentScreenBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentScreenBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        getBinding().tvOn.setOnClickListener(view ->  getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));//不會關屏幕
        getBinding().tvOff.setOnClickListener(view ->  getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));//屏幕開關正常

    }
}