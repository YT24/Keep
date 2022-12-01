package com.example.readwrite.aop;

/**
 * @author chaird
 * @create 2020-12-30 21:30
 */

import com.example.readwrite.config.db.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面表达式和方法
 */
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(com.example.readwrite.annotation.Slave)")
    public void readPointcut() {

    }


    @Pointcut("@annotation(com.example.readwrite.annotation.Master)")
    public void writePointcut() {

    }


    /**
     * Before方法，设置ThreadLocal里的一个变量为slave
     */
    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    /**
     * Before方法，设置ThreadLocal里的一个变量为master
     */
    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }

}