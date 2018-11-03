package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegActivity extends Activity implements View.OnClickListener{

    private ImageView regback;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        regback = (ImageView)findViewById(R.id.reg_back);
        regback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.reg_back:
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                break;
                default:
                    break;
        }
    }
}
