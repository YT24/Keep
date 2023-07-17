package com.keep.keepnebula.domain.pojo;

import lombok.Data;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author yangte
 * @description 边实体类
 * @date 2023/6/21 13:37
 */
@Data
@Space(name = "assets_graph")
@Table(name = "join")
public class EdgeJoin {

    @Column(name = "src_col")
    private String srcCol;

    @Column(name = "dest_col")
    private String destCol;

    @Column(name = "join_type")
    private String joinType;

    @Column(name = "source")
    private String source;
}
