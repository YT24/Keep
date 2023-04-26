package com.keep.seckill.service;

import com.keep.seckill.domain.dto.UserSecKillDto;
import com.keep.seckill.domain.entity.TSeckillGoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-04-24
 */
public interface TSeckillGoodsService extends IService<TSeckillGoods> {

    String doKill(UserSecKillDto userSecKillDto) throws Exception;
}
