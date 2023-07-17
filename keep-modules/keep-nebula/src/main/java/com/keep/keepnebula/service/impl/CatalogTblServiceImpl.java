package com.keep.keepnebula.service.impl;

import com.keep.keepnebula.dao.CatalogTblDao;
import com.keep.keepnebula.domain.pojo.CatalogTbl;
import com.keep.keepnebula.service.CatalogTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/21 09:34
 */
@Component
public class CatalogTblServiceImpl implements CatalogTblService {

    @Autowired
    private CatalogTblDao catalogTblDao;

    @Override
    public void insert(CatalogTbl catalogTbl) {
        //catalogTblDao.insertVertex(catalogTbl);
        catalogTblDao.insert(catalogTbl);
    }

    @Override
    public void updateById(CatalogTbl catalogTbl) {
        //catalogTblDao.updateVertex(catalogTbl);
        catalogTblDao.updateById(catalogTbl);
    }

    @Override
    public void deleteById(String vid) {
        //catalogTblDao.delVertex(vid);
        catalogTblDao.deleteById(vid);
    }


}
