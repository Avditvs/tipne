package com.souillard.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.souillard.R;

public class MenuButton extends LinearLayout{


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

    @SuppressLint("ResourceType")
    public void init(Context context, AttributeSet attrs){

        Drawable d;
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);
        textView = new TextView(getContext());
        imageView = new ImageView(getContext());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuButton);

        textView.setGravity(Gravity.CENTER);
        textView.setText(a.getText(R.styleable.MenuButton_text));
        textView.setTextColor(a.getColor(R.styleable.MenuButton_textColor, Color.BLACK));
        textView.setTextSize((int)a.getDimension(R.styleable.MenuButton_textSize, 20));

        d = getResources().getDrawable(a.getResourceId(R.styleable.MenuButton_src, 0));
        d.mutate().setColorFilter(a.getColor(R.styleable.MenuButton_image_color, Color.BLACK), PorterDuff.Mode.SRC_ATOP);
        imageView.setImageDrawable(d);

        int margin = (int)a.getDimension(R.styleable.MenuButton_margin, 0);

        LayoutParams lpTextView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpTextView.setMargins(margin, 0, margin, margin);
        textView.setLayoutParams(lpTextView);

        LayoutParams lpImageView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpImageView.setMargins(margin, margin, margin, 0);
        lpImageView.height = this.getMinimumHeight() - textView.getHeight() - 2*margin;
        lpImageView.width = this.getMinimumHeight() - 2*margin;
        imageView.setLayoutParams(lpImageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        GradientDrawable sp = new GradientDrawable();
        sp.setColor(a.getColor(R.styleable.MenuButton_background_color, Color.WHITE));
        sp.setCornerRadius((int)a.getDimension(R.styleable.MenuButton_corner_radius, 0f));
        this.setBackground(sp);



        addView(imageView);
        addView(textView);
        invalidate();

    }
}
