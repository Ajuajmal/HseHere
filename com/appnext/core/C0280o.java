package com.appnext.core;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class C0280o {
    protected static final String hW = "https://appnext.hs.llnwd.net/tools/sdk/config";
    protected HashMap<String, String> hX = null;
    private ArrayList<C0278a> hY;
    private int state = 0;

    public interface C0278a {
        void mo1197b(HashMap<String, String> hashMap);

        void error(String str);
    }

    private class C0279b extends AsyncTask<Object, Void, String> {
        final /* synthetic */ C0280o hZ;

        private C0279b(C0280o c0280o) {
            this.hZ = c0280o;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m229b(objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            aD((String) obj);
        }

        protected String m229b(Object... objArr) {
            try {
                return C0266f.m196a((String) objArr[0], (HashMap) objArr[1]);
            } catch (HttpRetryException e) {
                return "error: " + e.getReason();
            } catch (IOException e2) {
                return "error: network problem";
            }
        }

        protected void aD(String str) {
            super.onPostExecute(str);
            if (str == null) {
                this.hZ.state = 0;
                this.hZ.aC("unknown error");
            } else if (str.startsWith("error:")) {
                this.hZ.state = 0;
                this.hZ.aC(str.substring("error: ".length()));
            } else {
                try {
                    this.hZ.hX = this.hZ.m236j(str);
                    this.hZ.state = 2;
                    this.hZ.m233d(this.hZ.hX);
                } catch (Throwable th) {
                    C0266f.m194P(str);
                    C0266f.m194P("error " + th.getMessage());
                    this.hZ.state = 0;
                    this.hZ.aC("parsing error");
                }
                C0266f.m194P("finished loading config");
            }
        }
    }

    protected abstract String getUrl();

    protected abstract HashMap<String, String> mo1214s();

    protected abstract HashMap<String, String> mo1215t();

    public synchronized void m234a(C0278a c0278a) {
        if (this.hY == null) {
            this.hY = new ArrayList();
        }
        if (this.state != 2) {
            if (this.state == 0) {
                this.state = 1;
                C0266f.m194P("start loading config from " + getUrl());
                new C0279b().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{getUrl(), mo1214s()});
            }
            if (c0278a != null) {
                this.hY.add(c0278a);
            }
        } else if (c0278a != null) {
            c0278a.mo1197b(this.hX);
        }
    }

    public void m235c(HashMap<String, String> hashMap) {
        this.hX = hashMap;
    }

    public HashMap<String, String> cm() {
        return this.hX;
    }

    public boolean isLoaded() {
        return this.state == 2;
    }

    public String getValue(String str) {
        if (isLoaded() && cm().containsKey(str)) {
            return (String) cm().get(str);
        }
        return null;
    }

    public String get(String str) {
        if (isLoaded()) {
            if (mo1215t().containsKey(str)) {
                return get(str, (String) mo1215t().get(str));
            }
            return getValue(str);
        } else if (mo1215t().containsKey(str)) {
            return (String) mo1215t().get(str);
        } else {
            return null;
        }
    }

    public String get(String str, String str2) {
        return getValue(str) == null ? str2 : getValue(str);
    }

    protected HashMap<String, String> m236j(String str) throws JSONException {
        HashMap<String, String> hashMap = new HashMap();
        JSONObject jSONObject = new JSONObject(str);
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            String string = jSONObject.getString(str2);
            try {
                JSONObject jSONObject2 = new JSONObject(string);
                Iterator keys2 = jSONObject2.keys();
                while (keys2.hasNext()) {
                    String str3 = (String) keys2.next();
                    hashMap.put(str2 + "_" + str3, jSONObject2.getString(str3));
                }
            } catch (Throwable th) {
                hashMap.put(str2, string);
            }
        }
        return hashMap;
    }

    private void aC(String str) {
        synchronized ("https://appnext.hs.llnwd.net/tools/sdk/config") {
            Iterator it = this.hY.iterator();
            while (it.hasNext()) {
                ((C0278a) it.next()).error(str);
            }
            this.hY.clear();
        }
    }

    private void m233d(HashMap<String, String> hashMap) {
        synchronized ("https://appnext.hs.llnwd.net/tools/sdk/config") {
            Iterator it = this.hY.iterator();
            while (it.hasNext()) {
                ((C0278a) it.next()).mo1197b(hashMap);
            }
            this.hY.clear();
        }
    }
}
