package com.r.ui2019;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.r.ui2019.baseview.CoordinatesActivity;
import com.r.ui2019.canvas.CanvasAvtivity;
import com.r.ui2019.canvas_draw_api.BaewDrawActivity;
import com.r.ui2019.flowlayout.ClientActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_code1_bezier).setOnClickListener(this);
        findViewById(R.id.tv_code2_pathmeasure).setOnClickListener(this);
        findViewById(R.id.tv_code3_anim).setOnClickListener(this);
        findViewById(R.id.tv_code4_xfermode).setOnClickListener(this);
        findViewById(R.id.tv_code5_scroller_velocityTracker).setOnClickListener(this);
        findViewById(R.id.tv_code6_draw_flow).setOnClickListener(this);
        findViewById(R.id.tv_code7_svg).setOnClickListener(this);
        findViewById(R.id.tv_code8_canvas_clip).setOnClickListener(this);
        findViewById(R.id.tv_code8_canvas_draw).setOnClickListener(this);
        findViewById(R.id.tv_code8_canvas_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_code1_bezier:
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.tv_code2_pathmeasure:
                startActivity(new Intent(this, CoordinatesActivity.class));
                break;
            case R.id.tv_code3_anim:
                startActivity(new Intent(this, CanvasAvtivity.class));
                break;
            case R.id.tv_code4_xfermode:
                startActivity(new Intent(this, BaewDrawActivity.class));
                break;
            case R.id.tv_code5_scroller_velocityTracker:
                startActivity(new Intent(this,ClientActivity.class));
                break;
            case R.id.tv_code6_draw_flow:
                startActivity(new Intent(this,ClientActivity.class));
                break;
            case R.id.tv_code7_svg:
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.tv_code8_canvas_clip:
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.tv_code8_canvas_draw:
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.tv_code8_canvas_text:
                startActivity(new Intent(this, ClientActivity.class));
                break;
        }
    }

}
