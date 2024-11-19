package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

import lombok.Data;

/**
 * 课程相关
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */

@Data
@TableName("rtks_course_related")
public class CourseRelated {

    /**
     * 相关类型知识点
     *
     * @see Integer
     */
    public final static Integer RELATED_TYPE_KNOWLEDGE = 1;

    /**
     * 相关类型技能
     *
     * @see Integer
     */
    public final static Integer RELATED_TYPE_SKILL = 2;

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 相关id
     *
     * @see String
     */
    private String relatedId;

    /**
     * 重量
     *
     * @see Integer
     */
    private Integer weight = CourseInfoBo.DEFAULT_WEIGHT;

    /**
     * 类型
     *
     * @see Integer
     */
    private Integer type;
}
