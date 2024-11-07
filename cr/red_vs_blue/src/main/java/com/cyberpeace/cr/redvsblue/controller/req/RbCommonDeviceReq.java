package com.cyberpeace.cr.redvsblue.controller.req;

import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;
import lombok.Data;

import java.util.List;

/**
 * RB常见装置请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbCommonDeviceReq {
    /**
     * 常见设备上下文列表
     */
    private List<RbCommonDeviceContext> commonDeviceContextList;
    /**
     * 房间id
     */
    private String roomId;
}
