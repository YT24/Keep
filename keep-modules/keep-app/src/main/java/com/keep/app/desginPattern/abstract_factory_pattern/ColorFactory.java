package com.keep.app.desginPattern.abstract_factory_pattern;

public class ColorFactory extends AbstractFactory {

    @Override
    public Animal getAnimal(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Color getColor(String type) {
        if ("red".equals(type)) {
            Red red = new Red();
            return red;
        } else if ("green".equals(type)) {
            Green green = new Green();
            return green;
        }
        return null;
    }

}
