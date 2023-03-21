package com.keep.sso.convert;

import com.keep.sso.entity.KeepClient;
import com.keep.sso.entity.KeepMenu;
import com.keep.sso.entity.vo.KeepClientVo;
import com.keep.sso.entity.vo.KeepMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface KeepMenuConvert {

    KeepMenuConvert INSTANCE = Mappers.getMapper(KeepMenuConvert.class);


    KeepMenuVo toKeepMenuVo(KeepMenu keepMenu);

    List<KeepMenuVo> toKeepMenuVos(List<KeepMenu> keepMenu);
}
