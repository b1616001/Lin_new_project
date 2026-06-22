package com.example.lin_new_project.fragment;


import static android.content.Context.AUDIO_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Service;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.lin_new_project.databinding.FragmentVolumeBinding;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class VolumetFragment extends BaseBindingFragment<FragmentVolumeBinding> {
    private Vibrator myVibrator;
    private AudioManager mAudioManager;

    @Override
    protected FragmentVolumeBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentVolumeBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myVibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
        mAudioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        //詳請http://blog.csdn.net/leirenorlei/article/details/7842045
        initView();
    }

    private void initView() {
        getBinding().btnMusicMax.setOnClickListener(view -> mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND));
        getBinding().btnMusicAdjust.setOnClickListener(view -> mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI));
        getBinding().btnRingMax.setOnClickListener(view -> mAudioManager.setStreamVolume(AudioManager.STREAM_RING, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND));
        getBinding().btnRingAdjust.setOnClickListener(view -> mAudioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI));
        getBinding().btnAlarmMax.setOnClickListener(view -> mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, 7, AudioManager.FLAG_SHOW_UI));
        getBinding().btnAlarmAdjust.setOnClickListener(view -> mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI));
        getBinding().btnVibrate.setOnClickListener(view ->myVibrator.vibrate(3000));
    }
}