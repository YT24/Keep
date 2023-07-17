package org.nebula.contrib.ngbatis.handler;

// Copyright (c) 2022 All project authors. All rights reserved.
//
// This source code is licensed under Apache 2.0 License.

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ResultSet.Record;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import org.nebula.contrib.ngbatis.models.data.NgEdge;
import org.nebula.contrib.ngbatis.models.data.NgSubgraph;
import org.nebula.contrib.ngbatis.models.data.NgVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Convert the subgraph data from ResultSet to NgSubgraph.
 *
 * @author yeweicheng
 * @since 2023-01-07 4:55
 * <br> Now is history!
 */
@Component
public class NgSubgraphResultHandler extends AbstractResultHandler<NgSubgraph<?>, NgSubgraph<?>> {

    @Autowired
    private NgEdgeResultHandler edgeResultHandler;
    @Autowired
    private NgVertexResultHandler vertexResultHandler;

    @Override
    public NgSubgraph<?> handle(NgSubgraph<?> newResult, ResultSet result, Class resultType)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Record row = result.rowValues(0);
        return handle(newResult, row);
    }

    public NgSubgraph<?> handle(NgSubgraph<?> newResult, Record row) {
        ValueWrapper nodes = row.get("nodes");
        if (nodes != null) {
            List<ValueWrapper> nodesValueWrapper = nodes.asList();
            nodesValueWrapper.forEach(nodeValueWrapper -> {
                List vertexes = newResult.getVertexes();
                NgVertex<?> vertex = new NgVertex<>();
                vertex = vertexResultHandler.handle(vertex, nodeValueWrapper);
                vertexes.add(vertex);
            });
        }

        ValueWrapper relationships = row.get("relationships");
        if (relationships != null) {
            List<ValueWrapper> edgesValueWrapper = relationships.asList();
            edgesValueWrapper.forEach(edgeValueWrapper -> {
                List edges = newResult.getEdges();
                NgEdge<?> edge = new NgEdge<>();
                edge = edgeResultHandler.handle(edge, edgeValueWrapper);
                edges.add(edge);
            });
        }
        return newResult;
    }
}
