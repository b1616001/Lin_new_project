package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.fun.CommonUtils.checkStr;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.databinding.FragmentTtsBinding;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;

import java.util.Locale;


public class TtsFragment extends BaseBindingFragment<FragmentTtsBinding> implements TextToSpeech.OnInitListener{
    private TextToSpeech mTTS;
    @Override
    protected FragmentTtsBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentTtsBinding.inflate(layoutInflater);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTTS = new TextToSpeech(getContext(),this);
        initView();
    }

    private void initView() {
        getBinding().btnTts.setOnClickListener(view -> {
            if (checkStr(getBinding().editTts.getText().toString())){
                speak(getBinding().editTts.getText().toString().trim());
            }else {
                speak("輸入框空白   哈哈哈 ");
            }
        });
    }

    @Override
    public void onInit(int i) {
        Locale locale = Locale.TRADITIONAL_CHINESE;
        if (mTTS.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE
                ||mTTS.isLanguageAvailable(locale) == TextToSpeech.LANG_COUNTRY_AVAILABLE
                ||mTTS.isLanguageAvailable(locale) == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE){
            setLog("初始化"+mTTS.isLanguageAvailable(locale)+"成功");
            mTTS.setLanguage(locale);
        }else {
            setLog("初始化"+mTTS.isLanguageAvailable(locale)+"失敗");
        }

    }
    public boolean speak( String text){
        setLog("speak text:"+text);
        // 設定音調，值越大聲音越尖（女生），值越小則變成男聲,1.0是常規
        mTTS.setPitch(1.0f);
        // 設定語速
        mTTS.setSpeechRate(1.0f);
        //播放語音
        int ret = mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        if(ret == 0) {
            Toast.makeText(getActivity(), "文字轉語音成功...", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(getActivity(), "文字轉語音錯誤...", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        /* 釋放TextToSpeech的資源 */
        mTTS.shutdown();

        super.onDestroy();
    }
}