package hse.here2;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class fb_result extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Editor e = getSharedPreferences("sh", 0).edit();
        e.putString("fb_result", getIntent().getDataString());
        e.commit();
        finish();
    }
}
