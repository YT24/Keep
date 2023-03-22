package com.keep.sso.convert;

import com.keep.sso.entity.KeepRole;
import com.keep.sso.entity.dto.KeepUserSaveDto;
import com.keep.sso.entity.vo.KeepRoleVo;
import com.keep.sso.entity.vo.KeepUserVo;
import com.keep.sso.ticket.entity.KeepUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface KeepRoleConvert {

    KeepRoleConvert INSTANCE = Mappers.getMapper(KeepRoleConvert.class);


    KeepRoleVo toKeepRoleVo(KeepRole KeepRole);

    List<KeepRoleVo> toKeepRoleVos(List<KeepRole> kepRoles);

}
