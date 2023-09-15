package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

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
	private List<Integer> bandwidth = Lists.newArrayList();

    /**
     * 包
     */
	private List<Integer> packet = Lists.newArrayList();

    /**
     * 连接请求
     */
	private List<Integer> connection = Lists.newArrayList();

    /**
     * 成功建立的连接
     */
	private List<Integer> connected = Lists.newArrayList();

    /**
     * 行为步骤执行数
     */
	private List<Integer> step = Lists.newArrayList();

    /**
     * 会话数
     */
	private List<Integer> session = Lists.newArrayList();
}
