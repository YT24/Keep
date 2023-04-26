package com.keep.app.desginPattern.decorator;

public class Main {

    public static void main(String[] args) {
        Person person = new SimplePerson();

        Person carDecorator = new CarDecorator(person);
        Person bickDecorator = new BickDecorator(carDecorator);
        bickDecorator.run();
    }
}
