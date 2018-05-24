package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * 我们使用subscribe完成事件的订阅，即Observable和Observer的关联
 * 订阅之后会返回一个Subscription对象
 */
public class SubscribeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        normalMethod();

        subscription();
    }

    /**
     * 这是最常见的订阅方式
     * 这里注意：应该是被观察者（发送方）订阅 观察者（接收方）
     */
    private void normalMethod() {

        Observable observable = Observable.just(1);
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e("subscribe", "onNext: " + integer);
            }
        };


        observable.subscribe(subscriber);
    }


    /**
     * 当调用Observable.subscribe()，会返回一个Subscription对象。
     * 这个对象代表了被观察者和订阅者之间的联系。
     * <p>
     * 可以在后面使用这个Subscription对象来操作被观察者和订阅者之间的联系.
     * 典型的用法就是判断是否订阅和取消订阅（用于防止内存泄漏）
     */
    private void subscription() {
        Subscription subscription = Observable.just("Hello, World!")
                .subscribe(s -> System.out.println(s));

        if (subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
