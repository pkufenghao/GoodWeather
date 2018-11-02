package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText accountEdit;

    private EditText pwEdit;

    private Button loginBtn;

    private Button regBtn;

    private ImageView loginWX;

    private ImageView loginWB;

    private ImageView loginQQ;

    private ImageView seePW;

    private ImageView noseePW;


    private void initView(){

        accountEdit = (EditText)findViewById(R.id.et_account);

        pwEdit = (EditText)findViewById(R.id.et_pw);

        loginBtn = (Button)findViewById(R.id.login_btn);

        regBtn = (Button)findViewById(R.id.reg_btn);

        loginQQ = (ImageView)findViewById(R.id.login_qq);

        loginWB = (ImageView)findViewById(R.id.login_weibo);

        loginWX = (ImageView)findViewById(R.id.login_weixin);

        seePW = (ImageView)findViewById(R.id.pw_can_eye);

        noseePW = (ImageView)findViewById(R.id.pw_not_eye);

    }
    private void setevents(){
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
        //loginBtn.setOnClickListener(this);
        //regBtn.setOnClickListener(this);
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
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login_btn:
                Intent k = new Intent(this,MainActivity.class);
                startActivity(k);
                break;
            case R.id.reg_btn:
                Intent i = new Intent(this,RegActivity.class);
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
                break;
            case R.id.pw_not_eye:
                noseePW.setVisibility(View.GONE);
                seePW.setVisibility(View.VISIBLE);
                default:
                    break;
        }

    }
}
