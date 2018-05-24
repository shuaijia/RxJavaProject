package com.jia.rxjavaproject;

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
 * 操作符就是为了解决对Observable对象的 变换(关键词) 的问题，
 * 操作符用于在Observable和最终的Subscriber之间修改Observable发出的事件。
 * RxJava提供了很多很有用的操作符。
 * <p>
 * <p>
 * map操作符，就是用来把把一个事件转换为另一个事件的。
 * <p>
 * map()操作符就是用于变换Observable对象的，map操作符返回一个Observable对象，
 * 这样就可以实现链式调用，在一个Observable对象上多次使用map操作符，最终将最简洁的数据传递给Subscriber对象。
 */
public class MapActivity extends AppCompatActivity {

    private List<Person> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        for (int i = 0; i < 8; i++) {
            list.add(new Person(i, "haha"));
        }

        method();

        method2();
    }

    private void method() {

        Observable.from(list)
                .map(new Func1<Person, Person>() {
                    @Override
                    public Person call(Person person) {
                        if (person.getAge() % 2 == 0)
                            person.setName("js");
                        return person;
                    }
                })
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Log.e("map", "call: " + person.toString());
                    }
                });

    }

    /**
     * 另一个示例
     */
    private void method2() {
//        Observable.just("images/logo.png") // 输入类型 String
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String filePath) { // 参数类型 String
//                        return getBitmapFromPath(filePath); // 返回类型 Bitmap
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
//                        showBitmap(bitmap);
//                    }
//                });
    }


    /**
     * 可以看出：
     *
     * map() 方法将参数中的 String 对象转换成一个 Bitmap 对象后返回，而在经过 map() 方法后，
     * 事件的参数类型也由 String 转为了 Bitmap。这种直接变换对象并返回的，是最常见的也最容易理解的变换。
     * 不过 RxJava 的变换远不止这样，它不仅可以针对事件对象，还可以针对整个事件队列，这使得 RxJava 变得非常灵活。
     */
}
