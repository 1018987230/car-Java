package com.example.nft.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author wangyixiong
 * @Date 2023/3/16 下午3:40
 * @PackageName:com.example.nft.utils
 * @ClassName: ApplicationContextHolder
 * @Description: 手动地去调用容器的getBean方法来拿到bean
 * @Version 1.0
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入接口，将其存入静态变量
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    /**
     * 取得存在静态变量中的applicationContext
     */
    public static ApplicationContext getApplicationContext(){
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中获取bean，自动转型赋值的对象的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 清除ApplicationContext静态变量
     */
    public static void cleanApplicationContext(){
        applicationContext = null;
    }



    private static void checkApplicationContext() {
        if(applicationContext == null){
            throw new IllegalStateException("applicationContext注入失败！");
        }

    }
}
