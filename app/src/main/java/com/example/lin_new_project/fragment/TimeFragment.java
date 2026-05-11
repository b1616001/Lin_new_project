package com.example.lin_new_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.databinding.FragmentTimeBinding;
import com.example.lin_new_project.fun.ToastMethod;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;

import java.util.HashMap;


public class TimeFragment extends BaseBindingFragment<FragmentTimeBinding> {
    @Override
    protected FragmentTimeBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentTimeBinding.inflate(layoutInflater);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    private void initView() {
        HashMap<String,Boolean> hashMap =new HashMap<String, Boolean>();
        hashMap.put("2026年05月06日",true);
        hashMap.put("2026年05月17日",true);
        hashMap.put("2026年05月12日",true);
        hashMap.put("2026年05月01日",true);
        hashMap.put("2026年05月08日",true);
        hashMap.put("2026年05月09日",true);
        hashMap.put("2026年05月25日",true);

        getBinding().timeView.setHashMap(hashMap);
//        timeView.setTime(getTime());
        getBinding().timeView.setTime("2026年5月07日");
//        timeView.setValue(hashMap);//額外變動
        getBinding().timeView.getBinding().gvTime.setOnItemClickListener((parent, view, position, id) -> {
            if (getBinding().timeView.arrayList.get(position).trim().length()>0){
                String tt=getBinding().timeView.str_years+getBinding().timeView.arrayList.get(position)+"日";
                ToastMethod.showToast(getContext(),tt);
                getBinding().timeView.setValue(tt);
            }

        });
    }
}