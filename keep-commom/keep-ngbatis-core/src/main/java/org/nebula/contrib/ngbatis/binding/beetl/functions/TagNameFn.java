package org.nebula.contrib.ngbatis.binding.beetl.functions;

// Copyright (c) 2022 All project authors. All rights reserved.
//
// This source code is licensed under Apache 2.0 License.

import org.nebula.contrib.ngbatis.models.ClassModel;

import static org.nebula.contrib.ngbatis.proxy.NebulaDaoBasicExt.entityType;
import static org.nebula.contrib.ngbatis.utils.ReflectUtil.schemaByEntityType;

/**
 * 通过实体对象，获取 vertexName 与 edgeName
 *
 * @author yeweicheng
 * @since 2022-08-25 2:51
 * <br>Now is history!
 */
public class TagNameFn extends AbstractFunction<Object, ClassModel, Void, Void, Void, Void> {

    @Override
    public Object call(Object para, ClassModel cm) {
        Class<?> entityType = null;
        if (cm != null) {
            entityType = entityType(cm.getNamespace());
        } else {
            entityType = para.getClass();
        }
        return schemaByEntityType(entityType);
    }
}
