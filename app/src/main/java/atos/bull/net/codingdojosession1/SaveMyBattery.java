package atos.bull.net.codingdojosession1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 06/10/2014.
 */
public class SaveMyBattery extends Activity{

       Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.battery_layout);

        final AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        myButton = (Button) findViewById(R.id.saveButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errTxt = checkRequirements();
                if (errTxt != null) {
                    Toast.makeText(view.getContext(), errTxt, Toast.LENGTH_LONG).show();
                    return;
                };
                EditText editText = (EditText) findViewById(R.id.editTextThreshold);
                String str = editText.getText().toString();
                Intent intent = new Intent(view.getContext(), CheckBatteryLife.class);
                intent.putExtra("value", str);
                PendingIntent pi =  PendingIntent.getService(view.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                EditText repeatingText = (EditText) findViewById(R.id.editTextRepeating);
                int repeating = Integer.parseInt(repeatingText.getText().toString()) * 1000 * 60;


                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        Calendar.getInstance().getTimeInMillis(), repeating,
                        pi);
            }
        });
    }

    private String checkRequirements() {
        // Requirement for repeating
        EditText repeatingText = (EditText) findViewById(R.id.editTextRepeating);
        String repeatingTxt = repeatingText.getText().toString();
        if(repeatingTxt.isEmpty()) {
            return "Please enter a repeating value";
        }
        if(repeatingTxt.equals("0"))  {
            return "Please enter a positive repeating value";
        }

        // Requirement for threshold
        EditText thresholdText = (EditText) findViewById(R.id.editTextThreshold);
        String thresholdTxt = thresholdText.getText().toString();
        if(thresholdTxt.isEmpty()) {
            return "Please enter a threshold";
        }
        if(thresholdTxt.equals("0"))  {
            return "Please enter a positive threshold value";
        }

        return null;
    }
}
