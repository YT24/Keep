package com.keep.keepnebula.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yangte
 * @description model vertex (点对应的实体类)
 * @date 2023/6/20 18:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Space(name = "assets_graph")
@Table(name = "col")
public class CatalogCol {

    @Id
    private String id;

    @Column(name = "name")
    private String name;
}
