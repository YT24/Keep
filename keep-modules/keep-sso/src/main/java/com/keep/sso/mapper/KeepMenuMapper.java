package com.keep.sso.mapper;

import com.keep.sso.entity.KeepMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.keep.sso.entity.vo.KeepMenuOperationVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author system
 * @since 2023-03-20
 */
public interface KeepMenuMapper extends BaseMapper<KeepMenu> {

    List<KeepMenuOperationVo> listAll();
}
