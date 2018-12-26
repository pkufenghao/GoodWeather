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
    private String                      //六天天气
            week_today,temperatureH,temperatureL,climate,wind,week_today1,temperatureH1
            ,temperatureL1,climate1,wind1,week_today2,temperatureH2,temperatureL2,climate2,wind2;
    private String
            week_today3,temperatureH3,temperatureL3,climate3,wind3,week_today4,temperatureH4,
            temperatureL4,climate4,wind4,week_today5,temperatureH5,temperatureL5,
            climate5,wind5,fengl,fengl1,fengl2,fengl3,fengl4,fengl5;

    public String getWeek_today(){return week_today; }
    public String getTemperatureH(){return temperatureH;}
    public String getTemperatureL(){return temperatureL;}
    public String getClimate(){return climate;}
    public String getWind(){return wind;}
    public String getWeek_today1(){return week_today1;}
    public String getTemperatureH1(){return temperatureH1;}
    public String getTemperatureL1(){return temperatureL1;}
    public String getClimate1(){return climate1;}
    public String getWind1(){return wind1;}
    public String getWeek_today2(){return week_today2;}
    public String getTemperatureH2(){return temperatureH2;}
    public String getTemperatureL2(){return temperatureL2; }
    public String getClimate2(){ return climate2;}
    public String getWind2(){return  wind2;}

    public String getWeek_today3(){return week_today3;}
    public String getTemperatureH3(){return temperatureH3;}
    public String getTemperatureL3(){return temperatureL3;}
    public String getClimate3(){return climate3;}
    public String getWind3(){return wind3;}
    public String getWeek_today4(){return week_today4;}
    public String getTemperatureH4(){return temperatureH4;}
    public String getTemperatureL4(){return temperatureL4;}
    public String getClimate4(){return climate4;}
    public String getWind4(){return wind4;}
    public String getWeek_today5(){return week_today5;}
    public String getTemperatureH5(){return temperatureH5;}
    public String getTemperatureL5(){return temperatureL5;}
    public String getClimate5(){ return climate5;}
    public String getWind5(){return wind5;}

    public String getFengl(){return fengl;}
    public String getFengl1(){return fengl1;}
    public String getFengl2(){return fengl2;}
    public String getFengl3(){return fengl3;}
    public String getFengl4(){return fengl4;}
    public String getFengl5(){return fengl5;}


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
    public void setQuality(String quality){ this.quality = quality; }
    public void setFengxiang(String fengxiang){    //设置风向
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
    public void setWeek_today(String week_today){
        this.week_today=week_today;
    }
    public void setTemperatureH(String temperatureH){
        this.temperatureH=temperatureH;
    }
public void setTemperatureL(String temperatureL){
        this.temperatureL=temperatureL;
}
public void setClimate(String climate){
        this.climate=climate;
}
public void setWind(String wind){
        this.wind=wind;
}
public void setWeek_today1(String week_today1){
        this.week_today1=week_today1;
}
public void setTemperatureH1(String temperatureH1){
        this.temperatureH1=temperatureH1;
}
public void setTemperatureL1(String temperatureL1){
        this.temperatureL1=temperatureL1;
}
public void setClimate1(String climate1){
        this.climate1=climate1;
}
public void setWind1(String wind1){
        this.wind1=wind1;

}
public void setWeek_today2(String week_today2){
        this.week_today2=week_today2;
}
public void  setTemperatureH2(String temperatureH2){
        this.temperatureH2=temperatureH2;
}
public void setTemperatureL2(String temperatureL2){
        this.temperatureL2=temperatureL2;
}
public void setClimate2(String climate2){
        this.climate2=climate2;
}
public void setWind2(String wind2){
        this.wind2=wind2;
}
public void setWeek_today3(String week_today3){
        this.week_today3=week_today3;
}
public void setTemperatureH3(String temperatureH3){
        this.temperatureH3=temperatureH3;

}
public void setTemperatureL3(String temperatureL3){
        this.temperatureL3=temperatureL3;
}
public void setClimate3(String climate3){
        this.climate3=climate3;
}
public void setWind3(String wind3){
        this.wind3=wind3;
}
public void setWeek_today4(String week_today4){
        this.week_today4=week_today4;
}
public void setTemperatureH4(String temperatureH4){
        this.temperatureH4=temperatureH4;
}
public void setTemperatureL4(String temperatureL4){
        this.temperatureL4=temperatureL4;
}
public void setClimate4(String climate4){
        this.climate4=climate4;
}
public void setWind4(String wind4){
        this.wind4=wind4;
}
public void setWeek_today5(String week_today5){
        this.week_today5=week_today5;
}
public void setTemperatureH5(String temperatureH5){
        this.temperatureH5=temperatureH5;
}
public void setTemperatureL5(String temperatureL5){
        this.temperatureL5 =temperatureL5;
}
public void setClimate5(String climate5){
        this.climate5=climate5;

}
public void setWind5(String wind5){
        this.wind5=wind5;
}
public void setFengl(String fengl){
        this.fengl=fengl;
}
    public void setFengl1(String fengl1){
        this.fengl1=fengl1;
    }
    public void setFengl2(String fengl2){
        this.fengl2=fengl2;
    }
    public void setFengl3(String fengl3){
        this.fengl3=fengl3;
    }
    public void setFengl4(String fengl4){
        this.fengl4=fengl4;
    }
    public void setFengl5(String fengl5){
        this.fengl5=fengl5;
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
                ", Week_today='" + week_today + '\'' +
                ", temperatureH='" + temperatureH + '\'' +
                ", temperatureL='" + temperatureL + '\'' +
                ", climate='" + climate + '\'' +
                ", wind='" + wind + '\'' +
                ", Week_today1='" + week_today1 + '\'' +
                ", temperatureH1='" + temperatureH1 + '\'' +
                ", temperatureL1='" + temperatureL1 + '\'' +
                ", climate1='" + climate1 + '\'' +
                ", wind1='" + wind1 + '\'' +
                ", Week_today2='" + week_today2 + '\'' +
                ", temperatureH2='" + temperatureH2 + '\'' +
                ", temperatureL2='" + temperatureL2 + '\'' +
                ", climate2='" + climate2 + '\'' +
                ", wind2='" + wind2 + '\'' +
                ", Week_today3='" + week_today3 + '\'' +
                ", temperatureH3='" + temperatureH3 + '\'' +
                ", temperatureL3='" + temperatureL3 + '\'' +
                ", climate3='" + climate3 + '\'' +
                ", wind3='" + wind3 + '\'' +
                ", Week_today4='" + week_today4 + '\'' +
                ", temperatureH4='" + temperatureH4 + '\'' +
                ", temperatureL4='" + temperatureL4 + '\'' +
                ", climate4='" + climate4 + '\'' +
                ", wind4='" + wind4 + '\'' +
                ", Week_today5='" + week_today5 + '\'' +
                ", temperatureH5='" + temperatureH5 + '\'' +
                ", temperatureL5='" + temperatureL5 + '\'' +
                ", climate5='" + climate5 + '\'' +
                ", fengl='" + fengl + '\'' +
                ", fengl1='" + fengl1 + '\'' +
                ", fengl2='" + fengl2 + '\'' +
                ", fengl3='" + fengl3 + '\'' +
                ", fengl4='" + fengl4 + '\'' +
                ", fengl5='" + fengl5 + '\'' +

                '}';
    }

}
