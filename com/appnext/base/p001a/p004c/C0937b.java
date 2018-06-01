package com.appnext.base.p001a.p004c;

import android.content.ContentValues;
import android.database.Cursor;
import com.appnext.base.p001a.p003b.C0193d;
import com.appnext.base.p001a.p003b.C0933b;
import com.appnext.base.p001a.p004c.C0196d.C0195a;
import com.appnext.base.p005b.C0214l;
import java.util.ArrayList;
import java.util.List;

public class C0937b extends C0196d<C0933b> {
    private static final String COLUMN_TYPE = "type";
    public static final String ef = "collected_data_table";
    private static final String ei = "collected_data";
    private static final String ej = "collected_data_date";
    private String[] eh = new String[]{"type", ei, ej};

    protected /* synthetic */ C0193d mo1235b(Cursor cursor) {
        return m450c(cursor);
    }

    public static String ba() {
        return "create table collected_data_table ( type text not null, collected_data text not null, collected_data_date integer)";
    }

    public long m447a(C0933b c0933b) {
        return super.m39a(ef, m444c(c0933b));
    }

    public void m449b(C0933b c0933b) {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        super.m43a(ef, new String[]{"type"}, new String[]{c0933b.getType()}, arrayList);
    }

    public void m445S(String str) {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        super.m43a(ef, new String[]{"type"}, new String[]{str}, arrayList);
    }

    public void delete() {
        super.m35W(ef);
    }

    public List<C0933b> bb() {
        return super.m36X(ef);
    }

    public List<C0933b> m446T(String str) {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        return super.m42a(ef, new String[]{"type"}, new String[]{str}, null, arrayList);
    }

    private ContentValues m444c(C0933b c0933b) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", c0933b.getType());
        contentValues.put(ei, c0933b.aP());
        contentValues.put(ej, Long.valueOf(c0933b.aQ().getTime()));
        return contentValues;
    }

    protected String[] bc() {
        return this.eh;
    }

    protected C0933b m450c(Cursor cursor) {
        return new C0933b(cursor.getString(cursor.getColumnIndex("type")), cursor.getString(cursor.getColumnIndex(ei)), C0214l.m112c(cursor.getLong(cursor.getColumnIndex(ej))));
    }
}
