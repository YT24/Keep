package com.keep.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * stream流操作
 *
 * @author yangbowen
 */
public class ListStreamUtils {

    public static <T, K> List<K> toList(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return list.stream().map(keyMapper).collect(Collectors.toList());
    }

    public static <T, K> Map<K, T> toMap(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (v1, v2) -> v1));
    }

    public static <T, K, U> Map<K, U> toMap(List<T> list, Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends U> valueMapper) {
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1));
    }

    public static <T, K> Map<K, List<T>> toGroupBy(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyMapper));
    }

    public static <T, K, U> Map<K, List<U>> toGroupByValueList(List<T> list, Function<? super T, ? extends K> keyMapper,
                                                               Function<? super T, ? extends U> valueMapper) {
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    public static <T, K> Map<K, String> toGroupByValueStr(List<T> list, Function<? super T, ? extends K> keyMapper,
                                                          Function<? super T, String> valueMapper) {
        if (CollUtil.isEmpty(list)) {
            return MapUtil.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.joining(","))));
    }


}
