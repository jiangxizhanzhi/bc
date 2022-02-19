package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;

import lombok.Data;

@Data
public class HldTopoAssetsRsp {

	private JSONObject sceneData;

	private List<CrRoleConfig> redRoles = Lists.newArrayList();

	private List<CrRoleConfig> blueRole = Lists.newArrayList();

}
