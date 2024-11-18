package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneRsp implements Serializable {

    /**
     * id
     *
     * @see String
     */
    private String id;

    /**
     * 创建用户
     *
     * @see String
     */
    private String createUser;

    /**
     * 创建用户名
     *
     * @see String
     */
    private String createUsername;

    /**
     * 角色
     *
     * @see List
     */
    private List roles;

    /**
     * 类型
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 参考类型
     *
     * @see Integer
     */
    private Integer referenceType;

    /**
     * 引用元数据
     *
     * @see String
     */
    private String referenceMetaData;

    /**
     * 图标
     *
     * @see String
     */
    private String logo;

    /**
     * 场景配置
     *
     * @see Object
     */
    private Object sceneConfig;

    /**
     * 测试场景
     *
     * @see String
     */
    private String testScenes;

    /**
     * 应用场景
     *
     * @see String
     */
    private String appScenes;
}
