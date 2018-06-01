package hse.here2;

import android.graphics.Bitmap;

public class claseChat {
    protected Bitmap b1;
    protected String c1;
    protected String c2;
    protected boolean cargada_1;
    protected boolean coments;
    protected int fotos_chat;
    protected int fotos_perfil;
    protected boolean galeria;
    protected int id;
    protected int idapp;
    protected int idtema;
    protected int nfoto1;
    protected int nusus;
    protected int p_descr;
    protected int p_dist;
    protected int p_fnac;
    protected int p_sexo;
    protected boolean privados;
    protected String titulo;

    public claseChat(int idapp, int id, int idtema, String titulo, String c1, String c2, int fotos_perfil, int p_fnac, int p_sexo, int p_descr, int p_dist, int fotos_chat, boolean privados, boolean coments, boolean galeria, int nusus, Bitmap b1, int nfoto1, boolean cargada) {
        this.idapp = idapp;
        this.id = id;
        this.idtema = idtema;
        this.titulo = titulo;
        this.c1 = c1;
        this.c2 = c2;
        this.fotos_perfil = fotos_perfil;
        this.p_fnac = p_fnac;
        this.p_sexo = p_sexo;
        this.p_descr = p_descr;
        this.p_dist = p_dist;
        this.fotos_chat = fotos_chat;
        this.privados = privados;
        this.coments = coments;
        this.galeria = galeria;
        this.nusus = nusus;
        this.b1 = b1;
        this.nfoto1 = nfoto1;
        this.cargada_1 = cargada;
    }
}
