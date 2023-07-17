package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.EdgeJoin;

/**
 * @author yangte
 * @description 点与点关系 join
 * @date 2023/6/21 09:33
 */
public interface EdgeJoinService {

    void insert(Object from, EdgeJoin edgeJoin, Object to);
}
