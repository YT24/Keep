package com.keep.app.desginPattern.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBankTemplate {

    // 银行办理业务（取号，排队，业务办理）

    public void doBankBusiness(){
        getNumber();
        waitMoment();
        doBusiness();
    }
    // 具体业务办理
    abstract void doBusiness();


    public void waitMoment() {
        log.info("排队。。。。。");
    }

    public void getNumber() {
        log.info("取号。。。。");
    }
}
