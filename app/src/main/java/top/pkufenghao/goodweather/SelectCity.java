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

public class SelectCity extends Activity implements View.OnClickListener {

    private TextView title_city_name;       //顶部文本控件，显示选择城市名称

    private ImageView mBackBtn;             //返回按钮图片控件

    private ListView mlistView;             //列表控件

    private EditText mEditText;             //文本输入

    private List<City> citylist;            //列表控件，存储城市信息

    private ArrayAdapter myadapter;        //列表适配器

    private ArrayList<String> list_city;         //存储城市名称的列表控件

    private ArrayList<String> list_allpy;       //存储城市名称大写字母的列表控件

    private ArrayList<String> list_firstpy;     //存储城市名称第一个字大写列表控件

    private ArrayList<String> list_allfirstpy;  //存储城市名称首字母大写的列表控件

    private ArrayList<String> newlist;          //存储搜索后的城市名称的列表控件


    private void initView() {                                        //界面初始化函数


        title_city_name = (TextView) findViewById(R.id.title_name);  //顶部文本显示选择城市信息

        mlistView = (ListView) findViewById(R.id.list_view);         //列表控件显示城市列表

        mEditText = (EditText) findViewById(R.id.search_edit);
        mEditText.addTextChangedListener(mTextWatcher);

        list_city = new ArrayList<>();
        list_allpy = new ArrayList<>();
        list_firstpy = new ArrayList<>();
        list_allfirstpy = new ArrayList<>(); //初始化列表控件
        newlist = new ArrayList<>();
        MyApplication myApplication = (MyApplication) getApplication();//调用MyApplication类，并实例化

        citylist = myApplication.getCityList();                        //通过调用获取城市信息

        for (City city : citylist) {                                   //循环将城市名称添加到list列表中

            list_city.add(city.getCity());                               //城市名称

            list_allpy.add(city.getAllPY());                             //城市名称大写字母

            list_firstpy.add(city.getFirstPY());                          //城市名称第一字大写字母

            list_allfirstpy.add(city.getAllFirstPY());                    //城市名称首字母大写
        }

        myadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list_city);//初始化列表适配器
        mlistView.setAdapter(myadapter);                                //设置myadapter为列表适配器

    }

    private ArrayList<String> getNewData(String input_info) {             //得到搜索后的新列表
        for (int i = 0; i < list_city.size(); i++) {
            String input = list_city.get(i);                             //获得城市名称
            String input_allpy = list_allpy.get(i).toLowerCase();        //获得城市名称小写字母
            String input_firstpy = list_firstpy.get(i).toLowerCase();   //获得城市名称第一个字小写字母
            String input_allfirstpy = list_allfirstpy.get(i).toLowerCase();//获得城市名称首字母小写
            if (input_allpy.contains(input_info)) {
                newlist.add(input);
            }
        }
        return newlist;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {     //活动执行程序
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_city);               //设置布局文件

        mBackBtn = (ImageView) findViewById(R.id.title_back);//寻找并设置返回按钮图片
        mBackBtn.setOnClickListener(this);                  //为返回按钮设置监听事件
        initView();                                         //初始化选择城市界面

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//为列表中项目设置监听事件
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                for (City city : citylist){
                    if(city.getCity() == newlist.get(position)){                      //获取点击的位置，即可获得城市信息
                        Intent i = new Intent();                                      //初始化意图事件
                        i.putExtra("cityCode", city.getNumber());               //将城市代码发回主线程
                        title_city_name.setText("选择城市：" + city.getCity());        //顶部文字显示选择城市
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }

            }
        });

    }

    TextWatcher mTextWatcher = new TextWatcher() {                     //TextWatcher

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newlist.clear();                                        //列表清空
            if (mEditText.getText() != null) {                      //如果输入不为空
                String input_info = mEditText.getText().toString(); //得到输入字符串
                newlist = getNewData(input_info);                   //得到搜索后的列表
                myadapter = new ArrayAdapter(SelectCity.this, android.R.layout.simple_list_item_1, newlist);//初始化列表适配器
                mlistView.setAdapter(myadapter);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };


    @Override
    public void onClick(View v) {      //返回按钮事件
        switch (v.getId()) {
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
