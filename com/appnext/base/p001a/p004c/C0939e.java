package com.appnext.base.p001a.p004c;

import android.content.ContentValues;
import android.database.Cursor;
import com.appnext.base.p001a.p003b.C0193d;
import com.appnext.base.p001a.p003b.C0935e;
import com.appnext.base.p001a.p004c.C0196d.C0195a;
import com.appnext.base.p005b.C0214l;
import java.util.ArrayList;
import java.util.List;

public class C0939e extends C0196d<C0935e> {
    public static final String ef = "times_location_table";
    private static final String es = "latitude";
    private static final String et = "longitude";
    private static final String eu = "additional_data";
    private static final String ev = "date";
    private static final String ew = "times_type";
    private String[] eh = new String[]{es, et, eu, ev};

    private enum C0197a {
        Morning("morning"),
        AfterNoon("afternoon"),
        Night("night");
        
        private String mValue;

        private C0197a(String str) {
            this.mValue = str;
        }

        public String getValue() {
            return this.mValue;
        }
    }

    protected /* synthetic */ C0193d mo1235b(Cursor cursor) {
        return m463f(cursor);
    }

    public static String ba() {
        return "create table times_location_table ( latitude real, longitude real, additional_data text, date integer, times_type text)";
    }

    public long m458a(C0935e c0935e) {
        m457a(C0197a.Morning);
        return super.m39a(ef, m459a(c0935e, C0197a.Morning));
    }

    public long m460b(C0935e c0935e) {
        m457a(C0197a.AfterNoon);
        return super.m39a(ef, m459a(c0935e, C0197a.AfterNoon));
    }

    public long m462c(C0935e c0935e) {
        m457a(C0197a.Night);
        return super.m39a(ef, m459a(c0935e, C0197a.Night));
    }

    public void delete() {
        super.m35W(ef);
    }

    public List<C0935e> be() {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        return super.m42a(ef, new String[]{ew}, new String[]{String.valueOf(C0197a.Morning.getValue())}, m37Y(ev), arrayList);
    }

    public List<C0935e> bf() {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        return super.m42a(ef, new String[]{ew}, new String[]{String.valueOf(C0197a.AfterNoon.getValue())}, m37Y(ev), arrayList);
    }

    public List<C0935e> bg() {
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        return super.m42a(ef, new String[]{ew}, new String[]{String.valueOf(C0197a.Night.getValue())}, m37Y(ev), arrayList);
    }

    protected ContentValues m459a(C0935e c0935e, C0197a c0197a) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(es, c0935e.aX());
        contentValues.put(et, c0935e.aY());
        contentValues.put(eu, c0935e.aZ());
        contentValues.put(ev, Long.valueOf(c0935e.getDate().getTime()));
        contentValues.put(ew, c0197a.getValue());
        return contentValues;
    }

    protected String[] bc() {
        return this.eh;
    }

    protected C0935e m463f(Cursor cursor) {
        return new C0935e(Double.valueOf(cursor.getDouble(cursor.getColumnIndex(es))), Double.valueOf(cursor.getDouble(cursor.getColumnIndex(et))), cursor.getString(cursor.getColumnIndex(eu)), C0214l.m112c((long) cursor.getInt(cursor.getColumnIndex(ev))));
    }

    private void m457a(C0197a c0197a) {
        super.m46b(ef, ("date NOT IN (SELECT date FROM times_location_table WHERE times_type like '" + c0197a.getValue() + "' ORDER BY " + ev + " DESC LIMIT 1 ) ") + " AND times_type like '" + c0197a.getValue() + "'");
    }
}
