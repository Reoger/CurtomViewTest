package com.example.cm.circleview.view2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cm.circleview.R;

import java.util.Arrays;

/**
 *
 * @author luojie
 * @date 2018/8/2
 * way 1. to use PorterDuffxfermode to show the photo
 * 实现加边框的效果
 */

public class CircleImageView1 extends ImageView {

    public static final int DEFAULT_BORDER_WIDTH = 0;

    private Paint mPaint;
    private Paint mBorderPaint;
    private Shape mShape;
    private Shape mBorderShape;
    private float mRadius;
    private float mBorderWidth;
    private int mBorderColor;
    private PorterDuffXfermode mBorderDuffMode;
    private Bitmap mBorderBitmap;

    private float[] outerRadii = new float[8];

    public CircleImageView1(Context context) {
        this(context, null);
    }

    public CircleImageView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView1);
            mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView1_circle_border_width1, DEFAULT_BORDER_WIDTH);
            mBorderColor = a.getInt(R.styleable.CircleImageView1_circle_border_color1, Color.BLACK);
            a.recycle();
        }
        setLayerType(LAYER_TYPE_HARDWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        if (mBorderWidth != DEFAULT_BORDER_WIDTH) {
            mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setFilterBitmap(true);
            mBorderDuffMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int min = Math.min(width, height);
        mRadius = min / 2;
        setMeasuredDimension(min, min);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            if (mShape == null) {
                Arrays.fill(outerRadii, mRadius);
                mShape = new RoundRectShape(outerRadii, null, null);
                if (mBorderWidth != DEFAULT_BORDER_WIDTH && mBorderShape == null) {
                    mBorderShape = new RoundRectShape(outerRadii, null, null);
                }

            }
            mShape.resize(getWidth(), getHeight());
            if (mBorderWidth != DEFAULT_BORDER_WIDTH && mBorderShape != null) {
                mBorderShape.resize(getWidth() - mBorderWidth * 2, getHeight() - mBorderWidth * 2);
                makeStrokeBitmap();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.getSaveCount();
        canvas.save();
        super.onDraw(canvas);
        if (mBorderWidth != DEFAULT_BORDER_WIDTH && mBorderShape != null && mBorderBitmap != null) {
            int i = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
            //1.绘制外边框颜色的正方形
            mBorderPaint.setXfermode(null);
            canvas.drawBitmap(mBorderBitmap, 0, 0, mBorderPaint);
            //2.适当画布的中心位置
            canvas.translate(mBorderWidth, mBorderWidth);
            //3.绘制减去边框长度的图片，模式为DST_OUT
            mBorderPaint.setXfermode(mBorderDuffMode);
            mBorderShape.draw(canvas, mBorderPaint);
            canvas.restoreToCount(i);
        }

        if (mShape != null) {
            mShape.draw(canvas, mPaint);
        }
        canvas.restoreToCount(saveCount);
    }

    private void makeStrokeBitmap() {
        if (mBorderWidth <= 0) return;

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w == 0 || h == 0) {
            return;
        }

        releaseBorderBitmap();

        mBorderBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mBorderBitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(mBorderColor);
        c.drawRect(new RectF(0, 0, w, h), p);
    }

    private void releaseBorderBitmap() {
        if (mBorderBitmap != null) {
            mBorderBitmap.recycle();
            mBorderBitmap = null;
        }
    }


    public void build(String url) {
        Glide.with(getContext()).load(url).into(this);
    }
}
