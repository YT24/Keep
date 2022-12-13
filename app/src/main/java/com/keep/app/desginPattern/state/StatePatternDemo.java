package com.keep.app.desginPattern.state;

import com.alibaba.druid.support.spring.stat.annotation.Stat;

public class StatePatternDemo {
    public static void main(String[] args) {
        Context context = new Context();

        State startState = new StartState();
        startState.doAction(context);

        System.out.println(context.getState().toString());

        State stopState = new StopState();
        stopState.doAction(context);

        System.out.println(context.getState().toString());
    }
}