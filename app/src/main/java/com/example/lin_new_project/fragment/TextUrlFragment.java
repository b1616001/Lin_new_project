package com.example.lin_new_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.databinding.FragmentTextUrlBinding;
import com.example.lin_new_project.fun.UrlUtilsMethod;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class TextUrlFragment extends BaseBindingFragment<FragmentTextUrlBinding> {

    @Override
    protected FragmentTextUrlBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentTextUrlBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         UrlUtilsMethod.handleText(getBinding().tvUrl, "測試網址:https://github.com支持http、https、svn、ftp");

    }
}