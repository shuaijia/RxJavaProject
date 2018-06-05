package com.jia.rxjavaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Observable：发射源，英文释义“可观察的”，在观察者模式中称为“被观察者”或“可观察对象”；
 * <p>
 * Observer：接收源，英文释义“观察者”，没错！就是观察者模式中的“观察者”，可接收Observable、Subject发射的数据；
 * <p>
 * Subject：Subject是一个比较特殊的对象，既可充当发射源，也可充当接收源，为避免初学者被混淆，本章将不对Subject做过多的解释和使用，重点放在Observable和Observer上，先把最基本方法的使用学会，后面再学其他的都不是什么问题；
 * <p>
 * Subscriber：“订阅者”，也是接收源，那它跟Observer有什么区别呢？Subscriber实现了Observer接口，比Observer多了一个最重要的方法unsubscribe( )，用来取消订阅，当你不再想接收数据了，可以调用unsubscribe( )方法停止接收，Observer 在 subscribe() 过程中,最终也会被转换成 Subscriber 对象，一般情况下，建议使用Subscriber作为接收源；
 * <p>
 * Subscription：Observable调用subscribe( )方法返回的对象，同样有unsubscribe( )方法，可以用来取消订阅事件；
 * <p>
 * Action0：RxJava中的一个接口，它只有一个无参call（）方法，且无返回值，同样还有Action1，Action2...Action9等，Action1封装了含有* 1 个参的call（）方法，即call（T t），Action2封装了含有 2 *个参数的call方法，即call（T1 t1，T2 t2），以此类推；
 * <p>
 * Func0：与Action0非常相似，也有call（）方法，但是它是有返回值的，同样也有Func0、Func1...Func9;
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_base;
    private TextView tv_create_observable;
    private TextView tv_create_observer;
    private TextView tv_subscribe;
    private TextView tv_action_to_subscriber;
    private TextView tv_map;
    private TextView tv_flat_map;
    private TextView tv_filter;
    private TextView tv_do;
    private TextView tv_group_by;
    private TextView tv_scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // rxjava的示例
        Observable.just("Hello World!")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "I am kyrie!";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("jia", "call: " + s);
                    }
                });

        // lambda形式
        Observable.just("Hello World!")
                .map(s -> s + "I am kyrie!")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {

                });
    }

    private void initView() {
        tv_base = findViewById(R.id.tv_base);
        tv_create_observable = findViewById(R.id.tv_create_observable);
        tv_create_observer = findViewById(R.id.tv_create_observer);
        tv_subscribe = findViewById(R.id.tv_subscribe);
        tv_action_to_subscriber = findViewById(R.id.tv_action_to_subscriber);
        tv_map = findViewById(R.id.tv_map);
        tv_flat_map = findViewById(R.id.tv_flat_map);
        tv_filter = findViewById(R.id.tv_filter);
        tv_do = findViewById(R.id.tv_do);
        tv_group_by = findViewById(R.id.tv_group_by);
        tv_scheduler = findViewById(R.id.tv_scheduler);

        tv_base.setOnClickListener(this);
        tv_create_observable.setOnClickListener(this);
        tv_create_observer.setOnClickListener(this);
        tv_subscribe.setOnClickListener(this);
        tv_action_to_subscriber.setOnClickListener(this);
        tv_map.setOnClickListener(this);
        tv_flat_map.setOnClickListener(this);
        tv_filter.setOnClickListener(this);
        tv_do.setOnClickListener(this);
        tv_group_by.setOnClickListener(this);
        tv_scheduler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_base:

                startActivity(new Intent(this, NormalUseActivity.class));

                break;
            case R.id.tv_create_observable:

                startActivity(new Intent(this, CreateObservableActivity.class));

                break;
            case R.id.tv_create_observer:

                startActivity(new Intent(this, CreateObserverActivity.class));

                break;
            case R.id.tv_subscribe:

                startActivity(new Intent(this, SubscribeActivity.class));

                break;
            case R.id.tv_action_to_subscriber:

                startActivity(new Intent(this, ActionToSubscriberActivity.class));

                break;
            case R.id.tv_map:

                startActivity(new Intent(this, MapActivity.class));

                break;
            case R.id.tv_flat_map:

                startActivity(new Intent(this, FlatMapActivity.class));

                break;
            case R.id.tv_filter:

                startActivity(new Intent(this, FilterActivity.class));

                break;
            case R.id.tv_do:

                startActivity(new Intent(this, DoActivity.class));

                break;
             case R.id.tv_group_by:

                startActivity(new Intent(this, GroupByActivity.class));

                break;
            case R.id.tv_scheduler:

                startActivity(new Intent(this, SchedulerActivity.class));

                break;
        }
    }
}
