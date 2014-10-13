package atos.bull.net.codingdojosession1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 06/10/2014.
 */
public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action == "codingDojo1.BATTERY_LOW") {
            try {
                changeMobileData(context,false);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeMobileData (Context context, boolean isEnabled) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method dataConnSwitchMethod;
        Class telephonyManagerClass;
        Object ITelephonyStub;
        Class ITelephonyClass;

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
/*
        if(telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED){
            isEnabled = true;
        }else{
            isEnabled = false;
        }
*/
        telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
        Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony");
        getITelephonyMethod.setAccessible(true);
        ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
        ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());

        if (isEnabled) {
            dataConnSwitchMethod = ITelephonyClass
                    .getDeclaredMethod("disableDataConnectivity");
        } else {
            dataConnSwitchMethod = ITelephonyClass
                    .getDeclaredMethod("enableDataConnectivity");
        }
        dataConnSwitchMethod.setAccessible(true);
        dataConnSwitchMethod.invoke(ITelephonyStub);

    }
}
