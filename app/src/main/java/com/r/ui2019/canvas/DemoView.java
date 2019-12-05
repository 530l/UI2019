package com.r.ui2019.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import com.r.ui2019.R;

public class DemoView extends View {


    private Paint mPaint;

    private Bitmap bitmap1;

    private Bitmap bitmap2;


    //todo Xml直接定义 > xml中style引用 > defStyleAttr > defStyleRes > theme直接定义
//    1.Context- 上下文，用于view 所有地方
//    2.AttributeSet - XML属性（从XML加载时）
//    3.int defStyleAttr - 应用于View（在主题中定义）的默认样式
//    4.int defStyleResource - 如果defStyleAttr未使用，则应用于View的默认样式。
    public DemoView(Context context) {
        super(context);
        init();
    }

    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_dial_hor_three_stage_marker);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.sign_icon_gift_selected);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//todo        ⑴位移(translate)

        /**
         * todo 我们首先将坐标系移动一段距离绘制一个圆形，之后再移动一段距离绘制一个圆形，两次移动是可叠加的。
         * todo translate是坐标系的移动，可以为图形绘制选择一个合适的坐标系。
         * 请注意，位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动，如下：
         */

        // 在坐标原点绘制一个黑色圆形
//        mPaint.setColor(Color.BLACK);
//        canvas.translate(200, 200);
//        canvas.drawCircle(0, 0, 100, mPaint);
//
//        // 在坐标原点绘制一个蓝色圆形
//        mPaint.setColor(Color.BLUE);
//        canvas.translate(200, 200);
//        canvas.drawCircle(0, 0, 100, mPaint);


//todo         ⑵缩放(scale)
        /**
         * public void scale (float sx, float sy)
         * 两个参数是相同的分别为x轴和y轴的缩放比例
         *
         * public final void scale (float sx, float sy, float px, float py)
         * 比前一种多了两个参数，用来控制缩放中心位置的。
         */

        int mWidth = getWidth();
        int mHeight = getHeight();

//        // 将坐标系原点移动到画布正中心
//        canvas.translate(mWidth >> 1, mHeight >> 1);//相当于除2
//        RectF rect = new RectF(0, -400, 400, 0);   // 矩形区域
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect, mPaint);
//
//        //不会显示，若sx为0，则宽度为0，不会显示，sy同理  ， 1没有变化
//        canvas.scale(0.3f, 0.3f);                // 画布缩放
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect, mPaint);


        // 将坐标系原点移动到画布正中心
//        canvas.translate(mWidth / 2, mHeight / 2);
//
//        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
//
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect,mPaint);
//
//        canvas.scale(0.5f,0.5f,200,0);          // 画布缩放  <-- 缩放中心向右偏移了200个单位
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);

    }
}
