package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;
import lombok.Data;

import java.util.List;

@Data
public class TeamCommonDeviceReq {
    private List<TeamCommonDeviceContext> teamCommonDeviceContextList;
    private String roomId;
}
