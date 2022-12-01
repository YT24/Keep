package com.keep.app.desginPattern.abstract_factory_pattern;

public class AbstractFactoryPatternDemo {
    public static void main(String[] args) {
        AbstractFactory animal_factory = FactorProducer.getFactory("animal");
        Animal dog = animal_factory.getAnimal("dog");
        Animal cat = animal_factory.getAnimal("cat");
        dog.eat();
        cat.eat();

        AbstractFactory color_factory = FactorProducer.getFactory("color");
        Color red = color_factory.getColor("red");
        Color green = color_factory.getColor("green");
        red.say();
        green.say();
    }
}
