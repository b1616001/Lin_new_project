package com.example.lin_new_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentGifBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class GifFragment extends BaseBindingFragment<FragmentGifBinding> {

    @Override
    protected FragmentGifBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentGifBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}