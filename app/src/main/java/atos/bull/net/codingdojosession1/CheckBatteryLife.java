package atos.bull.net.codingdojosession1;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 06/10/2014.
 */
public class CheckBatteryLife extends IntentService{

    public CheckBatteryLife() {
        super("CheckBatteryLife");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String batteryLevel = intent.getStringExtra("value");
        float batteryThreshold = Float.parseFloat(batteryLevel)/100;

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

            System.out.println("CheckBatteryLife...");
        if (batteryPct <= batteryThreshold) {
            Intent batteryLowIntent = new Intent("codingDojo1.BATTERY_LOW");
            sendBroadcast(batteryLowIntent);
        }
    }
}
