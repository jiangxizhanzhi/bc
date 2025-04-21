package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.IdReq;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameOrderService;
import com.cyberpeace.space.service.SpaceActionFrameReleaseService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "防御行动框架-框架应用")
@RestController
@RequestMapping("/space/action_frame/application")
@RequiresAuthentication
@Slf4j
public class SpaceActionFrameApplicationController extends SpaceControllerBase<SpaceActionFrameApplicationService, SpaceActionFrameApplication, SpaceActionFrameApplicationBo, SpaceActionFrameApplicationListReq> {

	@Autowired
	private SpaceActionFrameReleaseService releaseService;

	@Autowired
	private SpaceActionFrameService frameService;

	@Autowired
	private SpaceActionFrameOrderService orderService;

	/**
	 * 搜索框架
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("指挥员搜索框架")
	@GetMapping("/search")
	public BaseRsp<List<SpaceActionFrameBo>> searchFrame(@ApiParam(name = "req") String req) {
		if (StringUtils.isBlank(req)) {
			return new BaseRsp<>(Lists.newArrayList());
		}
		List<SpaceActionFrameBo> res = frameService.searchFrame(req);
		return new BaseRsp<>(res);
	}

	/**
	 * 应用框架
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("指挥员应用框架")
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
		//  can only update frame
		SpaceActionFrameApplication entity = baseService.getById(req.getResourceId());
		entity.setFrame(JsonUtil.toJsonString(req.getFrame()));
		baseService.updateById(entity);
		return new BaseRsp<>();
	}

	/**
	 * 查询下发情况
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("指挥员查询下发情况")
	@GetMapping("/release")
	public BaseRsp<List<SpaceActionFrameReleaseBo>> listRelease(@ApiParam(value = "框架应用id", name = "application_id") String application_id) {
		List<SpaceActionFrameReleaseBo> res = releaseService.listBo(application_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 下发步骤
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("指挥员下发步骤")
	@PostMapping("/release")
	public BaseRsp releaseStep(@RequestBody SpaceActionFrameReleaseReq req) {
		releaseService.create(req);
		return new BaseRsp<>();
	}


	/**
	 * 中位线查询分配到的步骤
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("中位线查询分配到的步骤")
	@GetMapping("/step")
	public BaseRsp<List<SpaceActionFrameOrderBo>> mySteps(
			@ApiParam(value = "红蓝对抗id", name = "rvb_id") String rvb_id,
			@ApiParam(value = "用户id, 不传默认当前用户", name = "user_id") String user_id) {
		List<SpaceActionFrameOrderBo> res = releaseService.getMySteps(rvb_id, user_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 中位线下发战法指令
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("中位线下发战法指令")
	@PostMapping("/order")
	public BaseRsp releaseOrder(@RequestBody SpaceActionFrameOrderReq req) {
		orderService.create(req);
		return new BaseRsp<>();
	}

	/**
	 * 工位线查询分配到的战法指令
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("工位线查询分配到的战法指令")
	@GetMapping("/order")
	public BaseRsp<ListRsp<SpaceActionFrameOrderBo>> myOrders(SpaceActionFrameOrderListReq req) {
		ListRsp<SpaceActionFrameOrderBo> res = orderService.pageMyOrders(req);
		return new BaseRsp<>(res);
	}

	/**
	 * 工位线确认完成战法指令
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("工位线确认完成战法指令")
	@PostMapping("/order/complete")
	public BaseRsp completeOrder(@RequestBody IdReq req) {
		orderService.complete(req.getResourceId());
		return new BaseRsp<>();
	}
}
