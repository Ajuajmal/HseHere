package com.appnext.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class C0268g {

    public static class C0267a {
        private final Object hC;
        private final String hD;
        private Class<?> hE;
        private List<Class<?>> hF = new ArrayList();
        private List<Object> hG = new ArrayList();
        private boolean hH;
        private boolean hI;

        public C0267a(Object obj, String str) {
            this.hC = obj;
            this.hD = str;
            this.hE = obj != null ? obj.getClass() : null;
        }

        public <T> C0267a m212a(Class<T> cls, T t) {
            this.hF.add(cls);
            this.hG.add(t);
            return this;
        }

        public C0267a ci() {
            this.hH = true;
            return this;
        }

        public C0267a m211a(Class<?> cls) {
            this.hI = true;
            this.hE = cls;
            return this;
        }

        public Object cj() throws Exception {
            Method a = C0268g.m213a(this.hE, this.hD, (Class[]) this.hF.toArray(new Class[this.hF.size()]));
            if (this.hH) {
                a.setAccessible(true);
            }
            Object[] toArray = this.hG.toArray();
            if (this.hI) {
                return a.invoke(null, toArray);
            }
            return a.invoke(this.hC, toArray);
        }
    }

    public static Method m213a(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException {
        while (cls != null) {
            try {
                return cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchMethodException();
    }

    public static boolean aA(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
