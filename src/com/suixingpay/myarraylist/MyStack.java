package com.suixingpay.myarraylist;

public class MyStack {
    MyArrayList elements;
    int length;

    public MyStack () {
        elements = new MyArrayList();
        length = 0;
    }

    //push()方法
    public <T> void push(T ele) {
        length++;
        elements.add(ele);
    }
    //pop()方法
    public <T> T pop() {
        T temp = (T) elements.get(length-1);
        elements.remove(length-1);
        length--;
        return temp;
    }

    //size() 方法
    public int size() {
        return length;
    }



}
