package com.keep.app.desginPattern.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveMoney extends AbstractBankTemplate{
    @Override
    void doBusiness() {
        log.info("存款业务。。。。。");
    }
}
