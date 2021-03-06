package com.example.cm.circleview.view2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cm.circleview.R;

/**
 *
 * @author luojie
 * @date 2018/8/4
 * 添加外边框的效果
 */

public class CircleImageView2 extends ImageView {

    public static final int DEFAULT_BORDER_WIDTH = 0;
    private Paint mBorderPaint;
    private Paint mPaint;
    private int mRadius;
    private Matrix mMatrix;
    private int mBorderWidth;
    private int mBorderColor;
    private BitmapShader mBitmapShader;


    public CircleImageView2(Context context) {
        this(context, null);
    }

    public CircleImageView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView2);
            mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView2_circle_border_width2, DEFAULT_BORDER_WIDTH);
            mBorderColor = a.getInt(R.styleable.CircleImageView2_circle_border_color2, Color.BLACK);
            a.recycle();
        }
        super.setScaleType(ScaleType.CENTER_CROP);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();

        if (mBorderWidth != DEFAULT_BORDER_WIDTH) {
            mBorderPaint = new Paint();
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);

        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int min = Math.min(getMeasuredHeight(), getMeasuredWidth());
        mRadius = min / 2;
        setMeasuredDimension(min, min);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmapShader == null) {
            Bitmap bitmap = drawableToBitmap(getDrawable());
            //CLAMP表示，当所画图形的尺寸大于Bitmap的尺寸的时候，会用Bitmap四边的颜色填充剩余空间。
            //REPEAT表示，当我们绘制的图形尺寸大于Bitmap尺寸时，会用Bitmap重复平铺整个绘制的区域
            //MIRROR与REPEAT类似，当绘制的图形尺寸大于Bitmap尺寸时，MIRROR也会用Bitmap重复平铺整个绘图区域，与REPEAT不同的是，两个相邻的Bitmap互为镜像。
            if (bitmap == null) {
                super.onDraw(canvas);
                return;
            }

            mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            //这里可以进行图片相关的处理，注释的代码是设置比例缩放
//            float mScale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());
//            mMatrix.setScale(mScale, mScale);
//            mBitmapShader.setLocalMatrix(mMatrix);
        }

        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        if (mBorderWidth != DEFAULT_BORDER_WIDTH) {
            canvas.drawCircle(mRadius, mRadius, mRadius-mBorderWidth/2, mBorderPaint);
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return null;
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        //ARGB_8888 表示颜色色由4个8位组成即32位
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    public void build(String url) {
        Glide.with(getContext())
                .load(url)
                .into(this);
    }

}
