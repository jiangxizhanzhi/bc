package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 远程实体设备请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcActualDeviceReq implements Serializable {
    /**
     * 设备类型列表
     */
    private List<Integer> deviceTypes = Lists.newArrayList();
}
