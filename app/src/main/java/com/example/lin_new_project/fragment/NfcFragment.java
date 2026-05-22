package com.example.lin_new_project.fragment;


import static com.example.lin_new_project.MyApplication.nfc;
import static com.example.lin_new_project.fun.NFCMethod.getDec;
import static com.example.lin_new_project.fun.NFCMethod.getHex;
import static com.example.lin_new_project.fun.NFCMethod.getReversed;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.databinding.FragmentNfcBinding;
import com.example.lin_new_project.databinding.FragmentTextBinding;
import com.example.lin_new_project.fun.MsgEvent;
import com.example.lin_new_project.fun.RxBus;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class NfcFragment extends BaseBindingFragment<FragmentNfcBinding> {
    PendingIntent nfcPendingIntent;
    IntentFilter[] intentFiltersArray;
    NfcAdapter nfcAdpt;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String[][] techList = new String[][]{new String[]{
            NfcA.class.getName(), NfcB.class.getName(), NfcF.class.getName(),
            NfcV.class.getName(), IsoDep.class.getName(),
            MifareClassic.class.getName(), MifareUltralight.class.getName(),
            Ndef.class.getName()}};

    @Override
    protected FragmentNfcBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentNfcBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 獲取默認的NFC控制器
        nfcAdpt = NfcAdapter.getDefaultAdapter(getContext());
        // Check if the smartphone has NFC
        if (nfcAdpt == null) {
            showToast("不支援NFC");
            return;
        } else {
            if (!nfcAdpt.isEnabled()) {
                showToast("請開啟NFC功能");
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        }
        setIntentFiltersArray();
        initEventBus();
    }

    private void initEventBus() {
        EventBus.getDefault().register(this);
        Disposable disposable = RxBus.getInstance().toObservable(MsgEvent.class).subscribe(msg -> {
            if (msg.getType() == nfc) {
                getActivity().runOnUiThread(() -> {
                    Intent intent=(Intent) msg.getObj();
                    String action = intent.getAction();
                    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                            || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                            || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

                        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                        NdefMessage[] msgs;
                        if (rawMsgs != null) {
                            msgs = new NdefMessage[rawMsgs.length];
                            for (int i = 0; i < rawMsgs.length; i++) {
                                msgs[i] = (NdefMessage) rawMsgs[i];
                            }
                        } else {
                            // Unknown tag type
                            byte[] empty = new byte[0];
                            byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                            Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

//                byte[] payload = dumpTagData(tag).getBytes();
//                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
//                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
//                msgs = new NdefMessage[]{msg};
//
                            String nfc_id = dumpTagData(tag);
                            Log.d("nfc_id", nfc_id);
                            Log.v("NFC測試", "nfc_id：" + nfc_id);
                            getBinding().tvNfc.setText(nfc_id);
                        }
                    }

                });
                return;
            }
        });
        compositeDisposable.add(disposable);
    }
    private String dumpTagData(Parcelable p) {
        StringBuilder sb = new StringBuilder();
        Tag tag = (Tag) p;
        byte[] id = tag.getId();
        sb.append("Tag ID (hex): ").append(getHex(id)).append("\n");
        sb.append("Tag ID (dec): ").append(getDec(id)).append("\n");
        sb.append("ID (reversed): ").append(getReversed(id)).append("\n");
        Log.e("NFC測試", "Tag ID (hex):" + getHex(id) + "\r\nTag ID (dec):" + getDec(id) + "\r\nID (reversed):" + getReversed(id));

        //有必要再顯示
//        alert("刷code", sb.toString());

        String nfcReversed = String.valueOf(getReversed(id));


        return nfcReversed;

    }
    private void setIntentFiltersArray() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        Intent nfcIntent = new Intent(getContext(), getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        IntentFilter tagIntentFilter =
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{filter};
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdpt != null) {
            nfcAdpt.enableForegroundDispatch(getActivity(), nfcPendingIntent,
                    intentFiltersArray, this.techList);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdpt != null) {
            nfcAdpt.disableForegroundDispatch(getActivity());
        }
    }
//    @Override
//    public void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        Log.v("NFC測試", "進入NFC感應");
//        String action = intent.getAction();
//        Log.e("NFC測試", "NFC_action：" + action);
//        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
//                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
//                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
//
//            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//            NdefMessage[] msgs;
//            if (rawMsgs != null) {
//                msgs = new NdefMessage[rawMsgs.length];
//                for (int i = 0; i < rawMsgs.length; i++) {
//                    msgs[i] = (NdefMessage) rawMsgs[i];
//                }
//            } else {
//                // Unknown tag type
//                byte[] empty = new byte[0];
//                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
//                Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//
////                byte[] payload = dumpTagData(tag).getBytes();
////                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
////                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
////                msgs = new NdefMessage[]{msg};
////
//                String nfc_id = dumpTagData(tag);
//                Log.d("nfc_id", nfc_id);
//                Log.v("NFC測試", "nfc_id：" + nfc_id);
//                tv_nfc.setText(nfc_id);
//            }
//        }
//    }
}