package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.MainActivity.mainActivity;
import static com.example.lin_new_project.fun.CommonUtils.getColorByVERSION;
import static com.example.lin_new_project.webService.WebApiParameter.getApi_Uid_Parameter;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.R;
import com.example.lin_new_project.adapter.HomeAdapter;
import com.example.lin_new_project.databinding.FragmentHomeBinding;
import com.example.lin_new_project.fun.GPSPositioningMethod;
import com.example.lin_new_project.fun.RecyclerViewMethed;
import com.example.lin_new_project.fun.TimeMethod;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;
import com.example.lin_new_project.webService.IWebApiPath;
import com.example.lin_new_project.webService.WebApi;
import com.example.lin_new_project.webService.data.UidData;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class HomeFragment extends BaseBindingFragment<FragmentHomeBinding> {
    public String[] array_home;
    private HomeAdapter homeAdapter;

    @Override
    protected FragmentHomeBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentHomeBinding.inflate(layoutInflater);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        array_home = getResources().getStringArray(R.array.home_array);
        initView();
    }

    private void initView() {
        RecyclerViewMethed.init_VERTICAL(getActivity(), getBinding().recyclerView,
                true, 0, getColorByVERSION(R.color.white), true, true);
        homeAdapter = new HomeAdapter(getContext(), array_home);
        homeAdapter.setOnItemClickListener(string -> {
            int index = string.indexOf(".");
            if (index == -1) return;
            String p = string.substring(0, index);
            switch (p){
                case "1":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToRoomFragment(), 1);
                    break;
                case "2":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToLanguageFragment(), 2);
                    break;
                case "3":
                    getGPS();
                    break;
                case "4":
                    getApiUid();
                    break;
                case "5":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToTtsFragment(), 5);
                    break;
                case "6":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToSignatureFragment(), 6);
                    break;
                case "7":
                    setTime();
                    break;
                case "8":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToTimeFragment(), 8);
                    break;
                case "9":
                    arrayData();
                    break;
                case "10":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToMPAndroidChartFragment(), 10);
                    break;
                case "11":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToViewPagerFragment(), 11);
                    break;
                case "12":
                    mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToRecordingFragment(), 12);
                    break;
            }

        });
        getBinding().recyclerView.setAdapter(homeAdapter);
//        getBinding().imageView.setOnClickListener(view ->
//                mainActivity.pageChangeNavController(HomeFragmentDirections.actionHomeFragmentToTextFragment(),5)
//        );
    }



    private void getGPS() {
        GPSPositioningMethod gpsPositioning = new GPSPositioningMethod((LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE), mainActivity);
        gpsPositioning.startGPS();
        String wgs_x = "0", wgs_y = "0";
        if (gpsPositioning.getMyLongitude() != 0.0 || gpsPositioning.getMyLatitude() != 0.0) {
            wgs_x = String.valueOf(gpsPositioning.getMyLongitude());
            wgs_y = String.valueOf(gpsPositioning.getMyLatitude());

            showToast(wgs_x + "," + wgs_y);
        } else {
            showToast("定位失敗");
        }
    }
    private void getApiUid() {
        String time = getTime();
        String time2 = Integer.toString(Integer.parseInt(time) * 2895);
        String CHKTOKEN = time2.substring(time2.length() - 5);
        Map<String, Object> param=getApi_Uid_Parameter(CHKTOKEN);
        WebApi.request(WebApi.createApi(IWebApiPath.class).apiUid(param),
                new WebApi.IResponseListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        final int searchLoc = data.indexOf("{");
                        final int searchLoc2 = data.lastIndexOf("}");//此字元串中最後一次出現處的索引
                        String dd=data.substring(searchLoc, searchLoc2 + 1);
                        UidData uidData=  new Gson().fromJson(dd, UidData.class);
                        Log.d("WebApi", "onSuccess:" + new Gson().toJson(uidData));
                        showToast("WebApi->onSuccess:"+ new Gson().toJson(uidData));

                    }

                    @Override
                    public void onFail() {
                        Log.d("WebApi", "onFail" );
                        showToast("WebApi->onFail");
                    }
                });
    }

    private void showToast(String txt) {
        Toast.makeText(mainActivity, txt, Toast.LENGTH_LONG).show();
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        String ss = formatter.format(curDate);
        return ss;
    }
    private void arrayData() {
        String[] Array_A = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"
                , "A9", "A10", "A11", "A12", "A13", "A14", "A15"};
        String[] Array_B = {"B1", "B2", "B3", "B4", "B5"};
        ArrayList arrayList = new ArrayList();
        int aa = 0;
        boolean bb = false;
        for (int i = 0; i < Array_A.length; i++) {
            if (i > 0 && i % 3 == 0) {
                arrayList.add(Array_B[aa]);
                aa++;
            }
            arrayList.add(Array_A[i]);
            if (i == Array_A.length - 1) {
                if (Array_A.length % 3 == 0) {
                    bb = true;
                }
            }
        }
        if (bb) {
            arrayList.add(Array_B[aa++]);
        }


        String[] Array_C = (String[]) arrayList.toArray(new String[0]);
        setLog("混合陣列"+ arrayList.toString());
    }
    private void setTime() {
        try {
            Date dd = TimeMethod.stringToDate("2026-5-22 09:08:25", "yyyy-MM-dd HH:mm");
            setLog("時間格式轉換->dd:"+ dd);
            setLog("時間格式轉換->"+ TimeMethod.dateToString(dd, "yyyy-MM-dd HH:mm"));
            String Str = "2026-5-22 00:00";
            String Str2 = "2026-5-22 23:59";
            while (!Str.equals(Str2)) {
                setLog("時間格式轉換前"+ Str );
                Str = TimeMethod.addDayMINUTE(Str, 1, "yyyy-MM-dd HH:mm");
                setLog("時間格式轉換後"+ Str );
            }
        } catch (Exception e) {

        }
    }
}