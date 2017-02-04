package com.yan.bsrgifview.bsr;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2016/12/8.
 */
public class BSRPathPoint extends BSRPathBase {
    private Bitmap res;
    private Paint paint;
    private Matrix matrix;
    private Interpolator interpolator;

    private List<OnAnmEndListener> endListeners;
    private BSREvaluator.OnValueBackListener backListener;

    private boolean isAdjust = false;

    public void adjustWidth(boolean isAdjust) {
        this.isAdjust = isAdjust;
    }

    public BSRPathPoint() {
        super();
        paint = new Paint();
        matrix = new Matrix();
        endListeners = new ArrayList<>();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void setRes(Context context, int res) {
        this.res = BitmapFactory.decodeResource(context.getResources(), res);
    }

    public void setAntiAlias(boolean isAlias) {
        paint.setAntiAlias(isAlias);
    }

    public Bitmap getRes() {
        return res;
    }

    public void drawBSRPoint(Canvas canvas, float viewWidth, float viewhight) {
        if (isAdjust) {
            float times = viewWidth / getRes().getWidth();
            matrix.setScale(times, times);
            canvas.drawBitmap(res, matrix, paint);
        }
        if (canDraw) {
            canvas.drawBitmap(res, matrix, paint);
        }
    }

    public void startBsrAnimation(OnAnmEndListener endListener, final float alphaTrigger) {

        BSREvaluator bsrEvaluator = new BSREvaluator();
        if (alphaTrigger != -1) {
            if (backListener == null) {
                backListener = new BSREvaluator.OnValueBackListener() {
                    @Override
                    public void onValueBack(float value) {
                        if (value > alphaTrigger)
                            if (paint != null)
                                paint.setAlpha((int) (255 - 255 * (1 - ((1 / (1 - alphaTrigger)) * (1 - value)))));
                    }
                };
            }
            bsrEvaluator.setBackListener(backListener);
        }
        endListeners.add(endListener);
        final ValueAnimator anim = ValueAnimator.ofObject(bsrEvaluator, this);
        anim.setInterpolator((interpolator == null ? new AccelerateDecelerateInterpolator() : interpolator));
        anim.setDuration(during);
        anim.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        BSRPathBase bsrPathBase = (BSRPathBase) animation.getAnimatedValue();

                        float degree = 0;
                        if (attachPathBase != null) {
                            matrix.set(((BSRPathPoint) attachPathBase).getMatrix());
                            matrix.preTranslate(attachDx, attachDy);
                        } else {
                            if (lastPoint == null) {
                                lastPoint = new PointF();
                                lastPoint.set(bsrPathBase.truePointX, bsrPathBase.truePointY);
                            }

                            if (bsrPathBase.trueRotation == -10000) {
                                degree = getRotationPoint2Point(lastPoint.x, lastPoint.y, bsrPathBase.truePointX, bsrPathBase.truePointY);
                            }

                            matrix.setTranslate(bsrPathBase.truePointX, bsrPathBase.truePointY);
                        }
                        if (bsrPathBase.trueScaleValue != -1) {
                            matrix.preScale(bsrPathBase.trueScaleValue, bsrPathBase.trueScaleValue, res.getWidth() * xPercent, res.getHeight() * yPercent);
                        }

                        if (bsrPathBase.trueRotation == -10000) {
                            matrix.preRotate(degree, res.getWidth() * xPercent, res.getHeight() * yPercent);
                            if (lastPoint != null)
                                lastPoint.set(bsrPathBase.truePointX, bsrPathBase.truePointY);
                        } else {
                            matrix.preRotate(bsrPathBase.trueRotation, res.getWidth() * xPercent, res.getHeight() * yPercent);
                        }
                    }
                }
        );

        anim.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        for (OnAnmEndListener endListener : endListeners) {
                            endListener.onAnimationEnd(BSRPathPoint.this);
                        }
                    }
                }
        );

        ValueAnimator tempAnm = ValueAnimator.ofFloat(1);
        tempAnm.setDuration((long) delayTime);
        if (delayTime == 0) {
            anim.start();
            canDraw = true;
        } else {
            tempAnm.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    anim.start();
                    canDraw = true;
                }
            });
            tempAnm.start();
        }
    }

    public Matrix getMatrix() {
        return matrix;
    }

    boolean canDraw = false;

}
