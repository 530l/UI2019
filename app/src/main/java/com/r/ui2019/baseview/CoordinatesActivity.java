package com.r.ui2019.baseview;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.r.ui2019.R;
import com.r.ui2019.utils.UIUtils;

public class CoordinatesActivity extends AppCompatActivity {

    BV bv;
    LinearLayout root_ll;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinates);
        bv = findViewById(R.id.bv);
        root_ll = findViewById(R.id.root_ll);
        textView = findViewById(R.id.tv);

        //todo 为什么通过LayoutParams.height改变高度，使UI发生变化，但是View本身的height不会发生变化的?
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                 *               //总结：
//              //
//                //View绘制分三个步骤，顺序是：onMeasure，onLayout，onDraw。经代码亲测，log输出显示：
//                  // 调用invalidate方法只会执行onDraw方法；
//                  // 调用requestLayout方法只会执行onMeasure方法和onLayout方法，并不会执行onDraw方法。
//                 //
//                  //所以当我们进行View更新时，若仅View的显示内容发生改变且新显示内容不影响View的大小、位置，
//                  // 则只需调用invalidate方法；
//                   // 若View宽高、位置发生改变且显示内容不变，只需调用requestLayout方法；
//                     // 若两者均发生改变，则需调用两者，按照View的绘制流程，推荐先调用requestLayout方法再调用invalidate方法。
//                    //1.invalidate和postInvalidate：invalidate方法只能用于UI线程中，在非UI线程中，
//                       // 可直接使用postInvalidate方法，这样就省去使用handler的烦恼。
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bv.getLayoutParams();
//                params.height = UIUtils.dip2px(300);
                //如果不调用setLayoutParams，可以调用requestLayout 请求重新测量一次
//                        bv.setLayoutParams(params);//view方法
                //需注意原来的宽高需设置明确的值以上方法才可用，若设置wrap_content或match_parent则不可用
//                bv.requestLayout();//view方法
//                bv.invalidate();

//                int h2 = UIUtils.px2dip(CoordinatesActivity.this, bv.getLayoutParams().height);
//                int h3 = UIUtils.px2dip(CoordinatesActivity.this, bv.getHeight());
//                int h4 = UIUtils.px2dip(CoordinatesActivity.this, bv.getMeasuredHeight());
//                //
//                int h5 = UIUtils.px2dip(CoordinatesActivity.this, root_ll.getHeight());
//                Log.i("tt", "-------2------" + h2);
//                Log.i("tt", "-------3------" + h3);
//                Log.i("tt", "-------4------" + h4);
//                Log.i("tt", "-------5------" + h5);
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bv.getLayoutParams();
                params.height = UIUtils.dip2px(300);
                textView.setLayoutParams(params);//view方法

                //通过setLayoutParams 不能马上获取view本身的高度，requestLayout测量view的高度需要时间
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int h2 = UIUtils.px2dip(CoordinatesActivity.this, textView.getLayoutParams().height);
                        int h3 = UIUtils.px2dip(CoordinatesActivity.this, textView.getHeight());
                        int h4 = UIUtils.px2dip(CoordinatesActivity.this, textView.getMeasuredHeight());
                        int h5 = UIUtils.px2dip(CoordinatesActivity.this, root_ll.getHeight());
                        Log.i("tt", "-------2------" + h2);
                        Log.i("tt", "-------3------" + h3);
                        Log.i("tt", "-------4------" + h4);
                        Log.i("tt", "-------4------" + h5);
                    }
                }, 17);
                Toast.makeText(CoordinatesActivity.this, "s", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int h2 = UIUtils.px2dip(CoordinatesActivity.this, textView.getLayoutParams().height);
        int h3 = UIUtils.px2dip(CoordinatesActivity.this, textView.getHeight());
        int h4 = UIUtils.px2dip(CoordinatesActivity.this, textView.getMeasuredHeight());
        //
        int h5 = UIUtils.px2dip(CoordinatesActivity.this, root_ll.getHeight());
        Log.i("tt", "-------2------" + h2);
        Log.i("tt", "-------3------" + h3);
        Log.i("tt", "-------4------" + h4);
        Log.i("tt", "-------4------" + h5);
    }
}
