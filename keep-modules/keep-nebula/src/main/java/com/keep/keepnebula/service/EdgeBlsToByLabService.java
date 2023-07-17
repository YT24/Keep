package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.EdgeBlsToByLab;

/**
 * @author yangte
 * @description 点与点关系 belongsToByLab
 * @date 2023/6/21 09:33
 */
public interface EdgeBlsToByLabService {


    /**
     * belongsToByLab
     * 插入边
     *
     * @param from
     * @param edgeBlsToByLab
     * @param to
     */
    void insert(Object from, EdgeBlsToByLab edgeBlsToByLab, Object to);
}
