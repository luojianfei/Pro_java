package com.leo.pro.app.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.leo.pro.app.net.RequestModelConfig.CHECK_VERSION_NAME;

/**
 * 创建人 LEO
 * 创建时间 2019/2/13 10:29
 */

public class FileUtils {

    /**
     * 获取保存版本信息文件路径
     * @param context
     * @return
     */
    public static String getSaveVersionDirPath(Context context){
        String dirPath = context.getFilesDir()+"/version" ;
        File dirFile = new File(dirPath) ;
        if(!dirFile.exists())
            dirFile.mkdirs() ;
        return dirPath ;
    }
    /**
     * 获取保存apk文件路径
     * @param context
     * @return
     */
    public static String getSaveApkDirPath(Context context){
        String dirPath = context.getFilesDir()+"/apk" ;
        File dirFile = new File(dirPath) ;
        if(!dirFile.exists())
            dirFile.mkdirs() ;
        return dirPath ;
    }

    /**
     * 获取相机拍照保存sd卡路径
     * @return
     */
    public static String getCameraImgSDCardPath(Context context){
        if(SDCardUtils.isSDCardEnable()){
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/"+AppUtils.getAppName(context) ;
        }
        return "" ;
    }
    /**
     * 获取相机拍照保存项目中路径
     * @return
     */
    public static String getCameraImgProjectPath(Context context){
        return context.getFilesDir().getAbsolutePath()
                + "/img" ;
    }

    /**
     * 获取版本文件地址
     * @param context
     * @return
     */
    public static String getSaveVersionFilePath(Context context){
        return getSaveVersionDirPath(context)+File.separator+CHECK_VERSION_NAME ;
    }

    /**
     * 读取文件内容
     * @param file
     * @return
     */
    public static String readFile(File file){
        String result = "" ;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            result = new String(bos.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result ;
    }

}
