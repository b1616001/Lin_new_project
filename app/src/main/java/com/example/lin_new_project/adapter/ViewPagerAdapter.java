package com.example.lin_new_project.adapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lin_new_project.view.viewpager.ViewPagerView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<ViewPagerView> arrayList;

    public ViewPagerAdapter(ArrayList<ViewPagerView> arrayList) {
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(arrayList.get(position).getBinding().getRoot());
        return arrayList.get(position).getBinding().getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(arrayList.get(position).getBinding().getRoot());
    }
}
