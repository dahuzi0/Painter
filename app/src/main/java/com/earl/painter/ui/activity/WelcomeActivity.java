package com.earl.painter.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.earl.painter.R;
import com.earl.painter.bean.BaseActivity;
import com.earl.painter.utils.SharedPreferencesUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 启动页
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.iv_entry)
    ImageView mIVEntry;

    private static final int ANIM_TIME = 2000;

    private static final float SCALE_END = 1.15F;

    private static final int[] Imgs = {
            R.mipmap.welcomimg1, R.mipmap.welcomimg2,
            R.mipmap.welcomimg3, R.mipmap.welcomimg4,
            R.mipmap.welcomimg5, R.mipmap.welcomimg6,
            R.mipmap.welcomimg7, R.mipmap.welcomimg8,
            R.mipmap.welcomimg9, R.mipmap.welcomimg10,
            R.mipmap.welcomimg11, R.mipmap.welcomimg12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否是第一次开启应用
        Object isFirstOpen = SharedPreferencesUtil.getData(this, "frist", "");
        // 如果是第一次启动，则先进入功能引导页
        if ("".equals(isFirstOpen)) {
            //存储数据
            SharedPreferencesUtil.saveData(this, "frist", "frist");
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_welcome);
        startMainActivity();
    }

    private void startMainActivity() {
        Random random = new Random(SystemClock.elapsedRealtime());//SystemClock.elapsedRealtime() 从开机到现在的毫秒数（手机睡眠(sleep)的时间也包括在内）
        mIVEntry.setImageResource(Imgs[random.nextInt(Imgs.length)]);

        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {

                    @Override
                    public void call(Long aLong) {
                        startAnim();
                    }
                });
    }

    private void startAnim() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIVEntry, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIVEntry, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();
            }
        });
    }

    /**
     * 屏蔽物理返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
