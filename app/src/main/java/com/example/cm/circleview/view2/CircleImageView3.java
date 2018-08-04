package com.example.cm.circleview.view2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cm.circleview.R;

/**
 * Created by luojie on 2018/8/4.
 * 方式3. 使用clipPath 方式实现圆框效果
 */


public class CircleImageView3 extends ImageView {

    public static final int DEFAULT_BORDER_WIDTH = 0;
    private float mRadius;
    private int mBorderWidth;
    private int mBorderColor;
    private Paint mBorderPaint;
    private Path mPath;

    public CircleImageView3(Context context) {
        this(context,null);
    }

    public CircleImageView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(@Nullable AttributeSet attrs) {
        if (attrs!=null){
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView3);
            mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView3_circle_border_width3, DEFAULT_BORDER_WIDTH);
            mBorderColor = a.getInt(R.styleable.CircleImageView3_circle_border_color3, Color.BLACK);
            a.recycle();
        }
        if (mBorderWidth != DEFAULT_BORDER_WIDTH){
            mBorderPaint = new Paint();
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int min = Math.min(getMeasuredHeight(),getMaxWidth());
        mRadius = min/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.addCircle(mRadius, mRadius, mRadius,Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
        canvas.drawCircle(mRadius, mRadius, mRadius-mBorderWidth/2, mBorderPaint);
    }

    public void build(String url) {
        Glide.with(getContext())
                .load(url)
                .into(this);
    }
}
