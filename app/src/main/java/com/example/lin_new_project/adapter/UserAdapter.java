package com.example.lin_new_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lin_new_project.databinding.ItemhomeBinding;
import com.example.lin_new_project.databinding.ItemuserBinding;
import com.example.lin_new_project.room.entity.UserProfileEntity;
import com.google.gson.Gson;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<UserProfileEntity> array;
    private OnItemClickListener onItemClickListener;
    public UserAdapter(Context context, List<UserProfileEntity> array){
        this.context=context;
        this.array=array;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemuserBinding binding=ItemuserBinding.inflate(LayoutInflater.from(parent.getContext()),parent,
                false);
        return  new MyRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyRecyclerViewHolder holder1 = (MyRecyclerViewHolder) holder;
        int ii=position+1;
        holder1.binding.tvText.setText(ii+"."+new Gson().toJson(array.get(position)));


    }

    @Override
    public int getItemCount() {
        return array.size();
    }
    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        ItemuserBinding binding;
        MyRecyclerViewHolder(ItemuserBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
//            getBindingAdapterPosition() 得到的是元素位於當前綁定Adapter的位置，
//            而getAbsoluteAdapterPosition()方法得到的是元素位於合併後Adapter的絕對位置。
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener!=null){
                    if (getAdapterPosition()>=RecyclerView.NO_POSITION){
                        onItemClickListener.OnItemClick(array.get(getAdapterPosition()));
                    }

                }
            });
        }
    }

    public void setArray(List<UserProfileEntity> array) {
        this.array = array;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 介面中的點選每一項的實現方法，引數自己定義
         */
         void OnItemClick(UserProfileEntity userProfileEntity);
    }
}
