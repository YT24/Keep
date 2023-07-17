package com.keep.sso.convert;

import com.keep.sso.entity.KeepClient;
import com.keep.sso.entity.vo.KeepClientVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface KeepClientsConvert {

    KeepClientsConvert INSTANCE = Mappers.getMapper(KeepClientsConvert.class);


    KeepClientVo toKeepClientsVo(KeepClient keepClient);

    List<KeepClientVo> toKeepClientsVos(List<KeepClient> keepClients);


    KeepClientVo toKeepClientsVo(Map keepClient);
}
