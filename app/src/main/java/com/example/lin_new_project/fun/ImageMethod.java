package com.example.lin_new_project.fun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageMethod {
    public static void setImageSize(final ImageView image) {
        image.post(new Runnable() {
            @Override
            public void run() {
                try {
                    BitmapDrawable bd = (BitmapDrawable) image.getBackground();
                    Bitmap bm= bd.getBitmap();
                    Log.d("ImageView大小",bm.getWidth()+","+bm.getHeight());
                    Log.d("ImageView大小",image.getWidth()+","+image.getHeight());

                    int Height=image.getWidth()*bm.getHeight()/bm.getWidth();
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(image.getLayoutParams());
                    layoutParams.width=image.getWidth();
                    layoutParams.height=Height;
                    image.setLayoutParams(layoutParams);
//                    image.setMinimumHeight(Height);
//                    image.setMaxHeight(Height);
                    Log.d("ImageView大小",Height+"");
                }catch (Exception e){

                }
            }
        });
    }
    public static BitmapDrawable getBitmapDrawable(Resources resources,Drawable icon) {
        //縮放圖片
        BitmapDrawable bd = (BitmapDrawable) icon;
        Bitmap bitmap = bd.getBitmap();
        Matrix matrix = new Matrix();

        // 縮放尺吋
        bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        BitmapDrawable dd = new BitmapDrawable(resources,bitmap);
        return dd;
    }
    public static Bitmap getBitmap(Drawable icon) {
        //縮放圖片
        BitmapDrawable bd = (BitmapDrawable) icon;
        Bitmap bitmap = bd.getBitmap();
//        Matrix matrix = new Matrix();
//        matrix.reset();
//        // 縮放尺吋
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                bitmap.getWidth(), bitmap.getHeight(),
//                matrix, true);

        return bitmap;
    }
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static Drawable getDrawableByVERSION(Context context,int id) {
        //會根據Android 版本來抓取圖片-----------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }
    public static Bitmap getBitmapm(Drawable icon) {
        //縮放圖片
        BitmapDrawable bd = (BitmapDrawable) icon;
        Bitmap bitmap = bd.getBitmap();
        Matrix matrix = new Matrix();
        // 縮放尺吋
        bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);

        return bitmap;
    }
    public static String bitmapToBase64(final Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }
        String result = null;
        ByteArrayOutputStream baos = null;
        Matrix matrix = new Matrix();
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        try {
            if (bitmap2 != null) {
                baos = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 20, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    public Bitmap getBitmap(Context context, final String filename) {
        Bitmap imagbitmap = null;
        File cameraFile = new File(filename);
        Log.d("cameraFilepath", cameraFile.getAbsolutePath());

        Uri imageUri = Uri.fromFile(cameraFile);
        try {
            Log.d("cameraFilepath", filename);
            imagbitmap = BitmapFactory.decodeStream(
                    context.getContentResolver().openInputStream(imageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imagbitmap;
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
//
//        Bitmap marker = ThumbnailUtils.extractThumbnail(bit, newWidth, newHeight);
//        Matrix matrix2 = new Matrix();
//        matrix2.reset();
//        Bitmap newbm2 = Bitmap.createBitmap(newbm, 0, 0, newbm.getWidth(), newbm.getHeight(), matrix2,
//                true);
//        Log.d("圖片大小","newbm"+newbm.getWidth()+","+newbm.getHeight());
//        Log.d("圖片大小","marker"+marker.getWidth()+","+marker.getHeight());
//        Log.d("圖片大小","newbm2"+newbm2.getWidth()+","+newbm2.getHeight());
        return newbm;
    }
    public static Bitmap reSize(Bitmap bit, int MaxPx) {
        int width = bit.getWidth(), height = bit.getHeight();
        if (width < MaxPx && height < MaxPx) {
            return bit;
            // 傳進來的圖比要的小，不處理
        }
        float ratio = 1;// 縮放比例
        if (width > height && width > MaxPx) {
            //這張圖比較寬，依寬度進行等比例縮放
            ratio = (float) MaxPx / (float) width;
        } else if (height > MaxPx) {
            //這張圖比較高，依高度進行等比例縮放
            ratio = (float) MaxPx / (float) height;
        }
        if (ratio >= 1) {
            //如果比例不需要縮小，返回原圖，如果你有放大需求，可以修改這邊
            return bit;
        } else {
            int newW = (int) (width * ratio), newH = (int) (height * ratio);
            if (newW <= 0 || newH <= 0) {
                if (width > height && width > MaxPx) {
                    ratio = (float) MaxPx / (float) width;
                } else if (height > MaxPx) {
                    ratio = (float) MaxPx / (float) height;
                }
                newW = (int) (width * ratio);
                newH = (int) (height * ratio);
            }

            Bitmap marker = ThumbnailUtils.extractThumbnail(bit, newW, newH);

            return marker;
        }
    }
    public Bitmap getBitmap_Size(Context context,String url, int size) {

        Bitmap bitmap = null;
        try {
            bitmap = reSize(getBitmap(context,url), size);
            int degree = 0;
            ExifInterface exifInterface = new ExifInterface(url);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {

        }

        return bitmap;
    }
    public static String saveBitmap(Bitmap bitmap) {
//        File mediaStorageDir = new File(Environment
//                .getExternalStorageDirectory().getAbsolutePath(),
        String filename="";
        File mediaStorageDir = new File(Environment
                .getExternalStorageDirectory(),
                "測試");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();

        }
        Log.d("dirpath", mediaStorageDir.getAbsolutePath());
        // filename = mediaStorageDir.getPath() + File.separator
        // + "OP_GET_" + getRequest_id() + ".jpg";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        filename = mediaStorageDir.getPath() + File.separator
                + sf.format(new Date()) + ".jpg";//路徑

        FileOutputStream fOut;
        try {

            fOut = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //加载图片
        BitmapFactory.decodeResource(res, resId, options);
        //计算缩放比
        options.inSampleSize = 1;
        //重新加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
