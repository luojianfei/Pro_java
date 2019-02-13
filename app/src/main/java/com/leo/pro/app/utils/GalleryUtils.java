package com.leo.pro.app.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

/**
 * Create by zmm
 * Time 2018/5/24
 * PackageName com.tbidea.xunyitongPatientv10.Tools
 */
public class GalleryUtils {
    public static void openGallery(Activity activity,int requestCode){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        activity.startActivityForResult(intent,requestCode);//打开相册
    }
    public static void openGallery1(Fragment fragment, int requestCode){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        fragment.startActivityForResult(intent,requestCode);//打开相册
    }
    public static String getGalleryImagePath(Intent data,Activity activity){
        //判断手机版本号
        String path;
        if(Build.VERSION.SDK_INT>=19){
            //4.4及以上系统使用这个方法处理
            path=handleImageOnKitKat(data,activity);
        }else {
            //4.4以下系统使用这个方法处理
            path=handleImageBeforeKitKat(data,activity);
        }
        return path;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String handleImageOnKitKat(Intent data,Activity activity){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(activity,uri)){
            //如果是document类型的Uri，则通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];//解析出数字格式的id
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(activity,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(activity,contentUri,null);
            }
        } else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的uri，使用普通方法处理
            imagePath=getImagePath(activity,uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri，直接获取
            imagePath=uri.getPath();
        }
        return  imagePath;
    }

    public static String  handleImageBeforeKitKat(Intent data,Activity activity){
        Uri uri=data.getData();
        String imagePath=getImagePath(activity,uri,null);
        return imagePath;
    }
    public static String getImagePath(Activity activity,Uri uri,String selection){
        String path=null;
        Cursor cursor=activity.getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
