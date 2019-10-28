package com.suixingpay.myarraylist;

import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
        MyArrayList<Integer> list=new MyArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list.get(0));
        list.remove(0);
        list.set(0,100);
        System.out.println(list.get(0));
        for(Iterator<Integer> itr = list.iterator(); itr.hasNext();)
        {
            Object ele=itr.next();
            System.out.println(ele.toString());

        }
        System.out.println(list.contains(2));
        System.out.println("实现了全部功能");
    }
}
