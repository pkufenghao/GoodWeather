package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.pku.fenghao.bean.TodayWeather;
import cn.edu.pku.fenghao.util.NetUtil;

public class MainActivity extends Activity implements View.OnClickListener {//主活动

    private static final int UPDATE_TODAY_WEATHER = 1;                      //更新天气变量

    private ImageView mUpdateBtn;                                           //更新天气按钮

    private ImageView mCitySelect;                                          //选择城市按钮

    private TextView cityTv, timeTv, humidityTv, weekTv, pmDateTv, pmQualtyTv,//今日天气文本控件
            temperatureTv, climateTv, windTv, city_name_Tv,temperature_infoTv;
    private ImageView weatherImg, pmImg;                                    //今日天气图片控件

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {                       //处理消息机制
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:                                  //当为更新天气时，更新天气
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {                //onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);                          //设置布局文件

        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);   //初始化更新按钮
        mUpdateBtn.setOnClickListener(this);                            //为更新按钮添加监听事件

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {//检测网络函数
            Log.d("myWeather", "Internet OK");                    //网络OK
            Toast.makeText(MainActivity.this, "Internet OK", Toast.LENGTH_LONG).show();
        } else {
            Log.d("myWeather", "Internet Error");                  //网络错误
            Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_LONG).show();
        }

        mCitySelect = (ImageView) findViewById(R.id.title_city_manager);    //初始化城市选择按钮
        mCitySelect.setOnClickListener(this);                               //为按钮添加监听事件

