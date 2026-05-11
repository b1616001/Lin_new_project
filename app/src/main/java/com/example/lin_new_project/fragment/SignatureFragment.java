package com.example.lin_new_project.fragment;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentSignatureBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.fun.AndroidQStorageSaveUtils;
import com.example.lin_new_project.fun.ImageMethod;
import com.example.lin_new_project.fun.ToastMethod;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;
import com.github.gcacace.signaturepad.views.SignaturePad;


public class SignatureFragment extends BaseBindingFragment<FragmentSignatureBinding> {
    @Override
    protected FragmentSignatureBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentSignatureBinding.inflate(layoutInflater);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        getBinding().signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });
        getBinding().btnSave.setOnClickListener(view -> Save());
        getBinding().btnClear.setOnClickListener(view -> getBinding().signaturePad.clear());

    }
    private void Save() {
        try {
            Bitmap bmp =  getBinding().signaturePad.getSignatureBitmap();
            setLog( "圖片大小"+"bmp.getWidth"+bmp.getWidth());
            Bitmap bb= ImageMethod.reSize(bmp,512);//縮放圖片大小
//            ImageMethod.saveBitmap(bb);
            Uri bitmap_uri = AndroidQStorageSaveUtils.saveBitmap(getContext(), bb, Environment.DIRECTORY_DCIM, "Lin");
            if (AndroidQStorageSaveUtils.fileUriIsExists(getContext(), bitmap_uri)) {
                ToastMethod.showToast(getContext(), "儲存成功");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}