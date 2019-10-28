package com.suixingpay.myarraylist;

import jdk.internal.util.ArraysSupport;


import java.util.*;
import java.util.function.Consumer;

public class MyArrayList<E> {
    //DEFAULT_CAPACITY 默认容量为10
    private static final int DEFAULT_CAPACITY=10;

    //DEFAULTCAPACITY_EMPTY_ELEMENTDATA 默认容量的空数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    //EMPTY_ELEMENTDATA 空数组
    private static final Object[] EMPTY_ELEMENTDATA={};

    //elementData 对象数组
    Object[] elementData;

    //size 数组大小
    private int size;


    //为iterator做准备
    public int modCount = 0 ;

    //无参构造方法：初始化为一个默认的空对象数组
    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    //一个参数构造方法：给定初始化大小
    public MyArrayList(int initialCapacity){
        if(initialCapacity>0){
            this.elementData=new Object[initialCapacity];
        }else if(initialCapacity==0){
            this.elementData=EMPTY_ELEMENTDATA;
        }else{
            throw new IllegalArgumentException("非法的容量: "+ initialCapacity);
        }
    }

    //add()方法
    public boolean add(E e){
        modCount ++;
        add(e,elementData,size);
        return true;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        modCount++;
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.elementData).length)
            elementData = grow();
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        size = s + 1;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void add(E e, Object[] elementData, int s) {
        if(s==elementData.length){
            elementData = grow();
        }
        elementData[s]=e;
        size=s+1;
    }

    //扩容（看不懂）
    private Object[] grow() {
        return grow(size + 1);
    }
    private Object[] grow(int minCapacity) {
        //获取旧容量值
        int oldCapacity = elementData.length;
        // 判断 如果旧容量大于0 或者 对象数组不为默认容量的空数组
        if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {

            int newCapacity = ArraysSupport.newLength(oldCapacity,
                    minCapacity - oldCapacity, /* minimum growth */
                    oldCapacity >> 1           /* preferred growth */);
            return elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    //size()方法
    public int size(){
        return size;
    }

    //remove()方法
    public E remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;

        @SuppressWarnings("unchecked") E oldValue = (E) es[index];
        fastRemove(es, index);

        return oldValue;
    }

    private void fastRemove(Object[] es, int i) {
        modCount++;
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(es, i + 1, es, i, newSize - i);
        es[size = newSize] = null;
    }

    //get()方法
    public E get(int index) {
        Objects.checkIndex(index, size);
        return elementData(index);
    }

    //set()方法
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    public Iterator<E> iterator() {
        return new MyArrayList.Itr();
    }


    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        // prevent creating a synthetic constructor
        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = elementData;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size && modCount == expectedModCount; i++)
                    action.accept(elementAt(es, i));
                // update once at end to reduce heap write traffic
                cursor = i;
                lastRet = i - 1;
                checkForComodification();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    //contains()
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    int indexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }



}
