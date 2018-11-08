package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

public class LoginActivity extends Activity implements View.OnClickListener {
    //用户名文本框
    private EditText accountEdit;
    //密码文本框
    private EditText pwEdit;
    //登录按钮
    private Button loginBtn;
    //注册按钮
    private Button regBtn;
    //微信登录
    private ImageView loginWX;
    //微博登录
    private ImageView loginWB;
    //QQ登录
    private ImageView loginQQ;
    //可以看见密码
    private ImageView seePW;
    //不可见密码
    private ImageView noseePW;
    //是否登录过
    private Boolean isLogined;
    //登录sharedpreference
    private SharedPreferences login_sp;
    //编辑
    private SharedPreferences.Editor editor;
    //登录名
    private String login_name;
    //密码
    private String login_pw;

    //初始化函数
    private void initView() {

        accountEdit = (EditText) findViewById(R.id.et_account);

        pwEdit = (EditText) findViewById(R.id.et_pw);

        loginBtn = (Button) findViewById(R.id.login_btn);

        regBtn = (Button) findViewById(R.id.login_page_reg_btn);

        loginQQ = (ImageView) findViewById(R.id.login_qq);

        loginWB = (ImageView) findViewById(R.id.login_weibo);

        loginWX = (ImageView) findViewById(R.id.login_weixin);

        seePW = (ImageView) findViewById(R.id.pw_can_eye);

        noseePW = (ImageView) findViewById(R.id.pw_not_eye);

        login_sp = getSharedPreferences("UserReg", 0);

        login_name = login_sp.getString("UserRegName", "default");
        login_pw = login_sp.getString("UserRegPw", "default");

        //isLogined = login_sp.getBoolean("isLogined", true);

    }

    //事件函数
    private void setevents() {
        accountEdit.addTextChangedListener(accountTextWeacher);

        pwEdit.addTextChangedListener(pwTextWatcher);

        loginBtn.setOnClickListener(this);

        regBtn.setOnClickListener(this);

        loginQQ.setOnClickListener(this);

        loginWB.setOnClickListener(this);

        loginWX.setOnClickListener(this);

        seePW.setOnClickListener(this);

        noseePW.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {     //活动执行程序
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);               //设置布局文件
        initView();
        setevents();
        isLogined = login_sp.getBoolean("isLogined", false);
        editor = login_sp.edit();
        /////正常情况注释下面两行
        //editor.putBoolean("isLogined", false);
        //editor.commit();

        /**
         *如果用户注册则跳转到主页面
         */
            if (isLogined) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            }

    }


    TextWatcher accountTextWeacher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher pwTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if ((! TextUtils.isEmpty(accountEdit.getText()) ) && (! TextUtils.isEmpty(pwEdit.getText()) )) {
                    if ((accountEdit.getText().toString().equals(login_name)) && (pwEdit.getText().toString().equals(login_pw))) {
                        Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                        //实例化Editor对象
                        editor = login_sp.edit();
                        //存入数据已经登录
                        editor.putBoolean("isLogined", true);
                        //提交修改
                        editor.commit();
                        Intent k = new Intent(this, MainActivity.class);
                        startActivity(k);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "login_name:"+login_name, Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginActivity.this, "login_pw:"+login_pw, Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginActivity.this, "login_name_input:"+accountEdit.getText(), Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginActivity.this, "login_pw_input:"+pwEdit.getText(), Toast.LENGTH_LONG).show();
                    }
                } else if (( TextUtils.isEmpty(accountEdit.getText()) ) && (! TextUtils.isEmpty(pwEdit.getText()) )){
                    Toast.makeText(LoginActivity.this, "用户名为空!", Toast.LENGTH_SHORT).show();
                }else if ((! TextUtils.isEmpty(accountEdit.getText()) ) && ( TextUtils.isEmpty(pwEdit.getText()) )){
                    Toast.makeText(LoginActivity.this, "密码为空!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "用户名和密码为空!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_page_reg_btn:
                Intent i = new Intent(this, RegActivity.class);
                startActivity(i);
                //finish();
                break;
            case R.id.login_qq:
                break;
            case R.id.login_weibo:
                break;
            case R.id.login_weixin:
                break;
            case R.id.pw_can_eye:
                seePW.setVisibility(View.GONE);
                noseePW.setVisibility(View.VISIBLE);
                pwEdit.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                break;
            case R.id.pw_not_eye:
                noseePW.setVisibility(View.GONE);
                seePW.setVisibility(View.VISIBLE);
                pwEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            default:
                break;
        }

    }
}
