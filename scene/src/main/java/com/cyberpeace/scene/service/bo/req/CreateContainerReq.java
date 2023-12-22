package com.cyberpeace.scene.service.bo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cyberpeace.scene.service.bo.Net;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/21 19:56
 * @desc :
 **/
@Data
public class CreateContainerReq implements Serializable {

    /**
     * 容器名称
     */
    private String name;

    /**
     * 容器镜像id
     */
    private String image;

    /**
     * 容器启动命令
     */
    private String command;

    /**
     * 容器cpu
     */
    private Integer cpu;

    /**
     * 容器内存
     */
    private Integer memory;

    /**
     * 容器磁盘大小
     */
    private Integer disk;

    /**
     * 容器重启策略
     */
    @TableField(value = "restart_policy")
    private String restartPolicy;

    /**
     * 是否交互式
     */
    private boolean interactive;

    /**
     * 容器工作目录
     */
    private String workdir;

    /**
     * 容器是否支持tty
     */
    private boolean tty;

    /**
     * 容器网络配置
     */
    private List<Net> nets;

    /**
     * 容器运行时间
     */
    private String runTime;

    /**
     * 容器主机名
     */
    private String hostname;

    /**
     * 容器是否自动重启
     */
    @TableField(value = "auto_heal")
    private boolean autoHeal;

    /**
     * 容器是否支持特权模式
     */
    private boolean privileged;

}
