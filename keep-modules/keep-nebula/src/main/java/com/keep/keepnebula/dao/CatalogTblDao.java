package com.keep.keepnebula.dao;

import com.keep.keepnebula.domain.pojo.CatalogTbl;
import org.nebula.contrib.ngbatis.proxy.NebulaDaoBasic;
import org.springframework.data.repository.query.Param;

/**
 * @author yangte
 * @description
 * @date 2023/6/21 10:00
 */
public interface CatalogTblDao extends NebulaDaoBasic<CatalogTbl, String> {

    Integer insertVertex(@Param("catalogTbl") CatalogTbl catalogTbl);

    Integer upsertVertex(@Param("catalogTbl") CatalogTbl catalogTbl);

    Integer updateVertex(@Param("catalogTbl") CatalogTbl catalogTbl);

    Integer delVertex(@Param("vid") String vid);
}
