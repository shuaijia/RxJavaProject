package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Observable的创建
 * 一共介绍八种创建方式：
 * create：最基础的创建方式，传入Observable.OnSubscribe对象，其定义发送规则
 * just：传入不定长参数，其类型随意，可以不同，按顺序发送各个对象
 * from：传入list，遍历并逐个发送
 * interval：按固定间隔循环发送
 * range：传入起始值，传入变化量，逐个发送
 * repeat：重复发送某个特定值 特定次数，一般与just搭配使用
 * timer：固定时间后发送消息，类似于handler.postDelay方法
 * defer：有观察者时才创建Observable，并且为每个观察者创建一个新的Observable
 */
public class CreateObservableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_observable);

        create1();

        create2();

        create3();

        create4();

        create5();

        create6();

        create7();

        create8();
    }

    /**
     * 使用create,最基本的创建方式
     */
    private void create1() {

        Observable observer = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("create1"); //发射一个"create1"的String
                subscriber.onNext("create2"); //发射一个"create2"的String
                subscriber.onCompleted();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        });

    }

    /**
     * 使用just( )，将为你创建一个Observable并自动为你调用onNext( )发射数据
     */
    private void create2() {

        Observable observable = Observable.just(1, 2.3, 4, "js");

        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("just", "onNext: " + o.toString());
            }
        });
    }

    /**
     * 使用from( )，遍历集合，发送每个item
     * 注意：just()方法也可以传list，但是发送的是整个list对象，而from（）发送的是list的一个item
     */
    private void create3() {
        List<String> list = new ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");
        Observable observable = Observable.from(list);

        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("from", "onNext: " + o.toString());
            }
        });
    }

    /**
     * 使用interval( ),创建一个按固定时间间隔发射整数序列的Observable，可用作定时器
     * 第一个参数为  时间间隔大小
     * 第二个参数为  时间间隔单位
     */
    private void create4() {
        Observable observable = Observable.interval(1, TimeUnit.SECONDS);
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                Log.e("interval", "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("interval", "onError: " + e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e("interval", "onNext: " + o.toString());
                if (o.toString().equals("10"))
                    unsubscribe();
            }
        });
    }

    /**
     * 使用defer( )，有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
     */
    private void create5() {
        //注意此处的call方法没有Subscriber参数
        Observable deferObservable = Observable.defer(() -> Observable.just("deferObservable"));
    }

    /**
     * 使用range( ),创建一个发射特定整数序列的Observable，第一个参数为起始值，第二个为发送的个数，
     * 如果为0则不发送，负数则抛异常
     */
    private void create6() {
        Observable rangeObservable = Observable.range(1, 5);
        rangeObservable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("range", "onNext: " + o.toString());
            }
        });
    }

    /**
     * 使用timer( ),创建一个Observable，它在一个给定的延迟后发射一个特殊的值，
     * 等同于Android中Handler的postDelay( )方法
     */
    private void create7() {
        Observable timeObservable = Observable.timer(3, TimeUnit.SECONDS);  //3秒后发射一个值
        timeObservable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("timer", "onNext: " + o.toString());
            }
        });
    }

    /**
     * 使用repeat( ),创建一个重复发射特定数据的Observable
     */
    private void create8() {
        Observable.just("repeatObservable").repeat(3)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("repeat", "onNext: " + s);
                    }
                });
    }
}
