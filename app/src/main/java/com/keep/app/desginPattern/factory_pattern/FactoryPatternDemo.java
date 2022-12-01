package com.keep.app.desginPattern.factory_pattern;

public class FactoryPatternDemo {
    public static void main(String[] args) {
        AnimalFactory af = new AnimalFactory();
        Animal animal1 = af.getAnimal("dog");
        Animal animal2 = af.getAnimal("cat");

        animal1.eat();
        animal2.eat();
    }
}
