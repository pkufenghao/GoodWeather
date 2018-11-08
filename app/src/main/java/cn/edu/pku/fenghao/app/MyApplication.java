package cn.edu.pku.fenghao.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import cn.edu.pku.fenghao.bean.City;
import cn.edu.pku.fenghao.db.CityDB;

public class MyApplication extends Application{        //MyApplication继承Application类
    private static final String TAG = "MyAPP";          //定义TAG字符串
    private static MyApplication mApplication;          //初始化MyApplication对象

    private CityDB mCityDB;                             //初始化City对象

    private List<City> mCityList;                       //初始化列表

    private ListView mlistView;                         //初始化列表

    @Override
    public void onCreate(){                             //onCreate方法
        super.onCreate();
        Log.d(TAG,"MyApplication->Oncreate");       //输出信息

        mApplication = this;
        mCityDB = openCityDB();                          //获取数据库
        initCityList();


    }

    private void initCityList(){                        //初始化城市列表
        mCityList = new ArrayList<City>();              //初始化列表
        new Thread(new Runnable(){
            @Override
            public  void run(){
                prepareCityList();
            }
        }).start();//初始化新的编程，运行函数
    }

    private boolean prepareCityList(){                  //准备获取城市列表
        mCityList = mCityDB.getAllCity();               //从数据库中获取所有城市
        int i = 0;
        for (City city : mCityList){                    //遍历城市列表
            i++;
            String cityName = city.getCity();           //获取城市名称
            String cityCode = city.getNumber();         //获取城市代码
            Log.d(TAG,cityCode+":"+cityName);       //输出城市名称和代码
        }
        Log.d(TAG,"i="+i);
        return true;
    }

    public List<City> getCityList(){                    //获取城市列表
        return mCityList;
    }

    public static MyApplication getInstance(){          //获取单例Application
        return mApplication;
    }

    private CityDB openCityDB(){                                    //打开数据库
        String path = "/data"                                       //拼接路径
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "database1"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);                                   //从路径中查找数据库并初始化数据库
        Log.d(TAG,path);
        if (!db.exists()){                                          //如果数据库不存在，则在该路径下创建数据库
            String pathfolder =  "/data"
                    + Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + getPackageName()
                    + File.separator + "database1"
                    + File.separator;
            File dirFirstFolder = new File(pathfolder);             //初始化数据库
            if (!dirFirstFolder.exists()){                          //如果文件夹不存在，则新建一个
                dirFirstFolder.mkdirs();
                Log.i("MyAPP","mkdirs");
            }
            Log.i("MyAPP","db is not exists");
            try{
                InputStream is = getAssets().open("city.db");//打开数据库
                FileOutputStream fos = new FileOutputStream(db);        //初始化文件输出
                int len = -1;
                byte[] buffer = new byte[1024];                         //初始化缓存
                while ((len = is.read(buffer))!= -1){
                    fos.write(buffer,0,len);
                    fos.flush();
                }
                fos.close();
                is.close();
            }catch (IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this,path);
    }

}
