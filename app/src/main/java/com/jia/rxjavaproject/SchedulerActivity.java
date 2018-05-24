package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 在不指定线程的情况下， RxJava 遵循的是线程不变的原则，即：
 * 在哪个线程调用 subscribe()，就在哪个线程生产事件；
 * 在哪个线程生产事件，就在哪个线程消费事件。
 * 如果需要切换线程，就需要用到 Scheduler （调度器）。
 * <p>
 * 可以使用subscribeOn()指定观察者代码运行的线程，使用observerOn()指定订阅者运行的线程
 * <p>
 * RxJava内置的几种线程调度器
 * 1.Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
 * 2.Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
 * 3.Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
 * 4.Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
 * 5.另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
 */

/**
 * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
 * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
 * <p>
 * 注意：observeOn() 指定的是 Subscriber 的线程，而这个 Subscriber 并不一定是 subscribe() 参数中的 Subscriber（这块参考RxJava变换部分），而是 observeOn() 执行时的当前 Observable 所对应的 Subscriber ，即它的直接下级 Subscriber 。
 * 换句话说，observeOn() 指定的是它之后的操作所在的线程。因此如果有多次切换线程的需求，只要在每个想要切换线程的位置调用一次 observeOn() 即可。
 */
public class SchedulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        normalMethod();
    }

    /**
     * 最普通用法，长出现在Retrofit+RxJava进行网络请求，在io线程中请求，在主线程中接收处理
     */
    private void normalMethod() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.e("normal", "number:" + number);
                    }
                });
    }

    private void method2(){
        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, Object>() {
                    @Override
                    public Object call(Integer integer) {
                        return null;
                    }
                }) // 新线程，由 observeOn() 指定
                .observeOn(Schedulers.io())
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        return null;
                    }
                }) // IO 线程，由 observeOn() 指定
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {

                    }
                });  // Android 主线程，由 observeOn() 指定

    }

    /**
     * 值得注意：subscribeOn () 与 observeOn()都会返回了一个新的Observable，
     * 因此若不是采用上面这种直接流方式，而是分步调用方式，需要将新返回的Observable赋给原来的Observable，
     * 否则线程调度将不会起作用。
     *
     * 不同于 observeOn() ， subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的。
     */
}
