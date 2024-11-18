package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色任务编排阶段模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CrRoleMissionSchemeStage implements Serializable {

    /**
     * 阶段
     *
     * @see String
     */
    private String stage;

    /**
     * 任务
     *
     * @see List<String>
     */
    private List<String> missions;

    /**
     * 动态
     *
     * @see List<String>
     */
    private List<String> dynamic;
}
