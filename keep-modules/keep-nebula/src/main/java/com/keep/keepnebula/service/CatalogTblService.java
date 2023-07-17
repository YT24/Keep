package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.CatalogTbl;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/21 09:33
 */
public interface CatalogTblService {

    void insert(CatalogTbl catalogTbl);

    void updateById(CatalogTbl catalogTbl);

    void deleteById(String vid);
}
