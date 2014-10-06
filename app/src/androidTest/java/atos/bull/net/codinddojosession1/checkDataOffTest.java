package atos.bull.net.codinddojosession1;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 05/10/2014.
 */
public class checkDataOffTest extends ActivityUnitTestCase<SaveMyBattery> {

    private Button saveButton;
    private EditText editText;


    public checkDataOffTest() {
        super(SaveMyBattery.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), SaveMyBattery.class);
        startActivity(mLaunchIntent, null, null);
        saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        editText = (EditText) getActivity().findViewById(R.id.editText);
    }

    public void testDataAndBatteryLife() {

        editText.setText("30");
        saveButton.performClick();

        float warning = Float.parseFloat(editText.getText().toString()) /100;

        IntentFilter filter = new IntentFilter(PowerConnectionReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        PowerConnectionReceiver receiver = new PowerConnectionReceiver();
        getActivity().registerReceiver(receiver, filter);

        checkIntentService(warning, 1);
        checkIntentService(warning, (float) 0.2);
    }

    public void checkIntentService(float warning, float actualBattery) {

        Intent intent = new Intent(getActivity(), CheckBatteryLife.class);
        intent.putExtra("battery_critical", warning);
        getActivity().startService(intent);

        if(actualBattery >= warning) {
            assertEquals("Battery ok. Data enabled", true, checkMobileData());
        } else {
            assertEquals("Battery low. Data disabled", false, checkMobileData());
        }
    }

    private boolean checkMobileData() {
        boolean mobileDataEnabled = false;

        int conn = getConnectivityStatus(getActivity());
        if (conn == ConnectivityManager.TYPE_MOBILE) {
            mobileDataEnabled = true;
        }

        return mobileDataEnabled;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return ConnectivityManager.TYPE_MOBILE;
        }

        return -1;
    }

}
