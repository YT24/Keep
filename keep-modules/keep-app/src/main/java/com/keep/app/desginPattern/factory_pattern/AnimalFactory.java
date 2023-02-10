package com.keep.app.desginPattern.factory_pattern;

public class AnimalFactory {

    public Animal getAnimal(String type) {
        if ("dog".equals(type)) {
            Dog dog = new Dog();
            return dog;
        } else if ("cat".equals(type)) {
            Cat cat = new Cat();
            return cat;
        }
        return null;

    }


}
