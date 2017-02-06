package com.yan.bsrgift;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by yan on 2016/12/9.
 */

public class BSRGiftLayout extends FrameLayout {
    private float alphaTrigger = 0.8f;
    private boolean controlBySelf = false;

    public void setGetControl(boolean controlBySelf) {
        this.controlBySelf = controlBySelf;
    }

    private void init() {
    }

    public void addChild(BSRPathView bsrPathView) {
        addView(bsrPathView.getChild());
        bsrPathView.attachParent(this);
        startDrawAnimation(bsrPathView);
    }

    public void setAlphaTrigger(float alphaTrigger) {
        this.alphaTrigger = alphaTrigger;
    }

    public void addChildren(List<BSRPathView> bsrPathViews) {
        startDrawAnimation(bsrPathViews);
    }

    private void startAnimation(final List<BSRPathView> bsrPathViews) {
        for (BSRPathView bsrPathPoint : bsrPathViews) {
            startBsrPathPointAnimation(bsrPathPoint);
        }
    }

    private void startBsrPathPointAnimation(BSRPathView bsrPathView) {
        bsrPathView.startBsrAnimation(new OnAnmEndListener() {
            @Override
            public void onAnimationEnd(final BSRPathBase bsrPathPoint) {
                if (controlBySelf) return;
                removeView(((BSRPathView) bsrPathPoint).getChild());
            }

        }, alphaTrigger);

    }

    private void startDrawAnimation(Object obj) {
        if (obj instanceof List) {
            startAnimation((List<BSRPathView>) obj);
        } else
            startBsrPathPointAnimation((BSRPathView) obj);

    }

    public boolean isRunning() {
        return getChildCount() != 0;
    }

    public BSRGiftLayout(Context context) {
        super(context);
        init();
    }

    public BSRGiftLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BSRGiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
