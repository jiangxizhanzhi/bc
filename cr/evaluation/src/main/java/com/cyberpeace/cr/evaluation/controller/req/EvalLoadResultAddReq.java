package com.cyberpeace.cr.evaluation.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EvalLoadResultAddReq implements Serializable {
    /**
     * 主键
     */
    private Integer pk;
    /**
     * ID
     */
    private String id;
    /**
     * 工具名称
     */
    private String toolName;
    /**
     * 镜像名称
     */
    private String imageName;
    /**
     * 工具描述
     */
    private String toolDescription;
    /**
     * 作用类型
     */
    private String actionType;
    /**
     * 运行类型名称
     */
    private String actionTypeName;
    /**
     * 运行平台
     */
    private String runTable;
    /**
     * 运行平台名称
     */
    private String runTableName;
    /**
     * 控制类型
     */
    private String controlType;
    /**
     * 控制类型名称
     */
    private String controlTypeName;
    /**
     * 控制端口
     */
    private String controlPort;
    /**
     * 来源
     */
    private String source;
    /**
     * 附件
     */
    private String attachment;
    private String attachmentName;
    /**
     * 帮助文档
     */
    private String readMe;
    private String readMeName;
    /**
     * 封面
     */
    private String cover;
    private String coverName;
    /**
     * 默认命令
     */
    private String defaultCmd;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 同步时间
     */
    private LocalDateTime synchronizationTime;
    /**
     * 创建人Id
     */
    private String creatorId;
    /**
     * 漏洞情报
     */
    private List Loopholes;
    /**
     * 镜像类型
     */
    private String imageType;
    /**
     * role
     */
    private Integer role;

    /**
     * 工具实例id
     */
    private String instanceId;

    /**
     * 场景id
     */
    private String sceneId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 结果日志
     */
    private String resultLog;

    /**
     * 结果状态
     */
    private String resultStatus;

    /**
     * 结果类型
     */
    private String resultType;

    /**
     * 阶段id
     */
    private String stageId;

    /**
     * 阶段名称
     */
    private String stageName;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String finishTime;

    /**
     * 结果类型名称
     */
    private String resultTypeName;

    /**
     * 结果状态名称
     */
    private String resultStatusName;

    private String resourceId;
}
