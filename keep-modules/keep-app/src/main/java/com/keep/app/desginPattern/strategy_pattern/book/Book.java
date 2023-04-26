package com.keep.app.desginPattern.strategy_pattern.book;

public class Book {

    /**
     * 图书名称
     */
    private String name;

    /**
     * 折扣类型
     */
    private DiscountStrategy strategy;

    public Book(String name, DiscountStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    /**
     * Sets the strategy
     *
     * @param strategy strategy
     */
    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public void getDiscount(){
        System.out.println("图书名称：《" + name + "》，折扣算法为："+ strategy.getClass() +"，折扣价格为：" + strategy.calcDiscount());
    }
}