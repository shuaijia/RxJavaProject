package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 对于do系列操作符理解比较容易，他相当于给Observable执行周期的关键节点添加回调。
 * 当Observable执行到这个阶段的时候，这些回调就会被触发。
 * 在Rxjava do系列操作符有多个，如doOnNext，doOnSubscribe，doOnUnsubscribe，doOnCompleted，doOnError，doOnTerminate和doOnEach。
 * 当Observable每发送一个数据时，doOnNext会被首先调用，然后再onNext。
 * 若发射中途出现异常doOnError会被调用，然后onError。
 * 若数据正常发送完毕doOnCompleted会被触发，然后执行onCompleted。
 * 当订阅或者解除订阅doOnSubscribe，doOnUnsubscribe会被执行。
 */
public class DoActivity extends AppCompatActivity {

    public static final String TAG="do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do);

        method();

        method2();
    }

    private void method() {

        Observable.just(1, 2, 3)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "doOnNext: " );
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "doOnError: " );
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnCompleted: " );
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnSubscribe: " );
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnUnsubscribe: " );
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnTerminate: " );
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doAfterTerminate: " );
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted1: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError1: ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext1: " + integer);
                    }
                });
    }

    private void method2(){
        Observable.just(1,2,3)
                .doOnEach(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " );
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: "+integer);
                    }
                }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted1: " );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError1: " );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext1: "+integer);
            }
        });
    }
}
