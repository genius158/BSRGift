package com.yan.bsrgifview.bsr;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 2016/12/8.
 */
public class BSRPathBase {
    protected List<PointF> positionControlPoint;
    protected List<Float> scaleControl;
    protected List<Float> rotationControl;

    protected int during = 3000;

    protected PointF positionPointFirst;
    protected PointF positionPointLast;
    public float truePointX;
    public float truePointY;

    protected float rotationFirst;
    protected float rotationLast;
    public float trueRotation = -10000;

    protected float scaleFirst;
    protected float scaleLast = -10000;
    public float trueScaleValue = -1;

    protected float xPercent = 0.5f;
    protected float yPercent = 0.5f;

    protected BSRPathBase attachPathBase;
    protected float attachDx = 0;
    protected float attachDy = 0;

    protected PointF lastPoint;

    protected float delayTime = 0;

    protected boolean pointPercentTrigger = false;

    protected boolean isRotationFollow;

    public boolean isRotationFollow() {
        return isRotationFollow;
    }

    public void autoRotation() {
        isRotationFollow = true;
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

    public void setDuring(int during) {
        this.during = during;
    }

    public BSRPathBase() {
        positionControlPoint = new ArrayList<>();
        scaleControl = new ArrayList<>();
        rotationControl = new ArrayList<>();
        positionPointFirst = new PointF(0, 0);
        positionPointLast = new PointF(0, 0);
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

    public static float getRotationPoint2Point(float x1, float y1, float x2, float y2) {
        double rotation = 0;

        double k1 = (double) (y1 - y1) / (x1 * 2 - x1);
        double k2 = (double) (y2 - y1) / (x2 - x1);
        double tmpDegree = Math.atan((Math.abs(k1 - k2)) / (1 + k1 * k2)) / Math.PI * 180;

        if (x2 > x1 && y2 < y1) {  //第一象限
            rotation = 90 - tmpDegree;
        } else if (x2 > x1 && y2 > y1) {//第二象限
            rotation = 90 + tmpDegree;
        } else if (x2 < x1 && y2 > y1) { //第三象限
            rotation = 270 - tmpDegree;
        } else if (x2 < x1 && y2 < y1) { //第四象限
            rotation = 270 + tmpDegree;
        } else if (x2 == x1 && y2 < y1) {
            rotation = 0;
        } else if (x2 == x1 && y2 > y1) {
            rotation = 180;
        }

        return (float) rotation;
    }
}
