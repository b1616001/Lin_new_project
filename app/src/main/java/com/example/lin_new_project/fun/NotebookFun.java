package com.example.lin_new_project.fun;

import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class NotebookFun {
    public static String fileName = "Fiction";//存放記事本的檔案夾名稱
    public static String internalReadnnn(Activity activity, String dirName) {
        String ss = "";
        try {
            File dir = getExtermalStoragePrivateDir(activity, fileName);
            File f = new File(dir, dirName);//dirName記事本名稱
            FileInputStream inputStream = new FileInputStream(f);
            byte[] bytes = new byte[10240];
            StringBuffer sb = new StringBuffer();
            while (inputStream.read(bytes) != -1) {
                sb.append(new String(bytes));
            }
            ss = sb.toString();
            inputStream.close();
            return ss;


        } catch (Exception e) {
            e.printStackTrace();
            return "無資料";

        }
    }
    public static File extelnalPrivateCreateFoler(Activity activity, String dirName, String sb){
        File dir = getExtermalStoragePrivateDir(activity, fileName);
        File file = new File(dir, dirName);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(sb.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private static File getExtermalStoragePrivateDir(Activity activity, String fileName) {
        final File[] dirs = activity.getExternalFilesDirs(fileName);//name 資料夾名稱
        File primaryDir = null;
        if (dirs != null && dirs.length > 0) {
            primaryDir = dirs[0];
        }
        Log.d("NotebookFun","getExtermalStoragePrivateDir->"+primaryDir);
        return primaryDir;
    }
}
