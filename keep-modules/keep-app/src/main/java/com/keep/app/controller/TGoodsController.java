package com.keep.app.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.executor.CronExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author system
 * @since 2023-04-24
 */
@Slf4j
@RestController
@RequestMapping("/tGoods")
@RequiredArgsConstructor
public class TGoodsController {

    public ResponseResult secKill() {
        return ResponseResult.success();
    }

    public static void main(String[] args) throws Exception {
        String hour = "0 0 8-10 * * ?";
        String minute = "0 15-30 * ? * *";
        String second = "15-30 * * ? * *";
        CronExpression cron = null;
        try {
            cron = new CronExpression(second);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nextRunDate = simpleDateFormat.parse("2023-09-01 19:19:31");
        System.out.println(nextRunDate.getHours());
        System.out.println(nextRunDate.getMinutes());
        System.out.println(nextRunDate.getSeconds());
        boolean satisfiedBy = cron.isSatisfiedBy(nextRunDate);
        System.out.println(satisfiedBy);
        log.info("4567890");
    }
}

