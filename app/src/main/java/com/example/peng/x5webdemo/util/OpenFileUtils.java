package com.example.peng.x5webdemo.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * 用于打开下载的附件
 */
public class OpenFileUtils {

    public Intent openFile(Activity activity, String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        //获取扩展名
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1, file
                .getName().length()).toLowerCase();
        //根据扩展名调用相应的打开方法
        switch (suffix) {
            case "m4a":
            case "mp3":
            case "mid":
            case "xmf":
            case "ogg":
            case "mav":
                return getAudioFileIntent(path);
            case "3gp":
            case "mp4":
            case "avi":
                return getVedioFileIntent(path);
            case "jpg":
            case "gjf":
            case "png":
            case "jpeg":
            case "bmp":
                return getImageFileIntent(path);
            case "apk":
                return getAPKFileIntent(path);
            case "ppt":
                return getPPTFileIntent(path);
            case "xls":
                return getExcelFileIntent(path);
            case "doc":
                return getDOCFileIntent(activity, path);
            case "pdf":
                return getPDFFileIntent(path);
            case "chm":
                return getCHMFileIntent(path);
            case "txt":
                return getTXTFileIntent(path, false);
            default:
                return getDefaultIntent(activity, path);
        }

    }


    //获取一个打开audio文件的Intent
    private Intent getAudioFileIntent(String path) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //获取一个打开image文件的Intent
    private Intent getImageFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //获取一个打开apk文件的Intent
    private Intent getAPKFileIntent(String path) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.android.packageinstaller", "com.android" +
                ".packageinstaller.PackageInstallerActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("anroid.content.Intent.ACTION_VIEW");
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setComponent(comp);
        return intent;
    }

    //获取一个打开ppt文件的Intent
    private Intent getPPTFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //获取一个打开excel文件的Intent
    private Intent getExcelFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //获取一个打开doc文件的Intent
    private Intent getDOCFileIntent(Activity activity, String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(path);
            uri = FileProvider.getUriForFile(activity, "com.example.peng.x5webdemo.fileprovider",
                    file);
        } else {
            uri = Uri.fromFile(new File(path));
        }

        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //获取一个打开pdf文件的Intent
    private Intent getPDFFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    //获取一个打开chm文件的Intent
    private Intent getCHMFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //获取一个打开Text文件的Intent
    private Intent getTXTFileIntent(String path, boolean bo) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");
        if (bo) {
            Uri uri1 = Uri.parse(path);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(path));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }


    private Intent getDefaultIntent(Activity activity, String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.content.Intent.ACTION_VIEW");

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(path);
            uri = FileProvider.getUriForFile(activity, "com.example.peng.x5webdemo.fileprovider",
                    file);
        } else {
            uri = Uri.fromFile(new File(path));
        }
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    //获取一个打开Vedio文件的Intent
    private Intent getVedioFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }
}
