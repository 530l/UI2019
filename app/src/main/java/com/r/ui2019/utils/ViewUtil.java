package com.r.ui2019.utils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * Created by unicolas on 2016-09-29.
 */

public class ViewUtil {
    private static final String TAG = "ViewUtil";
    public static int getListViewParams(ListView listView) {
        //通过ListView获取其中的适配器adapter
        ListAdapter listAdapter = listView.getAdapter();

        //声明默认高度为0
        int totalHeight = 0;
        //便利ListView所有的item，累加所有item的高度就是ListView的实际高度
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getLayoutParams().height;
            Log.e(TAG, "getListViewParams: " + totalHeight + " -- " + listItem.getMeasuredHeight()+" -- "+listItem.getLayoutParams().height + " -- " + listAdapter.getCount() + " -- " + " -- " + i);
        }
        //将累加获取的totalHeight赋值给LayoutParams的height属性
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        return height;
    }

    public static int getListViewParamsForFLDA(ListView listView) {
        //通过ListView获取其中的适配器adapter
        ListAdapter listAdapter = listView.getAdapter();

        //声明默认高度为0
        int totalHeight = 0;
        //便利ListView所有的item，累加所有item的高度就是ListView的实际高度
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            Log.e(TAG, "getListViewParamsForFLDA: " + totalHeight + " -- " + listItem.getMeasuredHeight()+" -- "+listItem.getLayoutParams().height + " -- " + listAdapter.getCount() + " -- " + " -- " + i);
            listItem.measure(0, 0);
        }
        //将累加获取的totalHeight赋值给LayoutParams的height属性
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        return height;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int getGirdViewParams(GridView gridView) {
        //通过ListView获取其中的适配器adapter
        ListAdapter listAdapter = gridView.getAdapter();
        //声明默认高度为0
        int totalHeight = 0;
        //遍历ListView所有的item，累加所有item的高度就是ListView的实际高度
        for (int i = 0; i < listAdapter.getCount(); i += 2) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            Log.e(TAG, "getGirdViewParams: " + " -- " + totalHeight + " -- " + listItem.getMeasuredHeight() + " -- " + listItem.getLayoutParams().height + " -- " + listAdapter.getCount() + " -- " + " -- " + i);
            listItem.measure(0, 0);
        }
        //将累加获取的totalHeight赋值给LayoutParams的height属性
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getVerticalSpacing() * (listAdapter.getCount() - 1));
        int height = totalHeight + (gridView.getVerticalSpacing() * (listAdapter.getCount() - 1));

        return height;
    }

    public static int getListViewParamsForClassRecomm(ListView listView) {
        //通过ListView获取其中的适配器adapter
        ListAdapter listAdapter = listView.getAdapter();

        //声明默认高度为0
        int totalHeight = 0;
        //便利ListView所有的item，累加所有item的高度就是ListView的实际高度
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getLayoutParams().height;
            Log.e(TAG, "getListViewParamsForClassRecomm: " + totalHeight + " -- " + listItem.getMeasuredHeight()+" -- "+listItem.getLayoutParams().height + " -- " + listAdapter.getCount() + " -- "  + i);
            listItem.measure(0, 0);
        }
        //将累加获取的totalHeight赋值给LayoutParams的height属性
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        return height;
    }

    public static int getLayoutParamsHeight(ViewGroup viewGroup) {
        int height = viewGroup.getLayoutParams().height;
        int measuredHeight = viewGroup.getMeasuredHeight();
        Log.e(TAG, "getLayoutParamsHeight: " + height + " -- " + measuredHeight);
        return height;
    }

    public static int dp2px(Resources res, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static int sp2px(Resources res, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
    }

}
