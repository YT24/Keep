package com.keep.app.desginPattern.chain_of_responssibility_pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessHandler extends AbstractorHandler implements Handler{
    @Override
    public void operator() {
        log.info("user business ....");
        if(getHandler() != null){
            getHandler().operator();
        }
    }
}
