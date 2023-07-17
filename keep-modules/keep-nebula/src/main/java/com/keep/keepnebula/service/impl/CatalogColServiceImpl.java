package com.keep.keepnebula.service.impl;

import com.keep.keepnebula.dao.CatalogColDao;
import com.keep.keepnebula.domain.pojo.CatalogCol;
import com.keep.keepnebula.service.CatalogColService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/21 09:34
 */
@Component
public class CatalogColServiceImpl implements CatalogColService {

    @Autowired
    private CatalogColDao catalogColDao;

    @Override
    public void insert(CatalogCol catalogTbl) {
        catalogColDao.insert(catalogTbl);
    }

    @Override
    public void updateById(CatalogCol catalogTbl) {
        catalogColDao.updateById(catalogTbl);
    }

    @Override
    public void deleteById(String vid) {
        catalogColDao.deleteById(vid);
    }


}
