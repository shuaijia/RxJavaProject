package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jia.rxjavaproject.bean.Person;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * RxJava提供了Action0等一系列的类似的类
 * <p>
 * 当我们不需要（或者不关心）Subscriber的全部方法（如只关心onNext方法），就可以使用Action类来替代Subscriber
 */
public class ActionToSubscriberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_to_subscriber);

        method();

        method2();
    }

    /**
     * 我们其实并不关心OnComplete和OnError，我们只需要在onNext的时候做一些处理，这时候就可以使用Action1类
     */
    private void method() {

        Person person = new Person(21, "js");

        Observable<Person> observable = Observable.just(person);

        observable.subscribe(new Action1<Person>() {
            @Override
            public void call(Person person1) {
                Log.e("action1", "call: " + person1.toString());
            }
        });
    }

    /**
     * subscribe方法还有两个重载方法，分别是：
     * <p>
     * 两个参数： subscribe(onNextAction , onErrorAction)
     * 三个参数： subscribe(onNextAction , onErrorAction ,onCompletedAction)
     */
    private void method2() {

        Observable<Person> observable = Observable.create(new Observable.OnSubscribe<Person>() {
            @Override
            public void call(Subscriber<? super Person> subscriber) {
                subscriber.onNext(new Person(10, "hi"));
                subscriber.onError(new NumberFormatException());
            }
        });

        Action1<Person> onNextAction = new Action1<Person>() {
            @Override
            public void call(Person o) {
                Log.e("method2", "call: " + o.toString());
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable o) {
                Log.e("method2", "call: " + o.toString());
            }
        };

        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Log.e("method2", "call: ");
            }
        };

        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }


    /**
     * 知识点整理：
     *
     * Action0 是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的；
     * 由于 onCompleted() 方法也是无参无返回值的，因此 Action0 可以被当成一个包装对象，
     * 将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调。
     * 这样其实也可以看做将onCompleted() 方法作为参数传进了 subscribe()，相当于其他某些语言中的『闭包』。
     * Action1 也是一个接口，它同样只有一个方法 call(T param)，这个方法也无返回值，
     * 但有一个参数；与 Action0 同理，由于 onNext(T obj) 和 onError(Throwable error)也是单参数无返回值的，
     * 因此 Action1 可以将 onNext(obj) 和 onError(error) 打包起来传入 subscribe() 以实现不完整定义的回调。
     * 事实上，虽然 Action0 和 Action1 在 API 中使用最广泛，
     * 但 RxJava 是提供了多个 ActionX 形式的接口 (例如 Action2, Action3) 的，它们可以被用以包装不同的无返回值的方法。
     */
}
