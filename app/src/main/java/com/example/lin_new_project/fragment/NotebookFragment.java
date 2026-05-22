package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.fun.CommonUtils.checkStr;
import static com.example.lin_new_project.fun.FileMethod.getFile_uri;
import static com.example.lin_new_project.fun.NotebookFun.extelnalPrivateCreateFoler;
import static com.example.lin_new_project.fun.NotebookFun.internalReadnnn;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentNotebookBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.fun.AndroidQStorageSaveUtils;
import com.example.lin_new_project.fun.ToastMethod;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NotebookFragment extends BaseBindingFragment<FragmentNotebookBinding> implements View.OnClickListener {

    @Override
    protected FragmentNotebookBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentNotebookBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        getBinding().btnRead.setOnClickListener(this);
        getBinding().btnDeposit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String dirName=getBinding().edText.getText().toString();
        if (!checkStr(dirName)){
            showToast("檔名請勿空白");
            return;
        }
        if (view.equals(getBinding().btnRead)){
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(() -> {
               String txt= internalReadnnn(getActivity(),dirName);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        getBinding().tvTxt.setText(txt);
//                        System.out.println("序UI" + threadName + " thread");
//                        Toast.makeText(ExecutorsActivity.this, threadName, Toast.LENGTH_LONG).show();
                    }
                });
            });
        }
        if (view.equals(getBinding().btnDeposit)){
            ArrayList<HashMap> aa = new ArrayList<HashMap>();
            for (int i = 0; i < 2; i++) {
                HashMap<String, String> hh = new HashMap<String, String>();
                hh.put("x", "x" + i);
                hh.put("x1", "x1" + i);
                aa.add(hh);
            }
            try {
                JSONArray jsonArray = new JSONArray(aa.toString());
                setLog("存入資料->"+jsonArray);
                File file= extelnalPrivateCreateFoler(getActivity(),dirName,jsonArray.toString());
                if (AndroidQStorageSaveUtils.fileUriIsExists(getContext()
                        , getFile_uri(getContext(),file))) {
                  showToast( "儲存成功");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}