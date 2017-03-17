package com.example.baoanj.hw4;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by baoanj on 2016/10/24.
 */
public class DynamicActivity extends AppCompatActivity {
    private Button registButton;
    private Button sendButton;
    private EditText editText;
    private DynamicReceiver dynamicReceiver;

    private static final String DYNAMICACTION = "com.example.hw4.dynamicreceiver";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_layout);

        registButton = (Button) findViewById(R.id.regist);
        sendButton = (Button) findViewById(R.id.send);
        editText = (EditText) findViewById(R.id.broadcast);

        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dynamicReceiver == null) {
                    dynamicReceiver = new DynamicReceiver();
                    IntentFilter dynamic_filter = new IntentFilter();
                    dynamic_filter.addAction(DYNAMICACTION);
                    registerReceiver(dynamicReceiver, dynamic_filter);
                    registButton.setText("Unregister Broadcast");
                } else {
                    unregisterReceiver(dynamicReceiver);
                    dynamicReceiver = null;
                    registButton.setText("Register Broadcast");
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dynamicReceiver != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("imageId", R.mipmap.dynamic);
                    bundle.putString("name", editText.getText().toString());
                    Intent intent = new Intent(DYNAMICACTION);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                }
            }
        });
    }
}
