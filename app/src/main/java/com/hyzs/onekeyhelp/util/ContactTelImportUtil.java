package com.hyzs.onekeyhelp.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import com.hyzs.onekeyhelp.R;

/**
 * Created by wubin on 2017/3/13.
 */

public class ContactTelImportUtil {
    /**
     * 获取联系人头像
     *
     * @param context 上下文环境
     * @param photoId 头像ID
     * @return Bitmap
     */
    public static Bitmap getPhoto(Context context, String photoId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_replace);
        if (photoId != null && "".equals(photoId)) {
            String[] projection = new String[]{ContactsContract.Data.DATA15};
            String selection = "ContactsContract.Data._ID = " + photoId;
            Cursor cur = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);
            if (cur != null) {
                cur.moveToFirst();
                byte[] contactIcon = null;
                contactIcon = cur.getBlob(cur.getColumnIndex(ContactsContract.Data.DATA15));
                if (contactIcon != null) {
                    bitmap = BitmapFactory.decodeByteArray(contactIcon, 0, contactIcon.length);
                }
            }
        }
        return bitmap;
    }
}
