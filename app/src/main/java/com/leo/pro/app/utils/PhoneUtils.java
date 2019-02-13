package com.leo.pro.app.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.leo.pro.app.entity.ContactInfo;

/**
 * Created by HX·罗 on 2017/10/23.
 */

public class PhoneUtils {
    /**
     * 根据intent 获取电话本信息
     *
     * @param context
     * @param intent
     * @return
     */
    public static ContactInfo getPhoneNumber(Context context, Intent intent) {
        Cursor cursor = null;
        Cursor phone = null;
        String contactName ="";
        String phoneNum ="";
        ContactInfo contactInfo = new ContactInfo();
        try {
//            String[] projections = {ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER};
            String[] projections = {"display_name", "data1"};
            cursor = context.getContentResolver().query(intent.getData(), projections, null, null, null);
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            cursor.close();
            //  把电话号码中的  -  符号 替换成空格
            if (phoneNum != null) {
                phoneNum = phoneNum.replaceAll("-", " ");
                // 空格去掉  为什么不直接-替换成"" 因为测试的时候发现还是会有空格 只能这么处理
                phoneNum = phoneNum.replaceAll(" ", "");
            }
            contactInfo.setName(contactName);
            contactInfo.setPhoneNo(phoneNum);
//            cursor = context.getContentResolver().query(intent.getData(), projections, null, null, null);
//            if ((cursor == null) || (!cursor.moveToFirst())) {
//                return null;
//            }
//            int _id = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
//            String id = cursor.getString(_id);
//            int has_phone_number = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER);
//            int hasPhoneNumber = cursor.getInt(has_phone_number);
//            if (hasPhoneNumber > 0) {
//                phone = context.getContentResolver().query(
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                        null,
//                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
//                                + id, null, null);
//                while (phone.moveToNext()) {
//                    int numberIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                    int nameIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//                    String number = phone.getString(numberIndex);
//                    String name = phone.getString(nameIndex);
//                    contactInfo.setName(name);
//                    contactInfo.setPhoneNo(number);
//                }
//            }
            return contactInfo;
        } catch (Exception e) {

        } finally {
            if (cursor != null) cursor.close();
            if (phone != null) phone.close();
        }
        return null;
    }
}
