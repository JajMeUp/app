package com.wakemeup.ektoplasma.valou.wakemeup.utilities;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

public class BitmapUtils {

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, data);
        return Base64.encodeToString(data.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap stringToBitmap(String string) {
        try {
            byte [] data = Base64.decode(string, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static Bitmap getBitmapFromURI(Context ctx, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = ctx.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap picture = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return picture;
    }
}
