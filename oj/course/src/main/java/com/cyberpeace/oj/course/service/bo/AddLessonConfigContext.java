package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 新增课配置上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLessonConfigContext implements Serializable {

    /**
     * 节点类型
     *
     * @see Integer
     */
    private Integer nodeType;

    /**
     * 课程id
     *
     * @see String
     */
    private String courseId;

    /**
     * 课id
     *
     * @see String
     */
    private String lessonId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

}
