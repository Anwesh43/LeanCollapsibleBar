package com.anwesome.ui.leancollapsiblebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.Toolbar;


/**
 * Created by anweshmishra on 30/03/17.
 */
public class CollapsibleBar extends Toolbar {
    private Bitmap bitmap;
    private int color;
    private int time =0,w,h,a=0;
    public CollapsibleBar(Context context, Bitmap bitmap,String title,String subTitle,int color) {
        super(context);
        this.bitmap = bitmap;
        setTitle(title);
        setSubtitle(subTitle);
        setWillNotDraw(false);
        this.color = color;
        setTitleTextColor(Color.WHITE);
        setSubtitleTextColor(Color.WHITE);
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            bitmap = Bitmap.createScaledBitmap(bitmap,w,h,true);
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(bitmap,0,0,paint);
        int alpha = 0;
        if(getMeasuredHeight()<0.4f*h) {
            alpha = (int)(255*(1 - (getMeasuredHeight()-0.2f*h)/0.8f));
        }

        canvas.drawColor(Color.argb(alpha,Color.red(color),Color.green(color),Color.blue(color)));
        time++;
    }
    public boolean shouldScroll(int dir) {
        return (dir == 1 && getMeasuredHeight()>0.2f*h) || (dir == -1 && getMeasuredHeight()<h);
    }
    public void handleScroll(float gap) {
        setMeasuredDimension(w,getMeasuredHeight()-(int)gap);
        if(getMeasuredHeight()<0.2f*h) {
            setMeasuredDimension(w,(int)(0.2f*h));
        }
        if(getMeasuredHeight()>h) {
            setMeasuredDimension(w,h);
        }
        postInvalidate();
    }
}
