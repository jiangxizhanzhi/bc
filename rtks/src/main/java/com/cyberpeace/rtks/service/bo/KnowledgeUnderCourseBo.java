package com.cyberpeace.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程 bo 下知识
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class KnowledgeUnderCourseBo implements Serializable {

    /**
     * 相关id
     */
    private String relatedId;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 类型
     */
    private String type;

    /**
     * 重量
     */
    private Integer weight;

}
