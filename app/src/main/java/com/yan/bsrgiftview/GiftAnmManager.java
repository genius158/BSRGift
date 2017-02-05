package com.yan.bsrgiftview;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.yan.bsrgiftview.bsr.BSRGiftView;
import com.yan.bsrgiftview.bsr.BSRGiftLayout;
import com.yan.bsrgiftview.bsr.BSRPathBase;
import com.yan.bsrgiftview.bsr.BSRPathPoint;
import com.yan.bsrgiftview.bsr.BSRPathView;
import com.yan.bsrgiftview.bsr.OnAnmEndListener;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yan on 2016/12/14.
 */

public class GiftAnmManager {

    private BSRGiftLayout giftLayout;
    private Context context;
    int[] car1Ids = new int[]{
            R.drawable.gift_caro1,
            R.drawable.gift_caro2,
            R.drawable.gift_caro3,
            R.drawable.gift_caro4,
            R.drawable.gift_caro5,
            R.drawable.gift_caro6,
            R.drawable.gift_caro7,
            R.drawable.gift_caro8,
    };
    int[] car2Ids = new int[]{
            R.drawable.gift_car_t1,
            R.drawable.gift_car_t2,
    };
    int[] dragonIds = new int[]{
            R.drawable.dragon1,
            R.drawable.dragon2,
            R.drawable.dragon3,
            R.drawable.dragon4,
            R.drawable.dragon5,
            R.drawable.dragon6,
            R.drawable.dragon7,
            R.drawable.dragon8,
            R.drawable.dragon9,
            R.drawable.dragon10,
            R.drawable.dragon11,
            R.drawable.dragon12,
            R.drawable.dragon13,
            R.drawable.dragon14,
            R.drawable.dragon15,
            R.drawable.dragon16,
            R.drawable.dragon17,
            R.drawable.dragon18,
            R.drawable.dragon19,
            R.drawable.dragon20,
            R.drawable.dragon21,
            R.drawable.dragon22,
            R.drawable.dragon23,
            R.drawable.dragon24,
            R.drawable.dragon25,
            R.drawable.dragon26,
            R.drawable.dragon27,
            R.drawable.dragon28,
            R.drawable.dragon29,
            R.drawable.dragon30,
            R.drawable.dragon31,
            R.drawable.dragon32,
            R.drawable.dragon33,
            R.drawable.dragon34
    };

    public GiftAnmManager(BSRGiftLayout giftLayout, Context context) {
        this.context = context;
        this.giftLayout = giftLayout;
    }

    public void showCarOne() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        bsrGiftView.setAlphaTrigger(-1);

        final int during = 150;
        final Subscription[] subscription = new Subscription[1];

