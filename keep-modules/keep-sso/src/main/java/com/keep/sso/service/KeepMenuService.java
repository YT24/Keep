package com.keep.sso.service;

import com.keep.sso.entity.KeepMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.sso.entity.dto.SearchDto;
import com.keep.sso.entity.vo.KeepMenuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-03-20
 */
public interface KeepMenuService extends IService<KeepMenu> {

    List<KeepMenuVo> listByPage(SearchDto searchDto);
}
