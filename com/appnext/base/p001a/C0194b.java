package com.appnext.base.p001a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.p004c.C0936a;
import com.appnext.base.p001a.p004c.C0937b;
import com.appnext.base.p001a.p004c.C0938c;
import com.appnext.base.p001a.p004c.C0939e;

public class C0194b extends SQLiteOpenHelper {
    private static final String dH = "db459";
    private static volatile C0194b dI = null;
    private static final int dK = 6;

    public static C0194b m30i(Context context) {
        if (dI == null) {
            synchronized (C0194b.class) {
                if (dI == null) {
                    dI = new C0194b(context.getApplicationContext());
                }
            }
        }
        return dI;
    }

    private C0194b(Context context) {
        super(context, dH, null, 6);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        m31a(sQLiteDatabase);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(C0936a.ba());
            sQLiteDatabase.execSQL(C0937b.ba());
            sQLiteDatabase.execSQL(C0939e.ba());
            sQLiteDatabase.execSQL(C0938c.ba());
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    public void m31a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS category_table");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS collected_data_table");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS times_location_table");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS config_table");
            onCreate(sQLiteDatabase);
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
