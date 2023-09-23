package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

import lombok.Data;

@Data
public class RbCommonDeviceReq {
    private List<RbCommonDeviceContext> commonDeviceContextList;
    private String roomId;
}
