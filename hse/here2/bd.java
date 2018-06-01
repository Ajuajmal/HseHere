package hse.here2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bd extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_CATS = "CREATE TABLE cats(_id integer PRIMARY KEY,idcat integer,descr text NOT NULL);";
    private static final String CREATE_TABLE_FOTOS = "CREATE TABLE fotos(_id integer PRIMARY KEY,idprod integer NOT NULL,url text NOT NULL,img_p blob,img blob,orden integer NOT NULL);";
    private static final String CREATE_TABLE_PRODUCTOS = "CREATE TABLE productos (_id integer UNIQUE,orden integer PRIMARY KEY AUTOINCREMENT,ref text NOT NULL,titulo text NOT NULL,descr text NOT NULL,idcat integer NOT NULL,precio real,precio_descr text NOT NULL,anyo integer NOT NULL,mes integer NOT NULL,dia integer NOT NULL,url_detalle NOT NULL,url_compra NOT NULL,img1_p blob,img1 blob,img1_id integer NOT NULL,img1_url text NOT NULL,det_cargado integer NOT NULL);";
    public static final String DB_NAME = "ac_data";
    private static final int DB_VERSION = 24;

    public bd(Context context) {
        super(context, DB_NAME, null, 24);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCTOS);
        db.execSQL(CREATE_TABLE_FOTOS);
        db.execSQL(CREATE_TABLE_CATS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS fotos");
        db.execSQL("DROP TABLE IF EXISTS cats");
        onCreate(db);
    }
}
