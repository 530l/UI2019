package com.r.ui2019.canvas_draw_api;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.r.ui2019.R;

import java.util.Locale;

public class BaewDrawActivity extends AppCompatActivity {

    StegePointProcessView stegePointProcessView;
    ProcessHorView ada_hor_process;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baewdraw);
        stegePointProcessView = findViewById(R.id.stegePointProcessView);
        stegePointProcessView.setMaxProcess(30);
        stegePointProcessView.addOnClickGiftListener(new StegePointProcessView.IGiftClickListener() {
            @Override
            public void onGiftClick(int position, boolean isSelected) {
                Toast.makeText(BaewDrawActivity.this, "打开了 " + position, Toast.LENGTH_SHORT).show();
            }
        });
        stegePointProcessView.setTips(7, 15, 30);

        //
        ada_hor_process = findViewById(R.id.ada_hor_process);
        ada_hor_process.setCurProcess(20000);
        ada_hor_process.setTotalProcess(20000);
        ada_hor_process.setProcessBitmap(R.mipmap.icon_level_detail_process);
    }

    public void addX(View view) {
//        int cur = (int) (Math.random() * 10 + 2);
//        stegePointProcessView.setCurProcess(cur);
//        stegePointProcessView.setCurProcess(10);
        startAnim(view);
    }

    public void startAnim(View view) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 15000);
        animator.setDuration(700);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                ada_hor_process.setCurProcess(value);
            }
        });
        animator.start();
    }

}
