package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Getter;
import lombok.Setter;

/**
 * 类帮助 WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
public class ClassHelpWsMessageIdentifier extends WsMessageIdentifier {

    /**
     * 课堂id
     *
     * @see String
     */
    private String classroomId;

}
