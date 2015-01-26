package atos.bull.net.codingdojosession1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 06/10/2014.
 */
public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Boolean checkWifi = intent.getBooleanExtra("checkWiFi", false);
        Boolean check3G = intent.getBooleanExtra("check3G", false);

        if (action == "codingDojo1.BATTERY_LOW") {
            try {
                if (check3G)
                    changeMobileData(context, false);
                if (checkWifi)
                    changeWifi(context, false);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.getCause();
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        if (action == "codingDojo1.BATTERY_OK") {
            try {
                if (check3G)
                    changeMobileData(context, true);
                if (checkWifi)
                    changeWifi(context, true);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.getCause();
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeWifi(Context context, boolean b) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(b);
    }

    private void changeMobileData(Context context, boolean active) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method dataConnSwitchMethod;
        Class connectivityManagerClass;
        Object ITelephonyStub;
        Class ITelephonyClass;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
        Field iConnectivityManagerField = connectivityManagerClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);

        Object iConnectivityManager = iConnectivityManagerField.get(connectivityManager);
        Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(iConnectivityManager, active);
    }
}