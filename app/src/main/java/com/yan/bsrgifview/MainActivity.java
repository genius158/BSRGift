package com.yan.bsrgifview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yan.bsrgifview.bsr.BSRGiftLayout;
import com.yan.bsrgifview.bsr.BSRGiftView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    BSRGiftView imageButton;
    BSRGiftLayout giftLayout;
    GiftAnmManager giftAnmManager;
    int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (BSRGiftView) findViewById(R.id.gift_view);
        giftLayout = (BSRGiftLayout) findViewById(R.id.gift_layout);

        giftAnmManager = new GiftAnmManager(giftLayout, this);
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (time++ % 6) {
                    case 0:
//                        giftAnmManager.showPath();
                        giftAnmManager.showShip();
                        break;
                    case 1:
                        giftAnmManager.showCarTwo();
                        break;
                    case 2:
                        giftAnmManager.showDragon();
                        break;
                    case 3:
                        giftAnmManager.showKQ();
                        break;
                    case 4:
                        giftAnmManager.showCarOne();
                        break;
                    case 5:
                        giftAnmManager.showKiss();
                        break;
                }
            }
        });
    }
}
