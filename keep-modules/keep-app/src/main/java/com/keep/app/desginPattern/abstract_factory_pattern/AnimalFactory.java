package com.keep.app.desginPattern.abstract_factory_pattern;

public class AnimalFactory extends AbstractFactory {

    @Override
    public Animal getAnimal(String type) {
        if ("dog".equals(type)) {
            Animal dog = new Dog();
            return dog;
        } else if ("cat".equals(type)) {
            Animal cat = new Cat();
            return cat;
        }
        return null;

    }

    @Override
    public Color getColor(String type) {
        return null;
    }


}
