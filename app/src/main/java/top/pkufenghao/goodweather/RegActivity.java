package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegActivity extends Activity implements View.OnClickListener{
    //返回按钮
    private ImageView regback;
    //注册按钮
    private Button user_reg_btn;
    //用户名
    private EditText user_name;
    //密码
    private EditText user_pw;
    //sharedpreference编辑
    SharedPreferences.Editor reg_editor;
    //preference
    SharedPreferences reg_preferences;



    private void initView(){
        //初始化返回按钮
        regback = (ImageView)findViewById(R.id.reg_back);
        regback.setOnClickListener(this);
        //初始化注册按钮
        user_reg_btn= (Button)findViewById(R.id.user_reg_btn);
        user_reg_btn.setOnClickListener(this);
        //用户名输入框
        user_name = (EditText)findViewById(R.id.reg_user);
        //初始化密码输入框
        user_pw = (EditText)findViewById(R.id.reg_pw);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg);

        initView();
        //初始化preference
        reg_preferences = getSharedPreferences("UserReg", 0);
        reg_editor = reg_preferences.edit();


    }




    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.reg_back:
                //返回登录界面
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.user_reg_btn:
               if ((! TextUtils.isEmpty(user_name.getText())) && (! TextUtils.isEmpty(user_pw.getText()))){
                   //输入用户名和密码并保存
                   reg_editor.putString("UserRegName", user_name.getText().toString());
                   reg_editor.putString("UserRegPw", user_pw.getText().toString());
                   reg_editor.commit();
                   Toast.makeText(RegActivity.this, "reg_name_input:"+user_name.getText(), Toast.LENGTH_SHORT).show();
                   Toast.makeText(RegActivity.this, "reg_pw_input:"+user_pw.getText(), Toast.LENGTH_SHORT).show();
                   Toast.makeText(RegActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(this,LoginActivity.class);
                    startActivity(j);
                    finish();
                }else if (( TextUtils.isEmpty(user_name.getText())) && (! TextUtils.isEmpty(user_pw.getText()))){
                    Toast.makeText(RegActivity.this, "用户名为空!", Toast.LENGTH_SHORT).show();
                }else if ((! TextUtils.isEmpty(user_name.getText())) && ( TextUtils.isEmpty(user_pw.getText()))){
                   Toast.makeText(RegActivity.this, "密码为空!", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(RegActivity.this, "用户名和密码为空!", Toast.LENGTH_SHORT).show();
               }
                break;
                default:
                    break;
        }
    }
}
