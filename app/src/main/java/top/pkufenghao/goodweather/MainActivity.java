package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

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
import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.fenghao.bean.TodayWeather;
import cn.edu.pku.fenghao.util.NetUtil;


public class MainActivity extends Activity implements View.OnClickListener  {//主活动

    private static final int UPDATE_TODAY_WEATHER = 1;                      //更新天气变量

    private static final int UPDATE_FORCAST_WEATHER = 2;

    private String newCityCode;                                            //城市代码

    private String thisCityCode;                                                //当前城市

    private Boolean isFirstUsed;                                            //是否为第一次使用

    private SharedPreferences used_sp;                                     //存储使用信息

    private SharedPreferences.Editor editor;                                //编辑信息

    private ImageView mUpdateBtn;                                         //更新天气按钮

    private ImageView mShare;                                             //分享按钮

    private ImageView mLocation;                                            //定位按钮

    private ImageView mprogressBar;                                         //环形进度条

    private ImageView mCitySelect;                                          //选择城市按钮

    public LocationClient mLocationClient = null;

    private MyLocationListener myListener = new MyLocationListener();

    private ImageView[] dots;

    private int[] ids = {R.id.iv1,R.id.iv2};

    private ViewPagerAdapter vpAdapter;

    private ViewPager vp;

    private List<View> views;

    private TextView cityTv, timeTv, humidityTv, weekTv, pmDateTv, pmQualtyTv,//今日天气文本控件
            temperatureTv, climateTv, windTv, city_name_Tv, temperature_infoTv;
    private ImageView weatherImg, pmImg;                                    //今日天气图片控件

   // TodayWeather todayWeather_1 = null;
   // TodayWeather todayWeather_2 = null;
    //TodayWeather todayWeather_3 = null;
    //TodayWeather todayWeather_4 = null;
    //TodayWeather todayWeather_5 = null;


    private TextView thisdate,nextdate,next_2_date,next_3_date,next_4_date,next_5_date;

    private TextView thistem,nexttem,next_2_tem,next_3_tem,next_4_tem,next_5_tem;

    private TextView thistype,nexttype,next_2_type,next_3_type,next_4_type,next_5_type;

    private TextView thisfengli,nextfengli,next_2_fengli,next_3_fengli,next_4_fengli,next_5_fengli;

    private ImageView thisimg,nextimg,next_2_img,next_3_img,next_4_img,next_5_img;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {                       //处理消息机制
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:                                  //当为更新天气时，更新天气
                    updateTodayWeather((TodayWeather) msg.obj);
                    //updateForcastWeather(todayWeather_1,todayWeather_2,todayWeather_3,todayWeather_4,todayWeather_5);
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

        //mLocationClient = new LocationClient(getApplicationContext());
        // mLocationClient.registerLocationListener(myListener);
        init_more_weather();
        //initLocation();
        initEvents();
        initDots();
        //////正常情况注释下面两行
        editor.putBoolean("isFirstUsed", true);
        editor.commit();

        if (isFirstUsed) {
            initView_weather();                                 //第一次使用初始化
        } else {
            //initView();
            queryWeatherCode(used_sp.getString("thisCityCode", "default"));//之后更新天气
        }


        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {//检测网络函数
            Log.d("myWeather", "Internet OK");                    //网络OK
            Toast.makeText(MainActivity.this, "Internet OK", Toast.LENGTH_LONG).show();
        } else {
            Log.d("myWeather", "Internet Error");                  //网络错误
            Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_LONG).show();
        }



    }


    void initDots(){
        dots = new ImageView[views.size()];
        dots[0] = (ImageView)findViewById(ids[0]);
        dots[1] = (ImageView)findViewById(ids[1]);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的回调
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }



            @Override
            public void onPageSelected(int position) {
            for (int a = 0; a<ids.length;a++){
                if (a==position){
                    dots[a].setImageResource(R.drawable.shape_point);
                }else {
                    dots[a].setImageResource(R.drawable.shape_point_2);
                }
            }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("state:" + state);
            }
        });
    }




    private void init_more_weather(){
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.more_weather_1,null));
        views.add(inflater.inflate(R.layout.more_weather_2,null));
        vpAdapter = new ViewPagerAdapter(views,this);
        vp = (ViewPager)findViewById(R.id.more_weather_viewpager);
        vp.setAdapter(vpAdapter);

    }



    private void initLocation() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认GCJ02
