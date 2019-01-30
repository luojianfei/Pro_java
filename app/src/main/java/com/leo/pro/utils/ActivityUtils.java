package com.leo.pro.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.leo.pro.app.Constant;


/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class ActivityUtils {

    private static Intent intent ;
    private static Intent getIntent(){
        if (intent == null){
            intent = new Intent() ;
        }
        return  intent ;
    }

    /**
     * 获取最上层活动的activity
     */
    public static String getFontActiviy(Context context){
        ActivityManager mAm = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String activity_name = mAm.getRunningTasks(1).get(0).topActivity.getClassName();
        return activity_name ;
    }

    /**
     * 打开浏览器
     * @param context
     * @param url
     */
    public static void startOpenBrowse(Context context,String url){
        if(!TextUtil.isEmpty(url)){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            context.startActivity(intent);
        }
    }

    /**
     * activity页面跳转
     * @param context
     * @param clazz
     */
    public static void startActivityIntent(Context context,Class clazz){
        Activity activity = (Activity) context ;
        Intent intent = getIntent() ;
        intent.setClass(context,clazz) ;
        context.startActivity(intent);
    }/**
     * activity页面跳转
     * @param context
     * @param clazz
     * @param bundle
     */
    public static void startActivityIntent(Context context, Class clazz, Bundle bundle){
        Intent intent = getIntent() ;
        intent.setClass(context,clazz) ;
        if(bundle != null){
            intent.putExtras(bundle) ;
        }
        context.startActivity(intent);
    }

    /**
     * activity页面跳转返回
     * @param requestCode
     */

    public static void startActivityForResult(Context context, Intent intent, int requestCode) {
        Activity activity = (Activity) context ;
        activity.startActivityForResult(intent,Activity.RESULT_FIRST_USER  );
    }
    public static void startActivityForResult1(Context context, Intent intent, int requestCode) {
        Activity activity = (Activity) context ;
        activity.startActivityForResult(intent,requestCode  );
    }
    public static void startActivityForResultFragment(Fragment fragment, Intent intent,int requestCode) {
        fragment.startActivityForResult(intent,requestCode);
    }
    public static void startActivityForResultFragment2(android.support.v4.app.Fragment fragment, Intent intent, int requestCode) {
        fragment.startActivityForResult(intent,requestCode);
    }

    /**
     * 通过通讯录选择联系人
     * @param activity
     */
    public static void startActivityForContacts(Activity activity){
//        Intent intent = null ;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setClassName("com.android.contacts", "com.android.contacts.activities.ContactSelectionActivity");
//        } else {
//            intent = new Intent(Intent.ACTION_PICK,
//                    ContactsContract.Contacts.CONTENT_URI);
//        }
//        activity.startActivityForResult(intent, ConstantUtil.REQUEST_CODE_GETCONTACTS);
        // 跳转到联系人界面
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        activity.startActivityForResult(intent, Constant.REQUEST_CODE_GETCONTACTS);
    }

}
