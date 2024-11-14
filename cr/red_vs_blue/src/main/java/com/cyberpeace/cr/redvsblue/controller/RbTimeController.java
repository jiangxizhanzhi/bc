package com.cyberpeace.cr.redvsblue.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.redvsblue.controller.req.RbTimeReq;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 红蓝对抗时间Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "红蓝对抗实例授时")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue/time")
public class RbTimeController {

	/**
	 * 红蓝对抗房间Service
	 */
	@Autowired
	private RbRoomService rbRoomService;

	/**
	 * 创建
	 *
	 * @param rbTimeReq 红蓝对抗时间请求体
	 * @return {@code BaseRsp<RbTimeReq> }
	 */
	@ApiOperation("新增/修改")
    @PostMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RbTimeReq> create(@RequestBody @Validated RbTimeReq rbTimeReq) {

		RbRoom room = rbRoomService.getOrExceptionById(rbTimeReq.getRoomId());
		room.setTimeSetting(JsonUtil.toJsonString(rbTimeReq));
		rbRoomService.updateById(room);
		return new BaseRsp<>();
    }

	/**
	 * 获取
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<RbTimeReq> }
	 */
	@ApiOperation("查询")
    @GetMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RbTimeReq>
			get(@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
            value = "resource_id", required = true) String resourceId) {

		RbRoom room = rbRoomService.getOrExceptionById(resourceId);
		String timeJson = room.getTimeSetting();
		if (StringUtils.isBlank(timeJson)) {
			return new BaseRsp<>();
		} else {
			return new BaseRsp<>(JsonUtil.toJsonObject(timeJson, RbTimeReq.class));
		}
    }

}
