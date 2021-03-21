package cn.soneer.assetdata.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * project：assetdata
 * class：AtomicCounter
 * describe：TODO
 * time：2021/3/21 13:11
 * author：soneer
 * version:1.0
 */

public class AtomicCounter {
    private static final AtomicCounter atomicCounter = new AtomicCounter();


    private AtomicCounter() {

    }

    public static AtomicCounter getInstance() {
        return atomicCounter;
    }

    private static AtomicInteger counter = new AtomicInteger();

    public int getValue() {
        return counter.get();
    }

    public int increase() {
        return counter.incrementAndGet();
    }

    public int decrease() {
        return counter.decrementAndGet();
    }
}
