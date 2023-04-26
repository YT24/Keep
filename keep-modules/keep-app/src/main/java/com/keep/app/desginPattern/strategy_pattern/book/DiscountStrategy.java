package com.keep.app.desginPattern.strategy_pattern.book;

/**
 * @className: DiscountStrategy
 * @description:
 * @author: charon
 * @create: 2022-04-10 11:48
 */
public abstract class DiscountStrategy {

    /**
     * 价格
     */
    private double price = 0;

    /**
     * 数量
     */
    private int copies;

    public DiscountStrategy() {
    }

    public DiscountStrategy(double price, int copies) {
        this.price = price;
        this.copies = copies;
    }

    /**
     * 计算价格的策略方法
     * @return
     */
    abstract double calcDiscount();

    /**
     * Gets the value of price
     *
     * @return the value of price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the value of copies
     *
     * @return the value of copies
     */
    public int getCopies() {
        return copies;
    }
}
