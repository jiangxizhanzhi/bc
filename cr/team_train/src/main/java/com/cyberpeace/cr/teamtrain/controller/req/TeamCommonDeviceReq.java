package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;
import lombok.Data;

import java.util.List;

/**
 * 团队常见装置请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TeamCommonDeviceReq {
    /**
     * 团队通用设备上下文列表
     *
     * @see List<TeamCommonDeviceContext>
     */
    private List<TeamCommonDeviceContext> teamCommonDeviceContextList;
    /**
     * 房间id
     *
     * @see String
     */
    private String roomId;
}
