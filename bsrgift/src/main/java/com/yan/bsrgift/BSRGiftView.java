package com.yan.bsrgift;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2016/12/8.
 */

public class BSRGiftView extends View {
    private List<BSRPathPoint> bsrPathPoints;
    private BSRPathPoint directDrawBSRPoint;

    private int defaultTime = 1000;
    private ValueAnimator animMain;
    private float alphaTrigger = 0.8f;
    private float viewWidth;
    private float viewHeight;
    private OnAnmEndListener onSingleFinish;
    private OnAnimalFinish onAnimalFinish;

    protected void startDrawA() {
        if (animMain == null) {
            animMain = ValueAnimator.ofFloat(1);
            animMain.setInterpolator(new AccelerateDecelerateInterpolator());
            animMain.setDuration(defaultTime);
            animMain.setRepeatCount(ValueAnimator.INFINITE);
            animMain.setRepeatMode(ValueAnimator.RESTART);
            animMain.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    postInvalidate();
                }
            });
        }
        if (!animMain.isRunning()) {
            animMain.start();
        }
    }

    public boolean isRunning() {
        return (animMain != null) && animMain.isRunning();
    }

    public void stopDrawAnimation() {
        if (isRunning()) {
            animMain.cancel();
            if (onAnimalFinish != null) {
                onAnimalFinish.onFinish();
            }
        }
    }

    private void init() {
        bsrPathPoints = new ArrayList<>();
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (directDrawBSRPoint != null) {
            directDrawBSRPoint.drawBSRPoint(canvas, viewWidth, viewHeight, true);
        } else
            drawBSRPathPoint(canvas);

    }

    public void addBSRPathPoint(BSRPathPoint bsrPathPoint) {
        bsrPathPoints.add(bsrPathPoint);
        startDrawAnimation(bsrPathPoint);
    }

    public void addBSRPathPointAndDraw(BSRPathPoint bsrPathPoint) {
        directDrawBSRPoint = bsrPathPoint;
        postInvalidate();
    }

    public void addBSRPathPoints(List<BSRPathPoint> bsrPathPoints) {
        this.bsrPathPoints.addAll(bsrPathPoints);
        startDrawAnimation(bsrPathPoints);
    }

    private Matrix matrix;

    public void setScale(float scaleX, float scaleY) {
        matrix.setScale(scaleX, scaleY);
    }

    private void drawBSRPathPoint(Canvas canvas) {
        canvas.setMatrix(matrix);
        for (BSRPathPoint bsrPathPoint : bsrPathPoints) {
            bsrPathPoint.drawBSRPoint(canvas, viewWidth, viewHeight, false);
        }
    }

    private void startAnimation(final List<BSRPathPoint> bsrPathPoints) {
        for (BSRPathPoint bsrPathPoint : bsrPathPoints) {
            startBsrPathPointAnimation(bsrPathPoint);
        }
    }

    private void startBsrPathPointAnimation(BSRPathPoint bsrPathPoint) {
        bsrPathPoint.startBsrAnimation(new OnAnmEndListener() {
            @Override
            public void onAnimationEnd(BSRPathBase bsrPathP) {
                if (onSingleFinish != null) {
                    onSingleFinish.onAnimationEnd(bsrPathP);
                }
                BSRGiftView.this.bsrPathPoints.remove(bsrPathP);
                if (BSRGiftView.this.bsrPathPoints.isEmpty()) {
                    stopDrawAnimation();
                }
            }
        }, alphaTrigger);

    }

    public void setAlphaTrigger(float alphaTrigger) {
        this.alphaTrigger = alphaTrigger;
    }

    private void startDrawAnimation(Object obj) {
        if (obj instanceof List) {
            startAnimation((List<BSRPathPoint>) obj);
        } else
            startBsrPathPointAnimation((BSRPathPoint) obj);

        startDrawA();
    }


    public void setOnSingleFinish(OnAnmEndListener onSingleFinish) {
        this.onSingleFinish = onSingleFinish;
    }


    public interface OnAnimalFinish {
        void onFinish();
    }

    public void setOnAnimalFinish(OnAnimalFinish onAnimalFinish) {
        this.onAnimalFinish = onAnimalFinish;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    public BSRGiftView(Context context) {
        super(context);
        init();
    }

    public BSRGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BSRGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
