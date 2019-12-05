package com.r.ui2019.canvas_draw_api;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.r.ui2019.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 进度条自定义view
 * 礼物签到控件思路： 进度条背景，进度条，分几个阶段作为，画几个圆，圆中心画文字，画bitmap，
 * 可以自己定义：圆的半径是多少，进度条高度多少，围绕这个2个已知的值，进行计算，测量绘制
 */
public class StegePointProcessView extends View {
    private static final String TAG = "StegePointProcessView";

    private Paint processNorPaint;
    private Paint processRun0Paint;
    private Paint processRun1Paint;
    private Paint processRun2Paint;
    private Paint circlePaint;
    private Paint circlePointPaint;
    private Paint txtPaint;

    private RectF processRect;
    private RectF processRunRect;
    private RectF rectRun0;
    private RectF rectRun1;
    private RectF rectRun2;
    private Rect tempCalu;

    int oval_round_radius = 5;//进度条圆角半径

    private int colorProcessBgDef = 0xff2b2c30;    // 默认背景颜色


    private int colorStages[] = new int[]{    // 渐变颜色   从左到右
            0xffF5F5F5, 0xff8bb92b,    // 第一阶段
            0xffF5F5F5, 0xffFF4900,    // 第二阶段
            0xff12cab2, 0xff1283ca     // 第三阶段
    };

    int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private int processHeight = dp2px(10);   // 进度条高度
    private int radiusCicle = dp2px(20);   // 圆半径
    private int width;

    private Bitmap bitmapTemp;

    private int maxProcess = 100;
    private float curProcess = 0;

    class StageBean {
        RectF rect;
        Bitmap nor;
        Bitmap selected;
        boolean isSelect = false;
        boolean isLight = false;
        int tip = 0;

        StageBean(RectF rect, int norResId, int selectedResId) {
            this.rect = rect;
            this.nor = BitmapFactory.decodeResource(getResources(), norResId);
            this.selected = BitmapFactory.decodeResource(getResources(), selectedResId);
        }
    }

    private List<StageBean> datas;

//    private IGiftClickListener listener;

    public StegePointProcessView(Context context) {
        this(context, null);
    }

    public StegePointProcessView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StegePointProcessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(colorProcessBgDef);

        circlePointPaint = new Paint();
        circlePointPaint.setAntiAlias(true);
        circlePointPaint.setColor(Color.RED);
        circlePointPaint.setStrokeWidth(4);

        processNorPaint = new Paint();
        processNorPaint.setAntiAlias(true);
        processNorPaint.setColor(colorProcessBgDef);

        processRun0Paint = new Paint();
        processRun0Paint.setAntiAlias(true);

        processRun1Paint = new Paint();
        processRun1Paint.setAntiAlias(true);

