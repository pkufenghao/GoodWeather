package cn.edu.pku.fenghao.bean;

public class City {                 //设置城市类
    private String province;        //私有字符串省份
    private String city;            //私有字符串城市
    private String number;          //私有字符串城市列表
    private String firstPY;         //第一个字母
    private String allPY;           //所有拼音
    private String allFirstPY;      //所有第一个字母

    public City (String province,String city,String number,String firstPY,String allPY,String allFirstPY){//City的构造函数
        this.province = province;   //设置省份
        this.city = city;           //设置城市名称
        this.number =number;        //城市代码
        this.firstPY = firstPY;     //第一个字母
        this.allPY = allPY;         //所有字母
        this.allFirstPY = allFirstPY;//所有第一个字母
    }

    public String getCity() {       //获取城市名称
        return city;
    }

    public String getAllFirstPY() { //获取所有第一个字母
        return allFirstPY;
    }

    public String getAllPY() {      //获取所有字母
        return allPY;
    }

    public String getFirstPY() {    //获取第一个字母
        return firstPY;
    }

    public String getNumber() {     //获取城市代码
        return number;
    }

    public String getProvince() {   //获取省份
        return province;
    }


}
