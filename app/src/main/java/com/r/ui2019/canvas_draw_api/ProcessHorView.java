package com.r.ui2019.canvas_draw_api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.r.ui2019.R;
import com.r.ui2019.utils.ViewUtil;

/**
 * 横向进度条  带跟随角标
 */
public class ProcessHorView extends View {


    private static final String TAG = "ProcessHorView";

    //3个默认值
    private int DEF_TXT_SIZE = ViewUtil.sp2px(getResources(), 10);
    private int DEF_RECT_HEIGHT = ViewUtil.dp2px(getResources(), 5);
    private int DEF_HEIGHT = ViewUtil.dp2px(getResources(), 35);

    private int color_rect_stroke;//
    private int txt_color;//

    private int PROGRESS_MAX = 100;
    private int PROGRESS_CUR = 0;

    private Paint rectPaint;  // 矩形 及 进度条画笔
    private Paint txtPaint;  // 文字 图形 画笔

    private RectF processRect; // 进度条进度矩形
    private RectF rectF;  // 进度条背景矩形
    private RectF iconRect; // 图标矩形
    private Rect tempRect; // 计算用

    private Bitmap processBitmap;
    private String txtCurProcess;
    private String txtTotalProcess;


    public void setCurProcess(int cur) {
        this.PROGRESS_CUR = cur;
        this.txtCurProcess = String.valueOf(cur);
        postInvalidate();
    }

    public void setProcessBitmap(int res) {
        setProcessBitmap(BitmapFactory.decodeResource(getResources(), res));
    }

    public void setProcessBitmap(Bitmap processBitmap) {
        this.processBitmap = processBitmap;
    }

    public void setTotalProcess(int total) {
        this.PROGRESS_MAX = total;
        this.txtTotalProcess = String.valueOf(total);
    }

    public ProcessHorView(Context context) {
        super(context);
    }

    public ProcessHorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProcessHorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(Context context) {
        this.rectF = new RectF();
        this.iconRect = new RectF();
        this.tempRect = new Rect();
        this.processRect = new RectF();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(1);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setTextSize(DEF_TXT_SIZE);

        this.color_rect_stroke = getResources().getColor(R.color.color_white);
        this.txt_color = getResources().getColor(R.color.color_white);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, DEF_HEIGHT);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        int padding = ViewUtil.dp2px(getResources(), 16);

        if (processBitmap == null) return;

        int processBitmapWidth = processBitmap.getWidth();
        int processBitmapHeight = processBitmap.getHeight();

        float top = processBitmapHeight + ViewUtil.dp2px(getResources(), 3);

        rectF.set(processBitmapWidth >> 1, top, widthSize - padding, top + DEF_RECT_HEIGHT);

        processRect.set(processBitmapWidth >> 1, top, processBitmapWidth >> 1, top + DEF_RECT_HEIGHT);

        iconRect.set(processBitmapWidth >> 1, 0, processRect.right + processBitmapWidth, processBitmapHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //todo  // 矩形 及 进度条画笔
        this.rectPaint.setColor(this.color_rect_stroke);
        this.txtPaint.setColor(this.txt_color);

        if (processBitmap == null) {
            throw new IllegalArgumentException("Process Icon must be not null! Please call [setProcessBitmap(...)]");
        }
        //todo 占用的比例
        int bitmapWidth = processBitmap.getWidth() / 2;
        processRect.right = (rectF.width() * PROGRESS_CUR / PROGRESS_MAX) + bitmapWidth;

        iconRect.left = processRect.right - bitmapWidth;

        rectPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF, rectPaint);

        if (PROGRESS_CUR != 0) {
            rectPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(processRect, rectPaint);
        }

        canvas.drawBitmap(processBitmap, iconRect.left, iconRect.top, txtPaint);

        if (txtCurProcess == null) return;
        txtPaint.getTextBounds(txtCurProcess, 0, txtCurProcess.length(), tempRect);
        canvas.drawText(txtCurProcess,
                processRect.right - txtPaint.measureText(txtCurProcess) / 2,
                ViewUtil.dp2px(getResources(), 3) + tempRect.height(),
                txtPaint);

        if (txtTotalProcess == null) return;
        txtPaint.getTextBounds(txtTotalProcess, 0, txtTotalProcess.length(), tempRect);

        canvas.drawText(txtTotalProcess,
                rectF.right - tempRect.width(),
                rectF.bottom + ViewUtil.dp2px(getResources(), 3) + tempRect.height(),
                txtPaint);

    }
}
