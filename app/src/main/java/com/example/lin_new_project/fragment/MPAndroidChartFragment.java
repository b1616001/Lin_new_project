package com.example.lin_new_project.fragment;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentMpandroidChartBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.fun.PieChartMethod;
import com.example.lin_new_project.view.Radar2View;
import com.example.lin_new_project.view.RadarView;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class MPAndroidChartFragment extends BaseBindingFragment<FragmentMpandroidChartBinding> {
    private Radar2View radar2View;
    private RadarView radarView;
    private boolean booleanStackedBar=false;
    @Override
    protected FragmentMpandroidChartBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentMpandroidChartBinding.inflate(layoutInflater);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        initPieChart();
        getBinding().btnRadarChart.setOnClickListener(view -> {
            Invisble();
            getBinding().linearLayoutRadarChart.setVisibility(VISIBLE);
            getBinding().FrameLayoutR1.removeAllViews();
            getBinding().FrameLayoutR2.removeAllViews();
            getBinding().FrameLayoutR1.addView(getRadarView());
            getBinding().FrameLayoutR2.addView(getRadar2View());
        });
        getBinding().btnChart.setOnClickListener(view -> {
            Invisble();
            getBinding().linearLayoutChart.setVisibility(VISIBLE);
        });
        getBinding().btnStackedBar.setOnClickListener(view -> {
            Invisble();
            getBinding().linearLayoutStackedBar.setVisibility(VISIBLE);
            if (!booleanStackedBar){
                initStackedBar();
            }
        });
    }



    private void initPieChart() {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Integer> yValueList = new ArrayList<Integer>();
        arrayList.add("bb");
        arrayList.add("aa");
        arrayList.add("cc");
        yValueList.add(10);
        yValueList.add(20);
        yValueList.add(50);
        PieChartMethod.ini(getBinding().PieChartC1,true);//初始化PieChart 設定
        PieChartMethod.setData(getContext(),getBinding().PieChartC1,arrayList,yValueList);
    }
    private void initStackedBar() {
       getBinding().horizontalBarChart. setHighlightPerDragEnabled(true);
        getBinding().horizontalBarChart.setDrawBarShadow(false);
        getBinding().horizontalBarChart.setDrawValueAboveBar(false);
        //horizontalBarChart.setContentDescription("××表");
        getBinding().horizontalBarChart.getDescription().setEnabled(false);
        getBinding().horizontalBarChart.setNoDataText("142423");
        getBinding().horizontalBarChart.setDragEnabled(false);
        getBinding().horizontalBarChart.setPinchZoom(false); //scaling can now only be done on x- and y-axis separately
        getBinding().horizontalBarChart.setDrawGridBackground(false);
        getBinding().horizontalBarChart.getAxisRight().setEnabled(false); //不绘制右侧轴线
//        horizontalBarChart.getAxisLeft().setEnabled(false);
        getBinding().horizontalBarChart.setTouchEnabled(false); // 設定是否可以觸控
        getBinding().horizontalBarChart.setDragEnabled(false);// 是否可以拖拽
        getBinding().horizontalBarChart.setScaleEnabled(false);// 是否可以縮放
        getBinding().horizontalBarChart.setPinchZoom(false);//y軸的值是否跟隨圖表變換縮放;如果禁止，y軸的值會跟隨圖表變換縮放
        LinearLayout.LayoutParams gridview_params =
                (LinearLayout.LayoutParams)getBinding().horizontalBarChart .getLayoutParams();
        gridview_params.height=50*100;
        ArrayList<String> datamm = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datamm.add("ca" + (i + 1));
        }

        XAxis xl = getBinding().horizontalBarChart.getXAxis();
        xl.setValueFormatter(new MyXFormatter(datamm));
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setLabelRotationAngle(45);  //标签倾斜
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setCenterAxisLabels(false); //可不加这句，默认为false
        //xl.setGranularity(sCount);  //x轴坐标占的宽度
        xl.setGranularity(1f); //x轴坐标占的宽度
        //xl.setCenterAxisLabels(true);
//        xl.setAxisMinimum(10f); // 此轴显示的最小值
//        xl.setAxisMaximum(-0.3f); // 此轴显示的最大值
        xl.setLabelCount(50); //显示的坐标数量


        YAxis yl = getBinding().horizontalBarChart.getAxisLeft();
        yl.setDrawAxisLine(true);
//        yl.setAxisMaximum(0.5f);
//        yl.setAxisMaximum(1f);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); //this replaces setStartAtZero(true)
//        yl.setPosition(YAxis.Axi);
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//        float[] val = {54,24, 35, 73.6f, 94.2f, 23, 6, 86, 55, 44.4f, 77.77f, 0, 33,24, 35, 73.6f, 94.2f, 23, 6, 86, 55, 44.4f, 77.77f, 0, 33};
        float[] val = {54,24, 35, 73.6f, 94.2f, 23, 6, 86, 55, 44.4f, 77.77f, 0, 33,24, 35, 73.6f, 94.2f, 23, 6, 86, 55, 44.4f, 77.77f, 0, 33
                ,54,24, 35, 73.6f, 94.2f, 23, 6, 86, 55, 44.4f, 77.77f, 0, 33,24, 35, 73.6f, 94.2f, 23, 6, 86, 55, 44.4f, 77.77f, 0, 33};

        setData(50, val);
        getBinding().horizontalBarChart.setFitBars(false);
        getBinding().horizontalBarChart.animateXY(2000, 2000);

        Legend legend = getBinding().horizontalBarChart.getLegend();
        legend.setEnabled(false); //不显示图例

    }
    private void Invisble() {
        getBinding().linearLayoutRadarChart.setVisibility(INVISIBLE);
        getBinding().linearLayoutChart.setVisibility(INVISIBLE);
        getBinding().linearLayoutStackedBar.setVisibility(INVISIBLE);
    }

    public Radar2View getRadar2View() {
        if (radar2View==null){
            radar2View = new Radar2View(getContext());
            getBinding().FrameLayoutR2.setOnClickListener(view -> {
                Double[] percents=new Double[6];
                for (int i=0;i<6;i++){
                    percents[i]=Math.random();
                }
                radar2View.setPercents(percents);
            });
        }
        return radar2View;
    }

    public RadarView getRadarView() {
        if (radar2View==null)radarView = new RadarView(getContext());
        return radarView;
    }
    private void setData(int count, float[] val) {
        float barWidth = 0.8f; //每个彩色数据条的宽度
        float spaceForBar = 1f;//每个数据条实际占的宽度
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {

            //float val = (float) (Math.random() * range);
//            yVals1.add(new BarEntry(i * spaceForBar, val[i], getResources().getDrawable(R.drawable.ic_menu_camera)));
            yVals1.add(new BarEntry(i * spaceForBar,new float[]{val[i], 100-val[i]} ));
        }

        BarDataSet set1;

        if (getBinding().horizontalBarChart.getData() != null && getBinding().horizontalBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) getBinding().horizontalBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            getBinding().horizontalBarChart.getData().notifyDataChanged();
            getBinding(). horizontalBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "XXXX");
            set1.setColors(getColors());
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);

            data.setBarWidth(barWidth);
            getBinding().horizontalBarChart.setData(data);
            getBinding().horizontalBarChart.invalidate();
        }
    }

    private int[] getColors() {

        int stacksize =2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }


    public class MyXFormatter extends ValueFormatter {
        private List<String> mValues;

        public MyXFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value) {
            Log.d("dddd", String.valueOf(value));
            if (((int) value >= 0 && (int) (value) < mValues.size())) {
                return mValues.get((int) value);
            } else {
                return "";
            }


        }
    }
}