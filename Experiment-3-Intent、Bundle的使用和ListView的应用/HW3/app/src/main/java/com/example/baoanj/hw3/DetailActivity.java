package com.example.baoanj.hw3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by baoanj on 2016/10/17.
 */
public class DetailActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton imageButtonBack;
    private ImageButton imageButtonStar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        listView = (ListView) findViewById(R.id.detailList);
        imageButtonBack = (ImageButton) findViewById(R.id.back);
        imageButtonStar = (ImageButton) findViewById(R.id.star);

        Bundle bundle = this.getIntent().getExtras();
        String[] clickName = bundle.getStringArray("clickName");

        TextView textViewName = (TextView) findViewById(R.id.nameShow);
        textViewName.setText(clickName[0]);
        TextView textViewNumber = (TextView) findViewById(R.id.phonenumber);
        textViewNumber.setText(clickName[1]);
        TextView textViewAddress = (TextView) findViewById(R.id.address);
        textViewAddress.setText("手机   " + clickName[2]);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.topLayout);
        relativeLayout.setBackgroundColor(Color.parseColor("#" + clickName[3]));

        imageButtonStar.setTag(0);

        String[] menu = new String[] {"编辑联系人", "分享联系人", "加入黑名单", "删除联系人"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(DetailActivity.this, android.R.layout.simple_list_item_1, menu);
        listView.setAdapter(arrayAdapter);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.finish();
            }
        });

        imageButtonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageButtonStar.getTag().toString() == "0") {
                    imageButtonStar.setTag(1);
                    imageButtonStar.setImageResource(R.mipmap.full_star);
                } else {
                    imageButtonStar.setTag(0);
                    imageButtonStar.setImageResource(R.mipmap.empty_star);
                }
            }
        });
    }
}
