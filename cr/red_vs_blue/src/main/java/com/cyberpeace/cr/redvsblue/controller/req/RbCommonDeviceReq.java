package com.cyberpeace.cr.redvsblue.controller.req;

import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;
import lombok.Data;

import java.util.List;

/**
 * RB常见装置请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RbCommonDeviceReq {
    /**
     * 常见设备上下文列表
     *
     * @see List<RbCommonDeviceContext>
     */
    private List<RbCommonDeviceContext> commonDeviceContextList;
    /**
     * 房间id
     *
     * @see String
     */
    private String roomId;
}
