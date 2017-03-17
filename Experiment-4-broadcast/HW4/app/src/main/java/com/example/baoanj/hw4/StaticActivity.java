package com.example.baoanj.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baoanj on 2016/10/24.
 */
public class StaticActivity extends AppCompatActivity {

    private ListView listView;
    private static final String STATICACTION = "com.example.hw4.staticreceiver";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_layout);

        listView = (ListView) findViewById(R.id.contactsList);

        final List<Map<String, Object>> data = new ArrayList<>();
        final int[] images = new int[] {R.mipmap.apple, R.mipmap.banana, R.mipmap.cherry,
                R.mipmap.coco, R.mipmap.kiwi, R.mipmap.orange, R.mipmap.pear,
                R.mipmap.strawberry, R.mipmap.watermelon};
        final String[] name = new String[] {"Apple", "Banana", "Cherry", "Coco", "Kiwi",
                "Orange", "Pear", "Strawberry", "Watermelon",};
        for (int i = 0; i < 9; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("circleLabel", images[i]);
            temp.put("name", name[i]);
            data.add(temp);
        }

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item,
                new String[]{"circleLabel", "name"}, new int[]{R.id.circleLabel, R.id.name});
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.name);

                Bundle bundle = new Bundle();
                bundle.putInt("imageId", images[position]);
                bundle.putString("name", textView.getText().toString());
                Intent intent = new Intent(STATICACTION);
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });
    }
}
