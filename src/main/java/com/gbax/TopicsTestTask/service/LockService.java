package com.gbax.TopicsTestTask.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LockService {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public Lock getLock() {
        return lock;
    }

    public Condition getCondition() throws InterruptedException {
        return condition;
    }
}
