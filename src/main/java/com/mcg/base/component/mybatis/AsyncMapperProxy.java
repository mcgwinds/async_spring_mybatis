package com.mcg.base.component.mybatis;

import com.google.common.util.concurrent.*;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author mcg
 */
public class AsyncMapperProxy<T> implements InvocationHandler, Serializable {

    private final T mapperProxy;

    private  ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));


    public AsyncMapperProxy(T mapperProxy) {
        this.mapperProxy = mapperProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        ListenableFuture<Object> future = service.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return method.invoke(mapperProxy, args);
            }
        });

        Futures.addCallback(future, new FutureCallback<Object>(){
            @Override
            public void onSuccess(Object result) {
                System.out.println("onSuccess: ");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("onFailure: ");

            }},service);
        return future.get();

    }
}
