package top.pkufenghao.goodweather;

import android.app.Activity;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import java.util.List;

import cn.edu.pku.fenghao.app.MyApplication;
import cn.edu.pku.fenghao.bean.City;

public class MyLocationListener   extends BDAbstractLocationListener {

    public String cityCode;
    private String city;
    private String district;
    private String street;

    private String recity;
    private String redistrict;


    @Override
    public void onReceiveLocation(BDLocation location){
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取地址相关的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        String addr = location.getAddrStr();    //获取详细地址信息
        String country = location.getCountry();    //获取国家
        String province = location.getProvince();    //获取省份
        city = location.getCity();    //获取城市
                district = location.getDistrict();    //获取区县
         street = location.getStreet();    //获取街道信息

        recity = city.replace("市","");
        redistrict=district.replace("区","");
        Log.d("where","定位显示："+city+district+street);

    }

    public String getLocation() {
        return city+district+street;

    }

    public String reCityCode(){
        List<City> thisCityList;
        MyApplication myApplication;
        myApplication = MyApplication.getInstance();

        thisCityList = myApplication.getCityList();
        for(City city_: thisCityList){
            if (city_.getCity().equals(redistrict)){
                cityCode = city_.getNumber();
            }
        }
        Log.d("where","城市代码："+cityCode);
        return cityCode;
    }

}
