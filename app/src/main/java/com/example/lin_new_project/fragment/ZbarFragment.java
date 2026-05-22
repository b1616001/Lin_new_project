package com.example.lin_new_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.databinding.FragmentZbarBinding;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class ZbarFragment extends BaseBindingFragment<FragmentZbarBinding> {

    @Override
    protected FragmentZbarBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentZbarBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

    }
}