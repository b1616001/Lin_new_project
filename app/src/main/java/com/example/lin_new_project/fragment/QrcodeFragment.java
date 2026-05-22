package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.fun.CommonUtils.checkStr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.R;
import com.example.lin_new_project.databinding.FragmentQrcodeBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.fun.CodeUtils;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;
import com.google.zxing.EncodeHintType;

import java.util.HashMap;
import java.util.Map;


public class QrcodeFragment extends BaseBindingFragment<FragmentQrcodeBinding> {

    @Override
    protected FragmentQrcodeBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentQrcodeBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().tvQrcode.setOnClickListener(view1 -> {
            if (checkStr(getBinding().edQrcode.getText().toString())){
                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red);
                Bitmap bitmap = CodeUtils.createQRCode(getBinding().edQrcode.getText().toString(), 600, logo);
                getBinding().image.setImageBitmap(bitmap);
            }
        });

    }
}