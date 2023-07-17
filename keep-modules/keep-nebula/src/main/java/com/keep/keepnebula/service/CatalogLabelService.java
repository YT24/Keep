package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.CatalogLabel;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/21 09:33
 */
public interface CatalogLabelService {

    void insert(CatalogLabel catalogLabel);

    void updateById(CatalogLabel catalogLabel);

    void deleteById(String vid);
}
