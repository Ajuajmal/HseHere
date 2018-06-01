package hse.here2;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class t_rss_fr extends Fragment {
    private String c1;
    Bundle extras;
    config globales;
    int ind;
    private rss_adapter itemAdapter;
    private ArrayList<rss_item> listData;
    private SharedPreferences preferences;
    private View f66v = null;

    class C08251 implements OnItemClickListener {
        C08251() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
            t_rss_fr.this.itemAdapter.pos_sel = i;
            t_rss_fr.this.itemAdapter.notifyDataSetChanged();
            ((t_rss) t_rss_fr.this.getActivity()).onTutSelected(((rss_item) t_rss_fr.this.listData.get(i)).postLink);
        }
    }

    private class RssDataController extends AsyncTask<String, Integer, ArrayList<rss_item>> {
        ProgressBar pb;

        private RssDataController() {
        }

        protected void onPreExecute() {
            if (config.esClaro("#" + t_rss_fr.this.c1)) {
                this.pb = (ProgressBar) t_rss_fr.this.getActivity().findViewById(C0627R.id.pb_rss_inv);
            } else {
                this.pb = (ProgressBar) t_rss_fr.this.getActivity().findViewById(C0627R.id.pb_rss);
            }
            if (VERSION.SDK_INT > 20) {
                config.progress_color(this.pb, t_rss_fr.this.globales.c_icos);
            }
            this.pb.setVisibility(0);
        }

        protected ArrayList<rss_item> doInBackground(String... params) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                connection.setReadTimeout(StatusCodes.AUTH_DISABLED);
                connection.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                return config.tratar_rss(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ArrayList<rss_item> result) {
            if (result != null) {
                boolean mostrar_imgs = false;
                for (int i = 0; i < result.size(); i++) {
                    t_rss_fr.this.listData.add(result.get(i));
                    if (((rss_item) result.get(i)).postThumbUrl != null) {
                        mostrar_imgs = true;
                    }
                }
                if (!(mostrar_imgs && t_rss_fr.this.globales.secciones_a[t_rss_fr.this.ind].mostrar_img)) {
                    t_rss_fr.this.itemAdapter.mostrar_imgs = false;
                }
                if (!t_rss_fr.this.globales.secciones_a[t_rss_fr.this.ind].mostrar_fecha) {
                    t_rss_fr.this.itemAdapter.mostrar_fechas = false;
                }
                t_rss_fr.this.itemAdapter.notifyDataSetChanged();
                if (!(result.size() <= 0 || ((rss_item) result.get(0)).postLink == null || ((rss_item) result.get(0)).postLink.equals(""))) {
                    Editor e = t_rss_fr.this.preferences.edit();
                    e.putString("rss_" + t_rss_fr.this.globales.secciones_a[t_rss_fr.this.ind].id + "_ultpost", ((rss_item) result.get(0)).postLink);
                    e.commit();
                }
            }
            try {
                this.pb.setVisibility(8);
            } catch (Exception e2) {
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.globales = (config) getActivity().getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.preferences = getActivity().getSharedPreferences("sh", 0);
        this.extras = getActivity().getIntent().getExtras();
        this.ind = this.extras.getInt("ind");
        this.f66v = inflater.inflate(C0627R.layout.t_rss, container, false);
        ListView listView = (ListView) this.f66v.findViewById(C0627R.id.postListView);
        listView.setCacheColorHint(0);
        this.c1 = this.globales.secciones_a[this.ind].c1;
        String c2 = this.globales.secciones_a[this.ind].c2;
        String c_tit = this.globales.secciones_a[this.ind].c_tit;
        String c_fecha = this.globales.secciones_a[this.ind].c_fecha;
        if (!(this.c1.equals("") || this.c1.equals(""))) {
            listView.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.c1), Color.parseColor("#" + c2)}));
        }
        this.listData = new ArrayList();
        this.itemAdapter = new rss_adapter(this.f66v.getContext(), C0627R.layout.rss_item, this.listData);
        if (!c_tit.equals("")) {
            this.itemAdapter.c_tit = Integer.valueOf(Color.parseColor("#" + c_tit));
        }
        if (!c_fecha.equals("")) {
            this.itemAdapter.c_fecha = Integer.valueOf(Color.parseColor("#" + c_fecha));
        }
        this.itemAdapter.d_item_sel = getResources().getDrawable(C0627R.drawable.item_sel);
        this.itemAdapter.d_item_sel.setColorFilter(Color.parseColor("#FFFFFFFF"), Mode.MULTIPLY);
        listView.setAdapter(this.itemAdapter);
        listView.setOnItemClickListener(new C08251());
        return this.f66v;
    }

    @TargetApi(13)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new RssDataController().execute(new String[]{this.globales.secciones_a[this.ind].url});
        this.itemAdapter.modo_h = false;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int w_aux;
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            w_aux = size.x;
        } else {
            w_aux = display.getWidth();
        }
        if (!((t_rss) getActivity()).abrir_fuera && w_aux > ((int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f))) {
            this.itemAdapter.modo_h = true;
        }
    }
}
