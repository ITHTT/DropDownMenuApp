package com.htt.dropdownmenu.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.htt.dropdownmenu.R;
import com.htt.dropdownmenu.views.widgets.CircleProgressView;

/**
 * Created by HTT on 2016/6/8.
 */
public class CircleProgressActivity extends AppCompatActivity{
    private CircleProgressView circleProgressView=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_view);
        circleProgressView= (CircleProgressView) this.findViewById(R.id.circle_progress);
        circleProgressView.setProgressValue(90);

        CircleProgressView circleProgressView1=(CircleProgressView)this.findViewById(R.id.circle_progress01);
        circleProgressView1.setProgressValue(100);
    }
}
