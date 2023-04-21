package com.example.nft.Handler;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;


public class MySessionListener implements HttpSessionListener{

    public static AtomicInteger userCount = new AtomicInteger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        userCount.getAndIncrement();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        userCount.getAndDecrement();
    }
}
