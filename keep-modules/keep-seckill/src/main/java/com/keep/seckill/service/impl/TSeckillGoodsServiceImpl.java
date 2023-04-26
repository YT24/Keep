package com.keep.seckill.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.keep.seckill.domain.contants.GloableConstants;
import com.keep.seckill.domain.entity.TGoods;
import com.keep.seckill.domain.entity.TSeckillOrder;
import com.keep.seckill.domain.enums.OrderStatusEnum;
import com.keep.seckill.domain.SecKillOrder;
import com.keep.seckill.domain.dto.UserSecKillDto;
import com.keep.seckill.domain.entity.TOrder;
import com.keep.seckill.domain.entity.TSeckillGoods;
import com.keep.seckill.mapper.TSeckillGoodsMapper;
import com.keep.seckill.service.TGoodsService;
import com.keep.seckill.service.TOrderService;
import com.keep.seckill.service.TSeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.seckill.service.TSeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-04-24
 */
@Slf4j
@Service
public class TSeckillGoodsServiceImpl extends ServiceImpl<TSeckillGoodsMapper, TSeckillGoods> implements TSeckillGoodsService, InitializingBean, ApplicationListener<SecKillOrder> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private TSeckillOrderService tSeckillOrderService;


    @Autowired
    private TGoodsService tGoodsService;

    @Autowired
    private TOrderService tOrderService;


    @Override
    public String doKill(UserSecKillDto userSecKillDto){

        Object goods = redisTemplate.opsForValue().get(GloableConstants.PREF + userSecKillDto.getGoodsId());
        if(goods == null){
            return "秒杀商品不存在";
        }

        // 判断是否重复下单
        Object secKillOrder = redisTemplate.opsForValue().get(GloableConstants.ORDER + userSecKillDto.getGoodsId() + ":" + userSecKillDto.getUserId());
        if(secKillOrder != null){
            return "您已下单，不允许重复下单";
        }

        // 库存扣减
        Long decrement = redisTemplate.opsForValue().decrement(GloableConstants.PREF + userSecKillDto.getGoodsId());
        if(decrement.intValue() < 0){
            redisTemplate.opsForValue().increment(GloableConstants.PREF + userSecKillDto.getGoodsId());
            return "商品已售空";
        }

        // 存储当前用户下单信息
        SecKillOrder order = new SecKillOrder(userSecKillDto.getUserId(),userSecKillDto.getGoodsId());
        redisTemplate.opsForValue().set(GloableConstants.ORDER + userSecKillDto.getGoodsId() + ":" + userSecKillDto.getUserId(),order);

        // 发送mq 创建订单（使用发送事件模拟）
        applicationEventPublisher.publishEvent(order);


        return "秒杀成功";

    }


    @Transactional
    @Override
    public void onApplicationEvent(SecKillOrder secKillOrder) {
        // 判断库存是否充足
        TSeckillGoods seckillGoods = this.getById(secKillOrder.getGoodsId());
        if(seckillGoods.getStockCount() <= 0){
            return;
        }

        // 扣减库存
        // 减库存时判断库存是否足够
        boolean res = this.update(new UpdateWrapper<TSeckillGoods>()
                .setSql("stock_count = " + "stock_count-1")
                .eq("goods_id", secKillOrder.getGoodsId())
                .gt("stock_count", 0));

        // 生成订单
        if(res){
            TSeckillGoods goods = this.getById(secKillOrder.getGoodsId());
            if(goods == null){
                log.debug("秒杀ID商品不存在：{}",secKillOrder.getGoodsId());
                return;
            }
            TGoods tGoods = this.tGoodsService.getById(goods.getGoodsId());
            if(tGoods == null){
                log.debug("商品不存在：{}",secKillOrder.getGoodsId());
                return;
            }

            TOrder order = new TOrder();
            order.setGoodsId(secKillOrder.getGoodsId());
            order.setUserId(secKillOrder.getUserId());
            order.setStatus(OrderStatusEnum.UN_PAY.getStatus());
            order.setGoodsName(tGoods.getGoodsName());
            order.setGoodsPrice(goods.getSeckillPrice());
            order.setGoodsId(tGoods.getId());
            order.setCreateDate(LocalDateTime.now());
            this.tOrderService.save(order);

            TSeckillOrder tOrder = new TSeckillOrder();
            tOrder.setGoodsId(secKillOrder.getGoodsId());
            tOrder.setUserId(secKillOrder.getUserId());
            tOrder.setOrderId(order.getId());
            tSeckillOrderService.save(tOrder);
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("进入afterPropertiesSet方法中。。。。。");
        List<TSeckillGoods> list = this.lambdaQuery().select(TSeckillGoods::getGoodsId, TSeckillGoods::getStockCount).list();
        if(CollUtil.isEmpty(list)){
            return;
        }
        list.forEach(tSeckillGoods -> {
            redisTemplate.opsForValue().set(GloableConstants.PREF + tSeckillGoods.getGoodsId(),tSeckillGoods.getStockCount(),GloableConstants.EXPIRED, TimeUnit.SECONDS);
            log.debug("{} : {}",GloableConstants.PREF + tSeckillGoods.getGoodsId(),redisTemplate.opsForValue().get(GloableConstants.PREF + tSeckillGoods.getGoodsId()));
        });
    }
}
