package com.leo.pro.app.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider

import java.io.File

/**
 * Created by HX·罗 on 2017/6/9.
 */

object UriUtils {

    /**
     * 适配Android7.0 获取uri
     * @param context
     * @param file
     * @return
     */
    fun getUri(context: Context, file: File): Uri? {
        var uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//Android7.0
            FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
        } else {
            Uri.fromFile(file)
        }
        return uri
    }

    /**
     * 根据uri获取地址
     * @param context
     * @param uri
     * @param selection
     * @return
     */
    fun getRealPath(context: Context, uri: Uri, selection: String, selectionArgs: Array<String>): String? {
        var cursor: Cursor? = null
        val column = "_data"//路径保存在downloads表中的_data字段
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null)
                cursor.close()
        }
        return null
    }

    /**
     * 根据文件转换成对应的Uri
     *
     * @param ctx
     * @param file
     * @return
     */
    fun file2Uri(ctx: Context, file: File): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(ctx, "com.tbidea.xunyitongPatientv10.fileprovider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}
