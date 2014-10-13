package atos.bull.net.codingdojosession1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Florent Coquil <florent.coquil@bull.net> on 06/10/2014.
 */
public class SaveMyBattery extends Activity{

       Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.battery_layout);

        myButton = (Button) findViewById(R.id.saveButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String str = editText.getText().toString();
                Intent intent = new Intent(view.getContext(), CheckBatteryLife.class);
                intent.putExtra("value", str);
                startService(intent);
            }
        });
    }
}