//GCJ02：国测局坐标；
//BD09ll：百度经纬度坐标；
//BD09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        int span = 1000;
        option.setScanSpan(span);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效
        option.setIsNeedAddress(true);

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        //option.setWifiCacheTimeOut(5 * 60 * 1000);
//可选，V7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    private void initEvents() {

        city_name_Tv = (TextView) findViewById(R.id.title_city_name);       //初始化城市名称
        cityTv = (TextView) findViewById(R.id.city);                        //初始化城市
        timeTv = (TextView) findViewById(R.id.time);                        //初始化更新时间
        humidityTv = (TextView) findViewById(R.id.humidity);                //初始化湿度
        weekTv = (TextView) findViewById(R.id.week_today);                  //初始化日期
        pmDateTv = (TextView) findViewById(R.id.pm_data);                   //初始化pm25
        pmQualtyTv = (TextView) findViewById(R.id.pm2_5_quality);           //初始化空气质量
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);                   //初始化pm25图片
        temperatureTv = (TextView) findViewById(R.id.temperature);          //初始化温度
        temperature_infoTv = (TextView) findViewById(R.id.temperature_info); //初始化天气信息
        climateTv = (TextView) findViewById(R.id.climate);                  //初始化天气情况
        windTv = (TextView) findViewById(R.id.wind);                        //初始化风力
        weatherImg = (ImageView) findViewById(R.id.weather_img);            //初始化天气图片

        thisdate = (TextView)findViewById(R.id.next_day_week);
        nextdate = (TextView)findViewById(R.id.next_two_day_week);
        next_2_date = (TextView)findViewById(R.id.next_three_day_week);
        next_3_date = (TextView)findViewById(R.id.next_four_day_week);
        next_4_date = (TextView)findViewById(R.id.next_five_day_week);
        next_5_date = (TextView)findViewById(R.id.next_six_day_week);

        thisimg = (ImageView)findViewById(R.id.next_day_img_weather);
        nextimg = (ImageView)findViewById(R.id.next_two_day_img_weather) ;
        next_2_img =(ImageView)findViewById(R.id.next_three_day_img_weather) ;
        next_3_img =(ImageView)findViewById(R.id.next_four_day_img_weather) ;
        next_4_img =(ImageView)findViewById(R.id.next_five_day_img_weather) ;
        next_5_img =(ImageView)findViewById(R.id.next_six_day_img_weather) ;

        thistem = (TextView)findViewById(R.id.next_day_tem);
        nexttem = (TextView)findViewById(R.id.next_two_day_tem);
        next_2_tem = (TextView)findViewById(R.id.next_three_day_tem) ;
        next_3_tem = (TextView)findViewById(R.id.next_four_day_tem) ;
        next_4_tem = (TextView)findViewById(R.id.next_five_day_tem) ;
        next_5_tem = (TextView)findViewById(R.id.next_six_day_tem) ;

        thisfengli = (TextView)findViewById(R.id.next_day_fengli);
        nextfengli = (TextView)findViewById(R.id.next_two_day_fengli);
        next_2_fengli = (TextView)findViewById(R.id.next_three_day_fengli);
        next_3_fengli = (TextView)findViewById(R.id.next_four_day_fengli);
        next_4_fengli = (TextView)findViewById(R.id.next_five_day_fengli);
        next_5_fengli = (TextView)findViewById(R.id.next_six_day_fengli);

        thistype = (TextView)findViewById(R.id.next_day_weather_info);
        nexttype = (TextView)findViewById(R.id.next_two_day_weather_info) ;
        next_2_type = (TextView)findViewById(R.id.next_three_day_weather_info);
        next_3_type = (TextView)findViewById(R.id.next_four_day_weather_info);
        next_4_type = (TextView)findViewById(R.id.next_five_day_weather_info);
        next_5_type = (TextView)findViewById(R.id.next_six_day_weather_info);



        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);   //初始化更新按钮
        mUpdateBtn.setOnClickListener(this);                            //为更新按钮添加监听事件

        mLocation = (ImageView) findViewById(R.id.title_location);
        mLocation.setOnClickListener(this);

        mShare = (ImageView) findViewById(R.id.title_share);
        mShare.setOnClickListener(this);

        mCitySelect = (ImageView) findViewById(R.id.title_city_manager);    //初始化城市选择按钮
        mCitySelect.setOnClickListener(this);                               //为按钮添加监听事件


        mprogressBar = (ImageView) findViewById(R.id.title_update_progressbar);
        // mprogressBar.setOnClickListener(this);

        used_sp = getSharedPreferences("UsedInfo", 0);

        isFirstUsed = used_sp.getBoolean("isFirstUsed", true);
        //thisCityCode = used_sp.getString("thisCityCode","default");

        //实例化Editor对象
        editor = used_sp.edit();
    }

    private TodayWeather parseXML(String xmldata) {                         //XML 解析函数
        TodayWeather todayWeather = null;//设置今日天气对象
        TodayWeather todayWeather_1 = null;
       TodayWeather todayWeather_2 = null;
        TodayWeather todayWeather_3 = null;
        TodayWeather todayWeather_4 = null;
       TodayWeather todayWeather_5 = null;

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
                            todayWeather_1 = new TodayWeather();
                            todayWeather_2 = new TodayWeather();
                            todayWeather_3 = new TodayWeather();
                            todayWeather_4 = new TodayWeather();
                            todayWeather_5 = new TodayWeather();

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
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount==0) {//解析风向
                                eventType = xmlPullParser.next();
                                todayWeather.setFengxiang(xmlPullParser.getText());
                                Log.d("myWeather", "fengxiang: " + xmlPullParser.getText());
                                fengxiangCount++;

                            } else if (xmlPullParser.getName().equals("fengli")&&fengliCount==0 ) {      //解析风力
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli(xmlPullParser.getText());
                                Log.d("myWeather", "fengli: " + xmlPullParser.getText());
                                fengliCount++;

                            } else if (xmlPullParser.getName().equals("date") &&dateCount==0) {          //解析日期
                                eventType = xmlPullParser.next();
                                todayWeather.setDate(xmlPullParser.getText());
                                Log.d("myWeather", "date: " + xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") &&highCount==0) {          //解析高温
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh(xmlPullParser.getText().substring(2).trim());
                                Log.d("myWeather", "high: " + xmlPullParser.getText());
                                highCount++;

                            } else if (xmlPullParser.getName().equals("low")&&lowCount==0 ) {            //解析低温
                                eventType = xmlPullParser.next();
                                todayWeather.setLow(xmlPullParser.getText().substring(2).trim());
                                Log.d("myWeather", "low: " + xmlPullParser.getText());
                                lowCount++;

                            } else if (xmlPullParser.getName().equals("type")&&typeCount==0 ) {          //解析类型
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
        //Log.d("myWeather",todayWeather_1.toString());
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

    public float getInterpolation(float input) {
        return input;
    }

    @Override
    public void onClick(View view) {//按钮监听事件

        switch (view.getId()) {
            case R.id.title_city_manager:
                Intent i = new Intent(this, SelectCity.class);
                startActivityForResult(i, 1);
                break;
            case R.id.title_share:
                break;
            case R.id.title_update_btn:
                mUpdateBtn.setVisibility(view.GONE);
                mprogressBar.setVisibility(view.VISIBLE);
                SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
                //String cityCode = sharedPreferences.getString("main_city_code", "101010100");//获取城市代码
                //Log.d("myWeather", cityCode);

                if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {        //获取网络状态
                    Log.d("goodweather", "Internet OK");
                    //mprogressBar.setVisibility(view.VISIBLE);
                    queryWeatherCode(newCityCode);                                             //网络OK，请求获取城市代码
                    Toast.makeText(MainActivity.this, "Internet OK", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("goodweather", "Internet Error");                      //无网络
                    Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.title_location:
                mUpdateBtn.setVisibility(view.GONE);
                mprogressBar.setVisibility(view.VISIBLE);

                if (mLocationClient.isStarted()) {
                    mLocationClient.stop();
                }
                mLocationClient.start();


                if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {        //获取网络状态
                    Log.d("goodweather", "Internet OK");
                    //mprogressBar.setVisibility(view.VISIBLE);
                    queryWeatherCode(myListener.reCityCode());
                    Toast.makeText(MainActivity.this, newCityCode, Toast.LENGTH_SHORT).show();
                    //网络OK，请求获取城市代码
                    Toast.makeText(MainActivity.this, "Internet OK", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("goodweather", "Internet Error");                      //无网络
                    Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//利用意图获取城市代码
        if (requestCode == 1 && resultCode == RESULT_OK) {
            newCityCode = data.getStringExtra("cityCode");            //获取城市代码
            Log.d("myWeather", "选择的城市代码为" + newCityCode);

            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {  //网络状态
                Log.d("goodweather", "Internet OK");//网络OK
                queryWeatherCode(newCityCode);
                //存入数据
                editor.putBoolean("isFirstUsed", false);
                //editor.putString("thisCityCode",newCityCode);
                //提交修改
                editor.commit();
                Toast.makeText(MainActivity.this, "Internet OK", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "thisCityCode:" + newCityCode, Toast.LENGTH_SHORT).show();
            } else {
                Log.d("goodweather", "Internet Error");                 //网络错误
                Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_SHORT).show();
            }
        }


    }

    void initView_weather() {           //天气初始化函数

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
        mprogressBar.setVisibility(View.GONE);
        mUpdateBtn.setVisibility(View.VISIBLE);
        city_name_Tv.setText(todayWeather.getCity() + "天气");               //设置顶部城市
        cityTv.setText(todayWeather.getCity());                             //设置城市
        timeTv.setText(todayWeather.getUpdatetime() + "发布");              //设置更新时间
        temperature_infoTv.setText(todayWeather.getWendu()); //设置温度
        humidityTv.setText("湿度:" + todayWeather.getShidu());                //设置湿度
        if (todayWeather.getPm25() != null) pmDateTv.setText(todayWeather.getPm25());
        else pmDateTv.setText("无");
        if (todayWeather.getQuality() != null) pmQualtyTv.setText(todayWeather.getQuality());
        else pmQualtyTv.setText("无");
        //weekTv.setText(todayWeather.getDate());
        //pmDateTv.setText(todayWeather.getPm25());                           //设置PM25
        //pmQualtyTv.setText(todayWeather.getQuality());                      //设置空气质量
        weekTv.setText("今天" + todayWeather.getDate());                    //设置日期
        temperatureTv.setText(todayWeather.getHigh() + "~" + todayWeather.getLow());//设置温度范围
        climateTv.setText(todayWeather.getType());                          //设置天气状况
        windTv.setText("风力:" + todayWeather.getFengxiang() + todayWeather.getFengli());//设置星期
        //Toast.makeText(MainActivity.this, "更新成功！", Toast.LENGTH_LONG).show();//显示更新成功

   if (todayWeather.getPm25() != null) {
            int pm2_5 = Integer.parseInt(todayWeather.getPm25());
            if (pm2_5 <= 50) pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
            if (pm2_5 > 50 && pm2_5 <= 100)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_51_100);
            if (pm2_5 > 100 && pm2_5 <= 150)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
            if (pm2_5 > 150 && pm2_5 <= 200)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
            if (pm2_5 > 200 && pm2_5 <= 300)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
            if (pm2_5 > 300) pmImg.setImageResource(R.drawable.biz_plugin_weather_greater_300);
        }


        //根据解析的天气类型更新界面的天气图案
        String climate = todayWeather.getType();
        if (climate.equals("暴雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
            // thisimg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        if (climate.equals("暴雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
            //thisimg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        if (climate.equals("大暴雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
           // thisimg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        if (climate.equals("大雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
           // thisimg.setImageResource(R.drawable.biz_plugin_weather_daxue);
        if (climate.equals("大雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_dayu);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_dayu);
        if (climate.equals("多云"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
           // thisimg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        if (climate.equals("雷阵雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        if (climate.equals("雷阵雨冰雹"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        if (climate.equals("晴"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_qing);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_qing);
        if (climate.equals("沙尘暴"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
          //   thisimg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        if (climate.equals("特大暴雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        if (climate.equals("雾"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_wu);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_wu);
        if (climate.equals("小雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        if (climate.equals("小雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
            //thisimg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        if (climate.equals("阴"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_yin);
         //    thisimg.setImageResource(R.drawable.biz_plugin_weather_yin);
        if (climate.equals("雨夹雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        if (climate.equals("阵雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        if (climate.equals("阵雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
      //  thisimg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        if (climate.equals("中雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
          //  thisimg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        if (climate.equals("中雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
           // thisimg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
        Toast.makeText(MainActivity.this, "更新成功!", Toast.LENGTH_LONG).show();
    }

    void updateForcastWeather(TodayWeather todayWeather_1,TodayWeather todayWeather_2,TodayWeather todayWeather_3,TodayWeather todayWeather_4,TodayWeather todayWeather_5){

        nextdate.setText(todayWeather_1.getDate());
        next_2_date.setText(todayWeather_2.getDate());
        next_3_date.setText(todayWeather_3.getDate());
        next_4_date.setText(todayWeather_4.getDate());
        next_5_date.setText(todayWeather_5.getDate());


        nextimg =(ImageView)findViewById(R.id.next_two_day_img_weather) ;
        next_2_img =(ImageView)findViewById(R.id.next_three_day_img_weather) ;
        next_3_img =(ImageView)findViewById(R.id.next_four_day_img_weather) ;
        next_4_img =(ImageView)findViewById(R.id.next_five_day_img_weather) ;
        next_5_img =(ImageView)findViewById(R.id.next_six_day_img_weather) ;


        nexttem.setText(todayWeather_1.getLow() + "~" + todayWeather_1.getHigh());
        next_2_tem.setText(todayWeather_2.getLow() + "~" + todayWeather_2.getHigh());
        next_3_tem.setText(todayWeather_3.getLow() + "~" + todayWeather_3.getHigh());
        next_4_tem.setText(todayWeather_4.getLow() + "~" + todayWeather_4.getHigh());
        next_5_tem.setText(todayWeather_5.getLow() + "~" + todayWeather_5.getHigh());


        nextfengli.setText(todayWeather_1.getFengli());
        next_2_fengli.setText(todayWeather_2.getFengli());
        next_3_fengli.setText(todayWeather_3.getFengli());
        next_4_fengli.setText(todayWeather_4.getFengli());
        next_5_fengli.setText(todayWeather_5.getFengli());


        nexttype.setText(todayWeather_1.getType());
        next_2_type.setText(todayWeather_2.getType());
        next_3_type.setText(todayWeather_3.getType());
        next_4_type.setText(todayWeather_4.getType());
        next_5_type.setText(todayWeather_5.getType());

        String climate = todayWeather_1.getType();
        if (climate.equals("暴雪"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        if (climate.equals("暴雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        if (climate.equals("大暴雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        if (climate.equals("大雪"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_daxue);
        if (climate.equals("大雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_dayu);
        if (climate.equals("多云"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        if (climate.equals("雷阵雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        if (climate.equals("雷阵雨冰雹"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        if (climate.equals("晴"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_qing);
        if (climate.equals("沙尘暴"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        if (climate.equals("特大暴雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        if (climate.equals("雾"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_wu);
        if (climate.equals("小雪"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        if (climate.equals("小雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        if (climate.equals("阴"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_yin);
        if (climate.equals("雨夹雪"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        if (climate.equals("阵雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        if (climate.equals("阵雪"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        if (climate.equals("中雪"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        if (climate.equals("中雨"))
            nextimg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);

        String climate1 = todayWeather_2.getType();
        if (climate1.equals("暴雪"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        if (climate1.equals("暴雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        if (climate1.equals("大暴雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        if (climate1.equals("大雪"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_daxue);
        if (climate1.equals("大雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_dayu);
        if (climate1.equals("多云"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        if (climate1.equals("雷阵雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        if (climate1.equals("雷阵雨冰雹"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        if (climate1.equals("晴"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_qing);
        if (climate1.equals("沙尘暴"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        if (climate1.equals("特大暴雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        if (climate1.equals("雾"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_wu);
        if (climate1.equals("小雪"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        if (climate1.equals("小雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        if (climate1.equals("阴"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_yin);
        if (climate1.equals("雨夹雪"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        if (climate1.equals("阵雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        if (climate1.equals("阵雪"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        if (climate1.equals("中雪"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        if (climate1.equals("中雨"))
            next_2_img.setImageResource(R.drawable.biz_plugin_weather_zhongyu);

        String climate2 = todayWeather_3.getType();
        if (climate2.equals("暴雪"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        if (climate2.equals("暴雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        if (climate2.equals("大暴雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        if (climate2.equals("大雪"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_daxue);
        if (climate2.equals("大雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_dayu);
        if (climate2.equals("多云"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        if (climate2.equals("雷阵雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        if (climate2.equals("雷阵雨冰雹"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        if (climate2.equals("晴"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_qing);
        if (climate2.equals("沙尘暴"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        if (climate2.equals("特大暴雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        if (climate2.equals("雾"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_wu);
        if (climate2.equals("小雪"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        if (climate2.equals("小雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        if (climate2.equals("阴"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_yin);
        if (climate2.equals("雨夹雪"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        if (climate2.equals("阵雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        if (climate2.equals("阵雪"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        if (climate2.equals("中雪"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        if (climate2.equals("中雨"))
            next_3_img.setImageResource(R.drawable.biz_plugin_weather_zhongyu);

        String climate4 = todayWeather_4.getType();
        if (climate4.equals("暴雪"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        if (climate4.equals("暴雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        if (climate4.equals("大暴雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        if (climate4.equals("大雪"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_daxue);
        if (climate4.equals("大雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_dayu);
        if (climate4.equals("多云"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        if (climate4.equals("雷阵雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        if (climate4.equals("雷阵雨冰雹"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        if (climate4.equals("晴"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_qing);
        if (climate4.equals("沙尘暴"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        if (climate4.equals("特大暴雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        if (climate4.equals("雾"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_wu);
        if (climate4.equals("小雪"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        if (climate4.equals("小雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        if (climate4.equals("阴"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_yin);
        if (climate4.equals("雨夹雪"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        if (climate4.equals("阵雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        if (climate4.equals("阵雪"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        if (climate4.equals("中雪"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        if (climate4.equals("中雨"))
            next_4_img.setImageResource(R.drawable.biz_plugin_weather_zhongyu);

        String climate3 = todayWeather_5.getType();
        if (climate3.equals("暴雪"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        if (climate3.equals("暴雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        if (climate3.equals("大暴雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        if (climate3.equals("大雪"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_daxue);
        if (climate3.equals("大雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_dayu);
        if (climate3.equals("多云"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        if (climate3.equals("雷阵雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        if (climate3.equals("雷阵雨冰雹"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        if (climate3.equals("晴"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_qing);
        if (climate3.equals("沙尘暴"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        if (climate3.equals("特大暴雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        if (climate3.equals("雾"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_wu);
        if (climate3.equals("小雪"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        if (climate3.equals("小雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        if (climate3.equals("阴"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_yin);
        if (climate3.equals("雨夹雪"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        if (climate3.equals("阵雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        if (climate3.equals("阵雪"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        if (climate3.equals("中雪"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        if (climate3.equals("中雨"))
            next_5_img.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
    }
}

