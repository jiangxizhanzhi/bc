package com.cyberpeace.cr.redvsblue.controller.req;

import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;
import lombok.Data;

import java.util.List;

@Data
public class RbCommonDeviceReq {
    private List<RbCommonDeviceContext> commonDeviceContextList;
    private String roomId;
}
