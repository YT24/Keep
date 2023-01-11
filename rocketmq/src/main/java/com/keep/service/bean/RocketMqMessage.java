package com.keep.service.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yangte
 * @description : 消息
 * @date: 2020-08-20 11:03
 */

@Data
public class RocketMqMessage<T> implements Serializable {
    /**
     * 消息内容
     */
    private T content;


    /**
     * 消息的key
     */
    private String msgKey;

    /**
     * topic
     */
    private String producerTopic;
    /**
     * group
     */
    private String producerGroup;
    /**
     * tag
     */
    private String producerTag;
}
