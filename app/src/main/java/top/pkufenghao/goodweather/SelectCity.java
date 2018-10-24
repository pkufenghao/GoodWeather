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

    private TextView title_city_name;

    private ImageView mBackBtn;

    private ListView mlistView;

    private List<City> citylist,filtercitylist;

    //private  String title_cityname_holder;

    private ArrayAdapter  myadapter;

    private ArrayList<String> list;



    private void initView(){

       // TodayWeather todayWeather ;

        title_city_name = (TextView)findViewById(R.id.title_name);

        mlistView = (ListView)findViewById(R.id.list_view);

        //mBackBtn = (ImageView)findViewById(R.id.title_back);
        //mBackBtn.setOnClickListener(this);

       // title_city_name.setText("当前城市：" + todayWeather.getCity());

        list = new ArrayList<>();

        MyApplication myApplication = (MyApplication)getApplication();

        citylist = myApplication.getCityList();



        for (City  city  : citylist){

            list.add(city.getCity());

        }

        myadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        mlistView.setAdapter(myadapter);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                City city = citylist.get(position);
                Intent i = new Intent();
                i.putExtra("cityCode",city.getNumber());
                title_city_name.setText("选择城市：" + city.getCity());
                //title_cityname_holder = city.getCity();
                setResult(RESULT_OK,i);
                //finish();

            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_city);

        mBackBtn = (ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        initView();


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:
                //Intent i = new Intent();
                //i.putExtra("cityCode","101091103");
                // setResult(RESULT_OK,i);
                finish();
                break;
            default:
                break;
        }
    }



}
