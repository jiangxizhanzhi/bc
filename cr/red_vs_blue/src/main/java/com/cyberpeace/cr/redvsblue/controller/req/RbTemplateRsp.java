package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;

import lombok.Data;

@Data
public class RbTemplateRsp {

	private BaseInfoRsp baseInfo;

	private String resourceId;

	private List<JSONObject> redRoles;

	private List<JSONObject> blueRoles;
}
