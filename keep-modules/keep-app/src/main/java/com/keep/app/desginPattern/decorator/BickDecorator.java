package com.keep.app.desginPattern.decorator;

public class BickDecorator extends AbstractPersonDecorator{

    public BickDecorator(Person person) {
        super(person);
    }

    @Override
    public void run() {
        super.person.run();
        System.out.println("骑自行车。。。。。");
    }
}
