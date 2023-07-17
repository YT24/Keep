package com.keep.keepnebula.service.impl;

import com.keep.keepnebula.dao.CatalogLabelDao;
import com.keep.keepnebula.domain.pojo.CatalogLabel;
import com.keep.keepnebula.service.CatalogLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/21 09:34
 */
@Component
public class CatalogLabelServiceImpl implements CatalogLabelService {

    @Autowired
    private CatalogLabelDao catalogLabelDao;

    @Override
    public void insert(CatalogLabel catalogLabel) {
        catalogLabelDao.insert(catalogLabel);
    }

    @Override
    public void updateById(CatalogLabel catalogLabel) {
        catalogLabelDao.updateById(catalogLabel);
    }

    @Override
    public void deleteById(String vid) {
        catalogLabelDao.deleteById(vid);
    }


}
