package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.fun.AndroidQStorageSaveUtils.Uri_to_path;
import static com.example.lin_new_project.fun.AndroidQStorageSaveUtils.customizedFilePath;
import static com.example.lin_new_project.fun.AndroidQStorageSaveUtils.getFileDescriptor;
import static com.example.lin_new_project.fun.AndroidQStorageSaveUtils.getPathFromUri;
import static com.example.lin_new_project.fun.CommonUtils.getDrawableByVERSION;
import static com.example.lin_new_project.fun.FileMethod.getFile_uri;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.R;
import com.example.lin_new_project.databinding.FragmentRecordingBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.fun.AndroidQStorageSaveUtils;
import com.example.lin_new_project.fun.ToastMethod;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class RecordingFragment extends BaseBindingFragment<FragmentRecordingBinding> {
    int int_h = 0, int_m = 0, int_s = 0;
    Timer timer;
    public TimerTask tTask;
    private MediaRecorder recorder;
    boolean flagstrat = true;
    private Uri uri;

    @Override
    protected FragmentRecordingBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentRecordingBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        getBinding().checkbox.setOnClickListener(v -> {
            if (getBinding().checkbox.isChecked()) {
                getBinding().checkbox.setBackground(getDrawableByVERSION(R.drawable.icon_green));
                getBinding().tvTime.setVisibility(View.VISIBLE);
                getBinding().tvTime.setText("00:00:00");
                int_h = 0;
                int_m = 0;
                int_s = 0;
                timer = new Timer();
                tTask = getTimerTask();
                timer.schedule(tTask, 0, 1000);//每一秒執行一次，零秒後開始執行
                setMediaRecorder();


            } else {
                if (recorder != null) {
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                }
                getBinding().checkbox.setBackground(getDrawableByVERSION(R.drawable.icon_red));
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (tTask != null) {
                    tTask.cancel();
                    tTask = null;
                }
                getBinding().tvTime.setVisibility(View.GONE);
            }
        });
        getBinding().btnPlay.setOnClickListener(view -> {

            if (uri == null) {
                ToastMethod.showToast(getContext(), "無錄音");
                return;
            }
            try {
                MediaPlayer m = new MediaPlayer();
                m.setDataSource(getContext(), uri);
                m.prepare();
                m.start();
            } catch (Exception e) {

            }
        });
    }

    private void setMediaRecorder() {
        recorder = new MediaRecorder();// new出MediaRecorder物件
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 設定MediaRecorder的音訊源為麥克風
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // 設定MediaRecorder錄製的音訊格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        uri = AndroidQStorageSaveUtils.getAudioUri(getContext(),
                "3gp", Environment.DIRECTORY_MOVIES, "Lin");
//       String fileName = AndroidQStorageSaveUtils.Uri_to_path(getContext(), uri);//公有路徑轉換
        try {
            String fileName = uri.getPath();//公有路徑轉換
            FileDescriptor fileDescriptor = getFileDescriptor(getContext(), uri,"w");
            if (fileDescriptor == null) return;
            recorder.setOutputFile(fileDescriptor);
            setLog("錄音路徑:" + fileName);

            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            setLog("錄音異常:" + e);
        }
    }

    /// storage/emulated/0/Music/Lin/sample_1778203604610.mp3
    public TimerTask getTimerTask() {
        return new TimerTask() {
            public void run() {
                //每秒要執行的程式
                if (flagstrat) {
                    Message message = new Message();
                    if (int_s < 60) {
                        int_s++;
                    } else {
                        int_s = 0;
                        int_m++;
                        if (int_m == 60) {
                            int_m = 0;
                            int_h++;
                        }
                    }
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }

        };
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String sec = "";
                    String min = "";
                    String hour = "";
                    if (int_s < 10) {
                        sec = "0" + int_s;//秒數小於10補個零
                    } else {
                        sec = String.valueOf(int_s);
                    }
                    if (int_m < 10) {
                        min = "0" + String.valueOf(int_m);//分數小於10補個零
                    } else {
                        min = String.valueOf(int_m);//
                    }
                    if (int_h < 10) {
                        hour = "0" + int_h;//時數小於10補個零
                    } else {
                        hour = String.valueOf(int_h);//用textview顯示時數
                    }
                    getBinding().tvTime.setText(hour + ":" + min + ":" + sec);
                    break;
                default:
            }
        }
    };

    private String customizedFilePath() {
        File mediaStorageDir = new File(Environment
                .getExternalStorageDirectory(),
                "Recording");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = mediaStorageDir.getPath() + File.separator
                + sf.format(new Date()) + ".3gp";
        File cameraFile = new File(filename);
        Log.d("cameraFilepath", cameraFile.getAbsolutePath());
        return cameraFile.getAbsolutePath();
    }
}