package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

import lombok.Data;

/**
 * 红蓝对抗常用装置请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbCommonDeviceReq {
    /**
     * 常用装置上下文列表
     */
    private List<RbCommonDeviceContext> commonDeviceContextList;
    /**
     * 房间id
     */
    private String roomId;
}
