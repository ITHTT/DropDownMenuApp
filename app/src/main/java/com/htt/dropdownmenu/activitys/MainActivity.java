package com.htt.dropdownmenu.activitys;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htt.dropdownmenu.R;
import com.htt.dropdownmenu.modles.GoodInfo;
import com.htt.dropdownmenu.views.adapter.ConstellationAdapter;
import com.htt.dropdownmenu.views.adapter.GirdDropDownAdapter;
import com.htt.dropdownmenu.views.adapter.GoodListAdapter;
import com.htt.dropdownmenu.views.adapter.ListDropDownAdapter;
import com.htt.dropdownmenu.views.widgets.DropDownMenu;
import com.htt.dropdownmenu.views.widgets.goodcar.OnAddGoodListener;
import com.htt.dropdownmenu.views.widgets.goodcar.PathAnimation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnAddGoodListener{
    private DropDownMenu dropDownMenu;

    private String headers[] = {"城市", "年龄", "性别", "星座"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = {"不限", "男", "女"};
    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};

    private int constellationPosition = 0;

    private View contentView;
    private RecyclerView rvGoodList;
    private RelativeLayout goodListLayout;
    private ImageView ivGoodCar;
    private TextView tvGoodCounts;

    private List<GoodInfo> goodInfos;
    private int addGoods=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        dropDownMenu= (DropDownMenu) this.findViewById(R.id.drop_down_menu);

        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new GirdDropDownAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.layout_custom_menu_view, null);
        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = (TextView) constellationView.findViewById( R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                dropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                dropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                dropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                dropDownMenu.closeMenu();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });
        //init context view
        contentView= LayoutInflater.from(this).inflate(R.layout.layout_shopping_list,null);
        rvGoodList= (RecyclerView) contentView.findViewById(R.id.goods_lv);
        rvGoodList.setLayoutManager(new LinearLayoutManager(this));
        goodListLayout=(RelativeLayout)contentView.findViewById(R.id.layout_good_list);
        ivGoodCar=(ImageView)contentView.findViewById(R.id.iv_goods_car);
        tvGoodCounts=(TextView)contentView.findViewById(R.id.tv_goods_counts);

        getGoods();
        GoodListAdapter adapter=new GoodListAdapter(goodInfos,this);
        rvGoodList.setAdapter(adapter);

        //init dropdownview
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

    }

    private void getGoods(){
        goodInfos=new ArrayList<>(5);
        for(int i=0;i<15;i++){
            GoodInfo good=new GoodInfo();
            good.setGoodIconRes(R.mipmap.icon_good_apple_phone);
            good.setGoodName("苹果6s");
            goodInfos.add(good);
        }
    }

    @Override
    public void onAddGood(int position, ImageView imageView) {
        //得到起始点坐标
        int parentLoc[] = new int[2];
        goodListLayout.getLocationInWindow(parentLoc);
        int startLoc[] = new int[2];
        imageView.getLocationInWindow(startLoc);
        int endLoc[] = new int[2];
        ivGoodCar.getLocationInWindow(endLoc);


        final ImageView goods = new ImageView(getApplicationContext());
        goods.setImageDrawable(imageView.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        goodListLayout.addView(goods, params);

        float startX = startLoc[0] - parentLoc[0] + imageView.getWidth() / 2;
        float startY = startLoc[1] - parentLoc[1] + imageView.getHeight() / 2;
        float toX = endLoc[0] - parentLoc[0] + ivGoodCar.getWidth() / 3;
        float toY = endLoc[1] - parentLoc[1];


        Path path = new Path();
        path.moveTo(startX, startY);
        path.quadTo((startX + toX) / 2, startY, toX, toY);

//        PathAnimation animation = new PathAnimation(path);
//        animation.setDuration(1000);
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                addGoods++;
//                tvGoodCounts.setText(String.valueOf(addGoods));
//                goodListLayout.removeView(goods);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        goods.startAnimation(animation);

        //属性动画实现
        final PathMeasure mPathMeasure = new PathMeasure(path, false);
        final float[] mCurrentPosition=new float[2];
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);
        // 匀速插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                addGoods++;
                tvGoodCounts.setText(String.valueOf(addGoods));
                goodListLayout.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
