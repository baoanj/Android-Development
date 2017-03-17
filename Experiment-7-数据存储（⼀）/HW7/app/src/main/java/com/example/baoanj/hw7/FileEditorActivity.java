package com.example.baoanj.hw7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by baoanj on 2016/11/14.
 */
public class FileEditorActivity extends AppCompatActivity {

    private EditText fileET;
    private Button saveButton;
    private Button loadButoon;
    private Button clearFileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_file_editor);

        fileET = (EditText) findViewById(R.id.fileEdit);
        saveButton = (Button) findViewById(R.id.saveButoon);
        loadButoon = (Button) findViewById(R.id.loadButoon);
        clearFileButton = (Button) findViewById(R.id.clearFileButoon);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileOutputStream fileOutputStream = openFileOutput("hello", MODE_PRIVATE)) {
                    String str = fileET.getText().toString();
                    fileOutputStream.write(str.getBytes());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Toast.makeText(FileEditorActivity.this, "Save successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        loadButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileInputStream fileInputStream = openFileInput("hello")) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    fileET.setText(new String(contents));
                    Toast.makeText(FileEditorActivity.this, "Load successfully.", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Toast.makeText(FileEditorActivity.this, "Fail to load file.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileET.setText("");
            }
        });
    }
}
