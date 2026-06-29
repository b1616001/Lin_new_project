package com.example.lin_new_project.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.R;
import com.example.lin_new_project.databinding.FragmentScaleImageBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.view.ScaleImageView;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class ScaleImageFragment extends BaseBindingFragment<FragmentScaleImageBinding> {

    @Override
    protected FragmentScaleImageBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentScaleImageBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        getBinding().frameLayout.post(() -> {
            int[] location = new int[2];
            getBinding().frameLayout.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + getBinding().frameLayout.getMeasuredWidth();
            int bottom = top + getBinding().frameLayout.getMeasuredHeight();
            Log.d("資料長寬-座標", left + "\n" +
                    top + "\n" +
                    right + "\n" +
                    bottom);
            Log.d("資料長寬-寬", getBinding().frameLayout.getWidth() + "\n" +
                    getBinding().frameLayout.getMeasuredWidth() + "\n" +
                    getBinding().frameLayout.getMinimumWidth() + "\n" +
                    getBinding().frameLayout.getVerticalScrollbarWidth());
            Log.d("資料長寬-長", getBinding().frameLayout.getHeight() + "\n" +
                    getBinding().frameLayout.getMeasuredHeight() + "\n" +
                    getBinding().frameLayout.getMinimumHeight());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red);
            ScaleImageView scaleImageView=new ScaleImageView(getContext());
            scaleImageView.setImageBitmap(bitmap);
            scaleImageView.setData(getBinding().frameLayout.getWidth(), getBinding().frameLayout.getHeight(), left, top, right, bottom);
            getBinding().frameLayout.addView(scaleImageView);
        });

    }
}