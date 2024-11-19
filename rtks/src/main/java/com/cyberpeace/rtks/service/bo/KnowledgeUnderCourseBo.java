package com.cyberpeace.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 知识点下课程Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class KnowledgeUnderCourseBo implements Serializable {

    /**
     * 相关id
     *
     * @see String
     */
    private String relatedId;

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 类型
     *
     * @see String
     */
    private String type;

    /**
     * 权重
     *
     * @see Integer
     */
    private Integer weight;

}
