package com.example.baoanj.hw3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baoanj on 2016/10/17.
 */
public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        listView = (ListView) findViewById(R.id.contactsList);

        final Map<String, String[]> activityData = new HashMap<>();
        activityData.put("Aaron", new String[] {"Aaron", "17715523654", "江苏苏州电信", "BB4C3B"});
        activityData.put("Elvis", new String[] {"Elvis", "18825653224", "广东揭阳移动", "c48d30"});
        activityData.put("David", new String[] {"David", "15052116654", "江苏无锡移动", "4469b0"});
        activityData.put("Edwin", new String[] {"Edwin", "18854211875", "山东青岛移动", "20A17B"});
        activityData.put("Frank", new String[] {"Frank", "13955188541", "安徽合肥移动", "BB4C3B"});
        activityData.put("Joshua", new String[] {"Joshua", "13621574410", "江苏苏州移动", "c48d30"});
        activityData.put("Ivan", new String[] {"Ivan", "15684122771", "山东烟台联通", "4469b0"});
        activityData.put("Mark", new String[] {"Mark", "17765213579", "广东珠海电信", "20A17B"});
        activityData.put("Joseph", new String[] {"Joseph", "13315466578", "河北石家庄电信", "BB4C3B"});
        activityData.put("Phoebe", new String[] {"Phoebe", "17895466428", "山东东营移动", "c48d30"});

        final List<Map<String, Object>> data = new ArrayList<>();
        String[] name = new String[] {"Aaron", "Elvis", "David", "Edwin", "Frank",
                "Joshua", "Ivan", "Mark", "Joseph", "Phoebe"};
        for (int i = 0; i < 10; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("circleLabel", name[i].charAt(0) + "");
            temp.put("name", name[i]);
            data.add(temp);
        }

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item,
                new String[]{"circleLabel", "name"}, new int[]{R.id.circleLabel, R.id.name});
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.name);
                String clickName = tv.getText().toString();

                Intent intent =new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putStringArray("clickName", activityData.get(clickName));
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
                TextView tv = (TextView) view.findViewById(R.id.name);

                normalDialog.setTitle("删除联系人");
                normalDialog.setMessage("确认删除联系人" + tv.getText().toString() + "？");
                normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int ii) {
                        data.remove(i);
                        simpleAdapter.notifyDataSetChanged();
                    }
                });
                normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
                normalDialog.show();

                return true;
            }
        });
    }
}
