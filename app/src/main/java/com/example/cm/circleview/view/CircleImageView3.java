package com.example.cm.circleview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by luojie on 2018/8/3.
 * way3. to use ClipPath to do this
 */

public class CircleImageView3 extends ImageView {

    private float mRadius;
    private Path mPath;

    public CircleImageView3(Context context) {
        this(context,null);
    }

    public CircleImageView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
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
    }

    public void build(String url) {
        Glide.with(getContext())
                .load(url)
                .into(this);
    }
}
