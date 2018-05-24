package com.jia.rxjavaproject.bean;

import java.util.List;

/**
 * Description:
 * Created by jia on 2018/5/24.
 * 人之所以能，是相信能。
 */

public class Student {

    private List<Course> list;

    public List<Course> getList() {
        return list;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }

    public static class Course {
        private String name;

        public Course(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Course{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "list=" + list +
                '}';
    }
}
