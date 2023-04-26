package com.keep.app.desginPattern.strategy_pattern.book;

public class Client {

    public static void main(String[] args) {
        Book book1 = new Book("java设计模式", new NoDiscountStrategy());
        book1.getDiscount();

        FlatRateStrategy rateStrategy = new FlatRateStrategy(23.0, 5);
        rateStrategy.setDiscountPrice(1);
        Book book2 = new Book("java与模式",rateStrategy);
        book2.getDiscount();

        System.out.println("修改《java与模式》的折扣算法：");
        PercentageStrategy percentageStrategy = new PercentageStrategy(23, 5);
        percentageStrategy.setDiscountPercent(0.07);
        book2.setStrategy(percentageStrategy);
        book2.getDiscount();
    }
}