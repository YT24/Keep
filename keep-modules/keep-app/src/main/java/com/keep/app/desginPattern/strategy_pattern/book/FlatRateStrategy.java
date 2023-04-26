package com.keep.app.desginPattern.strategy_pattern.book;

import com.keep.app.desginPattern.strategy_pattern.book.DiscountStrategy;

public class FlatRateStrategy extends DiscountStrategy {

    /**
     * 折扣金额
     */
    private int discountPrice;

    public FlatRateStrategy(double price, int copies) {
        super(price,copies);
    }

    /**
     * Sets the discountPrice
     *
     * @param discountPrice discountPrice
     */
    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    double calcDiscount() {
        return discountPrice * getCopies();
    }
}