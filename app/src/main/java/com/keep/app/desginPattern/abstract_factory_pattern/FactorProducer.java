package com.keep.app.desginPattern.abstract_factory_pattern;

public class FactorProducer {
    public static AbstractFactory getFactory(String type) {
        if (type.equals("animal")) {
            return new AnimalFactory();
        } else if (type.equals("color")) {
            return new ColorFactory();
        }
        return new ColorFactory();
    }
}
