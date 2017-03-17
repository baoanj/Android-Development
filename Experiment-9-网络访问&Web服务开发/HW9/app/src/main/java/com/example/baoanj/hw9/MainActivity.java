package com.example.baoanj.hw9;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText cityName;
    private Button searchButton;
    private TextView cityView;
    private TextView updateTime;
    private TextView currentTem;
    private TextView intervalTem;
    private TextView humidity;
    private TextView airQuality;
    private TextView wind;
    private ListView listView;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    private static final String url = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";
    private static final int UPDATE_CONTENT = 0;
    private static final int NOT_CONNECTED = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_CONTENT:
                    List<String> list = (List<String>) message.obj;
                    if (list.size() > 5) {
                        linearLayout1.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.VISIBLE);
                        cityView.setText(list.get(1));
                        updateTime.setText(list.get(3) + "更新");
                        int index1 = list.get(4).indexOf("气温");
                        int index11 = list.get(4).indexOf("风向");
                        currentTem.setText(list.get(4).substring(index1 + 3, index11 - 1));
                        intervalTem.setText(list.get(8));
                        int index2 = list.get(4).indexOf("湿度");
                        humidity.setText(list.get(4).substring(index2));
                        int index3 = list.get(5).indexOf("空气");
                        airQuality.setText(list.get(5).substring(index3, list.get(5).length() - 1));
                        int index4 = list.get(4).indexOf("风力");
                        wind.setText(list.get(4).substring(index4 + 3, index2 - 1));

                        // ListView
                        int index5 = list.get(6).indexOf("感冒指数");
                        int index6 = list.get(6).indexOf("穿衣指数");
                        int index7 = list.get(6).indexOf("洗车指数");
                        int index8 = list.get(6).indexOf("运动指数");
                        int index9 = list.get(6).indexOf("空气污染指数");

                        final List<Map<String, Object>> data = new ArrayList<>();

                        Map<String, Object> temp1 = new LinkedHashMap<>();
                        temp1.put("index", list.get(6).substring(0, 5));
                        temp1.put("value", list.get(6).substring(6, index5 - 1));
                        data.add(temp1);

                        Map<String, Object> temp2 = new LinkedHashMap<>();
                        temp2.put("index", list.get(6).substring(index5, index5 + 4));
                        temp2.put("value", list.get(6).substring(index5 + 5, index6 - 1));
                        data.add(temp2);

                        Map<String, Object> temp3 = new LinkedHashMap<>();
                        temp3.put("index", list.get(6).substring(index6, index6 + 4));
                        temp3.put("value", list.get(6).substring(index6 + 5, index7 - 1));
                        data.add(temp3);

                        Map<String, Object> temp4 = new LinkedHashMap<>();
                        temp4.put("index", list.get(6).substring(index7, index7 + 4));
                        temp4.put("value", list.get(6).substring(index7 + 5, index8 - 1));
                        data.add(temp4);

                        Map<String, Object> temp5 = new LinkedHashMap<>();
                        temp5.put("index", list.get(6).substring(index8, index8 + 4));
                        temp5.put("value", list.get(6).substring(index8 + 5, index9 - 1));
                        data.add(temp5);

                        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, data, R.layout.item,
                                new String[] {"index", "value"}, new int[]{R.id.index, R.id.value});
                        listView.setAdapter(simpleAdapter);

                        // RecyclerView
                        ArrayList<Weather> weather_list = new ArrayList<>();

                        int index12 = list.get(7).indexOf("日");
                        Weather weather1 = new Weather(list.get(7).substring(0, index12 + 1),
                                list.get(7).substring(index12 + 2), list.get(8));
                        weather_list.add(weather1);

                        int index13 = list.get(12).indexOf("日");
                        Weather weather2 = new Weather(list.get(12).substring(0, index13 + 1),
                                list.get(12).substring(index13 + 2), list.get(13));
                        weather_list.add(weather2);

                        int index14 = list.get(17).indexOf("日");
                        Weather weather3 = new Weather(list.get(17).substring(0, index14 + 1),
                                list.get(17).substring(index14 + 2), list.get(18));
                        weather_list.add(weather3);

                        int index15 = list.get(22).indexOf("日");
                        Weather weather4 = new Weather(list.get(22).substring(0, index15 + 1),
                                list.get(22).substring(index15 + 2), list.get(23));
                        weather_list.add(weather4);

                        int index16 = list.get(27).indexOf("日");
                        Weather weather5 = new Weather(list.get(27).substring(0, index16 + 1),
                                list.get(27).substring(index16 + 2), list.get(28));
                        weather_list.add(weather5);

                        int index17 = list.get(32).indexOf("日");
                        Weather weather6 = new Weather(list.get(32).substring(0, index17 + 1),
                                list.get(32).substring(index17 + 2), list.get(33));
                        weather_list.add(weather6);

                        int index18 = list.get(37).indexOf("日");
                        Weather weather7 = new Weather(list.get(37).substring(0, index18 + 1),
                                list.get(37).substring(index18 + 2), list.get(38));
                        weather_list.add(weather7);

                        WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, weather_list);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, list.get(0), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case NOT_CONNECTED:
                    Toast.makeText(MainActivity.this, "当前没有可用网络!", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.city_name);
        searchButton = (Button) findViewById(R.id.search_button);
        cityView = (TextView) findViewById(R.id.city_view);
        updateTime = (TextView) findViewById(R.id.update_time);
        currentTem = (TextView) findViewById(R.id.current_tem);
        intervalTem = (TextView) findViewById(R.id.interval_tem);
        humidity = (TextView) findViewById(R.id.humidity);
        airQuality = (TextView) findViewById(R.id.air_quality);
        wind = (TextView) findViewById(R.id.wind);
        listView = (ListView) findViewById(R.id.listview);
        linearLayout1 = (LinearLayout) findViewById(R.id.isVisible1);
        linearLayout2 = (LinearLayout) findViewById(R.id.isVisible2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestWithHttpURLConnection();
            }
        });
    }

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    HttpURLConnection connection = null;
                    StringBuffer response = new StringBuffer();
                    try {
                        connection = (HttpURLConnection) ((new URL(url.toString()).openConnection()));
                        connection.setRequestMethod("POST");
                        connection.setReadTimeout(8000);
                        connection.setConnectTimeout(8000);

                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        String request = cityName.getText().toString();
                        request = URLEncoder.encode(request, "utf-8");
                        out.writeBytes("theCityCode=" + request + "&theUserID=7c7aae1f79f7455792cffd63327e453a");

                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        reader.close();
                        in.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if(connection!=null) {
                            connection.disconnect();
                        }
                    }

                    Message message = new Message();
                    message.what = UPDATE_CONTENT;
                    message.obj = parseXMLWithPull(response.toString());
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = NOT_CONNECTED;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private List<String> parseXMLWithPull(String xml) {
        List<String> list = new LinkedList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("string".equals(parser.getName())) {
                            String str = parser.nextText();
                            list.add(str);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
