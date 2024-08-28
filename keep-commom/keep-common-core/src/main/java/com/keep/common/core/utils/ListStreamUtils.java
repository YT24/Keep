package com.keep.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.keep.common.core.domain.vo.UserInfoVo;

import java.util.ArrayList;
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

    public static <T, K, E> Map<K, List<E>> toGroupByValueList(List<T> list, Function<? super T, ? extends K> keyMapper,
                                                               Function<? super T, ? extends E> valueMapper) {
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

    public static void main(String[] args) {
        List<UserInfoVo> userInfoVos = new ArrayList<>();
        userInfoVos.add(UserInfoVo.builder().id(1L).username("A").build());
        userInfoVos.add(UserInfoVo.builder().id(2L).username("B").build());
        userInfoVos.add(UserInfoVo.builder().id(3L).username("C").build());
        userInfoVos.add(UserInfoVo.builder().id(1L).username("A").build());
        userInfoVos.add(UserInfoVo.builder().id(2L).username("B").build());
        userInfoVos.add(UserInfoVo.builder().id(3L).username("C").build());
        System.out.println(toGroupByValueList(userInfoVos, UserInfoVo::getId, UserInfoVo::getUsername));
        System.out.println(toGroupByValueStr(userInfoVos, UserInfoVo::getId, UserInfoVo::getUsername));


    }


}
