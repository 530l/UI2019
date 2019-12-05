package com.r.ui2019.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.r.ui2019.utils.UIUtils;

public class TestViewGroup extends ViewGroup {

    Context context;

    public TestViewGroup(Context context) {
        super(context);
        this.context = context;
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 自定义ViewGroup 时候，一定要重新该方法，返回MarginLayoutParams ，
     * 要不然measureChildWithMargins android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
     * 拿不到Params  属性
     * LayoutParams 只有宽高
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 参数说明：View的宽 / 高测量规格
        // 会进行两次绘制，所以需要先进行清理

        int childCount = getChildCount();
        View childView = getChildAt(0);

        measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);

        // 3、获取子视图宽高
        int childWidth = childView.getMeasuredWidth();
        int childHeight = childView.getMeasuredHeight();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //MeasureSpec.EXACTLY：确切的大小，如：100dp或者march_parent
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //MeasureSpec.AT_MOST：大小不可超过某数值，如：wrap_content
            width = childWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //MeasureSpec.AT_MOST：大小不可超过某数值，如：wrap_content
            height = childHeight;
        }

        setMeasuredDimension(width, height);


    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top, right, bottom;
        View view = getChildAt(0);
        LayoutParams layoutParams = view.getLayoutParams();
        left = layoutParams.width;
        top = layoutParams.height;
        int ll = UIUtils.px2dip(context, left);
        int tt = UIUtils.px2dip(context, top);
        int MeasuredWidth = view.getMeasuredWidth();
        int MeasuredHeight = view.getMeasuredHeight();
        view.layout(0, 0, MeasuredWidth, MeasuredHeight);
    }
}
