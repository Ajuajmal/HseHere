package com.appnext.core;

import com.appnext.core.C0249b.C0248a;
import java.util.ArrayList;
import java.util.Iterator;

public class C0241a {
    private ArrayList<?> f16D = null;
    private Long gD = Long.valueOf(0);
    private ArrayList<C0248a> gE = new ArrayList();
    private String placementID;
    private int state = 0;

    public void m138a(C0248a c0248a) {
        if (c0248a != null) {
            this.gE.add(c0248a);
        }
    }

    public void m137a(C0241a c0241a) {
        if (c0241a != null && c0241a.gE != null) {
            this.gE.addAll(c0241a.gE);
        }
    }

    public void m141b(C0248a c0248a) {
        if (c0248a != null) {
            this.gE.remove(c0248a);
        }
    }

    public Long bS() {
        return this.gD;
    }

    public ArrayList<?> m143g() {
        return this.f16D;
    }

    public void setPlacementID(String str) {
        this.placementID = str;
    }

    public int getState() {
        return this.state;
    }

    public String getPlacementID() {
        return this.placementID;
    }

    public void setState(int i) {
        this.state = i;
    }

    public void m139a(Long l) {
        this.gD = l;
    }

    public void m142b(ArrayList<?> arrayList) {
        m140a(arrayList, true);
    }

    public void m140a(ArrayList<?> arrayList, boolean z) {
        this.f16D = arrayList;
        if (z) {
            m139a(Long.valueOf(System.currentTimeMillis()));
        }
    }

    public synchronized void m144g(ArrayList<?> arrayList) {
        Iterator it = this.gE.iterator();
        while (it.hasNext()) {
            ((C0248a) it.next()).mo1187a(arrayList);
        }
        this.gE.clear();
    }

    public synchronized void at(String str) {
        Iterator it = this.gE.iterator();
        while (it.hasNext()) {
            ((C0248a) it.next()).error(str);
        }
        this.gE.clear();
    }
}
