package com.kumpra.christinebpolesti.kumpra;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by christine B. Polesti on 7/3/2015.
 */
public class Database extends ContentProvider {

    static final String PROVIDER_NAME = "com.pols.kumpra";
    static final String URL = "content://" + PROVIDER_NAME + "/kumpra";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String UID = "_id";
    static final String ITEM = "item";
    static final String QUANTITY = "quantity";

    private SQLiteDatabase database;
    static final String DATABASE_NAME = "kumpra_db";
    static final String TABLE_NAME = "kumpra";
    static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEM+" VARCHAR(255), "+QUANTITY+" VARCHAR(255));";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS + TABLE_NAME");
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return (database == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);

        if(sortOrder == null || sortOrder == "") {
            sortOrder = ITEM;
        }
        Cursor cursor = sqLiteQueryBuilder.query(database, projection,selection,selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = database.insert(TABLE_NAME, "", values);
        if(rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        count = database.update(TABLE_NAME, values, selection, selectionArgs);

        return 0;
    }
}
