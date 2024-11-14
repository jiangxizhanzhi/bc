package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 类帮助 WS 消息标识符
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ClassHelpWsMessageIdentifier extends WsMessageIdentifier {

    /**
     * 课堂id
     */
    private String classroomId;

}
