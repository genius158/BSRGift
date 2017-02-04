package com.yan.bsrgifview.bsr;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class BSREvaluator implements TypeEvaluator<BSRPathBase> {


    @Override
    public BSRPathBase evaluate(float t, BSRPathBase startValue, BSRPathBase endValue) {
        if (backListener != null) {
            backListener.onValueBack(t);
        }
        List<PointF> pointPositionFList;
        List<Float> scaleList;
        List<Float> rotationList;
        if (endValue.isPositionInScreen()) {
            pointPositionFList = new ArrayList<>();
            for (PointF pointF : endValue.getPositionControlPoint()) {
                pointPositionFList.add(new PointF(pointF.x * endValue.screenWidth, pointF.y * endValue.screenHeight));
            }
        } else {
            pointPositionFList = endValue.getPositionControlPoint();
        }

        rotationList = endValue.getRotationControl();
        scaleList = endValue.getScaleControl();

        if (pointPositionFList != null && pointPositionFList.size() >= 2) {
            BigDecimal b1 = new BigDecimal(
                    BSRPointValueX(pointPositionFList, pointPositionFList.size() - 1, 0, t)
            );
            float f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            BigDecimal b2 = new BigDecimal(BSRPointValueY(pointPositionFList, pointPositionFList.size() - 1, 0, t));
            float f2 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

            endValue.setTruePositionPoint(f1, f2);
        } else {
            endValue.setTruePositionPoint(
                    endValue.getFirstPositionPoint().x + (endValue.getLastPositionPoint().x - endValue.getFirstPositionPoint().x) * t
                    , endValue.getFirstPositionPoint().y + (endValue.getLastPositionPoint().y - endValue.getFirstPositionPoint().y) * t
            );
        }

        if (!endValue.isAutoRotation() && rotationList.size() < 2) {
            endValue.trueRotation = endValue.getFirstRotation()
                    + (endValue.getLastRotation() - endValue.getFirstRotation()) * t;
        } else if (!endValue.isAutoRotation() && rotationList.size() >= 2) {
            endValue.trueRotation = (BSRValue(rotationList, rotationList.size() - 1, 0, t));
        }

        if (scaleList != null && scaleList.size() >= 2) {
            endValue.trueScaleValue = (BSRValue(scaleList, scaleList.size() - 1, 0, t));
        } else if (endValue.getLastScale() != -10000) {
            endValue.trueScaleValue = endValue.getFirstScale() + (endValue.getLastScale() - endValue.getFirstScale()) * t;
        }

        return endValue;
    }

    private OnValueBackListener backListener;

    public void setBackListener(OnValueBackListener backListener) {
        this.backListener = backListener;
    }

    public interface OnValueBackListener {
        void onValueBack(float value);
    }

    private float BSRValue(List<Float> floats, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * floats.get(j) + t * floats.get(j + 1);
        }
        return (1 - t) * BSRValue(floats, i - 1, j, t) + t * BSRValue(floats, i - 1, j + 1, t);
    }

    private float BSRPointValueX(List<PointF> pointFs, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * pointFs.get(j).x + t * pointFs.get(j + 1).x;
        }
        return (1 - t) * BSRPointValueX(pointFs, i - 1, j, t) + t * BSRPointValueX(pointFs, i - 1, j + 1, t);

    }

    private float BSRPointValueY(List<PointF> pointFs, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * pointFs.get(j).y + t * pointFs.get(j + 1).y;
        }
        return (1 - t) * BSRPointValueY(pointFs, i - 1, j, t) + t * BSRPointValueY(pointFs, i - 1, j + 1, t);
    }
}
