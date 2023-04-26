package com.keep.app.desginPattern.strategy_pattern.book;

import com.keep.app.desginPattern.strategy_pattern.book.DiscountStrategy;

/**
 * @className: NoDiscountStrategy
 * @description:
 * @author: charon
 * @create: 2022-04-10 11:48
 */
public class NoDiscountStrategy extends DiscountStrategy {
    @Override
    double calcDiscount() {
        return 0;
    }
}