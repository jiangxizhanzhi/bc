package com.cyberpeace.scene.service.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/28 9:46
 * @desc :
 **/
@Data
public class ContainerItem implements Serializable {

    private Integer id;

    /**
     * 容器唯一标识
     */
    private String uuid;

    /**
     * 容器id
     */
    private String containerId;

    /**
     * 容器名称
     */
    private String name;

    /**
     * 容器镜像
     */
    private String image;

    /**
     * 容器CPU
     */
    private Integer cpu;

    /**
     * 容器内存
     */
    private Integer memory;

    /**
     * 容器磁盘
     */
    private Integer disk;

    /**
     * 容器命令
     */
    private List<String> command;

    /**
     * 容器状态
     */
    private String status;

    /**
     * 容器状态详情
     */
    private String statusDetail;

    /**
     * 容器任务状态
     */
    private String taskState;

    /**
     * 容器工作目录
     */
    private String workdir;

    /**
     * 端口
     */
    private List<String> ports;

    /**
     * 主机名
     */
    private String hostname;

    /**
     * 地址信息
     */
    private JSONObject addresses;

    /**
     * 主机
     */
    private String host;

    /**
     * 重启策略
     */
    private String restartPolicy;

    /**
     * 是否交互式
     */
    private boolean interactive;

    /**
     * 是否tty
     */
    private boolean tty;

    /**
     * 是否自动删除
     */
    private boolean autoRemove;

    /**
     * 运行时长
     */
    private String runTime;

    /**
     * 启动时间
     */
    private Date startedAt;

    /**
     * 是否特权
     */
    private boolean privileged;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
