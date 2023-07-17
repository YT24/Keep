package org.nebula.contrib.ngbatis.handler;

// Copyright (c) 2022 All project authors. All rights reserved.
//
// This source code is licensed under Apache 2.0 License.

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import org.nebula.contrib.ngbatis.utils.ResultSetUtil;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结果集数据类型转换器。
 * <p> ResultSet -&gt; Collection&lt;Map&gt; </p>、
 *
 * @author yeweicheng
 * @since 2022-06-11 3:04
 * <br>Now is history!
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class CollectionMapResultHandler extends AbstractResultHandler<Collection, Map> {

    @Override
    public Collection handle(Collection newResult, ResultSet result, Class resultType) {
        List<String> columnNames = result.getColumnNames();
        int size = result.rowsSize();
        for (int i = 0; i < size; i++) {
            Map row = new HashMap();
            ResultSet.Record record = result.rowValues(i);
            for (int j = 0; j < columnNames.size(); j++) {
                ValueWrapper valueWrapper = record.values().get(j);
                row.put(columnNames.get(j), ResultSetUtil.getValue(valueWrapper));
            }
            newResult.add(row);
        }
        return newResult;
    }

}
