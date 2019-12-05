package com.r.ui2019.canvas_draw_api;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.r.ui2019.utils.UIUtils;

public class BarChart extends View {

    private Paint mPaint;//坐标画笔

    private Paint mTPaint;//条形图画笔

    protected float mWidth;
    protected float mHeight;

    private int startX;//左边直线起点，x , y
    private int getStartY;

    private int bottonMargin;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        //
        startX = UIUtils.dip2px(context, 20);
        getStartY = UIUtils.dip2px(context, 20);
        //
        bottonMargin = UIUtils.dip2px(context, 20);

        //
        mTPaint = new Paint();
        mTPaint.setColor(Color.GREEN);
        mTPaint.setAntiAlias(true);


    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //todo 画左边垂直标线
        canvas.drawLine(startX, getStartY, startX, mHeight - bottonMargin, mPaint);
        //todo 画底部横向直接
        canvas.drawLine(startX, mHeight - bottonMargin, mWidth - bottonMargin, mHeight - bottonMargin, mPaint);
        //todo 画条形图
        int curr;
        curr = startX + bottonMargin;
        for (int i = 0; i < 8; i++) {
            //todo 每次x左边的间隔，增加一倍
            canvas.drawRect(curr * (i + 1), mHeight / 3, curr * (i + 1) + bottonMargin,
                    mHeight - bottonMargin, mTPaint);
        }
    }
}
