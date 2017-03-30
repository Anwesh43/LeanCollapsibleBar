package com.anwesome.ui.leancollapsiblebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 30/03/17.
 */
public class CollapsibleLayout extends ViewGroup {
    private int w,h;
    private ScrollListener scrollListener;
    public CollapsibleLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        initWH(context);
        initGestureDetector();
    }
    public void initWH(Context context) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        Point size = new Point();
        display.getRealSize(size);
        w = size.x;
        h = size.y;
    }
    public CollapsibleLayout(Context context) {
        super(context);
        initWH(context);
        initGestureDetector();
    }
    public void initGestureDetector() {
       scrollListener =  new ScrollListener() {
            @Override
            public void handleScroll(float h, float gap,int dir) {
                for(int i=0;i<getChildCount();i++) {
                    View nextView = null;
                    View view = getChildAt(i);
                    if(i+1 <getChildCount()) {
                        nextView = getChildAt(i+1);
                    }
                    boolean lessThanNextView = nextView!=null?h<nextView.getY():true;
                    if(view instanceof CollapsibleBar && h>view.getY() && lessThanNextView) {
                        CollapsibleBar collapsibleBar = (CollapsibleBar)view;
                        if(collapsibleBar.shouldScroll(dir)) {
                            collapsibleBar.handleScroll(gap);
                            requestLayout();
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        };
    }
    private CollapsibleBar collapsibleBar;
    public void addCollapsibleBar(Bitmap bitmap,String title,String subTitle,int color) {
        collapsibleBar = new CollapsibleBar(getContext(),bitmap,title,subTitle,color);
        addView(collapsibleBar,new LayoutParams(w,h/2));
    }
    public void onMeasure(int wspec,int hspec) {
        int newH = 0;
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            measureChild(view,wspec,hspec);
            newH += view.getMeasuredHeight();
        }
        setMeasuredDimension(w,Math.max(newH,h));
    }
    public void onLayout(boolean reloaded,int a,int b,int w,int h) {
        int y = 0;
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            view.layout(0,y,w,y+view.getMeasuredHeight());
            y += view.getMeasuredHeight();
        }
    }
    private float y1,gap,total=0;
    private boolean isDown = false;
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!isDown) {
                    y1 = event.getY();
                    isDown = true;

                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDown) {
                    float y = event.getY();
                    int dir = 1;
                    if(y>y1) {
                        dir = -1;
                    }
                    float gap = y1-y;
                    total+=gap;
                    scrollListener.handleScroll(total,gap,dir);
                    y1 = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
}
