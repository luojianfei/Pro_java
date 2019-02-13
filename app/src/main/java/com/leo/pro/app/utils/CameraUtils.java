package com.leo.pro.app.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

public class CameraUtils {
    //打开相册
    public static String openCamera(Activity activity,String name,int requestCode) {
        Uri imageUri;
        //创建file对象，用于存储拍照后的图片；
//        File outputImage = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
//                + "/tbidea", name);
        File outputImage = new File(FileUtils.getCameraImgProjectPath(activity), name);
        outputImage.getParentFile().mkdirs();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(activity,
                    activity.getPackageName()+".fileprovider", outputImage);
//        } else {
//            imageUri = Uri.fromFile(outputImage);
//        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent,requestCode);
        return outputImage.getAbsolutePath();
    }
    //打开相册
    public static String openCamera2(Activity activity,String name,int requestCode) {
        Uri imageUri;
        //创建file对象，用于存储拍照后的图片；
        File outputImage = new File(FileUtils.getCameraImgSDCardPath(activity), name);
//        File outputImage = new File(activity.getFilesDir().getAbsolutePath()
//                + "/tbidea_file_img", name);
        outputImage.getParentFile().mkdirs();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(activity,
                    activity.getPackageName()+".fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent,requestCode);
        return outputImage.getAbsolutePath();
    }
    public static String openCamera1(Fragment fragment, String name, int requestCode) {
        Uri imageUri;
        //创建file对象，用于存储拍照后的图片；
        File outputImage = new File(FileUtils.getCameraImgSDCardPath(fragment.getActivity()), name);
        outputImage.getParentFile().mkdirs();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(fragment.getActivity(),
                    fragment.getActivity().getPackageName()+".fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        fragment.startActivityForResult(intent,requestCode);
        return outputImage.getAbsolutePath();
    }
}
