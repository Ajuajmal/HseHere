package com.appnext.base.p001a.p004c;

import android.content.ContentValues;
import android.database.Cursor;
import com.appnext.base.p001a.p003b.C0193d;
import com.appnext.base.p001a.p003b.C0932a;
import com.appnext.base.p001a.p004c.C0196d.C0195a;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class C0936a extends C0196d<C0932a> {
    private static final String COLUMN_PACKAGE_NAME = "package_name";
    public static final String ef = "category_table";
    private static final String eg = "category";
    private String[] eh = new String[]{COLUMN_PACKAGE_NAME, eg};

    protected /* synthetic */ C0193d mo1235b(Cursor cursor) {
        return m442a(cursor);
    }

    public static String ba() {
        return "create table category_table ( package_name text, category integer)";
    }

    public long m440a(C0932a c0932a) {
        return super.m39a(ef, m437b(c0932a));
    }

    public long m441a(JSONArray jSONArray) {
        return super.m40a(ef, jSONArray);
    }

    public void delete() {
        super.m35W(ef);
    }

    public void m438Q(String str) {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        super.m43a(ef, new String[]{COLUMN_PACKAGE_NAME}, new String[]{str}, arrayList);
    }

    public List<C0932a> bb() {
        return super.m36X(ef);
    }

    public List<C0932a> m439R(String str) {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        return super.m42a(ef, new String[]{COLUMN_PACKAGE_NAME}, new String[]{str}, null, arrayList);
    }

    private ContentValues m437b(C0932a c0932a) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PACKAGE_NAME, c0932a.getPackageName());
        contentValues.put(eg, c0932a.aO());
        return contentValues;
    }

    protected String[] bc() {
        return this.eh;
    }

    protected C0932a m442a(Cursor cursor) {
        return new C0932a(cursor.getString(cursor.getColumnIndex(COLUMN_PACKAGE_NAME)), Integer.valueOf(cursor.getInt(cursor.getColumnIndex(eg))));
    }
}
