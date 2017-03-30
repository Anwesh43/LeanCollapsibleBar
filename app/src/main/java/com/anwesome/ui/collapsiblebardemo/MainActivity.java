package com.anwesome.ui.collapsiblebardemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anwesome.ui.leancollapsiblebar.CollapsibleLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CollapsibleLayout collapsibleLayout = new CollapsibleLayout(this);
        collapsibleLayout.addCollapsibleBar(BitmapFactory.decodeResource(getResources(),R.drawable.gojira),"Demo","Demo works",Color.parseColor("#3F51B5"));
        setContentView(collapsibleLayout);
    }
}
