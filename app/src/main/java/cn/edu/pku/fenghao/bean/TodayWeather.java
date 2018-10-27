package cn.edu.pku.fenghao.bean;

public class TodayWeather {             //今日天气
    private String city;                //城市
    private String updatetime;          //更新时间
    private String wendu;               //温度
    private String shidu;               //湿度
    private String pm25;                //pm25
    private String quality;             //空气质量
    private String fengxiang;           //风向
    private String fengli;              //风力
    private String date;                //星期
    private String high;                //高温
    private String low;                 //低温
    private String type;                //类型

    public String getCity() {           //获取城市名称
        return city;
    }

    public String getUpdatetime() {     //获取更新时间
        return updatetime;
    }

    public String getWendu() {          //获取温度
        return wendu;
    }

    public String getDate() {           //获取日期
        return date;
    }

    public String getFengli() {         //获取风力
        return fengli;
    }

    public String getFengxiang() {      //获取风向
        return fengxiang;
    }

    public String getHigh() {           //获取高温
        return high;
    }

    public String getLow() {            //获取低温
        return low;
    }

    public String getPm25() {           //获取pm25
        return pm25;
    }

    public String getQuality() {        //获取空气质量
        return quality;
    }

    public String getShidu() {          //获取湿度
        return shidu;
    }

    public String getType() {           //获取类型
        return type;
    }
    public void setCity(String city){              //设置城市名称
        this.city = city;
    }
    public void setUpdatetime(String updatetime){  //设置更新时间
        this.updatetime = updatetime;
    }
    public void setWendu(String wendu){             //设置温度
        this.wendu = wendu;
    }
    public void setShidu(String shidu){             //设置湿度
        this.shidu = shidu;
    }
    public void setPm25(String pm25){               //设置pm25
        this.pm25 = pm25;
    }
    public void setQuality(String quality){         //设置空气质量
        this.quality = quality;
    }public void setFengxiang(String fengxiang){    //设置风向
        this.fengxiang = fengxiang;
    }
    public void setFengli(String fengli){           //设置风力
        this.fengli = fengli;
    }
    public void setDate (String date){              //设置日期
        this.date = date;
    }
    public void setHigh(String high){               //设置高温
        this.high = high;
    }
    public void  setLow(String low){                //设置低温
        this.low = low;
    }
    public void setType(String type){               //设置类型
        this.type = type;
    }

    @Override
    public String toString(){                       //今日天气字符串
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", quality='" + quality + '\''+
                ", fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli  +'\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
