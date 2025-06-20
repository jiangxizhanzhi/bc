package com.cyberpeace.oj.practice.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 执业类别父级
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PracticeCategoryParent implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 代号
     *
     * @see String
     */
    private String code;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 创建时间
     *
     * @see Date
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @see Date
     */
    private Date updateTime;

    /**
     * 父母
     *
     * @see PracticeCategoryParent
     */
    private PracticeCategoryParent parent;
}
