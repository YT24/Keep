package com.keep.keepnebula.service;

import com.keep.keepnebula.domain.pojo.EdgeLinksTo;

/**
 * @author yangte
 * @description 点与点关系 linksTo
 * @date 2023/6/21 09:33
 */
public interface EdgeLinksToService {


    void insert(Object from, EdgeLinksTo catalogLinksTo, Object to);
}
