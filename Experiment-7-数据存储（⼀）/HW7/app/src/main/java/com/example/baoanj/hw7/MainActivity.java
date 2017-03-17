package com.example.baoanj.hw7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText newPassET;
    private EditText conPassET;
    private Button okButton;
    private Button clearButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPassET = (EditText) findViewById(R.id.newPass);
        conPassET = (EditText) findViewById(R.id.conPass);
        okButton = (Button) findViewById(R.id.okButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        sharedPreferences = (MainActivity.this).getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("PASSWORD", null) != null) {
            newPassET.setVisibility(View.INVISIBLE);
            conPassET.setHint("Password");
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = newPassET.getText().toString();
                String conPass = conPassET.getText().toString();

                if (newPassET.getVisibility() == View.INVISIBLE) {
                    String pass = sharedPreferences.getString("PASSWORD", "Default");
                    if(conPass.equals(pass)) {
                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(newPass.equals("")) {
                        Toast.makeText(MainActivity.this, "Password cannot be empty.",Toast.LENGTH_SHORT).show();
                    } else if (newPass.equals(conPass)) {
                        editor.putString("PASSWORD", newPass);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Password Mismatch.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            newPassET.setText("");
            conPassET.setText("");
            }
        });
    }
}
