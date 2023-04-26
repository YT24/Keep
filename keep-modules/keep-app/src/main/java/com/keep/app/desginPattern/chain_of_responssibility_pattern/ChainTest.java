package com.keep.app.desginPattern.chain_of_responssibility_pattern;

public class ChainTest {

    public static void main(String[] args) {
        AuthHandler auth = new AuthHandler();
        BusinessHandler bus = new BusinessHandler();
        ResponseHandler resp = new ResponseHandler();
        auth.setHandler(bus);
        bus.setHandler(resp);
        auth.operator();
    }
}
