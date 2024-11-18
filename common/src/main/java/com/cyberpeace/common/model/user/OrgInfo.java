package com.cyberpeace.common.model.user;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import lombok.Data;

/**
 * 组织信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class OrgInfo implements Serializable {

    /**
     * 组织id，不要使用自增数字编号（必须）
     *
     * @see String
     */
    private String id;

    /**
     * 组织名
     *
     * @see String
     */
    private String name;

    /**
     * 子级组织
     *
     * @see List<OrgInfo>
     */
    private List<OrgInfo> children = Lists.newArrayList();

    /**
     * 父级组织id
     *
     * @see String
     */
    private String parent;

    /**
     * 组织全程
     *
     * @see String
     */
    private String path;
}
