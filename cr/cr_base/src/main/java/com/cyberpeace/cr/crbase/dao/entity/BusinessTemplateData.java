package com.cyberpeace.cr.crbase.dao.entity;

import lombok.Data;

/**
 * 靶场模板公共信息类
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class BusinessTemplateData extends BusinessTemplate {

    /**
     * 节点数量
     */
    private Integer nodesNum;

    /**
     * 资源占用数
     */
    private Integer resourceOccupiedNums;

    /**
     * 总CPU
     */
    private Integer totalCpu;

    /**
     * 总磁盘
     */
    private Integer totalDisk;

    /**
     * 总内存
     */
    private Integer totalRam;

}
