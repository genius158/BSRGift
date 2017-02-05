# BSRGift
#
![GIF](demo_gif.gif)

## 1.概述
直播项目的礼物模块，实现一些基本的礼物动画，随着项目的深入，BSR也将会继续完善

## 2.用法 详见[GiftAnmManager.java](https://github.com/genius158/BSRGift/blob/master/app/src/main/java/com/yan/bsrgiftview/GiftAnmManager.java)
**BSR提供BSRGiftView、和BSRGiftLayout两个控件**
<br>
<br>
1.BSRGiftView 可以实现对图片资源的所有基本的礼物的动画，可以是简单的线性动画，当然这里最主要的还是贝塞尔曲线的动画效果。
<br>
2.BSRGiftLayout 则是针对View的动画实现，用法与BSRGiftView一致。
<br>

```
//-设置动画源-
giftView.setRes(context, R.drawable.gift_car_t2); // 设置view的图片资源
giftLayout.addChild(view); // 设置giftLayout的view控件

//-用法-
bsr.setDuring(during); // 设置动画执行时间
bsr.positionInScreen(); // 设置位置为相对控件的位置（比如0.5是控件的中心点）
bsr.setFirstRotation(-90); // 设置动画初始旋转角度
bsr.autoRotation(); // 设置动画旋转跟随运动轨迹
bsr.adjustScaleInScreen(1f);// 设置资源相对容器的大小
```
