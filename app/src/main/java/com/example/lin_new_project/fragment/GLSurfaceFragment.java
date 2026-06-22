package com.example.lin_new_project.fragment;


import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.OpenGLR.OpenGLRenderer;
import com.example.lin_new_project.databinding.FragmentGlsurfaceBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class GLSurfaceFragment extends BaseBindingFragment<FragmentGlsurfaceBinding> {

    @Override
    protected FragmentGlsurfaceBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentGlsurfaceBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        GLSurfaceView view = new GLSurfaceView(getContext());
        view.setRenderer(new OpenGLRenderer());
        getBinding().frameLayout.addView(view);
    }
}