package com.keep.app.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tuple<M, T> {
    private final M m;
    private final T t;

}