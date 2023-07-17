package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.EdgeHasLab;

/**
 * @author yangte
 * @description 点与点关系 hasLab
 * @date 2023/6/21 09:33
 */
public interface EdgeHasLabService {


    void insert(Object from, EdgeHasLab edgeHasLab, Object to);
}
