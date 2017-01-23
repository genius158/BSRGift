package com.yan.bsrgifview.bsr;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.util.Log;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class BSREvaluator implements TypeEvaluator<BSRPathBase> {
    List<PointF> pointFList;
    List<Float> scaleList;
    List<Float> rotationList;

    @Override
    public BSRPathBase evaluate(float t, BSRPathBase startValue, BSRPathBase endValue) {
        if (backListener != null) {
            backListener.onValueBack(t);
        }

        pointFList = endValue.getPositionControlPoint();
        rotationList = endValue.getRotationControl();
        scaleList = endValue.getScaleControl();

        if (pointFList.size() >= 2) {
            BigDecimal b1 = new BigDecimal(deCasteljauX(pointFList.size() - 1, 0, t));
            float f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            BigDecimal b2 = new BigDecimal(deCasteljauY(pointFList.size() - 1, 0, t));
            float f2 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

            endValue.setTruePoint(f1, f2);
        }

        if (!endValue.isRotationFollow() && rotationList.size() < 2) {
            endValue.rotation = endValue.getRotation() * t;
        } else if (!endValue.isRotationFollow() && rotationList.size() >= 2) {
            endValue.rotation = (deCasteljauRotation(rotationList.size() - 1, 0, t));
        }

        if (scaleList.size() >= 2)
            endValue.currentScaleValue = (deCasteljauScale(scaleList.size() - 1, 0, t));

        return endValue;
    }

    OnValueBackListener backListener;

    public void setBackListener(OnValueBackListener backListener) {
        this.backListener = backListener;
    }

    public interface OnValueBackListener {
        void onValueBack(float value);
    }

    /**
     * deCasteljau算法
     *
     * @param i 阶数
     * @param j 0
     * @param t 时间
     * @return
     */
    private float deCasteljauX(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * pointFList.get(j).x + t * pointFList.get(j + 1).x;
        }
        return (1 - t) * deCasteljauX(i - 1, j, t) + t * deCasteljauX(i - 1, j + 1, t);
    }


    /**
     * deCasteljau算法
     *
     * @param i 阶数
     * @param j 0
     * @param t 时间
     * @return
     */
    private float deCasteljauY(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * pointFList.get(j).y + t * pointFList.get(j + 1).y;
        }
        return (1 - t) * deCasteljauY(i - 1, j, t) + t * deCasteljauY(i - 1, j + 1, t);
    }

    private float deCasteljauScale(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * scaleList.get(j) + t * scaleList.get(j + 1);
        }
        return (1 - t) * deCasteljauScale(i - 1, j, t) + t * deCasteljauScale(i - 1, j + 1, t);
    }

    private float deCasteljauRotation(int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * rotationList.get(j) + t * rotationList.get(j + 1);
        }
        return (1 - t) * deCasteljauRotation(i - 1, j, t) + t * deCasteljauRotation(i - 1, j + 1, t);
    }

}
