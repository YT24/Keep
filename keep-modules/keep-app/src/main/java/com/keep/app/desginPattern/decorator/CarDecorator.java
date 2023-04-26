package com.keep.app.desginPattern.decorator;

public class CarDecorator extends AbstractPersonDecorator{

    public CarDecorator(Person person) {
        super(person);
    }

    @Override
    public void run() {
        super.person.run();
        System.out.println("开汽车。。。。。");
    }
}