        processRun2Paint = new Paint();
        processRun2Paint.setAntiAlias(true);


        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);

        processRect = new RectF();
        processRunRect = new RectF();
        tempCalu = new Rect();
        rectRun0 = new RectF();
        rectRun1 = new RectF();
        rectRun2 = new RectF();

        // 计算高度用
        bitmapTemp = BitmapFactory.decodeResource(getResources(), R.mipmap.sign_icon_gift_selected);

        //3个礼物盒子
        datas = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            datas.add(new StageBean(new RectF(), R.mipmap.sign_icon_gift_normal, R.mipmap.sign_icon_gift_selected));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //todo //MeasureSpec.EXACTLY：确切的大小，如：100dp或者march_parent
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            this.width = width;
        } else {
            this.width = 1080;
        }
        //todo 高度：圆的直径+礼盒和圆的间隔+图片的高度 是整一个控件的高度
        int height = radiusCicle * 2 + radiusCicle / 2 + bitmapTemp.getHeight();
        setMeasuredDimension(this.width, height);

        //todo 底部的矩形  radiusCicle：圆半径   processHeight：进度条高度

        // （20 ，15） （width-20，25）
        //todo 矩形（left top ）(right bottom ) 4个点构造一个图形，
        //todo width-radiusCicle 是为了右边也有20dp间隔，左右2边各空出20dp
        //15   25
        // todo 这个背景的进度条高度其实就10dp，这些高度自己定义，符合计算就好
        processRect.set(radiusCicle, radiusCicle - processHeight / 2,
                this.width - radiusCicle, radiusCicle + processHeight / 2);

        //todo，processRunRect 就是保存4个点的坐标
        processRunRect.set(processRect.left, processRect.top, processRect.left, processRect.bottom);

        float stage = processRect.width() / datas.size() + radiusChicleOffsetValue;//阶段
        //float stage = processRect.width() / datas.size() ;//阶段

        //todo 3个渐变，分3个矩形来画，每个矩形的X坐标累加一倍
        rectRun0.set(processRunRect.left, processRunRect.top, stage, processRunRect.bottom);

        rectRun1.set(stage, processRunRect.top, stage * 2, processRunRect.bottom);

        rectRun2.set(stage * 2, processRunRect.top, processRunRect.right, processRunRect.bottom);
    }

    private int radiusChicleOffsetValue = radiusCicle / 2;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //todo 画进度条背景
        float stageWidth = processRect.width() / datas.size();  //每一阶段 宽度
        canvas.drawRoundRect(processRect, oval_round_radius, oval_round_radius, processNorPaint);
        //todo 画进度条值的矩形
        if (curProcess > 0) {
            //todo 绘制进度条的百分比计算逻辑
            // this.processRect.width() * curProcess / maxProcess  当前值在这个矩形的比例是多少
            //processRunRect.right = this.processRect.width() * curProcess / maxProcess;
            //+ processRect.left;  加这个偏移值是为了进度条背景颜色过圆底部下，避免看到间断，在30的位置情况下
            processRunRect.right = this.processRect.width() * curProcess / maxProcess + processRect.left;
            //processRect 背景   processRunRect 进度
            if (processRunRect.right > processRect.right) {
                processRunRect.right = processRect.right;
            }
            float stage = stageWidth + radiusChicleOffsetValue;
            if (processRunRect.right <= stage) {
                rectRun0.right = processRunRect.right;
                processRun0Paint.setShader(new LinearGradient(rectRun0.left,
                        radiusCicle, rectRun0.right,
                        radiusCicle, colorStages[0], colorStages[1],
                        Shader.TileMode.MIRROR));
            } else if (processRunRect.right > stage && processRunRect.right <= stage * 2) {
                rectRun1.right = processRunRect.right;
                processRun1Paint.setShader(new LinearGradient(rectRun1.left,
                        radiusCicle, rectRun1.right,
                        radiusCicle, colorStages[2], colorStages[3],
                        Shader.TileMode.MIRROR));
            } else {
                rectRun2.right = processRunRect.right;
                processRun2Paint.setShader(new LinearGradient(rectRun2.left,
                        radiusCicle, rectRun2.right, radiusCicle,
                        colorStages[4], colorStages[5],
                        Shader.TileMode.MIRROR));
            }
            canvas.drawRoundRect(rectRun0, oval_round_radius, oval_round_radius, processRun0Paint);
            if (rectRun1.left < rectRun1.right)
                canvas.drawRoundRect(rectRun1, 5, 5, processRun1Paint);
            if (rectRun2.left < rectRun2.right)
                canvas.drawRoundRect(rectRun2, 5, 5, processRun2Paint);
        }
        //todo 绘制三个圆
        txtPaint.setTextSize(sp2px(15));
        txtPaint.setColor(0xff676768);
        for (int i = 0; i < datas.size(); i++) {
            StageBean stageBean = datas.get(i);
            float stage = stageWidth * (i + 1) + radiusChicleOffsetValue;
            stageBean.isLight = processRunRect.right >= stage; // 圆是否点亮
            // 圆的颜色
            circlePaint.setColor(stageBean.isLight ? (i == 0 ? colorStages[1]
                    : (i == 1 ? colorStages[3] : colorStages[5])) : colorProcessBgDef);
            //todo 3个阶段+10dp偏移量作为圆的X坐标，进度条矩形buttom 25dp，top 15dp
            //todo  已经进度条矩形的高度是10， y坐标20dp刚好在进度条矩形中心，画圆，对称
            canvas.drawCircle(stage, radiusCicle, radiusCicle, circlePaint);
            canvas.drawPoint(stage, radiusCicle, circlePointPaint);

            //todo 圆中心文字
            if (stageBean.tip != 0) {
                String tip = String.valueOf(stageBean.tip);
                txtPaint.setColor(stageBean.isLight ? 0xfff1f1f1 : 0xff676767);
                //measureText() 会在左右两侧加上一些额外的宽度值，
                // 而 getTextBounds() 则是返回需要的最小宽度而已。
                txtPaint.getTextBounds(tip, 0, tip.length(), tempCalu);
//                if (stageBean.tip == 1) {
//
//                    // Text is '1', measureText 25.000000, getTextBounds 14
////                    float measuredWidth = txtPaint.measureText(tip);
////                    Rect mBounds = new Rect();
////                    txtPaint.getTextBounds(tip, 0, tip.length(), mBounds);
//
//                    txtPaint.getTextBounds(tip, 0, tip.length(), tempCalu);
//                    float measuredWidth = txtPaint.measureText(tip);
//
//                    // 打印宽度信息
//                    Log.d("Test", String.format(
//                            "Text is '%s', measureText %f, getTextBounds %d", tip,
//                            measuredWidth, tempCalu.width())
//                    );
//
//                }

                //todo  Text is '1', measureText 25.000000, getTextBounds 14
                int x = (int) (txtPaint.measureText(tip) / 2);
//                int x = tempCalu.width() / 2;
                int y = radiusCicle + tempCalu.height() / 2;
                //todo 画圆的时间，x坐标加了10的偏移量，这里要选和10偏移量趋近的值，
                canvas.drawText(tip, stage - x, y, txtPaint);


                // 圆下图标
                //todo bimap要画在圆中心的对称，stage 是圆的x坐标，
                // 减去图片宽度的做一半，作为left，
                //todo 高度：圆的直径+礼盒和圆的间隔+图片的高度 是整一个控件的高度
                // int height = radiusCicle * 2 + radiusCicle / 2 + bitmapTemp.getHeight();
                canvas.drawBitmap(stageBean.isSelect ? stageBean.selected : stageBean.nor,
                        stage - stageBean.selected.getWidth() / 2,
                        radiusCicle * 2 + radiusCicle / 2, txtPaint);
            }

            //------------------------------
            // 点击用rect 要扩展点击范围在这里设置
            stageBean.rect.set(((int) (stageWidth * i - stageBean.selected.getWidth() / 2)),
                    radiusCicle * 2 + radiusCicle / 2,
                    ((int) (stage + stageBean.selected.getWidth())),
                    radiusCicle * 2 + radiusCicle * 2);
        }
    }

    public void setTips(int one, int two, int three) {
        datas.get(0).tip = one;
        datas.get(1).tip = two;
        datas.get(2).tip = three;
    }

    private double oldTotal = 0;
    private double oldCur = 0;

    public void setCurProcess(double cur) {

        //
        int limit1st = datas.get(0).tip;
        int limit2nd = datas.get(1).tip;
        int limit3rd = datas.get(2).tip;

        if (limit1st == 0 || limit2nd == 0 || limit3rd == 0) {
            throw new NullPointerException("must be called StagePointProcessView#setTips(...) !!!");
        }

        if (limit2nd < limit1st) {
            throw new IllegalArgumentException("[limit2nd] must be more than [limit1st] !!");
        }

        if (limit3rd < limit1st || limit3rd < limit2nd) {
            throw new IllegalArgumentException("[limit3rd] must be more than [limit1st] & [limit2nd] !!");
        }

        double total = oldCur + cur;

        oldCur = total;

        //todo 计算规则：如果每段已满，就把剩余的值，去下一段比例，
        if (total >= limit2nd) {//todo 15
            total = ((total - limit2nd) / (maxProcess - limit2nd) * 0.33 + 0.66) * maxProcess;
        } else if (total >= limit1st && total < limit2nd) {//todo 7
            total = ((total - limit1st) / (limit2nd - limit1st) * 0.33 + 0.33) * maxProcess;
        } else {//todo 30
            total = (total / limit1st * 0.33 + 0) * maxProcess;
        }
        //start ---->end
        ValueAnimator animator = ValueAnimator.ofFloat(((float) oldTotal), ((float) (total)));
        oldTotal = total;
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(700);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                curProcess = value;
                postInvalidate();
            }
        });

        animator.start();
    }

    public void setMaxProcess(int max) {
        this.maxProcess = max;
    }

    public int getMaxProcess() {
        return this.maxProcess;
    }

    private IGiftClickListener listener;

    public void addOnClickGiftListener(IGiftClickListener listener) {
        this.listener = listener;
    }

    public interface IGiftClickListener {
        void onGiftClick(int position, boolean isSelected);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //todo 只有按下，或者抬起有作用，快速滑动不生效
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
            onTouchEvent(event);
            return true;
        } else {
            return super.dispatchTouchEvent(event);
        }
    }

    float x = 0;
    float y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (x == event.getX() && y == event.getY()) {  // 点击事件
                    for (int i = 0; i < datas.size(); i++) {
                        StageBean bean = datas.get(i);
                        if (bean.rect.contains(x, y)) {
                            if (bean.isLight) {
                                bean.isSelect = true;
                                invalidate();
                                if (listener != null) {
                                    listener.onGiftClick(i, bean.isSelect);
                                }
                            }
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }
}
