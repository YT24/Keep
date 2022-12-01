package com.keep.app.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;


@Getter
@ToString
public class NoticeEvent extends ApplicationEvent {

    private String title;

    private String content;

    public NoticeEvent(String title,String content) {
        super(title);
        this.title = title;
        this.content = content;
    }


}

