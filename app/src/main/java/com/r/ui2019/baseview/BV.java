package com.r.ui2019.baseview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class BV extends BaseView {
    public BV(Context context) {
        super(context);
    }

    public BV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);

    }
}
