package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends Activity{

    //是否是第一次使用
    private boolean isFirstUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        SharedPreferences preferences = getSharedPreferences("isFirstUse", 0);
        isFirstUse = preferences.getBoolean("isFirstUse", true);
        /**
         *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
         */
        if (isFirstUse) {
            Intent i = new Intent(this, GuideActivity.class);
            startActivity(i);
        } else {
            Intent j = new Intent(this, WelcomeActivity.class);
            startActivity(j);
        }
        finish();
        //实例化Editor对象
        SharedPreferences.Editor editor = preferences.edit();
        /////正常情况为false
        editor.putBoolean("isFirstUse", true);
        //提交修改
        editor.commit();
    }


}
