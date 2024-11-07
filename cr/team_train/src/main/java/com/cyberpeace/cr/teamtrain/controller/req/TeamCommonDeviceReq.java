package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;
import lombok.Data;

import java.util.List;

/**
 * 团队常见装置请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TeamCommonDeviceReq {
    /**
     * 团队通用设备上下文列表
     */
    private List<TeamCommonDeviceContext> teamCommonDeviceContextList;
    /**
     * 房间id
     */
    private String roomId;
}
