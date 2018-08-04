package com.example.cm.circleview.view1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cm.circleview.R;

import java.util.Arrays;

/**
 * Created by luojie on 2018/8/2.
 * way 1. to use PorterDuffxfermode to show the photo
 */

public class CircleImageView1 extends ImageView {

    private Paint mPaint;
    private Shape mShape;
    private float mRadius;

    private float[] outerRadii = new float[8];

    public CircleImageView1(Context context) {
        this(context,null);
    }

    public CircleImageView1(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleImageView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setLayerType(LAYER_TYPE_HARDWARE,null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int min = Math.min(width,height);
        mRadius = min/2;
        setMeasuredDimension(min,min);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            if (mShape == null){
                Arrays.fill(outerRadii,mRadius);
                mShape = new RoundRectShape(outerRadii,null,null);
            }
            mShape.resize(getWidth(),getHeight());
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.getSaveCount();
        canvas.save();
        super.onDraw(canvas);
        if (mShape != null){
            mShape.draw(canvas,mPaint);
        }
        canvas.restoreToCount(saveCount);
    }

    public void build(String url){
        Glide.with(getContext()).load(url).into(this);
    }
}
