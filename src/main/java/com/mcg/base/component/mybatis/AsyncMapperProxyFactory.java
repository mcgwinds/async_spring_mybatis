package com.mcg.base.component.mybatis;

import org.apache.ibatis.binding.MapperMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mcg
 */
public class AsyncMapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap();

    public AsyncMapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return this.mapperInterface;
    }

    public Map<Method, MapperMethod> getMethodCache() {
        return this.methodCache;
    }

    protected T newInstance(AsyncMapperProxy<T> asyncMapperProxy) {

        return (T)Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, asyncMapperProxy);
    }

    public T newInstance(T mapperProxy) {
        AsyncMapperProxy<T> asyncMapperProxy = new AsyncMapperProxy(mapperProxy);
        return this.newInstance(asyncMapperProxy);
    }
}

