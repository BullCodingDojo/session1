package atos.bull.net.codingdojosession1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 06/10/2014.
 */
public class SaveMyBattery extends Activity{

    Button myButton;

    EditText editTextThreshold;

    EditText editTextRepeating;

    CheckBox checkBoxWiFi;

    CheckBox checkBox3G;

    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.battery_layout);
        final Context context = this.getApplicationContext();
        act = this;
        editTextThreshold = (EditText) findViewById(R.id.editTextThreshold);
        checkBox3G = (CheckBox) findViewById(R.id.checkBox3G);
        checkBoxWiFi = (CheckBox) findViewById(R.id.checkBoxWiFi);
        editTextThreshold.addTextChangedListener(new TextWatcher() {
            public int maxThreshold = 100;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(charSequence.toString().isEmpty() || charSequence.toString().equals("0")) {
                    myButton.setEnabled(false);
                    editTextThreshold.setError(getResources().getString(R.string.nonNullValue));
                    return;
                }

                if (Integer.parseInt(charSequence.toString()) > maxThreshold) {
                    myButton.setEnabled(false);
                    editTextThreshold.setError(getResources().getString(R.string.maxThresholdError));
                }
                myButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editTextRepeating = (EditText) findViewById(R.id.editTextRepeating);
        editTextRepeating.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(charSequence.toString().isEmpty() || charSequence.toString().equals("0")) {
                    myButton.setEnabled(false);
                    editTextRepeating.setError(getResources().getString(R.string.nonNullValue));
                    return;
                }
                myButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        myButton = (Button) findViewById(R.id.saveButton);
        myButton.setEnabled(false);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideSoftKeyboard(act);
                String str = editTextThreshold.getText().toString();
                Intent intent = new Intent(view.getContext(), CheckBatteryLife.class);
                intent.putExtra("threshold", str);
                intent.putExtra("checkWiFi", checkBoxWiFi.isChecked());
                intent.putExtra("check3G", checkBox3G.isChecked());
                PendingIntent pi =  PendingIntent.getService(view.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                int repeating = Integer.parseInt(editTextRepeating.getText().toString()) * 1000 * 60;

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        Calendar.getInstance().getTimeInMillis(), repeating,
                        pi);
            }
        });

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
