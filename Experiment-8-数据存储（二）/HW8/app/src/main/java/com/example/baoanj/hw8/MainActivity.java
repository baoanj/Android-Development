package com.example.baoanj.hw8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ListView listView;
    private TextView nameDialog;
    private EditText birthdayDialog;
    private EditText giftDialog;
    private TextView phonenumber;

    private MyDB dbOpenHelper;
    private SQLiteDatabase dbRead;
    private SQLiteDatabase dbWrite;

    private List<Map<String, Object>> data;
    private SimpleAdapter simpleAdapter;

    private static final String DATABASE_NAME = "firstdb";
    private static final String SQL_UPDATE = "update birthdays set birth = ?, gift = ? where name = ?";
    private static final String SQL_DELETE = "delete from birthdays where name = ?";
    private static final String SQL_SELECTALL = "select * from birthdays";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        listView = (ListView) findViewById(R.id.listview);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View view1 = layoutInflater.inflate(R.layout.dialog_layout, null);

                nameDialog = (TextView) view1.findViewById(R.id.nameDialog);
                birthdayDialog = (EditText) view1.findViewById(R.id.birthdayDialog);
                giftDialog = (EditText) view1.findViewById(R.id.giftDialog);
                phonenumber = (TextView) view1.findViewById(R.id.phonenumber);

                final TextView tv1 = (TextView) view.findViewById(R.id.nameView);
                final TextView tv2 = (TextView) view.findViewById(R.id.birthdayView);
                final TextView tv3 = (TextView) view.findViewById(R.id.giftView);

                nameDialog.setText(tv1.getText().toString());
                birthdayDialog.setText(tv2.getText().toString());
                giftDialog.setText(tv3.getText().toString());

                Cursor cursor1 = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                String phone = "";
                while (cursor1.moveToNext()) {
                    int _id = cursor1.getColumnIndex(ContactsContract.Contacts._ID);
                    String contactId = cursor1.getString(_id);
                    String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    if(nameDialog.getText().toString().equals(contactName)) {
                        Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                        while (cursor2.moveToNext()) {
                            phone += cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "  ";
                        }
                        cursor2.close();
                    }
                }
                phonenumber.setText(phone);
                cursor1.close();
                if (phonenumber.getText().toString().equals("")) {
                    phonenumber.setText("无");
                }

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("修改条目")
                        .setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int ii) {
                                dbWrite = dbOpenHelper.getWritableDatabase();
                                dbWrite.execSQL(SQL_UPDATE, new Object[] {birthdayDialog.getText().toString(),
                                                giftDialog.getText().toString(), nameDialog.getText().toString()});
                                dbWrite.close();

                                tv1.setText(nameDialog.getText().toString());
                                tv2.setText(birthdayDialog.getText().toString());
                                tv3.setText(giftDialog.getText().toString());
                            }
                        })
                        .setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                            }
                        })
                        .setView(view1)
                        .show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
                final TextView tv = (TextView) view.findViewById(R.id.nameView);

                normalDialog.setTitle("删除条目");
                normalDialog.setMessage("是否删除【" + tv.getText().toString() + "】？");
                normalDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int ii) {
                        dbWrite = dbOpenHelper.getWritableDatabase();
                        dbWrite.execSQL(SQL_DELETE, new Object[]{tv.getText().toString()});
                        dbWrite.close();
                        data.remove(i);
                        simpleAdapter.notifyDataSetChanged();
                    }
                });
                normalDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
                normalDialog.show();

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbOpenHelper = new MyDB(MainActivity.this, DATABASE_NAME, null, 1);
        dbRead = dbOpenHelper.getReadableDatabase();

        data = new ArrayList<>();

        Cursor cursor = dbRead.rawQuery(SQL_SELECTALL, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String birth = cursor.getString(cursor.getColumnIndex("birth"));
            String gift = cursor.getString(cursor.getColumnIndex("gift"));

            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("name", name);
            temp.put("birthday", birth);
            temp.put("gift", gift);
            data.add(temp);
        }
        cursor.close();
        dbRead.close();

        simpleAdapter = new SimpleAdapter(this, data, R.layout.item,
                new String[] {"name", "birthday", "gift"}, new int[]{R.id.nameView, R.id.birthdayView, R.id.giftView});
        listView.setAdapter(simpleAdapter);
    }

}
