package com.keep.app.desginPattern.chain_of_responssibility_pattern;

public abstract class AbstractorHandler {

    private Handler handler;

    void setHandler(Handler handler){
        this.handler = handler;
    }

    Handler getHandler(){
        return this.handler;
    }
}
