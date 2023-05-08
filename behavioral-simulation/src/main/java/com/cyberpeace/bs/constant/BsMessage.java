package com.cyberpeace.bs.constant;

/**
 * 用户行为模拟错误信息
 *
 * @author SN
 */
public interface BsMessage {

    /**
     * 未知设备
     */
    String UNKNOWN_DEVICE = "检测到未知实体设备 %s，请联系管理员同步设备端口信息";

    /**
     * 设备不足
     */
    String DEVICE_INSUFFICIENT = "%s 设备不足，需要 %d，现有 %d 空闲";

    /**
     * 无可用设备
     */
    String NO_DEVICES = "当前没有可用实体设备";
}
