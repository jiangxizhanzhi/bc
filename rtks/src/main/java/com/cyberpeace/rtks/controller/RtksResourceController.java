package com.cyberpeace.rtks.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.rtks.service.RtksResourceService;
import com.cyberpeace.rtks.service.bo.resource.RtksResourceReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * rtks资源导入相关接口
 *
 * @author SN
 * @date 2023/05/31
 */

@RestController
@RequestMapping("/rtks/resource")
@Api(tags = "RTKS-资源导入")
// @RequiresAuthentication
public class RtksResourceController {

	/**
	 * RTKS资源Service
	 */
	@Autowired
	private RtksResourceService rtksResourceService;

	/**
	 * 导入岗位资源
	 * 导入的岗位资源视作初始资源的一部分
	 * 需要写入rtks_role和init_rtks_role
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("导入岗位")
	@PostMapping("/role")
	// @RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp loadRoles(@Valid @RequestBody RtksResourceReq req) {
		rtksResourceService.load(req);
		return new BaseRsp();
	}
}
