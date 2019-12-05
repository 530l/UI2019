package com.r.ui2019.baseview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;


public abstract class BaseView extends View {

    protected String TAG = this.getClass().getSimpleName();

    // 坐标画笔
    private Paint mCoordinatePaint;
    // 网格画笔
    private Paint mGridPaint;
    // 写字画笔
    private Paint mTextPaint;

    // 坐标颜色
    private int mCoordinateColor;
    private int mGridColor;

    // 网格宽度 50px
    private int mGridWidth = 50;

    // 坐标线宽度
    private final float mCoordinateLineWidth = 2.5f;
    // 网格宽度
    private final float mGridLineWidth = 1f;
    // 字体大小
    private float mTextSize;

    // 标柱的高度
    private final float mCoordinateFlagHeight = 8f;

    protected float mWidth;
    protected float mHeight;

    public BaseView(Context context) {
        this(context, null, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCoordinate(context);
        init(context);
    }


    /**
     * onSizeChanged() 在控件大小发生改变时调用。所以这里初始化会被调用一次
     * 作用：获取控件的宽和高度
     * 每次高度发生变化都回回调过来
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("tt", "-------------");
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }


    protected void initCoordinate(Context context) {
        mCoordinateColor = Color.BLACK;
        mGridColor = Color.LTGRAY;

        mTextSize = spToPx(10);

        mCoordinatePaint = new Paint();
        mCoordinatePaint.setAntiAlias(true);
        mCoordinatePaint.setColor(mCoordinateColor);
        mCoordinatePaint.setStrokeWidth(mCoordinateLineWidth);
        mCoordinatePaint.setColor(Color.RED);//
//        mCoordinatePaint.setStrokeWidth(6);//

        mGridPaint = new Paint();
        mGridPaint.setAntiAlias(true);
        mGridPaint.setColor(mGridColor);
        mGridPaint.setStrokeWidth(mGridLineWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mCoordinateColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
    }

    protected abstract void init(Context context);

    /**
     * 画坐标和网格，以画布中心点为原点
     *
     * @param canvas 画布
     */
    protected void drawCoordinate(Canvas canvas) {

        float halfWidth = mWidth / 2;//1080
        float halfHeight = mHeight / 2;//1800
//        canvas.drawPoint(halfWidth,halfHeight,mCoordinatePaint);
        // 画网格
        canvas.save();
        canvas.translate(halfWidth, halfHeight);
        //todo 移动view的宽高一半位置后作为原点坐标
        canvas.drawPoint(0, 0, mCoordinatePaint);
        //每条线的宽度间距为50px
        //curWidth 当前宽度间隔的累加
        int curWidth = mGridWidth;
//      todo  Y轴 负数向上，正数向下，X轴正数向右，负数向左
//        canvas.drawLine(curWidth, -halfHeight, curWidth, halfHeight, mGridPaint);


//       todo  画竖线，多加50是为了范围大点吧
        while (curWidth < halfWidth + mGridWidth) {

//         向右画直线
            canvas.drawLine(curWidth, -halfHeight, curWidth, halfHeight, mGridPaint);
//         向左画直线
            canvas.drawLine(-curWidth, -halfHeight, -curWidth, halfHeight, mGridPaint);

//       todo  画标柱 mCoordinateFlagHeight 柱的高度
            canvas.drawLine(curWidth, 0, curWidth, -mCoordinateFlagHeight, mCoordinatePaint);
            canvas.drawLine(-curWidth, 0, -curWidth, -mCoordinateFlagHeight, mCoordinatePaint);

//         标柱宽度（每两个画一个）
            if (curWidth % (mGridWidth * 2) == 0) {
                canvas.drawText(curWidth + "", curWidth, mTextSize * 1.5f, mTextPaint);
                canvas.drawText(-curWidth + "", -curWidth, mTextSize * 1.5f, mTextPaint);
            }
            curWidth += mGridWidth;
        }

        int curHeight = mGridWidth;

        //todo 左边 负数 屏幕的一半大小，可能超过，但是没关系，高度50间距每条线横线
//        canvas.drawLine(-halfWidth, curHeight, halfWidth, curHeight, mGridPaint);
        // 画横线
        while (curHeight < halfHeight + mGridWidth) {
//         向下画
            canvas.drawLine(-halfWidth, curHeight, halfWidth, curHeight, mGridPaint);
//         向上画
            canvas.drawLine(-halfWidth, -curHeight, halfWidth, -curHeight, mGridPaint);

//         画标柱
            canvas.drawLine(0, curHeight, mCoordinateFlagHeight, curHeight, mCoordinatePaint);
            canvas.drawLine(0, -curHeight, mCoordinateFlagHeight, -curHeight, mCoordinatePaint);

//         标柱宽度（每两个画一个）
            if (curHeight % (mGridWidth * 2) == 0) {
                canvas.drawText(curHeight + "", -mTextSize * 2, curHeight + mTextSize / 2, mTextPaint);
                canvas.drawText(-curHeight + "", -mTextSize * 2, -curHeight + mTextSize / 2, mTextPaint);
            }

            curHeight += mGridWidth;
        }
        canvas.restore();
        //todo 恢复正常状态，原点坐标在左上角（0，0）
        // 画 x，y 轴  halfWidth 屏幕的一半，
        canvas.drawLine(halfWidth, 0, halfWidth, mHeight, mCoordinatePaint);
        canvas.drawLine(0, halfHeight, mWidth, halfHeight, mCoordinatePaint);

    }

    /**
     * 转换 sp 至 px
     *
     * @param spValue sp值
     * @return px值
     */
    protected int spToPx(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 转换 dp 至 px
     *
     * @param dpValue dp值
     * @return px值
     */
    protected int dpToPx(float dpValue) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dpValue * metrics.density + 0.5f);
    }


}
