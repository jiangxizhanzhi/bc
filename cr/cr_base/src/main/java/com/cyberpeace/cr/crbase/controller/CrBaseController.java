package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.CurrentTimeRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 靶场基础Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "靶场公用接口")
@RestController
@Slf4j
@RequestMapping("/cr/common")
//@RequiresAuthentication
public class CrBaseController {

	/**
	 * 靶场常数Service
	 */
	@Autowired
	private CrConstantsService crConstantsService;

	/**
	 * 获取常数配置
	 *
	 * @param type 类型
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("获取常量配置")
	@GetMapping("/constants_config")
	public BaseRsp getConstantsConfig(@ApiParam(name = "type", value = "类型") Integer type) {
		List<CrConstantsConfig> constants = crConstantsService.getByType(type);
		// 任务类型
		if (type.equals(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode()) ||
				type.equals(CrConstantsConfig.ConstantsType.TOOL_TYPE.getCode())
				|| type.equals(CrConstantsConfig.ConstantsType.ALGORITHM_APPLY_TYPE.getCode())) {
			List<MissionCategoryRsp> rsp = StreamApiUtils.map(constants,
					constant -> new MissionCategoryRsp(constant.getResourceId(),
							constant.selectI18nName()));
			return new BaseRsp(rsp);
		} else {
			List<CodeNameRsp> rsp = StreamApiUtils.map(constants,
					constant -> new CodeNameRsp(constant.getCode(), constant.selectI18nName()));
			return new BaseRsp(rsp);
		}
	}

	/**
	 * 获取当前时间
	 *
	 * @return {@code BaseRsp<CurrentTimeRsp> }
	 */
	@ApiOperation("获取当前服务器时间")
	@GetMapping("/current_time")
	@RequiresPermissions(
			value = { MenuConstant.CMS_CLASS_ROOM, MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
					MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN },
			logical = Logical.OR)
	public BaseRsp<CurrentTimeRsp> getCurrentTime() {
		CurrentTimeRsp rsp = new CurrentTimeRsp();
		rsp.setCurrentTime(new Date());
		return new BaseRsp<>(rsp);
	}

}
