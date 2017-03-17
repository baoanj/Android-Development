package com.example.baoanj.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by baoanj on 2016/10/24.
 */
public class MainActivity extends AppCompatActivity {

    Button staticButton;
    Button dynamicButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        staticButton = (Button) findViewById(R.id.staticButton);
        dynamicButton = (Button) findViewById(R.id.dynamicButton);

        staticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, StaticActivity.class);
                startActivity(intent);
            }
        });

        dynamicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, DynamicActivity.class);
                startActivity(intent);
            }
        });
    }
}
