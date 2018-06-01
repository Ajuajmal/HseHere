package hse.here2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class rss_adapter extends ArrayAdapter<rss_item> {
    Integer c_fecha;
    Integer c_tit;
    protected Drawable d_item_sel;
    private ArrayList<rss_item> datas;
    private rss_adapter este;
    config globales;
    protected boolean modo_h = false;
    protected boolean mostrar_fechas = true;
    protected boolean mostrar_imgs = true;
    private Activity myContext;
    protected int pos_sel = -1;

    private class DownloadAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private DownloadAsyncTask() {
        }

        protected Integer doInBackground(Integer... params) {
            Integer ind = params[0];
            try {
                URL imageURL = new URL(((rss_item) rss_adapter.this.datas.get(ind.intValue())).postThumbUrl.replace(" ", "%20"));
                HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                conn.connect();
                InputStream is = conn.getInputStream();
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, options);
                is.close();
                conn.disconnect();
                if (options.outWidth == 1 && options.outHeight == 1) {
                    ((rss_item) rss_adapter.this.datas.get(ind.intValue())).postThumbUrl = null;
                    return Integer.valueOf(0);
                }
                config hse_here2_config = rss_adapter.this.globales;
                options.inSampleSize = config.calculateInSampleSize(options, 300, 300);
                HttpURLConnection conn2 = (HttpURLConnection) imageURL.openConnection();
                conn2.setDoInput(true);
                conn2.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                conn2.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                conn2.connect();
                InputStream is2 = conn2.getInputStream();
                options.inJustDecodeBounds = false;
                Bitmap bm_foto = BitmapFactory.decodeStream(is2, null, options);
                is2.close();
                if (bm_foto == null) {
                    return Integer.valueOf(-1);
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bm_foto.compress(CompressFormat.JPEG, 100, bos);
                byte[] bitmapdata = bos.toByteArray();
                try {
                    bos.close();
                } catch (IOException e) {
                }
                ((rss_item) rss_adapter.this.datas.get(ind.intValue())).postThumbBitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                return Integer.valueOf(0);
            } catch (IOException e2) {
                return Integer.valueOf(-1);
            }
        }

        protected void onPostExecute(Integer result) {
            if (result.intValue() == 0) {
                rss_adapter.this.este.notifyDataSetChanged();
            }
        }
    }

    static class ViewHolder {
        ImageView iv_item_der;
        ImageView iv_item_sel;
        TextView postDateView;
        ImageView postThumbView;
        TextView postTitleView;

        ViewHolder() {
        }
    }

    public rss_adapter(Context context, int textViewResourceId, ArrayList<rss_item> objects) {
        super(context, textViewResourceId, objects);
        this.myContext = (Activity) context;
        this.globales = (config) this.myContext.getApplicationContext();
        this.datas = objects;
        this.este = this;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = this.myContext.getLayoutInflater().inflate(C0627R.layout.rss_item, null);
            viewHolder = new ViewHolder();
            viewHolder.postThumbView = (ImageView) convertView.findViewById(C0627R.id.postThumb);
            viewHolder.postTitleView = (TextView) convertView.findViewById(C0627R.id.postTitleLabel);
            if (this.c_tit != null) {
                viewHolder.postTitleView.setTextColor(this.c_tit.intValue());
            }
            viewHolder.postDateView = (TextView) convertView.findViewById(C0627R.id.postDateLabel);
            if (this.c_fecha != null) {
                viewHolder.postDateView.setTextColor(this.c_fecha.intValue());
            }
            viewHolder.iv_item_sel = (ImageView) convertView.findViewById(C0627R.id.iv_item_sel);
            viewHolder.iv_item_der = (ImageView) convertView.findViewById(C0627R.id.iv_item_der);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!this.mostrar_imgs) {
            viewHolder.postThumbView.setVisibility(8);
        } else if (((rss_item) this.datas.get(position)).postThumbUrl == null) {
            viewHolder.postThumbView.setImageResource(17170445);
        } else if (((rss_item) this.datas.get(position)).postThumbBitmap == null) {
            viewHolder.postThumbView.setImageResource(C0627R.drawable.loader_g);
            if (!((rss_item) this.datas.get(position)).postThumbCargando) {
                ((rss_item) this.datas.get(position)).postThumbCargando = true;
                new DownloadAsyncTask().execute(new Integer[]{Integer.valueOf(position)});
            }
        } else {
            viewHolder.postThumbView.setImageBitmap(((rss_item) this.datas.get(position)).postThumbBitmap);
        }
        viewHolder.postTitleView.setText(((rss_item) this.datas.get(position)).postTitle);
        if (!this.mostrar_fechas || ((rss_item) this.datas.get(position)).postDate == null || ((rss_item) this.datas.get(position)).postDate.equals("")) {
            viewHolder.postDateView.setVisibility(8);
        } else {
            viewHolder.postDateView.setText(((rss_item) this.datas.get(position)).postDate);
        }
        if (!this.modo_h) {
            viewHolder.iv_item_der.setVisibility(8);
        } else if (position == this.pos_sel) {
            viewHolder.iv_item_sel.setImageDrawable(this.d_item_sel);
            viewHolder.iv_item_sel.setVisibility(0);
        } else {
            viewHolder.iv_item_sel.setVisibility(8);
        }
        return convertView;
    }
}
