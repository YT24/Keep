package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.CatalogCol;

/**
 * @author yangte
 * @description
 * @date 2023/6/21 09:33
 */
public interface CatalogColService {

    void insert(CatalogCol catalogCol);

    void updateById(CatalogCol catalogCol);

    void deleteById(String vid);
}
