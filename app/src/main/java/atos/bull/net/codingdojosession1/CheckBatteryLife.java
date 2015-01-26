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

        String batteryLevel = intent.getStringExtra("threshold");
        Boolean checkWifi = intent.getBooleanExtra("checkWiFi", false);
        Boolean check3G = intent.getBooleanExtra("check3G", false);

        float batteryThreshold = Float.parseFloat(batteryLevel)/100;

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);


        float batteryPct = level / (float)scale;

            System.out.println("CheckBatteryLife...");
        if (batteryPct <= batteryThreshold && (checkWifi ||check3G)) {
            Intent batteryLowIntent = new Intent("codingDojo1.BATTERY_LOW");
            batteryLowIntent.putExtra("checkWiFi", checkWifi);
            batteryLowIntent.putExtra("check3G", check3G);
            sendBroadcast(batteryLowIntent);
        }
        else if(batteryPct > batteryThreshold && (checkWifi ||check3G)) {
            Intent batteryLowIntent = new Intent("codingDojo1.BATTERY_OK");
            batteryLowIntent.putExtra("checkWiFi", checkWifi);
            batteryLowIntent.putExtra("check3G", check3G);
            sendBroadcast(batteryLowIntent);
        }
    }
}
