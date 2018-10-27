package cn.edu.pku.fenghao.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.fenghao.bean.City;

public class CityDB {                                   //城市数据库
    public static final String CITY_DB_NAME = "city.db";//设置数据库名称
    private static final String CITY_TABLE_NAME = "city";//设置城市列表名称
    private SQLiteDatabase db;                          //设置数据库类型

    public CityDB (Context context,String path){        //构造函数
        db = context.openOrCreateDatabase(path,Context.MODE_PRIVATE,null);
    }

    public List<City> getAllCity(){                     //列表函数，返回城市列表
        List<City> list = new ArrayList<City>();            //初始化列表
        Cursor c = db.rawQuery("SELECT * from " + CITY_TABLE_NAME,null);//查找数据库
        while (c.moveToNext()){                         //数据库下一项不为空，即执行循环
            String province = c.getString(c.getColumnIndex("province"));//获得省份
            String city = c.getString(c.getColumnIndex("city"));        //获得城市名称
            String number = c.getString(c.getColumnIndex("number"));    //获得城市代码
            String allPY = c.getString(c.getColumnIndex("allpy"));      //获得城市字母
            String allFirstPY = c.getString(c.getColumnIndex("allfirstpy"));//获得城市第一个字母
            String firstPY = c.getString(c.getColumnIndex("firstpy"));  //获得第一个字母
            City item = new City(province,city, number, firstPY, allPY, allFirstPY);//初始化City对象item
            list.add(item);                                                         //将item添加进列表
        }
        return list;                                                                //返回列表
    }
}
