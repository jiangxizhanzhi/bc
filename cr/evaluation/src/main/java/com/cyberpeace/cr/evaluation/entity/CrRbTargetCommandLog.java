package com.cyberpeace.cr.evaluation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 靶标命令执行结果日志(CrRbTargetCommandLog)实体类
 *
 * @author makejava
 * @since 2025-02-10 11:34:31
 */
@Data
public class CrRbTargetCommandLog implements Serializable {
    private static final long serialVersionUID = -12487330158841474L;
    /**
     * 主键
     */
    private Integer pk;
    /**
     * 关联的命令模板id
     */
    private String id;
    /**
     * 场景id
     */
    private String sceneId;
    /**
     * 节点id
     */
    private String nodeId;
    /**
     * 执行人
     */
    private String commandUser;
    /**
     * 执行协议
     */
    private String protocol;
    /**
     * 执行的模板ids
     */
    private String commandTemplates;
    /**
     * 执行的模板参数
     */
    private String commandParameters;
    /**
     * 执行的命令
     */
    private String commandText;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime excutedTime;
    /**
     * 执行状态(0:失败 1:成功)
     */
    private Integer status;
    /**
     * 执行结果
     */
    private String result;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}

