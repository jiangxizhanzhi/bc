package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFramePageReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 空间动作帧控制器
 *
 * @author SN
 * @date 2024/11/05
 */
@Api(tags = "防御行动框架-行动框架管理")
@RestController
@RequestMapping("/space/action_frame/action_frame")
@RequiresAuthentication
public class SpaceActionFrameController extends SpaceActionFrameBaseController<SpaceActionFrameService, SpaceActionFrame, SpaceActionFrameBo, SpaceActionFramePageReq> {

	/**
	 * Service
	 */
	@Autowired
	private SpaceActionFrameService service;

	@Autowired
	private SpaceActionFrameApplicationService applicationService;

	/**
	 * 获取框架
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<SpaceActionFrameLevel> }
	 */
	@ApiOperation("获取框架")
    @GetMapping("/frame")
	public BaseRsp<ActionFrameArrangeBo>
			getFrame(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		ActionFrameArrangeBo res = service.getFrame(resource_id);
		return new BaseRsp<>(res);
    }

	/**
	 * 保存帧
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("保存框架")
	@PostMapping("/frame")
	public BaseRsp saveFrame(@RequestBody ActionFrameArrangeBo req) {
		service.saveFrame(req);
		return new BaseRsp<>();
	}

}
