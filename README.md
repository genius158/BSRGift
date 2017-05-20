# BSRGift
#
![演示gif](demo_gif.gif)

## 1.概述
直播项目的礼物模块，实现一些基本的礼物动画，随着项目的深入，BSR也将会继续完善(2017-5-20,很不幸项目搁浅了。。。关于优化，有兴趣童鞋的可以试试重写textureview、surfaceview，效果应该要好很多)

## 2.用法 详见[GiftAnmManager.java](https://github.com/genius158/BSRGift/blob/master/app/src/main/java/com/yan/bsrgiftview/GiftAnmManager.java)(资源文件切勿用于商业用途)
**BSR提供BSRGiftView、和BSRGiftLayout两个控件**
<br>
<br>
1.BSRGiftView 可以实现对图片资源的所有基本的礼物的动画，可以是简单的线性动画，当然这里最主要的还是贝塞尔曲线的动画效果。
<br>
2.BSRGiftLayout 则是针对View的动画实现，用法与BSRGiftView一致。
<br>
<br>
BSRPath* 为动画数据类
<br>
```
//-控件设置-
bsrGiftView.setRes(context, R.drawable.gift_car_t2); // 设置view的图片资源
bsrGiftLayout.addChild(bsrPathView); // 设置giftLayout的bsrPathView动画资源，并播放动画
bsrGiftView.addBSRPathPoints(bsrPathPoints); // 加入一组bsr并播放动画，不可保持之前执行的动画
bsrGiftView.addBSRPathPointAndDraw(bsrPathPoint); // 添加和播放一帧动画，用于帧动画
bsrGiftView.addBSRPathPoint(bsrPathPoint); // 加入一个动画数据，并播放，可保持之前执行的动画

//-动画数据设置-
bsrPath.setDuring(during); // 设置动画执行时间
bsrPath.setPositionInScreen(true); // 设置位置为相对控件的位置（比如0.5是控件的中心点）
bsrPath.setFirstRotation(-90); // 设置动画初始旋转角度
bsrPath.setAutoRotation(true); // 设置动画旋转跟随运动轨迹
bsrPath.setAdjustScaleInScreen(1f);// 设置资源相对容器的大小
bsrPath.attachPoint(bsr2);// 设置bsr的位移跟随bsr2
bsrPath.setPositionXPercent(0.5f);// 设置bsrX轴上位移的基准点
bsrPath.setAlphaTrigger(0.9f);// 设置动画的淡出在动画执行的到0.9的时候

bsrPath.setScale(0.5f);// 恒定bsr的缩放
bsrPath.setPositionPoint(0.5f,0.5f);// 恒定bsr的位置
bsrPath.setRotation(100);// 恒定bsr的恒定旋转角度

bsrPath.addScaleControl(0.5f);// 添加缩放的控制点用于贝塞尔效果
bsrPath.addRotationControl(30);// 添加旋转的控制点用于贝塞尔效果
bsrPath.addPositionControlPoint(200);// 添加位移的控制点用于贝塞尔效果，如果调用setPositionInScreen(true)，填入的参数为相对父View界面的比例值
```
