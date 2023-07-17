package com.keep.keepnebula;

import com.keep.keepnebula.dao.CatalogTblDao;
import com.keep.keepnebula.dao.EdgeHasLabDao;
import com.keep.keepnebula.dao.EdgeJoinDao;
import com.keep.keepnebula.dao.EdgeLinksToDao;
import com.keep.keepnebula.domain.pojo.CatalogTbl;
import com.keep.keepnebula.domain.pojo.EdgeHasLab;
import com.keep.keepnebula.domain.pojo.EdgeJoin;
import com.keep.keepnebula.domain.pojo.EdgeLinksTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KeepNebulaApplicationTests {


    @Autowired
    private CatalogTblDao catalogTblDao;

    @Autowired
    private EdgeJoinDao catalogJoinDao;

    @Autowired
    private EdgeLinksToDao edgeLinksToDao;

    @Autowired
    private EdgeHasLabDao catalogHasLabDao;

    @Test
    void contextLoadsEdge() {
        CatalogTbl catalogTblA = new CatalogTbl();
        catalogTblA.setId("10001");
        catalogTblA.setName("table_a");
        catalogTblDao.insertSelective(catalogTblA);
        catalogTblA.setName("table_a`");
        catalogTblDao.updateByIdSelective(catalogTblA);

        CatalogTbl catalogTblB = new CatalogTbl();
        catalogTblB.setId("10002");
        catalogTblB.setName("table_b");
        catalogTblDao.insertSelective(catalogTblB);
        catalogTblB.setName("table_b`");
        catalogTblDao.updateByIdSelective(catalogTblB);

        EdgeJoin edgeJoin = new EdgeJoin();
        edgeJoin.setSrcCol("user_id1");
        edgeJoin.setDestCol("u_id1");
        edgeJoin.setSource("auto1");
        edgeJoin.setJoinType("inner join1");
        catalogJoinDao.insertEdgeSelective(catalogTblA, edgeJoin, catalogTblB);


    }

    @Test
    void contextLoadsTag() {
        CatalogTbl catalogTblA = new CatalogTbl();
        catalogTblA.setId("10001");
        catalogTblA.setName("table_a");
        catalogTblDao.insertSelective(catalogTblA);
        catalogTblA.setName("table_a`");
        catalogTblDao.updateByIdSelective(catalogTblA);

        CatalogTbl catalogTblB = new CatalogTbl();
        catalogTblB.setId("10002");
        catalogTblB.setName("table_b");
        catalogTblDao.insertSelective(catalogTblB);
        catalogTblB.setName("table_b`");
        catalogTblDao.updateByIdSelective(catalogTblB);

        CatalogTbl catalogTblC = new CatalogTbl();
        catalogTblC.setId("10003");
        catalogTblC.setName("table_c");
        catalogTblDao.insertSelective(catalogTblC);
        catalogTblC.setName("table_c`");
        catalogTblDao.updateByIdSelective(catalogTblC);


        EdgeJoin edgeJoin = new EdgeJoin();
        edgeJoin.setSrcCol("user_id");
        edgeJoin.setDestCol("u_id");
        edgeJoin.setSource("auto");
        edgeJoin.setJoinType("inner join");
        catalogJoinDao.insertEdgeSelective(catalogTblA, edgeJoin, catalogTblB);
        //catalogJoinDao.insertEdgeSelective(catalogTblB, catalogJoin, catalogTblA);

        EdgeLinksTo catalogLinksTo = new EdgeLinksTo();
        edgeLinksToDao.insertEdgeSelective(catalogTblA, catalogLinksTo, catalogTblB);
        //catalogLinksToDao.insertEdgeSelective(catalogTblB, catalogLinksTo, catalogTblA);

        EdgeHasLab hasLab = new EdgeHasLab();
        catalogHasLabDao.insertEdgeSelective(catalogTblB, hasLab, catalogTblC);
        //catalogHasLabDao.insertEdgeSelective(catalogTblC, hasLab, catalogTblB);


    }

}
