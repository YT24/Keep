package com.keep.keepnebula.dao;

import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;
import org.springframework.stereotype.Component;

/**
 * @author yangte
 * @description
 * @date 2023/6/21 10:00
 */
@Component
public interface AssetsGraphInitDao extends NebulaDaoBasic<Object, String> {

    /**
     * 创建Tag
     */
    void insertTag();

    /**
     * 创建 Edge Type
     */
    void insertEdge();

    /**
     * 创建Index
     */
    void insertIndex();


}
