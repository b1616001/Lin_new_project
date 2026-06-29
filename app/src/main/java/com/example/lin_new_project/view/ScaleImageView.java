package com.example.lin_new_project.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


@SuppressLint("AppCompatCustomView")
public class ScaleImageView extends ImageView {
    //初始狀態的Matrix
    private Matrix mMatrix = new Matrix();
    //進行變動狀況下的Matrix
    private Matrix mChangeMatrix = new Matrix();
    //圖片的Bitmap
    private Bitmap mBitmap = null;
    //手機畫面尺寸資訊
    private DisplayMetrics mDisplayMetrics;
    //設定縮放最小比例
    private float mMinScale = 1.0f;
    //設定縮放最大比例
    private float mMaxScale = 5.0f;
    //圖片狀態 - 初始狀態
    private static final int STATE_NONE = 0;
    //圖片狀態 - 拖動狀態
    private static final int STATE_DRAG = 1;
    //圖片狀態 - 縮放狀態
    private static final int STATE_ZOOM = 2;
    //當下的狀態
    private int mState = STATE_NONE;
    //第一點按下的座標
    private PointF mFirstPointF = new PointF();
    //第二點按下的座標
    private PointF mSecondPointF = new PointF();
    //兩點距離
    private float mDistance = 1f;
    //圖片中心座標
    private float mCenterX, mCenterY;
    private Context context;
    private int width, height;//View 的長寬
    int left;
    int top;
    int right;
    int bottom;

    public ScaleImageView(Context context) {
        super(context);
        this.context = context;

    }

    //圖片縮放層級設定
    private void Scale() {
        //取得圖片縮放的層級
        float level[] = new float[9];
        mMatrix.getValues(level);

        //狀態為縮放時進入
        if (mState == STATE_ZOOM) {
            if (level[0] < mMinScale) {
                Log.d("縮放狀態", "小於");
                //若層級小於1則縮放至原始大小
//                mMatrix.setScale(mMinScale, mMinScale);
//                mMatrix.postTranslate(mCenterX, mCenterY);
            } else {
                Log.d("縮放狀態", "於於於於");
            }

            //若縮放層級大於最大層級則顯示最大層級
            if (level[0] > mMaxScale) {
//                mMatrix.set(mChangeMatrix);
                Log.d("縮放狀態", "大於");
            }
        }
    }

    //兩點距離
    private float Spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }

    //兩點中心
    private void MidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    //圖片縮放設定
    public void build_image() {
        //取得Context
        Context mContext = getContext();
        //取得手機畫面尺寸資訊
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();

        this.setBackgroundColor(Color.BLACK);
        //設置縮放的型態
        this.setScaleType(ScaleType.MATRIX);
        //將Bitmap帶入
        this.setImageBitmap(mBitmap);
        mCenterX = (float) ((width / 2) - (mBitmap.getWidth() / 2));
        mCenterY = (float) ((height / 2) - (mBitmap.getHeight() / 2));

        //將圖片放置畫面中央
        Log.d("資料HH", width + "," + mBitmap.getWidth());
        Log.d("資料HH", height + "," + mBitmap.getHeight());
        Log.d("資料", mCenterX + "");
        Log.d("資料", mCenterY + "");
        mMatrix.postTranslate(mCenterX, mCenterY);
//this.post(new Runnable() {
//    @Override
//    public void run() {
//        Log.d("資料長寬",getWidth()+","+getHeight());
//
//    }
//});


        //將mMatrix帶入
        this.setImageMatrix(mMatrix);

        //設置Touch觸發的Listener動作
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //多點觸碰偵測
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    //第一點按下進入
                    case MotionEvent.ACTION_DOWN:
                        mChangeMatrix.set(mMatrix);
                        mFirstPointF.set(event.getX(), event.getY());
                        mState = STATE_DRAG;
                        break;

                    //第二點按下進入
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mDistance = Spacing(event);
                        //只要兩點距離大於10就判定為多點觸碰
                        if (Spacing(event) > 10f) {
                            mChangeMatrix.set(mMatrix);
                            MidPoint(mSecondPointF, event);
                            mState = STATE_ZOOM;
                        }
                        break;

                    //離開觸碰
                    case MotionEvent.ACTION_UP:
                        break;

                    //離開觸碰，狀態恢復
                    case MotionEvent.ACTION_POINTER_UP:
                        mState = STATE_NONE;
                        break;

                    //滑動過程進入
                    case MotionEvent.ACTION_MOVE:
                        if (mState == STATE_DRAG) {
                            mMatrix.set(mChangeMatrix);
                            mMatrix.postTranslate(event.getX() - mFirstPointF.x, event.getY() - mFirstPointF.y);
                        } else if (mState == STATE_ZOOM) {
                            float NewDistance = Spacing(event);
                            if (NewDistance > 10f) {
                                mMatrix.set(mChangeMatrix);
                                float NewScale = NewDistance / mDistance;
                                mMatrix.postScale(NewScale, NewScale, mSecondPointF.x, mSecondPointF.y);
                            }
                        }
                        break;
                }

                //將mMatrix滑動縮放控制帶入
                ScaleImageView.this.setImageMatrix(mMatrix);
                //縮放設定
                Scale();

                return true;
            }
        });
    }

    public void setData(int width, int height, int left, int top, int right, int bottom) {
        //取得圖片Bitmap
        this.width = width;
        this.height = height;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (mBitmapDrawable != null) {
            if (width > height) {
                mBitmap = enlarge(mBitmapDrawable.getBitmap(), height, false);
            } else {
                mBitmap = enlarge(mBitmapDrawable.getBitmap(), width, true);
            }
//            mBitmap = mBitmapDrawable.getBitmap();
            build_image();
        }
    }

    public int dip2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int dpFromPx(final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public int pxFromDp(final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
    public static Bitmap enlarge(Bitmap bit, int MaxPx,boolean bb) {
        int width = bit.getWidth(), height = bit.getHeight();
        Log.d("圖片大小",width+","+height+","+MaxPx);
        // 设置想要的大小
        int newWidth = 0;
        int newHeight = 0;
        float ratio = 1;// 縮放比例
        if (bb){
            ratio = (float) MaxPx / (float) width;
            newWidth=MaxPx;
            newHeight= (int) (ratio*height);
            Log.d("圖片大小","寬"+newWidth+","+newHeight+","+ratio);
        }else {
            ratio = (float) MaxPx / (float) height;
            newHeight=MaxPx;
            newWidth= (int) (ratio*width);
            Log.d("圖片大小","高"+newWidth+","+newHeight+","+ratio);
        }

        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bit, 0, 0, width, height, matrix,
                true);

        return newbm;
    }
}
