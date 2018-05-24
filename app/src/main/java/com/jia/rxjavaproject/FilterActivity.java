package com.jia.rxjavaproject;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jia.rxjavaproject.bean.Person;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * RxJava中的过滤操作符
 * <p>
 * 顾名思义，这类操作符主要用于对事件数据的筛选过滤，只返回满足我们条件的数据。
 * 过滤类操作符主要包含：
 * Filter Take TakeLast TakeUntil Skip SkipLast ElementAt Debounce Distinct DistinctUntilChanged First Last等等
 */
public class FilterActivity extends AppCompatActivity {

    private List<Person> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        for (int i = 0; i < 20; i++) {
            list.add(new Person(i, "js" + i));
        }

        method1();

        method2();

        method3();

        method4();

        method5();

        method6();

        method7();

        method8();

        method9();

        method10();
    }

    /**
     * filter(Func1)用来过滤观测序列中我们不想要的值，只返回满足条件的值
     * filter传入Func1对象，第一个泛型是传入的发射类型，第二个参数是boolean类型，表示是否过滤
     */
    private void method1() {

        Observable.from(list)
                .filter(new Func1<Person, Boolean>() {
                    @Override
                    public Boolean call(Person person) {
                        return person.getAge() > 10;
                    }
                })
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("filter", "call: " + person.toString());
                    }
                });

    }

    /**
     * take方法，传入一整数n，表示只取前n个数据
     */
    private void method2() {

        Observable.from(list)
                .take(3)
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("take", "call: " + person);
                    }
                });

    }

    /**
     * takeLast(int)同样用一个整数n作为参数，只不过它发射的是观测序列中后n个元素
     */
    private void method3() {
        Observable.from(list)
                .takeLast(3)
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("take", "call: " + person);
                    }
                });
    }

    /**
     * skip(int)让我们可以忽略Observable发射的前n项数据
     */
    private void method4() {
        Observable.from(list)
                .skip(3)
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("skip", "call: " + person);
                    }
                });
    }

    /**
     * skipLast(int)忽略Observable发射的后n项数据
     */
    private void method5() {
        Observable.from(list)
                .skipLast(3)
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("skipLast", "call: " + person);
                    }
                });
    }

    /**
     * elementAt(int)用来获取元素Observable发射的事件序列中的第n项数据，并当做唯一的数据发射出去
     */
    private void method6() {
        Observable.from(list)
                .elementAt(3)
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("elementAt", "call: " + person);
                    }
                });
    }

    /**
     * first()顾名思义，它是的Observable只发送观测序列中的第一个数据项
     */
    private void method7() {
        Observable.from(list)
                .first()
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("first", "call: " + person);
                    }
                });
    }


    /**
     * last()只发射观测序列中的最后一个数据项
     */
    private void method8() {
        Observable.from(list)
                .last()
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("first", "call: " + person);
                    }
                });
    }

    /**
     * distinct去除重复，有无参形式
     * 还可以传入Func1对象，自己实现判断逻辑
     */
    private void method9() {
        Observable.just(2, 1, 2, 2, 3, 4, 3, 4, 5, 5)
                .distinct()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        System.out.print(i + " ");
                    }
                });

        list.add(new Person(3, "js3"));
        Observable.from(list)
                .distinct(new Func1<Person, String>() {

                    @Override
                    public String call(Person p) {
                        return p.getName();
                    }
                })
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("distinct", "call: " + person);
                    }
                });
    }

    /**
     * debounce(long, TimeUnit)过滤掉了由Observable发射的速率过快的数据；
     * 如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
     * 通常我们用来结合RxBing(Jake Wharton大神使用RxJava封装的Android UI组件)使用，防止button重复点击。
     */
    private void method10(){

    }
}
