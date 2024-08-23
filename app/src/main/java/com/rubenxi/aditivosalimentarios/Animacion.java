package com.rubenxi.aditivosalimentarios;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.Random;

public class Animacion {
    Random random = new Random();
    Animation animationRotation;
    AnimationSet animationSet1;
    int size, speed, speedRota;
    TextView emojianimado;
    Animation animationMove;
    String emoji;

    public Animacion(TextView emojianimado, String emoji){
        this.emojianimado=emojianimado;
        this.emoji=emoji;
    }
    public void animacion(){
         size = 10 + random.nextInt(30);  
         speed = 800 + random.nextInt(1000);  
         speedRota = 100 + random.nextInt(300);  

        emojianimado.setTextSize(size);  


        emojianimado.setText(emoji);
        emojianimado.setVisibility(View.VISIBLE);
        animationMove = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.5f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F);

        animationMove.setDuration(speed);  
        animationMove.setInterpolator(new LinearInterpolator());  
        animationMove.setRepeatCount(0);
         
        animationMove.setFillAfter(true);

        animationRotation = new RotateAnimation(0F,360F,Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF,0.5F);

        animationRotation.setDuration(speedRota);  
        animationRotation.setInterpolator(new LinearInterpolator());  

        animationRotation.setRepeatCount(Animation.INFINITE);
        animationRotation.setRepeatMode(Animation.RESTART);  
        animationRotation.setFillAfter(true);

        animationSet1 = new AnimationSet(false);
        animationSet1.setFillAfter(true);
        animationSet1.addAnimation(animationRotation);
        animationSet1.addAnimation(animationMove);

        emojianimado.startAnimation(animationSet1);
        animationMove.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animacion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
