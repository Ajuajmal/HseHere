package hse.here2;

import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
