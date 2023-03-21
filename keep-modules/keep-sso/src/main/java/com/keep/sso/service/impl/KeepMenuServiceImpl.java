package com.keep.sso.service.impl;

import com.keep.sso.convert.KeepMenuConvert;
import com.keep.sso.entity.KeepMenu;
import com.keep.sso.entity.dto.SearchDto;
import com.keep.sso.entity.vo.KeepMenuVo;
import com.keep.sso.mapper.KeepMenuMapper;
import com.keep.sso.service.KeepMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-03-20
 */
@Service
public class KeepMenuServiceImpl extends ServiceImpl<KeepMenuMapper, KeepMenu> implements KeepMenuService {

    @Override
    public List<KeepMenuVo> listByPage(SearchDto searchDto) {
        List<KeepMenu> list = this.lambdaQuery().list();
        List<KeepMenuVo> vos = KeepMenuConvert.INSTANCE.toKeepMenuVos(list);
        return streamBuildTree(vos);
    }

    public List<KeepMenuVo> streamBuildTree(List<KeepMenuVo> treeList) {
        List<KeepMenuVo> list = treeList.stream()
                // 筛选出父节点
                .filter(t -> t.getParentId() == null)
                // 设置父节点的子节点列表
                .map(item -> {
                    item.setChildren(streamGetChildren(item, treeList));
                    return item;
                })
                .collect(Collectors.toList());
        return list;
    }

    /**
     * stream 方式递归查找子节点列表
     *
     * @return
     */
    public List<KeepMenuVo> streamGetChildren(KeepMenuVo tree, List<KeepMenuVo> treeList) {
        List<KeepMenuVo> list = treeList.stream()
                .filter(t -> Objects.equals(t.getParentId(), tree.getId()))
                .map(item -> {
                    item.setChildren(streamGetChildren(item, treeList));
                    return item;
                })
                .collect(Collectors.toList());
        return list;
    }
}
