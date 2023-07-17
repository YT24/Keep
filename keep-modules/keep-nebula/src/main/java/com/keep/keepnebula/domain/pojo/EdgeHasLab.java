package com.keep.keepnebula.domain.pojo;

import lombok.Data;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Table;

/**
 * @author yangte
 * @description 边实体类
 * @date 2023/6/21 13:37
 */
@Data
@Space(name = "assets_graph")
@Table(name = "hasLab")
public class EdgeHasLab {


}
