package com.example.onlineshopp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtils {
    public static Bitmap drawableToBitmap(Context context, int drawableResId) {
        // Lấy Drawable từ resource
        Drawable drawable = context.getResources().getDrawable(drawableResId);

        // Chuyển Drawable thành Bitmap
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // Tạo Bitmap từ Drawable
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        // Kiểm tra xem mảng byte không null và có độ dài lớn hơn 0
        if (byteArray != null && byteArray.length > 0) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        return null; // Trả về null nếu mảng byte không hợp lệ
    }
}
