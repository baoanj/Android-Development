package com.example.baoanj.hw8;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by baoanj on 2016/11/21.
 */
public class AddActivity extends AppCompatActivity {

    private EditText nameEdit;
    private EditText birthEdit;
    private EditText giftEdit;
    private Button addItem;

    private SQLiteDatabase dbRead;
    private SQLiteDatabase dbWrite;

    private static final String DATABASE_NAME = "firstdb";
    private static final String SQL_SELECT = "select * from birthdays where name = ?";
    private static final String SQL_INSERT = "insert into birthdays (name, birth, gift) values (?, ?, ?)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_info);

        nameEdit = (EditText) findViewById(R.id.nameEdit);
        birthEdit = (EditText) findViewById(R.id.birthdayEdit);
        giftEdit = (EditText) findViewById(R.id.giftEdit);
        addItem = (Button) findViewById(R.id.addItem);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEdit.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this, "名字为空，请完善", Toast.LENGTH_SHORT).show();
                } else {
                    MyDB dbOpenHelper = new MyDB(AddActivity.this, DATABASE_NAME, null, 1);
                    dbRead = dbOpenHelper.getReadableDatabase();
                    Cursor cursor = dbRead.rawQuery(SQL_SELECT, new String[] {nameEdit.getText().toString()});
                    if (cursor.moveToFirst()) {
                        Toast.makeText(AddActivity.this, "名字重复，请核查", Toast.LENGTH_SHORT).show();
                    } else {
                        dbWrite = dbOpenHelper.getWritableDatabase();
                        dbWrite.execSQL(SQL_INSERT, new Object[]{nameEdit.getText().toString(),
                                        birthEdit.getText().toString(), giftEdit.getText().toString()});
                        dbWrite.close();
                        AddActivity.this.finish();
                    }
                    cursor.close();
                    dbRead.close();
                }
            }
        });
    }
}