        initView();                                                         //初始化布局
    }

    private TodayWeather parseXML(String xmldata) {                         //XML 解析函数
        TodayWeather todayWeather = null;                                   //设置今日天气对象
        int fengxiangCount = 0;
        int fengliCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();//初始化fac
            XmlPullParser xmlPullParser = fac.newPullParser();              //初始化xmlPullParser
            xmlPullParser.setInput(new StringReader(xmldata));              //读取字符串
            int eventType = xmlPullParser.getEventType();                   //获得事件类型
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("resp")) {
                            todayWeather = new TodayWeather();
                        }

                        if (todayWeather != null) {
                            if (xmlPullParser.getName().equals("city")) {                               //解析城市名称
                                eventType = xmlPullParser.next();
                                todayWeather.setCity(xmlPullParser.getText());
                                Log.d("myWeather", "city: " + xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")) {                  //解析更新时间
                                eventType = xmlPullParser.next();
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                                Log.d("myWeather", "updatetime: " + xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {                       //解析湿度
                                eventType = xmlPullParser.next();
                                todayWeather.setShidu(xmlPullParser.getText());
                                Log.d("myWeather", "shidu: " + xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {                       //解析温度
                                eventType = xmlPullParser.next();
                                todayWeather.setWendu(xmlPullParser.getText());
                                Log.d("myWeather", "wendu: " + xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {                        //解析pm25
                                eventType = xmlPullParser.next();
                                todayWeather.setPm25(xmlPullParser.getText());
                                Log.d("myWeather", "pm25: " + xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {                     //解析空气质量
                                eventType = xmlPullParser.next();
                                todayWeather.setQuality(xmlPullParser.getText());
                                Log.d("myWeather", "quality: " + xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {//解析风向
                                eventType = xmlPullParser.next();
                                todayWeather.setFengxiang(xmlPullParser.getText());
                                Log.d("myWeather", "fengxiang: " + xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {      //解析风力
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli(xmlPullParser.getText());
                                Log.d("myWeather", "fengli: " + xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {          //解析日期
                                eventType = xmlPullParser.next();
                                todayWeather.setDate(xmlPullParser.getText().substring(3).trim());
                                Log.d("myWeather", "date: " + xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {          //解析高温
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh(xmlPullParser.getText().substring(2).trim());
                                Log.d("myWeather", "high: " + xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {            //解析低温
                                eventType = xmlPullParser.next();
                                todayWeather.setLow(xmlPullParser.getText().substring(2).trim());
                                Log.d("myWeather", "low: " + xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {          //解析类型
                                eventType = xmlPullParser.next();
                                todayWeather.setType(xmlPullParser.getText());
                                Log.d("myWeather", "type: " + xmlPullParser.getText());
                                typeCount++;
                            }
                        }
                        break;
                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        break;
                }
                // 进入下一个元素并触发相应事件
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e)

        {
            e.printStackTrace();
        } catch (IOException e)

        {
            e.printStackTrace();
        }
        return todayWeather;
    }


    private void queryWeatherCode(String cityCode) {    //请求天气更新
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;//网址
        Log.d("myWeather", address);
        new Thread(new Runnable() {
            @Override
            public void run() {                                 //执行新的线程
                HttpURLConnection con = null;                       //初始化网址
                TodayWeather todayWeather = null;                   //初始化今日天气
                try {
                    URL url = new URL(address);                     //url设置为address
                    con = (HttpURLConnection) url.openConnection();//打开url
                    con.setRequestMethod("GET");                    //设置请求方式为获取
                    con.setConnectTimeout(8000);                    //连接时间
                    con.setReadTimeout(8000);                       //读取时间
                    InputStream in = con.getInputStream();          ///设置输入流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));//设置输入缓存
                    StringBuilder response = new StringBuilder();//字符串缓存
                    String str;
                    while ((str = reader.readLine()) != null) {//添加字符串
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr = response.toString();//打印字符串
                    Log.d("myWeather", responseStr);

                    todayWeather = parseXML(responseStr);//解析字符串
                    if (todayWeather != null) {
                        Log.d("myWeather", todayWeather.toString());

                        Message msg = new Message();
                        msg.what = UPDATE_TODAY_WEATHER;        //设置更新
                        msg.obj = todayWeather;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {                //按钮监听事件

        if (view.getId() == R.id.title_city_manager){   //选择城市按钮
            Intent i = new Intent(this,SelectCity.class);
            startActivityForResult(i,1);
        }

        if (view.getId() == R.id.title_update_btn) {     //更新天气按钮

            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code", "101010100");//获取城市代码
            Log.d("myWeather", cityCode);

            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {        //获取网络状态
                Log.d("goodweather", "Internet OK");
                queryWeatherCode(cityCode);                                             //网络OK，请求获取城市代码
                Toast.makeText(MainActivity.this, "Internet OK", Toast.LENGTH_LONG).show();
            } else {
                Log.d("goodweather", "Internet Error");                      //无网络
                Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){//利用意图获取城市代码
        if (requestCode == 1 && resultCode == RESULT_OK){
            String newCityCode= data.getStringExtra("cityCode");            //获取城市代码
            Log.d("myWeather","选择的城市代码为"+newCityCode);

            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {  //网络状态
                Log.d("goodweather", "Internet OK");                    //网络OK
                queryWeatherCode(newCityCode);
                Toast.makeText(MainActivity.this, "Internet OK", Toast.LENGTH_LONG).show();
            } else {
                Log.d("goodweather", "Internet Error");                 //网络错误
                Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_LONG).show();
            }
        }


    }

    void initView() {           //天气初始化函数
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);       //初始化城市名称
        cityTv = (TextView) findViewById(R.id.city);                        //初始化城市
        timeTv = (TextView) findViewById(R.id.time);                        //初始化更新时间
        humidityTv = (TextView) findViewById(R.id.humidity);                //初始化湿度
        weekTv = (TextView) findViewById(R.id.week_today);                  //初始化日期
        pmDateTv = (TextView) findViewById(R.id.pm_data);                   //初始化pm25
        pmQualtyTv = (TextView) findViewById(R.id.pm2_5_quality);           //初始化空气质量
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);                   //初始化pm25图片
        temperatureTv = (TextView) findViewById(R.id.temperature);          //初始化温度
        temperature_infoTv = (TextView)findViewById(R.id.temperature_info); //初始化天气信息
        climateTv = (TextView) findViewById(R.id.climate);                  //初始化天气情况
        windTv = (TextView) findViewById(R.id.wind);                        //初始化风力
        weatherImg = (ImageView) findViewById(R.id.weather_img);            //初始化天气图片

        city_name_Tv.setText("N/A");                                        //默认设置城市名称
        cityTv.setText("N/A");                                              //默认设置城市
        timeTv.setText("N/A");                                              //默认设置更新时间
        humidityTv.setText("N/A");                                          //默认设置湿度
        pmQualtyTv.setText("N/A");                                          //默认设置空气质量
        pmDateTv.setText("N/A");                                            //默认设置PM25
        weekTv.setText("N/A");                                              //默认设置星期
        temperatureTv.setText("N/A");                                       //默认设置温度
        temperature_infoTv.setText("N/A");                                  //默认设置温度范围
        climateTv.setText("N/A");                                           //默认设置天气状况
        windTv.setText("N/A");                                              //默认设置风力
    }

    void updateTodayWeather(TodayWeather todayWeather) {    //更新今日天气
        city_name_Tv.setText(todayWeather.getCity() + "天气");               //设置顶部城市
        cityTv.setText(todayWeather.getCity());                             //设置城市
        timeTv.setText(todayWeather.getUpdatetime() + "发布");              //设置更新时间
        temperature_infoTv.setText("温度:" + todayWeather.getWendu() + "℃"); //设置温度
        humidityTv.setText("湿度:" + todayWeather.getShidu());                //设置湿度
        pmDateTv.setText(todayWeather.getPm25());                           //设置PM25
        pmQualtyTv.setText(todayWeather.getQuality());                      //设置空气质量
        weekTv.setText("今天" + todayWeather.getDate());                    //设置日期
        temperatureTv.setText(todayWeather.getHigh() + "~" + todayWeather.getLow());//设置温度范围
        climateTv.setText(todayWeather.getType());                          //设置天气状况
        windTv.setText("风力:" + todayWeather.getFengxiang() + todayWeather.getFengli());//设置星期
        Toast.makeText(MainActivity.this, "更新成功！", Toast.LENGTH_LONG).show();//显示更新成功
    }
}