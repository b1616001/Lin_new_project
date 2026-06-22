package com.example.lin_new_project.window;
import android.content.Context;
import android.graphics.PointF;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.example.lin_new_project.databinding.WindowMediaMenuBinding;
import com.example.lin_new_project.viewBinding.BaseWindow;


public class MediaMenuWindow extends BaseWindow<WindowMediaMenuBinding> {
    private  int viewHeight = 716;//490
    private final int maxWidth = 450;
    private final int minWidth = 160;
    private int viewX = 50;
    private int viewY = 120;
    private final PointF pointF = new PointF();
    private boolean isActionUp = true;
    public MediaMenuWindow(Context context) {
        super(context, 500, 1432, Gravity.END | Gravity.TOP, 50, 150);
        Looper.myQueue().addIdleHandler(() -> {
            iniView();
            return false;
        });
    }

    @Override
    protected WindowMediaMenuBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return WindowMediaMenuBinding.inflate(layoutInflater);
    }
    @Override
    public void dismiss() {
        super.dismiss();
    }
    private void iniView() {
        getBinding().tvMeum.setOnTouchListener(touchListener);
        getBinding().tvDismiss.setOnClickListener(v->dismiss());
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    isActionUp = true;
                    pointF.set(event.getX(), event.getY());
                    view.setPressed(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    viewX = viewX - movedX;
                    viewY = viewY + movedY;
                    layoutParams.x = viewX;
                    layoutParams.y = viewY;
                    mWindowManager.updateViewLayout(getBinding().getRoot(), layoutParams);
//                    update(viewX, viewY, -1, -1, true);
                    if (isRelMove(pointF, event)) {
                        isActionUp = false;
                        view.setPressed(false);
                    } else {
                        isActionUp = true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isActionUp) {
                        view.performClick();
                    }
                    view.setPressed(false);
                    //   view.performClick(); //up 時觸發click
                    break;
            }
            return true; // false 會觸發 click, true 不會
        }
    };


    /**
     * 判斷是否真的有移動
     */
    public boolean isRelMove(PointF downPointF, MotionEvent moveEvent) {
        return moveEvent.getAction() == MotionEvent.ACTION_MOVE && Math.abs(moveEvent.getX() - downPointF.x) > 0;
    }

}
