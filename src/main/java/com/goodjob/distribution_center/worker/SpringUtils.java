package com.goodjob.distribution_center.worker;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> clz) {
        return context.getBean(clz);
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        context = ctx;
    }
}

