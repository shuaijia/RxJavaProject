package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jia.rxjavaproject.bean.Person;
import com.jia.rxjavaproject.bean.Student;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

/**
 * GroupBy操作符将原始Observable分拆为一些Observables集合，它们中的每一个发射原始Observable数据序列的一个子序列。
 * 哪个数据项由哪一个Observable发射是由一个函数判定的，这个函数给每一项指定一个Key，Key相同的数据会被同一个Observable发射。
 */
public class GroupByActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_by);

        method();
    }

    private void method() {

        ArrayList<Object> list = new ArrayList<Object>();
        list.add(new Person());
        list.add(new Person());
        list.add(new Student());
        list.add(new Student());
        list.add(new Person());
        list.add(new Student());
        list.add(new Student());

        Observable.from(list)
                .groupBy(new Func1<Object, Integer>() {
                    @Override
                    public Integer call(Object o) {
                        if (o.getClass().getName().equals(Person.class.getName())) {
                            return 1;
                        } else {
                            return 2;
                        }
                    }
                })
                .subscribe(new Action1<GroupedObservable<Integer, Object>>() {
                    @Override
                    public void call(GroupedObservable<Integer, Object> integerObjectGroupedObservable) {
                        int key = integerObjectGroupedObservable.getKey();

                        switch (key) {
                            case 1:

                                integerObjectGroupedObservable.subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        Log.e("groupby", "call: 接收Person对象");
                                    }
                                });

                                break;
                            case 2:

                                integerObjectGroupedObservable.subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        Log.e("groupby", "call: 接收Student对象");
                                    }
                                });

                                break;
                        }
                    }
                });
    }

    /**
     * 在GroupBy的Func1()函数中按你的逻辑分组，并将每个信息对应的组的key标志返回，如例子中我每个标志都是Integer类型的，
     * GroupBy会返回Observable的一个特殊子类GroupedObservable，这个特殊子类有个额外的方法getKey(),可用于获得当前信息的组别。
     */
}
