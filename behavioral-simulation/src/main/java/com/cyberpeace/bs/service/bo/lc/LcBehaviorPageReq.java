package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

/**
 * 远程行为分页请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcBehaviorPageReq implements Serializable {

    /**
     * 分页
     */
    private Integer page;

    /**
     * 页的大小
     */
    private Integer size;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 网络类型
     */
    private Integer networkType;

    /**
     * 名称
     */
    private String name;
}
