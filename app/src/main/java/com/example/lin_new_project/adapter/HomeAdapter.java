package com.example.lin_new_project.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lin_new_project.databinding.ItemhomeBinding;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private String[] array_home;
    private OnItemClickListener onItemClickListener;
    public HomeAdapter(Context context, String[] array_home){
        this.context=context;
        this.array_home=array_home;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemhomeBinding binding=ItemhomeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,
                false);
        return  new MyRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyRecyclerViewHolder holder1 = (MyRecyclerViewHolder) holder;
        holder1.binding.tvLanguage.setText(array_home[position]);


    }

    @Override
    public int getItemCount() {
        return array_home.length;
    }
    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        ItemhomeBinding binding;
        MyRecyclerViewHolder(ItemhomeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
//            getBindingAdapterPosition() 得到的是元素位於當前綁定Adapter的位置，
//            而getAbsoluteAdapterPosition()方法得到的是元素位於合併後Adapter的絕對位置。
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener!=null){
                    if (getAdapterPosition()>=RecyclerView.NO_POSITION){
                        onItemClickListener.OnItemClick(array_home[getAdapterPosition()]);
                    }

                }
            });
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 介面中的點選每一項的實現方法，引數自己定義
         */
         void OnItemClick(String string);
    }
}
