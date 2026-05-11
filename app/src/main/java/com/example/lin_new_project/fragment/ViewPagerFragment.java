package com.example.lin_new_project.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.lin_new_project.adapter.ViewPagerAdapter;
import com.example.lin_new_project.databinding.FragmentViewPagerBinding;
import com.example.lin_new_project.view.viewpager.BlackView;
import com.example.lin_new_project.view.viewpager.BlueView;
import com.example.lin_new_project.view.viewpager.RedView;
import com.example.lin_new_project.view.viewpager.ViewPagerView;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class ViewPagerFragment extends BaseBindingFragment<FragmentViewPagerBinding> {
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private ArrayList<ViewPagerView> arrayList;
    private RedView redView;
    private BlueView blueView;
    private BlackView blackView;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected FragmentViewPagerBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentViewPagerBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        redView=new RedView(getContext());
        blueView=new BlueView(getContext());
        blackView=new BlackView(getContext());
        arrayList=new ArrayList<>();
        arrayList.add(redView);
        arrayList.add(blueView);
        arrayList.add(blackView);
        viewPagerAdapter=new ViewPagerAdapter(arrayList);
        tabLayout=getBinding().tabLayout;
        viewpager=getBinding().viewPager;
        tabLayout.addTab(tabLayout.newTab().setText("紅色"));
        tabLayout.addTab(tabLayout.newTab().setText("藍色"));
        tabLayout.addTab(tabLayout.newTab().setText("黑色"));
        viewpager.setAdapter(viewPagerAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setLog("onPageScrolled:"+position);
            }

            @Override
            public void onPageSelected(int position) {
                setLog("onPageSelected:"+position);
                for (int i=0;i<arrayList.size();i++){
                    if (i==position){
                        arrayList.get(i).initData();
                    }else {
                        arrayList.get(i).dismiss();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                setLog("onPageScrollStateChanged:"+state);
            }
        });
    }
}