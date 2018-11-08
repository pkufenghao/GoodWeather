package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_activity);
        initView();
        //延时5秒
        handler.sendEmptyMessageDelayed(0, 2000);

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getHome();      //延时5秒进入登录界面
        }

    };

    public void getHome(){
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);//进入登录界面
        startActivity(intent);
        finish();
    }



    protected int getLayoutID() {
        return R.layout.welcome_activity;
    }


    protected void initListener() {

    }


    protected void initView() {

    }


    protected void initData() {

    }
}
