package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 练习类别父母
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PracticeCategoryParent implements Serializable {
    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 代号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 父母
     */
    private PracticeCategoryParent parent;
}
