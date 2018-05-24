package com.jia.rxjavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jia.rxjavaproject.bean.Student;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * map适用于一对一转换，当然也可以配合flatmap进行适用
 * flatmap适用于一对多，多对多的场景
 */
public class FlatMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);


        method();
    }

    private void method() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            List<Student.Course> courses = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                Student.Course course = new Student.Course("课程" + j);
                courses.add(course);
            }
            student.setList(courses);
            students.add(student);
        }


        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Student.Course>>() {
                    @Override
                    public Observable<Student.Course> call(Student student) {
                        return Observable.from(student.getList());
                    }
                })
                .subscribe(new Action1<Student.Course>() {
                    @Override
                    public void call(Student.Course r) {
                        Log.e("flatMap", "call: " + r.toString());
                    }
                });
    }

    /**
     * map与flatMap的区别
     *
     * 1、map返回的是结果集，flatmap返回的是包含结果集的Observable（返回结果不同）
     * 2、map被订阅时每传递一个事件执行一次onNext方法，
     *    flatmap多用于多对多，一对多，再被转化为多个时，一般利用from/just进行一一分发，
     *    被订阅时将所有数据传递完毕汇总到一个Observable然后一一执行onNext方法（执行顺序不同）
     * 3、map只能单一转换，单一指的是只能一对一进行转换，指一个对象可以转化为另一个对象但是不能转换成对象数组
     *  （map返回结果集不能直接使用from/just再次进行事件分发，一旦转换成对象数组的话，
     *   再处理集合/数组的结果时需要利用for一一遍历取出，而使用RxJava就是为了剔除这样的嵌套结构，使得整体的逻辑性更强。）
     *   flatmap既可以单一转换也可以一对多/多对多转换，flatmap要求返回Observable，
     *   因此可以再内部进行from/just的再次事件分发，一一取出单一对象（转换对象的能力不同）
     */
}
