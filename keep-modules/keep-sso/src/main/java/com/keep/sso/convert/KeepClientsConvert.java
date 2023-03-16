package com.keep.sso.convert;

import com.keep.sso.entity.KeepClient;
import com.keep.sso.entity.vo.KeepClientVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface KeepClientsConvert {

    KeepClientsConvert INSTANCE = Mappers.getMapper(KeepClientsConvert.class);


    KeepClientVo toKeepClientsVo(KeepClient keepClient);

    List<KeepClientVo> toKeepClientsVos(List<KeepClient> keepClients);
}
