package com.yan.bsrgift;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2016/12/7.
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

        PointF firstPositionPoint = new PointF(
                endValue.getFirstPositionPoint().x
                , endValue.getFirstPositionPoint().y);
        PointF lastPositionPoint = new PointF(
                endValue.getLastPositionPoint().x
                , endValue.getLastPositionPoint().y);

        if (endValue.isPositionInScreen()) {
            firstPositionPoint = new PointF(
                    endValue.getFirstPositionPoint().x * endValue.screenWidth
                    , endValue.getFirstPositionPoint().y * endValue.screenHeight);
            lastPositionPoint = new PointF(
                    endValue.getLastPositionPoint().x * endValue.screenWidth
                    , endValue.getLastPositionPoint().y * endValue.screenHeight);

            pointPositionFList = new ArrayList<>();
            for (PointF pointF : endValue.getPositionControlPoint()) {
                pointPositionFList.add(
                        new PointF(pointF.x * endValue.screenWidth, pointF.y * endValue.screenHeight));
            }
        } else {
            pointPositionFList = endValue.getPositionControlPoint();
        }

        rotationList = endValue.getRotationControl();
        scaleList = endValue.getScaleControl();

        if (pointPositionFList != null && pointPositionFList.size() >= 2) {
            endValue.setTruePositionPoint(
                    getValueRoundHalfUp(getBSRPointValueX(pointPositionFList, pointPositionFList.size() - 1, 0, t))
                    , getValueRoundHalfUp(getBSRPointValueY(pointPositionFList, pointPositionFList.size() - 1, 0, t))
            );
        } else {
            endValue.setTruePositionPoint(
                    firstPositionPoint.x
                            + (lastPositionPoint.x
                            - firstPositionPoint.x) * t
                    , firstPositionPoint.y
                            + (lastPositionPoint.y
                            - firstPositionPoint.y) * t
            );
        }

        if (!endValue.isAutoRotation() && rotationList.size() < 2) {
            endValue.trueRotation = endValue.getFirstRotation()
                    + (endValue.getLastRotation() - endValue.getFirstRotation()) * t;
        } else if (!endValue.isAutoRotation() && rotationList.size() >= 2) {
            endValue.trueRotation = (
                    getValueRoundHalfUp(getBSRValue(rotationList, rotationList.size() - 1, 0, t)));
        }

        if (scaleList != null && scaleList.size() >= 2) {
            endValue.trueScaleValue = (
                    getBSRValue(scaleList, scaleList.size() - 1, 0, t));
        } else if (endValue.getLastScale() != Integer.MIN_VALUE) {
            endValue.trueScaleValue = endValue.getFirstScale()
                    + (endValue.getLastScale() - endValue.getFirstScale()) * t;
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

    private float getValueRoundHalfUp(float value) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    private float getBSRValue(List<Float> floats, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * floats.get(j) + t * floats.get(j + 1);
        }
        return (1 - t) * getBSRValue(floats, i - 1, j, t) + t * getBSRValue(floats, i - 1, j + 1, t);
    }

    private float getBSRPointValueX(List<PointF> pointFs, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * pointFs.get(j).x + t * pointFs.get(j + 1).x;
        }
        return (1 - t) * getBSRPointValueX(pointFs, i - 1, j, t) + t * getBSRPointValueX(pointFs, i - 1, j + 1, t);

    }

    private float getBSRPointValueY(List<PointF> pointFs, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * pointFs.get(j).y + t * pointFs.get(j + 1).y;
        }
        return (1 - t) * getBSRPointValueY(pointFs, i - 1, j, t) + t * getBSRPointValueY(pointFs, i - 1, j + 1, t);
    }
}
