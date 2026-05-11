package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.MainActivity.mainActivity;
import static com.example.lin_new_project.fun.LanguageUtils.refreshApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentLanguageBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.enums.LanguageEnum;
import com.example.lin_new_project.fun.LanguageUtils;
import com.example.lin_new_project.fun.MmkvUtils;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class LanguageFragment extends BaseBindingFragment<FragmentLanguageBinding> {
    private LanguageEnum languageEnum;
    @Override
    protected FragmentLanguageBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentLanguageBinding.inflate(layoutInflater);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        languageEnum = LanguageEnum.getLanguageEnum(MmkvUtils.getLanguageCountry(),false);
        initView();
    }

    private void initView() {
        getBinding().btnCn.setOnClickListener(view ->setLanguage(LanguageEnum.Chinese) );
        getBinding().btnTw.setOnClickListener(view ->setLanguage(LanguageEnum.TW) );

    }

    public void setLanguage(LanguageEnum language) {
        languageEnum=language;
        refreshApp(mainActivity,languageEnum);
    }


}