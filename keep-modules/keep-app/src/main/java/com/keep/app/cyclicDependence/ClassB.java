package com.keep.app.cyclicDependence;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
@Data
@ToString
@Aspect
public class ClassB implements BeanPostProcessor {


    private ClassA classA;

    public ClassB() {
        log.info("---creat B ---- success !!!");
    }


}
