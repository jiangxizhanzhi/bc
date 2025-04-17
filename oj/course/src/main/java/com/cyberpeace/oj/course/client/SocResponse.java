package com.cyberpeace.oj.course.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocResponse<T> {
    /**
     * 消息流水 与请求参数相同
     */
    private String messageSn;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应结果数据
     */
    private T result;
}
