package com.example.username.slideshow;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;

    int[] mImageResources = {
            R.drawable.slide00,
            R.drawable.slide01,
            R.drawable.slide02,
            R.drawable.slide03,
            R.drawable.slide04,
            R.drawable.slide05,
            R.drawable.slide06,
            R.drawable.slide07,
            R.drawable.slide08,
            R.drawable.slide09,
    };

    int mPosition = 0;

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

        mImageSwitcher.setInAnimation(MainActivity.this,android.R.anim.slide_in_left);
        mImageSwitcher.setOutAnimation(MainActivity.this,android.R.anim.slide_out_right);

        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                // イメージビュー生成工場
                ImageView imageView = new ImageView(MainActivity.this);
                return imageView;
            }
        });

        mImageSwitcher.setImageResource(R.drawable.slide00);

        Button prevButton = (Button) findViewById(R.id.prevButton);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePosition(-1);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePosition(1);
            }
        });

        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.getdown);
        mMediaPlayer.setLooping(true);

    }

    // 画面が表示されたときに呼ばれるメソッド
    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer.start();
    }


    // 画面が閉じられるときに呼ばれるメソッド
    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
    }

    private void movePosition(int move) {
        mPosition = mPosition + move;
        if (mPosition >= mImageResources.length) {
            mPosition = 0;
        } else if(mPosition < 0) {
            mPosition = mImageResources.length - 1;
        }
        mImageSwitcher.setImageResource(mImageResources[mPosition]);
    }
}
