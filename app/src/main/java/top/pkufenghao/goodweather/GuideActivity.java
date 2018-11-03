package top.pkufenghao.goodweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;

public class GuideActivity extends Activity implements View.OnClickListener{

    private ViewPager mViewPager;

    private int[] mImgIDs = new int[]{R.drawable.login_app, R.drawable.zhubeijing4, R.drawable.biz_plugin_weather_shenzhen_bg};

    private Button start_btn;

    private int mPainDis;

    private ImageView redPoint;

    private LinearLayout img_container;

    private ArrayList<ImageView> mimageViewArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.guide_activity);
        initView();
        initData();


        class GuideAdapter extends PagerAdapter {

            //item的个数
            @Override
            public int getCount() {
                return mimageViewArrayList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            //初始化item布局
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView view = mimageViewArrayList.get(position);
                container.addView(view);
                return view;
            }

            //销毁item
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
        GuideAdapter adapter = new GuideAdapter();
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        //监听布局是否已经完成  布局的位置是否已经确定
        redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //避免重复回调        出于兼容性考虑，使用了过时的方法
                redPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //布局完成了就获取第一个小灰点和第二个之间left的距离
                mPainDis = img_container.getChildAt(1).getLeft() - img_container.getChildAt(0).getLeft();
                System.out.println("距离：" + mPainDis);
            }
        });


        //ViewPager滑动Pager监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的回调
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当滑到第二个Pager的时候，positionOffset百分比会变成0，position会变成1，所以后面要加上position*mPaintDis
                int letfMargin = (int) (mPainDis * positionOffset) + position * mPainDis;
                //在父布局控件中设置他的leftMargin边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redPoint.getLayoutParams();
                params.leftMargin = letfMargin;
                redPoint.setLayoutParams(params);
            }


            /**
             * 设置按钮最后一页显示，其他页面隐藏
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                System.out.println("position:" + position);
                if (position == mimageViewArrayList.size() - 1) {
                    start_btn.setVisibility(View.VISIBLE);
                } else {
                    start_btn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("state:" + state);
            }
        });
    }








    private void initView(){

        mViewPager = (ViewPager)findViewById(R.id.guide_page);

        img_container = (LinearLayout)findViewById(R.id.img_container);

        redPoint = (ImageView)findViewById(R.id.shape_voal);

        start_btn = (Button)findViewById(R.id.guide_start_btn);
        start_btn.setOnClickListener(this);


    }

    private void initData(){

        mimageViewArrayList = new ArrayList<>();

        for (int i = 0; i<mImgIDs.length; i++){
            ImageView view = new ImageView(this);
            view.setImageResource(mImgIDs[i]);

            mimageViewArrayList.add(view);

            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.shape_point);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            for (;i>0;){
                params.leftMargin = 20;
            }

            pointView.setLayoutParams(params);

            img_container.addView(pointView);


        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.guide_start_btn:
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }

    }
}
