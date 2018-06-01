package hse.here2;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class cats extends ExpandableListActivity {
    private String[][] children;
    config globales;
    private String[] groups;
    private int[][] ids;
    ExpandableListAdapter mAdapter;

    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        public Object getChild(int groupPosition, int childPosition) {
            return cats.this.children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return (long) childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return cats.this.children[groupPosition].length;
        }

        public TextView getGenericView() {
            LayoutParams lp = new LayoutParams(-1, 64);
            TextView textView = new TextView(cats.this);
            textView.setLayoutParams(lp);
            textView.setGravity(19);
            textView.setPadding(60, 0, 0, 0);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return cats.this.groups[groupPosition];
        }

        public int getGroupCount() {
            return cats.this.groups.length;
        }

        public long getGroupId(int groupPosition) {
            return (long) groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        config.aplicar_color_top(this, this.globales.c1);
        SQLiteDatabase db = new bd(getApplicationContext()).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT _id,idcat,descr FROM cats WHERE idcat=0 ORDER BY descr", null);
        this.groups = new String[c.getCount()];
        this.children = new String[c.getCount()][];
        this.ids = new int[c.getCount()][];
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                this.groups[c.getPosition()] = c.getString(c.getColumnIndex("descr"));
                int idcat = c.getInt(c.getColumnIndex("_id"));
                Cursor c2 = db.rawQuery("SELECT _id,idcat,descr FROM cats WHERE idcat=" + idcat + " ORDER BY descr", null);
                String[] elems = new String[(c2.getCount() + 1)];
                elems[0] = "Todo";
                int[] elems_ids = new int[(c2.getCount() + 1)];
                elems_ids[0] = idcat;
                if (c2.moveToFirst()) {
                    while (!c2.isAfterLast()) {
                        elems[c2.getPosition() + 1] = c2.getString(c2.getColumnIndex("descr"));
                        elems_ids[c2.getPosition() + 1] = c2.getInt(c2.getColumnIndex("_id"));
                        c2.moveToNext();
                    }
                }
                c2.close();
                this.children[c.getPosition()] = elems;
                this.ids[c.getPosition()] = elems_ids;
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        this.mAdapter = new MyExpandableListAdapter();
        setListAdapter(this.mAdapter);
    }

    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent data = new Intent();
        data.putExtra("idcat", this.ids[groupPosition][childPosition]);
        if (childPosition == 0) {
            data.putExtra("cat", this.groups[groupPosition]);
        } else {
            data.putExtra("cat", this.children[groupPosition][childPosition]);
        }
        setResult(-1, data);
        finish();
        return true;
    }
}
