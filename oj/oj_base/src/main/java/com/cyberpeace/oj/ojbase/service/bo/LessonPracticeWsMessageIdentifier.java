package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 课程练习 WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonPracticeWsMessageIdentifier extends WsMessageIdentifier {

    /**
     * 课id
     *
     * @see String
     */
    private String lessonId;

    /**
     * 实践id
     *
     * @see String
     */
    private String practiceId;

    /**
     * 从 Web
     *
     * @see Boolean
     */
    private Boolean fromWeb;

    /**
     * 共享
     *
     * @see Integer
     */
    private Integer shared;

}
