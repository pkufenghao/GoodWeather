package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import cn.edu.pku.fenghao.app.MyApplication;
import cn.edu.pku.fenghao.bean.City;
import cn.edu.pku.fenghao.bean.TodayWeather;

public class SelectCity extends Activity implements View.OnClickListener{

    private TextView title_city_name;       //顶部文本控件，显示选择城市名称

    private ImageView mBackBtn;             //返回按钮图片控件

    private ListView mlistView;             //列表控件

    private List<City> citylist;            //列表控件，存储城市信息

    private ArrayAdapter  myadapter;        //列表适配器

    private ArrayList<String> list;         //存储字符串的列表控件



    private void initView(){                                        //界面初始化函数

       // TodayWeather todayWeather ;

        title_city_name = (TextView)findViewById(R.id.title_name);  //顶部文本显示选择城市信息

        mlistView = (ListView)findViewById(R.id.list_view);         //列表控件显示城市列表

        //mBackBtn = (ImageView)findViewById(R.id.title_back);
        //mBackBtn.setOnClickListener(this);

       // title_city_name.setText("当前城市：" + todayWeather.getCity());

        list = new ArrayList<>();                                   //初始化列表控件

        MyApplication myApplication = (MyApplication)getApplication();//调用MyApplication类，并实例化

        citylist = myApplication.getCityList();                        //通过调用获取城市信息

        for (City  city  : citylist){                                   //循环将城市名称添加到list列表中

            list.add(city.getCity());

        }

        myadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);//初始化列表适配器
        mlistView.setAdapter(myadapter);                                //设置myadapter为列表适配器

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//为列表中项目设置监听事件
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                City city = citylist.get(position);                         //获取点击的位置，即可获得城市信息
                Intent i = new Intent();                                    //初始化意图事件
                i.putExtra("cityCode",city.getNumber());               //将城市代码发回主线程
                title_city_name.setText("选择城市：" + city.getCity());        //顶部文字显示选择城市
                //title_cityname_holder = city.getCity();
                setResult(RESULT_OK,i);
                //finish();

            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState){     //活动执行程序
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_city);               //设置布局文件

        mBackBtn = (ImageView)findViewById(R.id.title_back);//寻找并设置返回按钮图片
        mBackBtn.setOnClickListener(this);                  //为返回按钮设置监听事件
        initView();                                         //初始化选择城市界面


    }

    @Override
    public void onClick(View v){      //返回按钮事件
        switch (v.getId()){
            case R.id.title_back:       //当为返回按钮时，执行下面程序
                //Intent i = new Intent();
                //i.putExtra("cityCode","101091103");
                // setResult(RESULT_OK,i);
                finish();               //结束该活动
                break;
            default:
                break;
        }
    }



}
