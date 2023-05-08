package com.cyberpeace.bs.service.bo.lc;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 远程监控响应
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcMonitorRsp implements Serializable {

    /**
     * 带宽
     */
    List<Integer> bandwidth = Lists.newArrayList();

    /**
     * 包
     */
    List<Integer> packet = Lists.newArrayList();

    /**
     * 连接请求
     */
    List<Integer> connection = Lists.newArrayList();

    /**
     * 成功建立的连接
     */
    List<Integer> connected = Lists.newArrayList();

    /**
     * 行为步骤执行数
     */
    List<Integer> step = Lists.newArrayList();

    /**
     * 会话数
     */
    List<Integer> session = Lists.newArrayList();
}