        Flowable.interval(during, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Subscriber<Long>() {
                    int index = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription[0] = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        BSRPathPoint carOne = new BSRPathPoint();
                        carOne.setDuring(during);
                        carOne.setInterpolator(new LinearInterpolator());
                        carOne.setRes(context, car1Ids[index++ % 7]);
                        carOne.adjustScaleInScreen(0.8f);
                        carOne.setAntiAlias(true);
                        bsrGiftView.addBSRPathPointAndDraw(carOne);
                    }

                    public void onError(Throwable t) {
                    }

                    public void onComplete() {
                    }
                });

        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.positionInScreen();
        bsrPathView.addPositionControlPoint(1, 0.1f);
        bsrPathView.addPositionControlPoint(0f, 0.3f);
        bsrPathView.addPositionControlPoint(0f, 0.3f);
        bsrPathView.addPositionControlPoint(0f, 0.3f);
        bsrPathView.addPositionControlPoint(0f, 0.3f);
        bsrPathView.addPositionControlPoint(0f, 0.3f);
        bsrPathView.addPositionControlPoint(0f, 0.3f);
        bsrPathView.addPositionControlPoint(-1f, 0.5f);

        bsrPathView.setDuring(3000);
        bsrPathView.addEndListeners(new OnAnmEndListener() {
            @Override
            public void onAnimationEnd(BSRPathBase bsrPathPoint) {
                subscription[0].cancel();
            }
        });

        giftLayout.addChild(bsrPathView);
    }

    public void showCarOnePath() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        bsrGiftView.setAlphaTrigger(-1);
        final int during = 150;
        final Subscription[] subscription = new Subscription[1];

        Flowable.interval(during, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Subscriber<Long>() {
                    int index = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription[0] = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        BSRPathPoint carOne = new BSRPathPoint();
                        carOne.setDuring(during);
                        carOne.setInterpolator(new LinearInterpolator());
                        carOne.setRes(context, car1Ids[index++ % 7]);
                        carOne.centerInside();
                        carOne.setAntiAlias(true);
                        bsrGiftView.addBSRPathPointAndDraw(carOne);
                    }

                    public void onError(Throwable t) {
                    }

                    public void onComplete() {
                    }
                });

        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.positionInScreen();
        bsrPathView.setPositionXPercent(0.5f);
        bsrPathView.setPositionYPercent(0.5f);
        bsrPathView.addPositionControlPoint(0,0);
        bsrPathView.addPositionControlPoint(0,1);
        bsrPathView.addPositionControlPoint(1,1);
        bsrPathView.addPositionControlPoint(1,0);
        bsrPathView.setScale(0.3f);
        bsrPathView.setFirstRotation(90);
        bsrPathView.setXPercent(0.5f);
        bsrPathView.setYPercent(0.5f);
        bsrPathView.autoRotation();
        bsrPathView.setDuring(3000);
        bsrPathView.addEndListeners(new OnAnmEndListener() {
            @Override
            public void onAnimationEnd(BSRPathBase bsrPathPoint) {
                subscription[0].cancel();
            }
        });

        giftLayout.addChild(bsrPathView);
    }

    public void showCarTwo() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        bsrGiftView.setAlphaTrigger(-1);
        final int during = 300;
        final Subscription[] subscription = new Subscription[1];
        Flowable.interval(during, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Subscriber<Long>() {
                    int index = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription[0] = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        BSRPathPoint carTwo = new BSRPathPoint();
                        carTwo.setDuring(during);
                        carTwo.setInterpolator(new LinearInterpolator());
                        carTwo.setRes(context, car2Ids[index++ % 2]);
                        carTwo.adjustScaleInScreen(1f);
                        carTwo.setAntiAlias(true);
                        bsrGiftView.addBSRPathPointAndDraw(carTwo);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.positionInScreen();
        bsrPathView.addPositionControlPoint(0f, 0.3f);
        bsrPathView.addPositionControlPoint(0.06f, 0.3f);
        bsrPathView.addPositionControlPoint(0.06f, 0.3f);
        bsrPathView.addPositionControlPoint(0.06f, 0.3f);
        bsrPathView.addPositionControlPoint(0.06f, 0.3f);
        bsrPathView.addPositionControlPoint(0.06f, 0.3f);
        bsrPathView.addPositionControlPoint(0.06f, 0.3f);
        bsrPathView.addPositionControlPoint(0.06f, 0.3f);
        bsrPathView.addScaleControl(0.2f);
        bsrPathView.addScaleControl(0.8f);
        bsrPathView.addScaleControl(0.8f);
        bsrPathView.addScaleControl(0.8f);
        bsrPathView.addScaleControl(0.8f);
        bsrPathView.addScaleControl(0.8f);
        bsrPathView.addScaleControl(0.8f);
        bsrPathView.addScaleControl(10f);
        bsrPathView.setXPercent(0f);
        bsrPathView.setYPercent(0f);
        bsrPathView.setDuring(2000);
        bsrPathView.setInterpolator(new AccelerateInterpolator());
        bsrPathView.addEndListeners(new OnAnmEndListener() {
            @Override
            public void onAnimationEnd(BSRPathBase bsrPathPoint) {
                subscription[0].cancel();
            }
        });
        giftLayout.addChild(bsrPathView);
    }

    public void showKQ() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        int during = 1200;
        bsrGiftView.setAlphaTrigger(1);
        final List<BSRPathPoint> bsrPathPoints = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            BSRPathPoint bsrPointT = new BSRPathPoint();

            if (i == 0)
                bsrPointT.setLastRotation(0);
            else if (i > 0 && i < 7) {
                bsrPointT.setLastRotation(i * 20);
            } else if (i >= 7 && i < 13) {
                bsrPointT.setLastRotation(-(i - 6) * 20);
            }
            bsrPointT.positionInScreen();
            bsrPointT.setPositionPoint(0.5f, 0.2f);
            bsrPointT.setPositionXPercent(0.5f);
            bsrPointT.setRes(context, R.drawable.kongque_cibang1);
            bsrPointT.setDuring(during);
            bsrPointT.setXPercent(0.5f);
            bsrPointT.setYPercent(1.0f);
            bsrPointT.adjustScaleInScreen(1f);
            bsrPointT.setScale(0.2f);
            bsrPointT.setAntiAlias(true);
            bsrPointT.setInterpolator(new DecelerateInterpolator());
            bsrPathPoints.add(bsrPointT);
        }

        BSRPathPoint bsrPoint = new BSRPathPoint();
        bsrPoint.attachPoint(bsrPathPoints.get(0), 0, 250);
        bsrPoint.setRes(context, R.drawable.kongque_main);
        bsrPoint.setDuring(during);
        bsrPoint.adjustScaleInScreen(1);
        bsrPoint.setScale(0.4f);
        bsrPoint.setInterpolator(new DecelerateInterpolator());
        bsrPoint.setAntiAlias(true);
        bsrPathPoints.add(bsrPoint);

        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.setDuring(during * 2);

        giftLayout.addChild(bsrPathView);
        bsrGiftView.addBSRPathPoints(bsrPathPoints);
    }

    public void showPositionInScreen() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        int during = 2000;
        bsrGiftView.setAlphaTrigger(0.99f);
        final List<BSRPathPoint> bsrPathPoints = new ArrayList<>();

        BSRPathPoint bsrPoint = new BSRPathPoint();
        bsrPoint.setRes(context, R.drawable.gift_car_t2);
        bsrPoint.setDuring(during);
        bsrPoint.positionInScreen();
        bsrPoint.setFirstRotation(-90);
        bsrPoint.autoRotation();
        bsrPoint.adjustScaleInScreen(1f);
        bsrPoint.addPositionControlPoint(0f, 0f);
        bsrPoint.addPositionControlPoint(1f, 0f);
        bsrPoint.addPositionControlPoint(1f, 1f);
        bsrPoint.addPositionControlPoint(0f, 1f);
        bsrPoint.setScale(0.4f);
        bsrPoint.setInterpolator(new DecelerateInterpolator());
        bsrPoint.setAntiAlias(true);
        bsrPoint.setPositionXPercent(0.5f);
        bsrPoint.setPositionYPercent(0.5f);
        bsrPathPoints.add(bsrPoint);

        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.setDuring(during * 2);

        giftLayout.addChild(bsrPathView);
        bsrGiftView.addBSRPathPoints(bsrPathPoints);
    }

    public void showDragon() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        final int during = 60;
        Flowable.interval(during, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Subscriber<Long>() {
                    Subscription subscription;
                    int index = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (index++ < 33) {
                            BSRPathPoint dragon = new BSRPathPoint();
                            dragon.setDuring(during + 2);
                            dragon.setInterpolator(new LinearInterpolator());
                            dragon.setRes(context, dragonIds[index]);
                            bsrGiftView.addBSRPathPointAndDraw(dragon);
                        } else {
                            bsrGiftView.addBSRPathPointAndDraw(null);
                            subscription.cancel();
                        }
                    }

                    public void onError(Throwable t) {
                    }

                    public void onComplete() {
                    }
                });
        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.setDuring(2000 * 2);
        giftLayout.setAlphaTrigger(0.99f);
        giftLayout.addChild(bsrPathView);
    }

    public void showKiss() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        int during = 2000;
        bsrGiftView.setAlphaTrigger(0.99f);
        List<BSRPathPoint> bsrPathPoints = new ArrayList<>();

        BSRPathPoint pathPoint = new BSRPathPoint();
        pathPoint.setRes(context, R.drawable.kiss_chun);
        pathPoint.setDuring(during);
        pathPoint.setPositionPoint(400, 500);
        pathPoint.addScaleControl(0.2f);
        pathPoint.addScaleControl(0.8f);
        pathPoint.addScaleControl(0.8f);
        pathPoint.addScaleControl(0.8f);
        pathPoint.addScaleControl(0.8f);
        pathPoint.addScaleControl(0.8f);
        pathPoint.addScaleControl(0.8f);
        pathPoint.setXPercent(0.5f);
        pathPoint.setYPercent(0.5f);
        pathPoint.adjustScaleInScreen(0.5f);
        pathPoint.setAntiAlias(true);
        bsrPathPoints.add(pathPoint);

        BSRPathPoint pathPointHeart1 = new BSRPathPoint();
        pathPointHeart1.setRes(context, R.drawable.kiss_xin);
        pathPointHeart1.setDuring(during);
        pathPointHeart1.setPositionPoint(200, 600);
        pathPointHeart1.addScaleControl(0.0f);
        pathPointHeart1.addScaleControl(0.0f);
        pathPointHeart1.addScaleControl(0.0f);
        pathPointHeart1.addScaleControl(0.0f);
        pathPointHeart1.addScaleControl(0.8f);
        pathPointHeart1.addScaleControl(0.8f);
        pathPointHeart1.addScaleControl(0.8f);
        pathPointHeart1.addScaleControl(0.8f);
        pathPointHeart1.setXPercent(0.5f);
        pathPointHeart1.setYPercent(0.5f);
        pathPointHeart1.adjustScaleInScreen(0.21f);
        pathPointHeart1.setAntiAlias(true);
        bsrPathPoints.add(pathPointHeart1);

        BSRPathPoint pathPointHeart2 = new BSRPathPoint();
        pathPointHeart2.setRes(context, R.drawable.kiss_xin);
        pathPointHeart2.setDuring(during);
        pathPointHeart2.setPositionPoint(110, 620);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.0f);
        pathPointHeart2.addScaleControl(0.5f);
        pathPointHeart2.addScaleControl(0.5f);
        pathPointHeart2.setXPercent(0.5f);
        pathPointHeart2.setYPercent(0.5f);
        pathPointHeart2.adjustScaleInScreen(0.17f);
        pathPointHeart2.setAntiAlias(true);
        bsrPathPoints.add(pathPointHeart2);

        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.setDuring(during * 2);

        giftLayout.addChild(bsrPathView);
        bsrGiftView.addBSRPathPoints(bsrPathPoints);
    }

    public void showShip() {
        final BSRGiftView bsrGiftView = new BSRGiftView(context);
        int during = 2000;
        bsrGiftView.setAlphaTrigger(0.99f);
        List<BSRPathPoint> bsrPathPoints = new ArrayList<>();

        BSRPathPoint pathPoint = new BSRPathPoint();
        pathPoint.setRes(context, R.drawable.ship02);
        pathPoint.setDuring(during);

        pathPoint.positionInScreen();
        pathPoint.addPositionControlPoint(0f, 0.6f);
        pathPoint.addPositionControlPoint(0.5f, 0.5f);
        pathPoint.addPositionControlPoint(0.5f, 0.5f);
        pathPoint.addPositionControlPoint(0.5f, 0.5f);
        pathPoint.addPositionControlPoint(0.5f, 0.5f);
        pathPoint.setPositionXPercent(0.5f);
        pathPoint.setPositionYPercent(0.5f);
        pathPoint.adjustScaleInScreen(1);
        pathPoint.addScaleControl(0.3f);
        pathPoint.addScaleControl(0.7f);
        pathPoint.addScaleControl(0.7f);
        pathPoint.addScaleControl(0.7f);
        pathPoint.addScaleControl(0.7f);
        pathPoint.setXPercent(0.5f);
        pathPoint.setYPercent(0.5f);
        pathPoint.setAntiAlias(true);
        bsrPathPoints.add(pathPoint);

        BSRPathPoint pathPoint2 = new BSRPathPoint();
        pathPoint2.setRes(context, R.drawable.ship01);
        pathPoint2.setDuring(during);
        pathPoint2.attachPoint(bsrPathPoints.get(0), 0, 430);
        pathPoint2.setXPercent(0.5f);
        pathPoint2.setYPercent(0.5f);
        pathPoint2.adjustScaleInScreen(1);
        pathPoint2.addRotationControl(-2f);
        pathPoint2.addRotationControl(-2f);
        pathPoint2.addRotationControl(-2f);
        pathPoint2.addRotationControl(-2f);
        pathPoint2.addRotationControl(5f);
        pathPoint2.addRotationControl(5f);
        pathPoint2.addRotationControl(0f);
        pathPoint2.setAntiAlias(true);
        bsrPathPoints.add(pathPoint2);

        BSRPathView bsrPathView = new BSRPathView();
        bsrPathView.setChild(bsrGiftView);
        bsrPathView.setDuring(during * 2);

        giftLayout.addChild(bsrPathView);
        bsrGiftView.addBSRPathPoints(bsrPathPoints);

    }

    public void onDestroy() {

    }

    public static class Action implements Serializable {
        public AnimationUserDataBean userBean;
        public AnimationInfoBean.DataBean dataBean;
        public int times = 1;

        public Action(AnimationUserDataBean userBean, AnimationInfoBean.DataBean dataBean) {
            this.userBean = userBean;
            this.dataBean = dataBean;
        }
    }

}
