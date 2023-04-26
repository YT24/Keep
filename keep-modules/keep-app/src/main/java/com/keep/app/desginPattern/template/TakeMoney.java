package com.keep.app.desginPattern.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TakeMoney extends AbstractBankTemplate{
    @Override
    void doBusiness() {
        log.info("取款业务。。。。。");
    }
}
