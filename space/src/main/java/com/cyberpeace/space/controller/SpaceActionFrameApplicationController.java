package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameReleaseService;
import com.cyberpeace.space.service.context.frame.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "知识库-算法库-框架应用")
@RestController
@RequestMapping("/space/action_frame/application")
@RequiresAuthentication
@Slf4j
public class SpaceActionFrameApplicationController extends SpaceControllerBase<SpaceActionFrameApplicationService, SpaceActionFrameApplication, SpaceActionFrameApplicationBo, SpaceActionFrameApplicationListReq> {

	@Autowired
	private SpaceActionFrameReleaseService releaseService;

	/**
	 * 应用框架
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("应用框架")
	@PostMapping("/")
	public BaseRsp applyFrame(@RequestBody SpaceActionFrameApplicationReq req) {
		baseService.create(req);
		return new BaseRsp<>();
	}

	@ApiOperation("更新")
	@PatchMapping("")
    @Override
	public BaseRsp updateA(@RequestBody SpaceActionFrameApplicationBo req) {
		if (StringUtils.isBlank(req.getResourceId())) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		// only update frame
		SpaceActionFrameApplication entity = baseService.getById(req.getResourceId());
		entity.setFrame(JsonUtil.toJsonString(req.getFrame()));
		baseService.updateById(entity);
		return new BaseRsp();
	}

	/**
	 * 查询下发情况
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("查询下发情况")
	@GetMapping("/release")
	public BaseRsp<List<SpaceActionFrameReleaseBo>> applyFrame(@ApiParam(value = "框架应用id", name = "application_id") String application_id) {
		List<SpaceActionFrameReleaseBo> res = releaseService.listBo(application_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 下发步骤
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("下发步骤")
	@PostMapping("/release")
	public BaseRsp applyFrame(@RequestBody SpaceActionFrameReleaseReq req) {
		releaseService.create(req);
		return new BaseRsp<>();
	}
}
