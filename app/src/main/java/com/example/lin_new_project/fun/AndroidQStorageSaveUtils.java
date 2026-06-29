package com.example.lin_new_project.fun;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AndroidQStorageSaveUtils {
    public static FileDescriptor getFileDescriptor(Context context, Uri uri, String mode) {
        try {
            //r（只讀）、w（只寫）、rw（讀寫）和 rwt（覆盖）
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, mode);
            FileDescriptor fileDescriptor=parcelFileDescriptor.getFileDescriptor();
            return fileDescriptor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static Boolean fileUriIsExists(Context context, Uri uri) {
        boolean isExists = false;
        try {
            //r（只讀）、w（只寫）、rw（讀寫）和 rwt（覆盖）
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            isExists = parcelFileDescriptor.getFileDescriptor().valid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExists;
    }

    public static Uri customizedFilePath(Context context, String dirName, String Folder) {
        //Pictures、DCIM都是存放圖片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "title_1");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample_" + System.currentTimeMillis() + ".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, dirName + "/" + Folder);//Folder資料夾名稱
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            return context.getContentResolver().insert(contentUri, values);
        } else {
            File mediaStorageDir = getFolder(dirName + "/" + Folder);
            String filename = mediaStorageDir.getPath() + File.separator +
                    "sample_" + System.currentTimeMillis() + ".png";//路徑
            File file = new File(filename);
            Log.d("圖片抓取", file.getAbsolutePath());
            return FileMethod.getFile_uri(context, filename);
        }


    }

    public static Uri saveNotebookText(Context context, String information, String dirName, String Folder) {
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (dirName.equals(Environment.DIRECTORY_DOWNLOADS)) {
                contentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            } else {
                contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "title_1");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample_" + System.currentTimeMillis() + ".txt");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, dirName + "/" + Folder);
            Uri uri = context.getContentResolver().insert(contentUri, values);
            try {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                outputStream.write(information.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return uri;
        } else {
            File mediaStorageDir = getFolder(dirName + "/" + Folder);
            String filename = mediaStorageDir.getPath() + File.separator +
                    "sample_" + System.currentTimeMillis() + ".txt";//路徑
            Log.d("txt路徑", filename);
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(filename);
                fOut.write(information.getBytes());
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return FileMethod.getFile_uri(context, filename);
        }


    }

    public static Uri saveNotebookInputStream(Context context, InputStream stream, String suffix, String dirName, String Folder) {
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (dirName.equals(Environment.DIRECTORY_DOWNLOADS)) {
                contentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            } else {
                contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "title_1");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample_" + System.currentTimeMillis() + "." + suffix);
            values.put(MediaStore.Images.Media.RELATIVE_PATH, dirName + "/" + Folder);
            Uri uri = context.getContentResolver().insert(contentUri, values);
            try {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                int read;
                byte[] buffer = new byte[2048];
                do {
                    read = stream.read(buffer);
                    outputStream.write(buffer, 0, read);
                } while (read != -1);

                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return uri;
        } else {
            return saveArchives_old(context, dirName + "/" + Folder, suffix, stream);

        }

    }

    private static Uri saveArchives_old(Context context, String Folder, String suffix, InputStream stream) {
        File mediaStorageDir = getFolder(Folder);
        String filename = mediaStorageDir.getPath() + File.separator +
                "sample_" + System.currentTimeMillis() + "." + suffix;//路徑
        Log.d("路徑", filename);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(filename);
            int read;
            byte[] buffer = new byte[2048];
            while ((read = stream.read(buffer)) != -1) {
                fOut.write(buffer, 0, read);
            }
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FileMethod.getFile_uri(context, filename);
    }

    public static Uri saveBitmap(Context context, Bitmap bitmap, String dirName, String Folder) {
        //Pictures、DCIM都是存放圖片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "title_1");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample_" + System.currentTimeMillis() + ".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, dirName + "/" + Folder);
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uri = context.getContentResolver().insert(contentUri, values);
            try {
                ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "w");
                FileOutputStream outputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return uri;
        } else {
            File mediaStorageDir = getFolder(dirName + "/" + Folder);
            String filename = mediaStorageDir.getPath() + File.separator +
                    "sample_" + System.currentTimeMillis() + ".png";//路徑
            Log.d("圖片路徑", filename);
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return FileMethod.getFile_uri(context, filename);
        }


    }


    public static Uri saveVideo(Context context, InputStream stream, String suffix, String dirName, String Folder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "title_1");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample_" + System.currentTimeMillis() + "." + suffix);
            values.put(MediaStore.Images.Media.MIME_TYPE, "video/" + suffix);
            values.put(MediaStore.Images.Media.RELATIVE_PATH, dirName + "/" + Folder);
            Uri contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            Uri uri = context.getContentResolver().insert(contentUri, values);
            try {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                int read;
                byte[] buffer = new byte[2048];
                while ((read = stream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return uri;
        } else {
            return saveArchives_old(context, dirName + "/" + Folder, suffix, stream);
        }

    }

    public static Uri saveAudio(Context context, InputStream stream, String suffix, String dirName, String Folder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.TITLE, "title_1");
            values.put(MediaStore.Audio.Media.DISPLAY_NAME, "sample_" + System.currentTimeMillis() + "." + suffix);
            values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/" + suffix);
            values.put(MediaStore.Audio.Media.RELATIVE_PATH, dirName + "/" + Folder);
            Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Uri uri = context.getContentResolver().insert(contentUri, values);
            try {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                int read;
                byte[] buffer = new byte[2048];
                while ((read = stream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return uri;
        } else {
            return saveArchives_old(context, dirName + "/" + Folder, suffix, stream);
        }

    }

    /**
     * 公有Uri轉路徑
     *
     * @param uri 图片的uri
     */
    public static String Uri_to_path(Context context, Uri uri) {
        String path = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            Cursor cursor = context.getContentResolver().query(uri, new String[]{
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media._ID}, null, null, null, null);
            while (cursor.moveToNext()) {
                path = cursor.getString(0);
            }
            cursor.close();
            return path;
        } else {
            return getPathFromUri(context, uri);
//            path = uri.getPath();
//            String authroity = uri.getAuthority();
//            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//            Log.d("圖片抓取N", path + "," + authroity + "," + sdPath + ",");
//            if (!path.startsWith(sdPath)) {
//                int sepIndex = path.indexOf(File.separator, 1);
//                if (sepIndex == -1) path = null;
//                else {
//                    Log.d("圖片抓取N2", path.substring(sepIndex));
//                    path = sdPath + path.substring(sepIndex);
//                }
//            }
//            return path;
        }
    }


    public static Intent doPickPhotoFromGallery(Context context) {
        try {
//            final Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/*");


//
            final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            final PackageManager  packageManager = context.getPackageManager();
            final Intent intent = new Intent(Intent.ACTION_PICK, uri);
            List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                // 如果有可用的Activity
                Intent picker = new Intent(Intent.ACTION_PICK, uri);
                // 使用Intent Chooser
                return Intent.createChooser(picker, "選取圖片");

            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 公有路徑轉Uri
     *
     * @param path 图片的path
     */
    public static Uri getImageContentUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (new File(path).exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static Bitmap getBitmap(Context context, String path) {
        try {
            int degree = 0;
            Uri uu;
            if (path.contains("http")) {
                uu = Uri.parse(path);
            } else {
                uu = FileMethod.getFile_uri(context, path);//可使用
            }
            Uri u1 = AndroidQStorageSaveUtils.getImageContentUri(context, path);//可使用
            //AndroidQStorageSaveUtils.getImageContentUri()與FileMethod.getFile_uri都可以將圖片路徑轉為Uri
            InputStream inputStream = context.getContentResolver().openInputStream(uu);//InputStream只能被讀取一次
            ExifInterface exifInterface = new ExifInterface(context.getContentResolver().openInputStream(uu));
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
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return bitmap;
        } catch (Exception e) {
            Log.d("錯誤", e.toString());
            return null;
        }

    }

    public static boolean deleteFoder(File file) {
        try {
            Log.d("刪除資料", file.toString());
            if (file.exists()) { // 判断文件是否存在
                if (file.isFile()) { // 判断是否是文件
                    file.delete(); // delete()方法 是删除的意思;
                } else if (file.isDirectory()) { // 否则如果它是一个目录
                    File[] files = file.listFiles(); // 声明目录下所有的文件 files[];
                    if (files != null) {
                        for (File value : files) { // 遍历目录下所有的文件
                            deleteFoder(value); // 把每个文件 用这个方法进行迭代
                        }
                    }
                }
                boolean isSuccess = file.delete();
                if (!isSuccess) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d("刪除資料", e.toString());
        }
        return false;
    }

    public static String getReadTxt(Context context, Uri uri) {
        String ss = "";
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            byte[] bytes = new byte[10240];
            StringBuffer sb = new StringBuffer();

            while (inputStream.read(bytes) != -1) {
                sb.append(new String(bytes));
            }
            inputStream.close();
            ss = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ss;
    }

    private static File getFolder(String Folder) {
        //android 10以下使用 判斷是否有資料夾
        File mediaStorageDir = new File(Environment
                .getExternalStorageDirectory(),
                Folder);
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        return mediaStorageDir;
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document類型的Uri，通過document id處理，內部會調用Uri.decode(docId)進行解碼
            String docId = DocumentsContract.getDocumentId(uri);
            //primary:Azbtrace.txt
            //video:A1283522
            String[] splits = docId.split(":");
            String type = null, id = null;
            if (splits.length == 2) {
                type = splits[0];
                id = splits[1];
            }
            switch (uri.getAuthority()) {
                case "com.android.externalstorage.documents":
                    if ("primary".equals(type)) {
                        path = Environment.getExternalStorageDirectory() + File.separator + id;
                    }
                    break;
                case "com.android.providers.downloads.documents":
                    if ("raw".equals(type)) {
                        path = id;
                    } else {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                                Long.parseLong(docId));
                        path = getMediaPathFromUri(context, contentUri, null, null);
                    }
                    break;
                case "com.android.providers.media.documents":
                    Uri externalUri = null;
                    switch (type) {
                        case "image":
                            externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "video":
                            externalUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "audio":
                            externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            break;
                    }
                    if (externalUri != null) {
                        String selection = "_id=?";
                        String[] selectionArgs = new String[]{id};
                        path = getMediaPathFromUri(context, externalUri, selection, selectionArgs);
                    }
                    break;
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            path = getMediaPathFromUri(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            //如果是file類型的Uri(uri.fromFile)，直接獲取圖片路徑即可
            path = uri.getPath();
        }
        //確保如果返回路徑，則路徑合法
        return path == null ? null : (new File(path).exists() ? path : null);
    }

    private static String getMediaPathFromUri(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path;
        String authroity = uri.getAuthority();
        path = uri.getPath();
        String sdPah = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (authroity.equals(context.getPackageName() + ".myfileprovider")) {
            if (!path.startsWith(sdPah)) {
                int sepIndex = path.indexOf(File.separator, 1);
                if (sepIndex == -1) path = null;
                else {
                    path = sdPah + path.substring(sepIndex);
                }
            }
            return path;
        } else {
            if (path == null || !new File(path).exists()) {
                ContentResolver resolver = context.getContentResolver();
                String[] projection = new String[]{MediaStore.MediaColumns.DATA};
                Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            int index = cursor.getColumnIndexOrThrow(projection[0]);
                            if (index != -1) path = cursor.getString(index);
                            Log.i("TAG", "getMediaPathFromUri query " + path);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            path = null;
                        } finally {
                            cursor.close();
                        }
                    }
                }
            }
            return path;
        }


    }

    public static Uri getAudioUri(Context context, String suffix, String dirName, String Folder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "title_1");
            // 設定檔案名稱
            values.put(MediaStore.Video.Media.DISPLAY_NAME, "sample_" + System.currentTimeMillis() + "." + suffix);
            // 設定檔案名稱
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/" + suffix);
            // 設定檔案類型（Android 10+ 推薦）
            values.put(MediaStore.Video.Media.IS_PENDING, 1);
            values.put(MediaStore.Video.Media.RELATIVE_PATH, dirName + "/" + Folder);
            Uri contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            Uri uri = context.getContentResolver().insert(contentUri, values);

            return uri;
        } else {
        File mediaStorageDir = getFolder(Folder);
        String filename = mediaStorageDir.getPath() + File.separator +
                "sample_" + System.currentTimeMillis() + "." + suffix;//路徑
//        File cameraFile = new File(filename);
//        Log.d("cameraFilepath", cameraFile.getAbsolutePath());
//        return cameraFile.getAbsolutePath();
        return FileMethod.getFile_uri(context, filename);
        }

    }
//    public static File UriToFile(Context context,Uri uri ) {
//        try {
//            InputStream inputStream = context.getContentResolver().openInputStream(uri);//InputStream只能被讀取一次
//            inputStream
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }

}

