package com.appnext.base.p001a.p004c;

import android.database.Cursor;
import android.text.TextUtils;
import com.appnext.base.p001a.p003b.C0193d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p001a.p004c.C0196d.C0195a;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class C0938c extends C0196d<C0934c> {
    private static final String COLUMN_STATUS = "status";
    private static final String f59Z = "data";
    public static final String ef = "config_table";
    public static final String ek = "key";
    private static final String el = "sample";
    private static final String em = "sample_type";
    private static final String en = "cycle";
    private static final String eo = "cycle_type";
    private static final String ep = "exact";
    private String[] eh = new String[]{ek, "status", el, em, en, eo, ep, "data"};

    protected /* synthetic */ C0193d mo1235b(Cursor cursor) {
        return m456d(cursor);
    }

    public static String ba() {
        return "create table config_table ( key text primary key, status text not null default 'off', sample text not null default '1', sample_type text not null default '',cycle text not null default '1', cycle_type text not null default 'once', exact text not null default 'false', data text not null default '')";
    }

    public long m453a(JSONArray jSONArray) {
        return super.m40a(ef, jSONArray);
    }

    public long m454a(JSONObject jSONObject) {
        return super.m44b(ef, jSONObject);
    }

    public void delete() {
        super.m35W(ef);
    }

    public void m451U(String str) {
        if (!TextUtils.isEmpty(str)) {
            List arrayList = new ArrayList();
            arrayList.add(C0195a.Equals);
            super.m43a(ef, new String[]{ek}, new String[]{str}, arrayList);
        }
    }

    public List<C0934c> bb() {
        return super.m36X(ef);
    }

    public C0934c m452V(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        List arrayList = new ArrayList();
        arrayList.add(C0195a.Equals);
        List a = super.m42a(ef, new String[]{ek}, new String[]{str}, null, arrayList);
        if (a == null || a.size() <= 0) {
            return null;
        }
        return (C0934c) a.get(0);
    }

    protected String[] bc() {
        return this.eh;
    }

    protected C0934c m456d(Cursor cursor) {
        return new C0934c(cursor.getString(cursor.getColumnIndex("status")), cursor.getString(cursor.getColumnIndex(el)), cursor.getString(cursor.getColumnIndex(em)), cursor.getString(cursor.getColumnIndex(en)), cursor.getString(cursor.getColumnIndex(eo)), Boolean.valueOf(cursor.getString(cursor.getColumnIndex(ep))).booleanValue(), cursor.getString(cursor.getColumnIndex(ek)), cursor.getString(cursor.getColumnIndex("data")));
    }
}
