package com.appnext.base.p001a.p004c;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.p002a.C0191a;
import com.appnext.base.p001a.p002a.C0191a.C0190a;
import com.appnext.base.p001a.p003b.C0193d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class C0196d<MODEL extends C0193d> {
    private static final int eq = -1;
    private static final String er = " DESC";

    protected enum C0195a {
        Equals(" = ? "),
        GreaterThan(" >= ? "),
        LessThan(" <= ? ");
        
        private String mOperator;

        private C0195a(String str) {
            this.mOperator = str;
        }

        public String bd() {
            return this.mOperator;
        }
    }

    protected abstract MODEL mo1235b(Cursor cursor);

    protected abstract String[] bc();

    protected long m39a(String str, ContentValues contentValues) {
        try {
            long insert = C0191a.aL().aM().insert(str, null, contentValues);
            C0191a.aL().aN();
            return insert;
        } catch (SQLiteFullException e) {
            C0191a.aL().m29a(C0190a.DatabaseOrDiskFull, new Exception(e.getMessage()));
            return -1;
        } catch (Throwable th) {
            C0191a.aL().m29a(C0190a.Global, th);
            return -1;
        }
    }

    protected long m38a(SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues) {
        long j = -1;
        try {
            j = sQLiteDatabase.insertWithOnConflict(str, null, contentValues, 5);
        } catch (SQLiteFullException e) {
            C0191a.aL().m29a(C0190a.DatabaseOrDiskFull, new Exception(e.getMessage()));
        } catch (Throwable th) {
            C0191a.aL().m29a(C0190a.Global, th);
        }
        return j;
    }

    private ContentValues m33b(JSONObject jSONObject) {
        try {
            ContentValues contentValues = new ContentValues();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                contentValues.put(str, jSONObject.getString(str));
            }
            return contentValues;
        } catch (Throwable th) {
            C0216b.m114a(th);
            return null;
        }
    }

    protected long m41a(String str, JSONObject jSONObject) {
        try {
            long insert = C0191a.aL().aM().insert(str, null, m33b(jSONObject));
            C0191a.aL().aN();
            return insert;
        } catch (SQLiteFullException e) {
            C0191a.aL().m29a(C0190a.DatabaseOrDiskFull, new Exception(e.getMessage()));
            return -1;
        } catch (Throwable th) {
            C0191a.aL().m29a(C0190a.Global, th);
            return -1;
        }
    }

    protected long m44b(String str, JSONObject jSONObject) {
        try {
            long insertWithOnConflict = C0191a.aL().aM().insertWithOnConflict(str, null, m33b(jSONObject), 5);
            C0191a.aL().aN();
            return insertWithOnConflict;
        } catch (SQLiteFullException e) {
            C0191a.aL().m29a(C0190a.DatabaseOrDiskFull, new Exception(e.getMessage()));
            return -1;
        } catch (Throwable th) {
            C0191a.aL().m29a(C0190a.Global, th);
            return -1;
        }
    }

    protected long m40a(String str, JSONArray jSONArray) {
        long j = -1;
        if (jSONArray != null) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                int length = jSONArray.length();
                sQLiteDatabase = C0191a.aL().aM();
                sQLiteDatabase.beginTransaction();
                int i = 0;
                while (i < length) {
                    i++;
                    j = m38a(sQLiteDatabase, str, m33b(jSONArray.getJSONObject(i)));
                }
                sQLiteDatabase.setTransactionSuccessful();
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.endTransaction();
                    C0191a.aL().aN();
                }
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.endTransaction();
                    C0191a.aL().aN();
                }
            }
        }
        return j;
    }

    protected void m35W(String str) {
        m43a(str, null, null, null);
    }

    protected void m43a(String str, String[] strArr, String[] strArr2, List<C0195a> list) {
        try {
            SQLiteDatabase aM = C0191a.aL().aM();
            String str2 = null;
            if (strArr != null) {
                str2 = m32a(strArr, (List) list);
            }
            aM.delete(str, str2, strArr2);
            C0191a.aL().aN();
        } catch (SQLiteFullException e) {
            C0191a.aL().m29a(C0190a.DatabaseOrDiskFull, new Exception(e.getMessage()));
        } catch (Throwable th) {
            C0191a.aL().m29a(C0190a.Global, new Exception(th.getMessage()));
        }
    }

    protected void m46b(String str, String str2) {
        try {
            C0191a.aL().aM().delete(str, str2, null);
            C0191a.aL().aN();
        } catch (SQLiteFullException e) {
            C0191a.aL().m29a(C0190a.DatabaseOrDiskFull, new Exception(e.getMessage()));
        } catch (Throwable th) {
            C0191a.aL().m29a(C0190a.Global, new Exception(th.getMessage()));
        }
    }

    protected List<MODEL> m36X(String str) {
        try {
            List<MODEL> e = m34e(C0191a.aL().aM().query(str, bc(), null, null, null, null, null));
            C0191a.aL().aN();
            return e;
        } catch (Throwable th) {
            C0216b.m114a(th);
            return null;
        }
    }

    protected List<MODEL> m42a(String str, String[] strArr, String[] strArr2, String str2, List<C0195a> list) {
        try {
            List<MODEL> e = m34e(C0191a.aL().aM().query(str, bc(), m32a(strArr, (List) list), strArr2, null, null, str2));
            C0191a.aL().aN();
            return e;
        } catch (Throwable th) {
            C0216b.m114a(th);
            return null;
        }
    }

    protected String m37Y(String str) {
        return str + er;
    }

    private List<MODEL> m34e(Cursor cursor) {
        List<MODEL> arrayList = new ArrayList();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(mo1235b(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    private String m32a(String[] strArr, List<C0195a> list) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                stringBuilder.append(strArr[i]);
                stringBuilder.append(((C0195a) list.get(i)).bd());
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
        return stringBuilder.toString();
    }
}
