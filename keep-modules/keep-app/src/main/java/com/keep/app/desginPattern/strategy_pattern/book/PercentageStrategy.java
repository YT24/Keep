package com.keep.app.desginPattern.strategy_pattern.book;

import com.keep.app.desginPattern.strategy_pattern.book.DiscountStrategy;

public class PercentageStrategy extends DiscountStrategy {

    /**
     * 折扣百分比
     */
    private double discountPercent;

    public PercentageStrategy(double price, int copies) {
        super(price, copies);
    }

    /**
     * Sets the discountPercent
     *
     * @param discountPercent discountPercent
     */
    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    double calcDiscount() {
        return getCopies() * getPrice() * discountPercent;
    }
}