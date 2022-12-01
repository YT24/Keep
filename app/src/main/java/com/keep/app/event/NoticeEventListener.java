package com.keep.app.event;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NoticeEventListener implements ApplicationListener<NoticeEvent> {

    @Async
    @Override
    public void onApplicationEvent(NoticeEvent noticeEvent) {
        log.info(Thread.currentThread().getName() + "线程处理事件");
        log.info(JSONObject.toJSONString(noticeEvent));
    }
}
