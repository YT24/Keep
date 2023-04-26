package com.keep.app.desginPattern.decorator;

public abstract class AbstractPersonDecorator implements Person {

    protected final Person person;

    public AbstractPersonDecorator(Person person){
        this.person = person;
    }
}
