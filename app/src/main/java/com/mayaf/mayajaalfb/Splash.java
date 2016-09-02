package com.mayaf.mayajaalfb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by user on 7/11/2016.
 */
public class Splash extends Activity {
    ImageView iv1, iv2, iv3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        iv1 = (ImageView) findViewById(R.id.splash);
        //iv2 = (ImageView) findViewById(R.id.imageView2);
        // iv3 = (ImageView) findViewById(R.id.imageView3);
        //iv1.setVisibility(View.GONE);


        Handler handler=new Handler();
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                secondimage();
            }
        },2500);

    }
    void secondimage(){

        iv1.setVisibility(View.VISIBLE);
    }*/
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intentnext();

            }
        },2500);
    }
    void intentnext(){
        Intent intent=new Intent(Splash.this,MainActivity.class);
        startActivity(intent);
        finish();

    }


}
