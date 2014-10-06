package atos.bull.net.codinddojosession1;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 06/10/2014.
 */
public class CheckBatteryLife extends IntentService{
    public CheckBatteryLife(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
