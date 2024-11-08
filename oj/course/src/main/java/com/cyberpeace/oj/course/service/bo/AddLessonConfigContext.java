package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增课配置上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLessonConfigContext implements Serializable {

    /**
     * 节点类型
     */
    private Integer nodeType;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 课id
     */
    private String lessonId;

    /**
     * 名称
     */
    private String name;

}
