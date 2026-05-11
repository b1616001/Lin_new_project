package com.example.lin_new_project.fun;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin_new_project.R;


public class ToastMethod {

	private static Toast toastStart;

	public static void showToast(Context context, String msg, int duration) {

		TextView message =new TextView(context);
		message.setText(msg);
		message.setTextColor(Color.WHITE);
		message.setBackground(ImageMethod.getDrawableByVERSION(context, R.drawable.btn_bgd_blue));
		message.setTextSize(20);
		message.setGravity(Gravity.CENTER);
		if (toastStart != null) {
			toastStart.cancel();
		}

		toastStart = new Toast(context);
		toastStart.setDuration(duration);
		toastStart.setView(message);
		toastStart.show();
	}
	public static void showToast(Context context, String msg) {
		TextView message =new TextView(context);
		message.setText(msg);
		message.setTextColor(Color.WHITE);
		message.setBackground(ImageMethod.getDrawableByVERSION(context,R.drawable.btn_bgd_blue));
		message.setTextSize(20);
		message.setGravity(Gravity.CENTER);
		if (toastStart != null) {
			toastStart.cancel();
		}
		toastStart = new Toast(context);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(message);
		toastStart.show();
	}

}
