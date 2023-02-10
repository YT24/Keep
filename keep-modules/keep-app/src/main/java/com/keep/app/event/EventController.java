package com.keep.app.event;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EventController {


    /**
     * 注入spring 事件发布器
     */
    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "标题",paramType = "query",required = true),
            @ApiImplicitParam(name = "content",value = "内容",paramType = "query",required = true),
    })
    @RequestMapping("/publish")
    public String publish(@RequestParam("title") String title, @RequestParam("content")String content) {
        NoticeEvent event = new NoticeEvent(title, content);
        System.out.println("接口收到请求，内容如下：");
        System.out.println(event);
        eventPublisher.publishEvent(event);
        String ret = "事件发布成功";
        System.out.println(ret);
        return ret;
    }
}
