package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * onCompleted()执行后，之后的任务都不会再交给观察者
 * onError()执行后，之后的任务都不会再交给观察者
 *
 * 也就是说Observer的 onCompleted()和onError()最多只会执行一次，而onNext可能会执行多次
 */
public class NormalUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        rxDo();
    }

    private void rxDo() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hi，Weavey！");
                subscriber.onError(new NullPointerException());
                subscriber.onNext("Hi，！");
                subscriber.onCompleted();
                subscriber.onNext("Hi，js！");
            }
        });

        Observer observer = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e("jia", "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("jia", "onError: " + e.toString());
            }

            @Override
            public void onNext(String o) {
                Log.e("jia", "onNext: " + o.toString());
            }
        };

        observable.subscribe(observer);
    }
}
