package hse.here2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class bd_provider extends ContentProvider {
    private static final String AUTHORITY = "hse.here2.bd_provider";
    public static final int CATS = 103;
    private static final String CATS_BASE_PATH = "cats";
    public static final int CATS_ID = 113;
    public static final String CATS_ITEM_TYPE = "vnd.android.cursor.item/mt-cat";
    public static final String CATS_TYPE = "vnd.android.cursor.dir/mt-cat";
    public static final Uri CATS_URI = Uri.parse("content://hse.here2.bd_provider/cats");
    public static final Uri CONTENT_URI = Uri.parse("content://hse.here2.bd_provider/tutorials");
    public static final int FOTOS = 102;
    private static final String FOTOS_BASE_PATH = "fotos";
    public static final int FOTOS_ID = 112;
    public static final String FOTOS_ITEM_TYPE = "vnd.android.cursor.item/mt-foto";
    public static final String FOTOS_TYPE = "vnd.android.cursor.dir/mt-foto";
    public static final Uri FOTOS_URI = Uri.parse("content://hse.here2.bd_provider/fotos");
    public static final int PRODUCTOS = 101;
    private static final String PRODUCTOS_BASE_PATH = "productos";
    public static final int PRODUCTOS_ID = 111;
    public static final String PRODUCTOS_ITEM_TYPE = "vnd.android.cursor.item/mt-producto";
    public static final String PRODUCTOS_TYPE = "vnd.android.cursor.dir/mt-producto";
    public static final Uri PRODUCTOS_URI = Uri.parse("content://hse.here2.bd_provider/productos");
    public static final int TUTORIALS = 100;
    private static final String TUTORIALS_BASE_PATH = "tutorials";
    public static final int TUTORIAL_ID = 110;
    private static final UriMatcher sURIMatcher = new UriMatcher(-1);
    private bd mDB;

    static {
        sURIMatcher.addURI(AUTHORITY, TUTORIALS_BASE_PATH, 100);
        sURIMatcher.addURI(AUTHORITY, "tutorials/#", TUTORIAL_ID);
        sURIMatcher.addURI(AUTHORITY, PRODUCTOS_BASE_PATH, 101);
        sURIMatcher.addURI(AUTHORITY, "productos/#", PRODUCTOS_ID);
        sURIMatcher.addURI(AUTHORITY, FOTOS_BASE_PATH, 102);
        sURIMatcher.addURI(AUTHORITY, "fotos/#", FOTOS_ID);
    }

    public boolean onCreate() {
        this.mDB = new bd(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        String tabla = "";
        if (uriType == PRODUCTOS_ID || uriType == 101) {
            tabla = PRODUCTOS_BASE_PATH;
        } else if (uriType == FOTOS_ID || uriType == 102) {
            tabla = FOTOS_BASE_PATH;
        } else if (uriType == CATS_ID || uriType == 103) {
            tabla = CATS_BASE_PATH;
        }
        queryBuilder.setTables(tabla);
        switch (uriType) {
            case 101:
            case 102:
            case 103:
                break;
            case PRODUCTOS_ID /*111*/:
            case FOTOS_ID /*112*/:
            case CATS_ID /*113*/:
                queryBuilder.appendWhere("_id=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(this.mDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsAffected;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = this.mDB.getWritableDatabase();
        String id;
        switch (uriType) {
            case 101:
                rowsAffected = sqlDB.delete(PRODUCTOS_BASE_PATH, selection, selectionArgs);
                break;
            case 102:
                rowsAffected = sqlDB.delete(FOTOS_BASE_PATH, selection, selectionArgs);
                break;
            case 103:
                rowsAffected = sqlDB.delete(CATS_BASE_PATH, selection, selectionArgs);
                break;
            case PRODUCTOS_ID /*111*/:
                id = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(PRODUCTOS_BASE_PATH, selection + " and id=" + id, selectionArgs);
                    break;
                }
                rowsAffected = sqlDB.delete(PRODUCTOS_BASE_PATH, "_id=" + id, null);
                break;
            case FOTOS_ID /*112*/:
                id = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(FOTOS_BASE_PATH, selection + " and id=" + id, selectionArgs);
                    break;
                }
                rowsAffected = sqlDB.delete(FOTOS_BASE_PATH, "_id=" + id, null);
                break;
            case CATS_ID /*113*/:
                id = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(CATS_BASE_PATH, selection + " and id=" + id, selectionArgs);
                    break;
                }
                rowsAffected = sqlDB.delete(CATS_BASE_PATH, "_id=" + id, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case 101:
                return PRODUCTOS_TYPE;
            case 102:
                return FOTOS_TYPE;
            case 103:
                return CATS_TYPE;
            case PRODUCTOS_ID /*111*/:
                return PRODUCTOS_ITEM_TYPE;
            case FOTOS_ID /*112*/:
                return FOTOS_ITEM_TYPE;
            case CATS_ID /*113*/:
                return CATS_ITEM_TYPE;
            default:
                return null;
        }
    }

    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        String tabla = "";
        if (uriType == 101) {
            tabla = PRODUCTOS_BASE_PATH;
        } else if (uriType == 102) {
            tabla = FOTOS_BASE_PATH;
        } else if (uriType == 103) {
            tabla = CATS_BASE_PATH;
        } else {
            throw new IllegalArgumentException("Invalid URI for insert");
        }
        try {
            long newID = this.mDB.getWritableDatabase().insertOrThrow(tabla, null, values);
            if (newID > 0) {
                Uri newUri = ContentUris.withAppendedId(uri, newID);
                getContext().getContentResolver().notifyChange(uri, null);
                return newUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        } catch (SQLiteConstraintException e) {
            return null;
        }
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsAffected;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = this.mDB.getWritableDatabase();
        String tabla = "";
        if (uriType == PRODUCTOS_ID || uriType == 101) {
            tabla = PRODUCTOS_BASE_PATH;
        } else if (uriType == FOTOS_ID || uriType == 102) {
            tabla = FOTOS_BASE_PATH;
        } else if (uriType == CATS_ID || uriType == 103) {
            tabla = CATS_BASE_PATH;
        }
        switch (uriType) {
            case 101:
            case 102:
            case 103:
                rowsAffected = sqlDB.update(tabla, values, selection, selectionArgs);
                break;
            case PRODUCTOS_ID /*111*/:
            case FOTOS_ID /*112*/:
            case CATS_ID /*113*/:
                StringBuilder modSelection = new StringBuilder("_id=" + uri.getLastPathSegment());
                if (!TextUtils.isEmpty(selection)) {
                    modSelection.append(" AND " + selection);
                }
                rowsAffected = sqlDB.update(tabla, values, modSelection.toString(), null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
}
