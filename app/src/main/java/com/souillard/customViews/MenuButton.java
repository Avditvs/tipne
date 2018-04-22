package com.souillard.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.souillard.R;

public class MenuButton extends LinearLayout {


    private TextView textView = null;
    private ImageView imageView = null;





    public MenuButton(Context context) {
        super(context);
    }

    public MenuButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MenuButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs){

        Drawable d = null;
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);
        textView = new TextView(getContext());
        imageView = new ImageView(getContext());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuButton);

        textView.setText("test");
        textView.setGravity(Gravity.CENTER);
        textView.setText(a.getText(R.styleable.MenuButton_text));
        textView.setTextColor(a.getColor(R.styleable.MenuButton_textColor, Color.BLACK));
        textView.setTextSize(a.getInt(R.styleable.MenuButton_textSize, 20));
        d = getResources().getDrawable(a.getResourceId(R.styleable.MenuButton_src, 0));
        d.mutate().setColorFilter(a.getColor(R.styleable.MenuButton_image_color, Color.BLACK), PorterDuff.Mode.SRC_ATOP);
        imageView.setImageDrawable(d);

        int margin = a.getInteger(R.styleable.MenuButton_margin, 0);
        LayoutParams lpImageView = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpImageView.setMargins(margin, margin, margin, 0);
        imageView.setLayoutParams(lpImageView);
        LayoutParams lpTextView = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpTextView.setMargins(margin, 0, margin, margin);
        textView.setLayoutParams(lpTextView);
        imageView.setMinimumHeight(this.getMinimumHeight() - textView.getHeight() - 2*margin);
        imageView.setMinimumWidth(this.getMinimumWidth() - textView.getWidth()-2*margin);


        addView(imageView);
        addView(textView);
        invalidate();


    }






}
