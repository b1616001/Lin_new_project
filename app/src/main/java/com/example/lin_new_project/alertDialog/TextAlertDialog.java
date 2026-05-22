package com.example.lin_new_project.alertDialog;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.lin_new_project.databinding.DialogTextBinding;
import com.example.lin_new_project.viewBinding.BaseAlertDialog;

public class TextAlertDialog extends BaseAlertDialog<DialogTextBinding> {
    private OnClickListener onClickListener;
    @Override
    protected DialogTextBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DialogTextBinding.inflate(layoutInflater);
    }
    public TextAlertDialog(Context context) {
        super(context);
        setCancelable(false);
        initView();
    }

    private void initView() {
        getBinding().tvYes.setOnClickListener(view -> {
            if (onClickListener!=null){
                onClickListener.OnClick();
            }
            dismiss();
        });
    }
    public void Show(String title,String yes){
        getBinding().tvTitle.setText(title);
        getBinding().tvYes.setText(yes);
        show();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void OnClick();
    }
}
