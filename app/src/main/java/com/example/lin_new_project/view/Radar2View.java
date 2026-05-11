package com.example.lin_new_project.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.lin_new_project.R;


public class Radar2View extends View {
    private int count = 6; //几边形
    private int layerCount = 5; //层数
    private float angle; //每条边对应的圆心角
    private int centerX; //圆心x
    private int centerY; //圆心y
    private float radius; //半径
    private Paint polygonPaint; //边框paint
    private Paint linePaint; //连线paint
    private Paint txtPaint; //文字paint
    private Paint circlePaint; //圆点paint
    private Paint regionColorPaint; //覆盖区域paint
    private Double[] percents = {0.8, 0.4, 0.12, 0.4, 0.8, 0.99} ; //覆盖区域百分比
    private String[] titles = {"擊殺", "生存", "支援","經濟", "殺怪", "輸出"};//文字



    public Radar2View(Context context) {
        super(context);
        //计算圆心角
        angle = (float) (Math.PI * 2 / count);

        polygonPaint = new Paint();
        polygonPaint.setColor(ContextCompat.getColor(context, R.color.black));
        polygonPaint.setAntiAlias(true);
        polygonPaint.setStyle(Paint.Style.STROKE);
        polygonPaint.setStrokeWidth(0.3f);

        linePaint = new Paint();
        linePaint.setColor(ContextCompat.getColor(context, R.color.black));
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(0.3f);

        txtPaint = new Paint();
        txtPaint.setColor(ContextCompat.getColor(context, R.color.black));
        txtPaint.setAntiAlias(true);
        txtPaint.setStyle(Paint.Style.FILL);
        txtPaint.setTextSize(40f);

        circlePaint = new Paint();
        circlePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        circlePaint.setAntiAlias(true);

        regionColorPaint = new Paint();
        regionColorPaint.setColor(ContextCompat.getColor(context, R.color.color1));
        regionColorPaint.setStyle(Paint.Style.FILL);
        regionColorPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRegion(canvas);
        drawLines(canvas);
//        drawText(canvas);
        drawPolygon(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //在控件大小發生改變時調用。所以這裡初始化會被調用一次
        super.onSizeChanged(w, h, oldw, oldh);//此視圖的當前寬度,此視圖的當前高度,此視圖的舊寬度,此視圖的舊高度
        radius = Math.min(h, w) / 2 * 0.7f;//Math.min(h, w) 返回兩這值中的較小者
        centerX = w / 2;
        centerY = h / 2;
    }
    public void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / layerCount;//半徑/層級數
        for (int i = 1; i <= layerCount; i++) {
            float curR = r * i; //当前所在层的半径
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    //每一层第一个点坐标
                    path.moveTo(centerX, centerY - curR);//起點座標
                } else {
                    //顺时针记录其余顶角的点坐标
                    float x = (float) (centerX + Math.sin(angle * j) * curR);
                    float y = (float) (centerY - Math.cos(angle * j) * curR);
                    path.lineTo(x, y);//從最後一個點到指定點（x，y）添加一條線。
                }
            }
            //最外层的顶角外面的五个小圆点(图中红色部分)
            if (i == layerCount) {
//                for (int j = 0; j < count; j++) {
//                    float x = (float) (centerX + Math.sin(angle * j) * (curR + 12));
//                    float y = (float) (centerY - Math.cos(angle * j) * (curR + 12));
//                    canvas.drawCircle(x, y, 4, circlePaint);
//                }
                float ff=r*(i+0.3f);
                canvas.drawCircle(centerX, centerY, ff, polygonPaint);

            }
            path.close();
            canvas.drawPath(path, polygonPaint);
        }
    }

    private void drawLines(Canvas canvas) {
        float r = radius / layerCount;
        for (int i = 0; i < count; i++) {
            //起始坐标 从中心开始的话 startx=centerX , startY=centerY
            float startX = (float) (centerX + Math.sin(angle * i) * r);
            float startY = (float) (centerY - Math.cos(angle * i) * r);
            //末端坐标
            float endX = (float) (centerX + Math.sin(angle * i) * radius*1.15f);
            float endY = (float) (centerY - Math.cos(angle * i) * radius*1.15f);
            Log.d("座標:","endX:"+endX+"\nendY"+endY);
            if (i==0){
                txtPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(titles[i], endX, endY*0.9f, txtPaint);
            } else if (i==1){
                txtPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(titles[i], endX*1.01f, endY, txtPaint);
            }else if (i==2){
                txtPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(titles[i], endX*1.01f, endY*1.05f , txtPaint);
            }else if (i==3){
                txtPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(titles[i], endX, endY*1.05f, txtPaint);
            }else if (i==4){
                txtPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(titles[i], endX*0.99f, endY*1.05f , txtPaint);
            }else if (i==5){
                txtPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(titles[i], endX*0.99f, endY, txtPaint);
            }


            canvas.drawLine(centerX, centerY, endX, endY, linePaint);
        }
    }
    //设置几边形，**注意：设置几边形需要重新计算圆心角**
    public void setCount(int count){
        this.count = count;
        angle = (float) (Math.PI * 2 / count);
        invalidate();
    }

    //设置层数
    public void setLayerCount(int layerCount){
        this.layerCount = layerCount;
        invalidate();
    }
    public void setPercents(Double[] percents){
        this.percents=percents;
        invalidate();
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            //获取到雷达图最外边的坐标
            float x = (float) (centerX + Math.sin(angle * i) * (radius + 12)*1.1f);
            float y = (float) (centerY - Math.cos(angle * i) * (radius + 12)*1.1f);
            float ff=angle * i;
            Log.d("顯示文字","x:"+x+"\ny:"+y+"\nff"+ff);
            if (angle * i == 0) {
                //第一个文字位于顶角正上方
                txtPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(titles[i], x, y - 18, txtPaint);
                txtPaint.setTextAlign(Paint.Align.LEFT);
                Log.d("顯示文字0",titles[i]);
            } else if (angle * i > 0 && angle * i < Math.PI / 2) {
                //微调
                canvas.drawText(titles[i], x + 18, y + 10, txtPaint);
                Log.d("顯示文字1",titles[i]);

            } else if (angle * i >= Math.PI / 2 && angle * i < Math.PI) {
                //最右下的文字获取到文字的长、宽，按文字长度百分比向左移
                String txt = titles[i];
                Rect bounds = new Rect();
                txtPaint.getTextBounds(txt, 0, txt.length(), bounds);
                float height = bounds.bottom - bounds.top;
                float width = txtPaint.measureText(txt);
                canvas.drawText(txt, x - width * 0.4f, y + height + 18, txtPaint);
                Log.d("顯示文字2",titles[i]);

            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {
                //同理最左下的文字获取到文字的长、宽，按文字长度百分比向左移
                String txt = titles[i];
                Rect bounds = new Rect();
                txtPaint.getTextBounds(txt, 0, txt.length(), bounds);
                float width = txtPaint.measureText(txt);
                float height = bounds.bottom - bounds.top;
                canvas.drawText(txt, x - width * 0.6f, y + height + 18, txtPaint);
                Log.d("顯示文字3",titles[i]);

            } else if (angle * i >= 3 * Math.PI / 2 && angle * i < 2 * Math.PI) {
                //文字向左移动
                String txt = titles[i];
                float width = txtPaint.measureText(txt);
                canvas.drawText(txt, x - width - 18, y + 10, txtPaint);
                Log.d("顯示文字4",titles[i]);

            }

        }
    }
    private void drawRegion(Canvas canvas) {
        if (percents==null){
            return;
        }
        Path path = new Path();
        float r = radius / layerCount;//每层的间距
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                path.moveTo(centerX, (float) (centerY - r - (radius - r) * percents[i]));
            } else {
                float x = (float) (centerX + Math.sin(angle * i) * (percents[i] * (radius - r) + r));
                float y = (float) (centerY - Math.cos(angle * i) * (percents[i] * (radius - r) + r));
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, regionColorPaint);
    }




}
