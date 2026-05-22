package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.fun.CommonUtils.getColorByVERSION;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lin_new_project.R;
import com.example.lin_new_project.adapter.RecyclerAdapter;
import com.example.lin_new_project.data.RecyclerData;
import com.example.lin_new_project.databinding.FragmentSimpleItemBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.fun.CommonUtils;
import com.example.lin_new_project.fun.RecyclerViewMethed;
import com.example.lin_new_project.fun.SimpleItemTouchHelper;
import com.example.lin_new_project.fun.SimpleItemTouchHelperCallback;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class SimpleItemFragment extends BaseBindingFragment<FragmentSimpleItemBinding> {
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected FragmentSimpleItemBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentSimpleItemBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        RecyclerViewMethed.init_VERTICAL(getActivity(), getBinding().recyclerView,
                true, 0, getColorByVERSION(R.color.white), true, true);
        List<RecyclerData> list=new ArrayList<>();
        for (int i=0;i<50;i++){
            RecyclerData recyclerData=new RecyclerData();
            recyclerData.setId(i);
            recyclerData.setName("A"+i);
            list.add(recyclerData);
        }
        recyclerAdapter=new RecyclerAdapter(getContext(),list);
        recyclerAdapter.setOnItemClickListener((data, position) -> {
            showToast(new Gson().toJson(data));
        });
        getBinding().recyclerView.setAdapter(recyclerAdapter);
        SimpleItemTouchHelper itemTouchHelper = new SimpleItemTouchHelper(new SimpleItemTouchHelperCallback(new SimpleItemTouchHelperCallback.Sthc_Movement() {
            @Override
            public void onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();

                int dataIndexFrom = fromPos;
                int dataIndexTo = toPos;

//                Log.d(TAG, "onItemMove: from: " + fromPos + ", to: " + toPos + ", size: " + routeSectionList.size());

                if (recyclerAdapter.isReverse()) {
                    int index = list.size() - 1;
                    dataIndexFrom = index - fromPos;
                    dataIndexTo = index - toPos;
//                    Log.d(TAG, "data index: from: " + dataIndexFrom + ", to: " + dataIndexTo + ", index: " + index);
                }

                ///< 禁止拖动到新增菜单的底部
                if (toPos >= (list.size()) || toPos < 0) {
                    return;
                }

                RecyclerAdapter.MyRecyclerViewHolder holder1 = (RecyclerAdapter.MyRecyclerViewHolder) viewHolder;
                RecyclerAdapter.MyRecyclerViewHolder holder2 = (RecyclerAdapter.MyRecyclerViewHolder) target;

//                holder1.binding.tvOrder.setText(String.valueOf(toPos + 1));
//                holder2.binding.tvOrder.setText(String.valueOf(fromPos + 1));
//
//                RouteSection data1 = routeSectionList.get(fromPos);
//                RouteSection data2 = routeSectionList.get(toPos);
//                int o1 = data1.getOrder();
//                int o2 = data2.getOrder();
//                data1.setOrder(o2);
//                data2.setOrder(o1);
//                routeSectionList.set(fromPos, data2);
//                routeSectionList.set(toPos, data1);

                holder1.binding.tvId.setText(String.valueOf(dataIndexTo ));
                holder2.binding.tvId.setText(String.valueOf(dataIndexFrom
                ));

                RecyclerData data1 = list.get(dataIndexFrom);
                RecyclerData data2 = list.get(dataIndexTo);
                int o1 = data1.getId();
                int o2 = data2.getId();
                data1.setId(o2);
                data2.setId(o1);
                list.set(dataIndexFrom, data2);
                list.set(dataIndexTo, data1);


//                Collections.swap(paragraphsDataList, fromPos, toPos);

                recyclerAdapter.notifyItemMoved(fromPos, toPos);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                return;
            }

            /**
             * 控制每个条目的可操作状态 - 滑动，拖拽等  对应->getMovementFlags
             * @param position
             * @return
             */
            @Override
            public int[] getFlag(int position) {
                if (position == (list.size())) {
                    return new int[]{0, 0};
                } else {
                    return new int[]{ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.UP};
                }
            }
        }));
        itemTouchHelper.attachToRecyclerView(getBinding().recyclerView);
    }
}