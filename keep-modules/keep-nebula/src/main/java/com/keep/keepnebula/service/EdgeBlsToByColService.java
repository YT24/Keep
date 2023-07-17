package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.EdgeBlsToByCol;

/**
 * @author yangte
 * @description 点与点关系 belongsToByCol
 * @date 2023/6/21 09:33
 */
public interface EdgeBlsToByColService {


    void insert(Object from, EdgeBlsToByCol edgeBlsToByCol, Object to);
}
