package com.example.onlineshopp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Database.Datahelps;

public class Content extends ContentProvider {
    private static final String AUTHORITY = "com.example.myapplication.Content";
    private static final String PATH = "products";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

    private Datahelps database;
    public Content() {
        // Có thể để trống hoặc thực hiện một số khởi tạo nếu cần
    }

    @Override
    public boolean onCreate() {
        database = new Datahelps(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(Datahelps.TABLE_NAME, strings, s,
                strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = database.getWritableDatabase();
        long id = db.insert(Datahelps.TABLE_NAME, null, contentValues);
        Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
        getContext().getContentResolver().notifyChange(returnUri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsDeleted = db.delete(Datahelps.TABLE_NAME, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsUpdated = db.update(Datahelps.TABLE_NAME, contentValues, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
