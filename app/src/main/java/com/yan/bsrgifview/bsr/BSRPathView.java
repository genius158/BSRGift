package com.yan.bsrgifview.bsr;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2016/12/8.
 */
public class BSRPathView extends BSRPathBase {
    private View view;
    private BSREvaluator.OnValueBackListener backListener;
    private List<OnAnmEndListener> endListeners;

    private Interpolator interpolator;

    public void setChild(View child) {
        view = child;
    }

    public View getChild() {
        return view;
    }

    public BSRPathView() {
        super();
        endListeners = new ArrayList<>();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void startBsrAnimation(OnAnmEndListener endListener, final float alphaTrigger) {
        BSREvaluator bsrEvaluator = new BSREvaluator();
        if (alphaTrigger != -1) {
            if (backListener == null) {
                backListener = new BSREvaluator.OnValueBackListener() {
                    @Override
                    public void onValueBack(float value) {
                        if (value > alphaTrigger) {
                            view.setAlpha(((1 / (1 - alphaTrigger)) * (1 - value)));
                        }
                    }
                };
            }
            bsrEvaluator.setBackListener(backListener);
        }

        endListeners.add(endListener);
        ValueAnimator anim = ValueAnimator.ofObject(bsrEvaluator, this);
        anim.setInterpolator((interpolator == null ? new AccelerateDecelerateInterpolator() : interpolator));
        anim.setDuration(during);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BSRPathBase bsrPathBase = (BSRPathBase) animation.getAnimatedValue();
                view.setX(bsrPathBase.truePointX);
                view.setY(bsrPathBase.truePointY);

                view.setPivotX(bsrPathBase.xPercent);
                view.setPivotY(bsrPathBase.yPercent);

                if (bsrPathBase.trueScaleValue != -1) {
                    view.setScaleX(bsrPathBase.trueScaleValue);
                    view.setScaleY(bsrPathBase.trueScaleValue);
                }

                if (bsrPathBase.trueRotation == -10000) {
                    if (lastPoint == null) {
                        lastPoint = new PointF();
                        lastPoint.set(bsrPathBase.truePointX, bsrPathBase.truePointY);
                    }

                    float degree = getRotationPoint2Point(lastPoint.x, lastPoint.y, bsrPathBase.truePointX, bsrPathBase.truePointY);
                    view.setRotation(degree + bsrPathBase.getFirstRotation());

                    lastPoint.set(bsrPathBase.truePointX, bsrPathBase.truePointY);
                }

                if (onAnimationStartListener != null) {
                    onAnimationStartListener.onAnimationStart();
                }
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                for (OnAnmEndListener endListener : endListeners) {
                    endListener.onAnimationEnd(BSRPathView.this);
                }
            }
        });
        anim.start();
    }

    public void addEndListeners(OnAnmEndListener endListener) {
        this.endListeners.add(endListener);
    }

    OnAnimationStartListener onAnimationStartListener;

    public void setOnAnimationStartListener(OnAnimationStartListener onAnimationStartListener) {
        this.onAnimationStartListener = onAnimationStartListener;
    }

    public interface OnAnimationStartListener {
        void onAnimationStart();
    }

}
