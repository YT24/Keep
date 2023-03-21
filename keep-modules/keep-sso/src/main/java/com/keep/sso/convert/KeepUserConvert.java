package com.keep.sso.convert;

import com.keep.sso.entity.KeepMenu;
import com.keep.sso.entity.dto.KeepUserSaveDto;
import com.keep.sso.entity.vo.KeepMenuVo;
import com.keep.sso.entity.vo.KeepUserVo;
import com.keep.sso.ticket.entity.KeepUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface KeepUserConvert {

    KeepUserConvert INSTANCE = Mappers.getMapper(KeepUserConvert.class);


    KeepUserVo toKeepUserVo(KeepUser user);

    List<KeepUserVo> toKeepUserVos(List<KeepUser> users);

    KeepUser toKeepUser(KeepUserSaveDto keepUserSaveDto);
}
