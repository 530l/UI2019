package com.r.ui2019.canvas_draw_api;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.r.ui2019.R;
import com.r.ui2019.baseview.BaseView;


public class BaseDrawView extends BaseView {

    protected Paint mPaint;

    protected int mColor1;
    protected int mColor2;

    protected int mLineWidth;
    protected int mBorderWidth;

    protected RectF mRectF;

    public BaseDrawView(Context context) {
        this(context, null);
    }

    public BaseDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mColor1 = ContextCompat.getColor(context, R.color.canvas_orange_color);
        mColor2 = ContextCompat.getColor(context, R.color.canvas_red_color);

        mRectF = new RectF();
        mRectF.left = -150;
        mRectF.top = -150;
        mRectF.right = 400;
        mRectF.bottom = 150;

        mLineWidth = dpToPx(1.5f);
        mBorderWidth = dpToPx(1f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCoordinate(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
    }

    protected void setPaint(int color, int width) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(width);
    }

}
