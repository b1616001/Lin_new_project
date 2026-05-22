package com.example.lin_new_project.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lin_new_project.data.RecyclerData;
import com.example.lin_new_project.databinding.ItemRacyerBinding;
import com.example.lin_new_project.fun.DragInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DragInterface {
    private static final String TAG = "RecyclerViewAdapter";
    private List<RecyclerData> list;
    private OnItemClickListener onItemClickListener;
    private Context context;

    private boolean reverse = false;
    public RecyclerAdapter(Context context,List<RecyclerData> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRacyerBinding binding = ItemRacyerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        RecyclerView.ViewHolder viewHolder = new MyRecyclerViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyRecyclerViewHolder holder1 = (MyRecyclerViewHolder) holder;

        RecyclerData data = list.get(position);

        if (reverse) {
            holder1.binding.tvId.setText(String.valueOf(list.size() - position));

        } else {
            holder1.binding.tvId.setText(String.valueOf(position ));
        }

        holder1.binding.tvName.setText(data.getName());

        }


    @Override
    public int getItemCount() {
        return list.size() ;
    }



    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isReverse() {
        return reverse;
    }



    public void setList(List<RecyclerData> list) {
        this.list = list;
        if (reverse) {
            this.list = new ArrayList<>(list);
            Collections.sort(this.list, Collections.reverseOrder(Comparator.comparingInt(section -> section.getId())));
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(this.list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
       public ItemRacyerBinding binding;
        MyRecyclerViewHolder(ItemRacyerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                //此處回傳點選監聽事件
                if (onItemClickListener != null) {
                    if (getAdapterPosition() > RecyclerView.NO_POSITION) {
//                        onItemClickListener.OnItemClick(routeSectionList.get(getAdapterPosition()),getAdapterPosition());

                        if (reverse) {
                            onItemClickListener.OnItemClick(list.get(getAdapterPosition()), list.size() - getAdapterPosition() - 1);
                        } else {
                            onItemClickListener.OnItemClick(list.get(getAdapterPosition()),getAdapterPosition());
                        }
                    }
                }
            });
        }
    }


    public interface OnItemClickListener {
        /**
         * 介面中的點選每一項的實現方法，引數自己定義
         *
         * @param data 點選的item的資料
         */
        void OnItemClick(RecyclerData data, int position);
    }
}
