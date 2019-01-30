package com.leo.pro.app.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.leo.pro.app.CustomApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Create by zmm
 * Time 2018/11/29
 * PackageName com.tbidea.xunyitongPatientv10.Tools
 */
public class GlideUtils {
    /**
     * @param context
     * @param url
     * @param outputImage
     */
    public static void saveUrlImage(final Context context, String url, final File outputImage) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .toBytes()
                .into(new SimpleTarget<byte[]>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        // 下载成功回调函数
                        // 数据处理方法，保存bytes到文件
                        copy(context, outputImage, bytes);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // 下载失败回调
                    }
                });
    }

    /**
     * @param context
     * @param url
     * @param outputImage
     */
    public static void saveUrlImage2(final Context context, String url, final File outputImage) {
        File file = null;
        try {
            file = Glide.with(context)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        copy(file, outputImage);
    }

    /**
     * 加载图片
     *
     * @param urlName
     * @param view
     */
    public static void loadStringBitmap(String urlName, final ImageView view) {
//        Glide.with(XYTApplication.context).load(urlName == null?"":urlName)
//                .centerCrop()
////                .placeholder(defaultDrawable)
//                .crossFade()
//                .into(view);

        Glide.with(CustomApplication.mContext).load(urlName == null?"":urlName)
//                .placeholder(defaultDrawable)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(view);
    }

    /**
     * 复制文件
     *
     * @param outputImage 文件
     * @param bytes       数据
     */
    public static void copy(Context context, File outputImage, byte[] bytes) {
        try {
            //如果手机已插入sd卡,且app具有读写sd卡的权限
            outputImage.getParentFile().mkdirs();
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
            }
            FileOutputStream output = null;
            output = new FileOutputStream(outputImage);
            output.write(bytes);
//                Log.i(TAG, "copy: success，" + filename);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (!target.getParentFile().exists())
                target.getParentFile().mkdirs();
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
