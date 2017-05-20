package com.yan.bsrgift;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2016/12/8.
 */
public class BSRPathBase {
    private List<PointF> positionControlPoint;
    private List<Float> scaleControl;
    private List<Float> rotationControl;

    int during = 3000;

    private PointF positionPointFirst;
    private PointF positionPointLast;
    float truePointX;
    float truePointY;

    private float rotationFirst;
    private float rotationLast;
    float trueRotation = Integer.MIN_VALUE;

    private float scaleFirst;
    private float scaleLast = Integer.MIN_VALUE;
    float trueScaleValue = -1;

    float xPercent = 0.5f;
    float yPercent = 0.5f;

    float xPositionPercent = 0.0f;
    float yPositionPercent = 0.0f;

    BSRPathBase attachPathBase;
    float attachDx = 0;
    float attachDy = 0;

    PointF lastPoint;
    float delayTime = 0;

    private boolean pointPercentTrigger = false;
    private boolean isAutoRotation;
    boolean isPositionInScreen = false;

    float screenWidth = Integer.MIN_VALUE;
    float screenHeight = Integer.MIN_VALUE;

    float scaleInScreen = Integer.MIN_VALUE;
    boolean isCenterInside = false;
    List<OnAnmEndListener> endListeners;

    private double rotationPoint2Point;

    public void setAdjustScaleInScreen(float scaleInScreen) {
        this.scaleInScreen = scaleInScreen;
    }

    public void centerInside() {
        isCenterInside = true;
        scaleInScreen = 1;
    }

    public void setPositionInScreen(boolean isPositionInScreen) {
        this.isPositionInScreen = isPositionInScreen;
    }


    public void setPositionXPercent(float xPositionPercent) {
        this.xPositionPercent = xPositionPercent;
    }

    public void setPositionYPercent(float yPositionPercent) {
        this.yPositionPercent = yPositionPercent;
    }

    public boolean isPositionInScreen() {
        return isPositionInScreen;
    }

    public boolean isAutoRotation() {
        return isAutoRotation;
    }

    public void setAutoRotation(boolean isAutoRotation) {
        this.isAutoRotation = isAutoRotation;
    }

    public void setPointPercentTrigger(boolean pointPercentTrigger) {
        this.pointPercentTrigger = pointPercentTrigger;
    }

    public int getDuring() {
        return during;
    }

    public void setXPercent(float xPercent) {
        this.xPercent = xPercent;
    }

    public void setYPercent(float yPercent) {
        this.yPercent = yPercent;
    }

    public void setLastRotation(float rotationLast) {
        this.rotationLast = rotationLast;
    }

    public void setFirstRotation(float rotationFirst) {
        this.rotationFirst = rotationFirst;
    }

    public float getLastRotation() {
        return this.rotationLast;
    }

    public float getFirstRotation() {
        return this.rotationFirst;
    }

    public void setRotation(float rotation) {
        setFirstScale(rotation);
        setLastRotation(rotation);
    }

    public PointF getFirstPositionPoint() {
        return positionPointFirst;
    }

    public void setFirstPositionPoint(float pointPositionX, float pointPositionY) {
        this.positionPointFirst.set(pointPositionX, pointPositionY);
    }

    public PointF getLastPositionPoint() {
        return positionPointLast;
    }

    public void setLastPositionPoint(float pointPositionX, float pointPositionY) {
        this.positionPointLast.set(pointPositionX, pointPositionY);
    }

    public void setPositionPoint(float pointPositionX, float pointPositionY) {
        setFirstPositionPoint(pointPositionX, pointPositionY);
        setLastPositionPoint(pointPositionX, pointPositionY);
    }

    public float getFirstScale() {
        return scaleFirst;
    }

    public void setFirstScale(float scaleFirst) {
        this.scaleFirst = scaleFirst;
    }

    public float getLastScale() {
        return scaleLast;
    }

    public void setLastScale(float scaleLast) {
        this.scaleLast = scaleLast;
    }

    public void setScale(float scale) {
        setFirstScale(scale);
        setLastScale(scale);
    }

    public void setDuring(int during) {
        this.during = during;
    }

    public BSRPathBase() {
        positionControlPoint = new ArrayList<>();
        scaleControl = new ArrayList<>();
        rotationControl = new ArrayList<>();
        positionPointFirst = new PointF(0, 0);
        positionPointLast = new PointF(0, 0);
        endListeners = new ArrayList<>();
    }

    public List<Float> getRotationControl() {
        return rotationControl;
    }

    public void addRotationControl(Float rotation) {
        this.rotationControl.add(rotation);
    }

    public List<PointF> getPositionControlPoint() {
        return positionControlPoint;
    }

    public void attachPoint(BSRPathBase bsrPathBase) {
        this.attachPathBase = bsrPathBase;
    }

    public void attachPoint(BSRPathBase bsrPathBase, float dx, float dy) {
        this.attachPathBase = bsrPathBase;
        attachDx = dx;
        attachDy = dy;
    }

    public void addPositionControlPoint(float x, float y) {
        positionControlPoint.add(new PointF(x, y));
    }

    public void addScaleControl(float scaleValue) {
        scaleControl.add(scaleValue);
    }

    public List<Float> getScaleControl() {
        return scaleControl;
    }

    public void setTruePositionPoint(float x, float y) {
        truePointX = x;
        truePointY = y;
    }

    public void setDelay(float delayTime) {
        this.delayTime = delayTime;
    }

    public float getDelay() {
        return delayTime;
    }


    float getRotationPoint2Point(float x1, float y1, float x2, float y2) {
        double k1 = 0;//(double) (y1 - y1) / (x1 * 2 - x1)
        double k2 = (double) (y2 - y1) / (x2 - x1);
        double tmpDegree = Math.atan((Math.abs(k1 - k2)) / (1 + k1 * k2)) / Math.PI * 180;

        if (x2 > x1 && y2 < y1) {
            rotationPoint2Point = 90 - tmpDegree;
        } else if (x2 > x1 && y2 > y1) {
            rotationPoint2Point = 90 + tmpDegree;
        } else if (x2 < x1 && y2 > y1) {
            rotationPoint2Point = 270 - tmpDegree;
        } else if (x2 < x1 && y2 < y1) {
            rotationPoint2Point = 270 + tmpDegree;
        } else if (x2 == x1 && y2 < y1) {
            rotationPoint2Point = 0;
        } else if (x2 == x1 && y2 > y1) {
            rotationPoint2Point = 180;
        } else if (x1 == x1 && y2 == y2) {
        }

        return (float) rotationPoint2Point;
    }
}
