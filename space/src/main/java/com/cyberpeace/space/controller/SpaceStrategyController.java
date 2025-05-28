package com.cyberpeace.space.controller;

import com.cyberpeace.common.annoations.CallCount;
import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.model.req.IdReq;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.strategy.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 空间策略Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Api(tags = "知识空间-知识库-策略库")
@RestController
@RequestMapping("/space/strategy")
@Slf4j
public class SpaceStrategyController {

	/**
	 * 空间策略Service
	 *
	 * @see SpaceStrategyService
	 */
	@Autowired
	private SpaceStrategyService spaceStrategyService;

	/**
	 * 获取框架
	 *
	 * @return 基础RSP<列表 < 空间策略框架博>>
	 * @throws
	 * @see BaseRsp<List<SpaceStrategyFrameBo>>
	 */
	@ApiOperation("获取框架")
	@GetMapping("/frame")
	public BaseRsp<List<SpaceStrategyFrameBo>> getFrame() {
		SpaceStrategyFrameBo bo = spaceStrategyService.getFrame();
		return new BaseRsp<>(bo.getChildren());
	}

	/**
	 * 新增框架
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see SpaceStrategyFrameAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("增加下一级")
	@PostMapping("/frame")
	public BaseRsp addFrame(@RequestBody SpaceStrategyFrameAddReq req) {
		spaceStrategyService.addFrame(req);
		return new BaseRsp();
	}

	/**
	 * 修改框架
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see SpaceStrategyFrameModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("编辑框架")
	@PatchMapping("/frame")
	public BaseRsp modifyFrame(@RequestBody SpaceStrategyFrameModifyReq req) {
		spaceStrategyService.modify(req);
		return new BaseRsp();
	}

	/**
	 * 删除框架
	 *
	 * @param idReq id请求体
	 * @return 基础响应
	 * @throws
	 * @see IdReq
	 * @see BaseRsp
	 */
	@ApiOperation("删除框架")
	@DeleteMapping("/frame")
	public BaseRsp deleteFrame(@RequestBody IdReq idReq) {
		spaceStrategyService.deleteFrame(idReq.getResourceId());
		return new BaseRsp();
	}

	/**
	 * 获取策略
	 *
	 * @param frame_id 框架id
	 * @param name     名称
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see BaseRsp<List<SpaceStrategySimple>>
	 */
	@ApiOperation("获取策略列表")
	@GetMapping("/list")
	public BaseRsp<List<SpaceStrategySimple>> getStrategies(
			@ApiParam(value = "框架id", name = "frame_id") String frame_id,
			@ApiParam(value = "名称", name = "name") String name) {
		List<SpaceStrategySimple> res = spaceStrategyService.listStrategiesByFrame(frame_id, name);
		return new BaseRsp<>(res);
	}

	/**
	 * 分页策略
	 *
	 * @param req      请求体
	 * @param frame_id 框架id
	 * @param name     名称
	 * @return base rsp< list rsp< Space Strategy bo>>
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see String
	 * @see BaseRsp<ListRsp<SpaceStrategyBo>>
	 */
	@ApiOperation("策略列表分页")
	@GetMapping("/page")
	public BaseRsp<ListRsp<SpaceStrategyBo>> pageStrategies(
			PageReqBase req,
			@ApiParam(value = "框架id", name = "frame_id") String frame_id,
			@ApiParam(value = "名称", name = "name") String name) {
		ListRsp<SpaceStrategyBo> res = spaceStrategyService.pageStrategiesByFrame(req, frame_id, name);
		return new BaseRsp<>(res);
	}

	/**
	 * 新增策略
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see SpaceStrategyAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("新建策略")
	@PostMapping("")
	public BaseRsp addStrategy(@RequestBody SpaceStrategyAddReq req) {
		spaceStrategyService.addStrategy(req);
		return new BaseRsp();
	}

	/**
	 * 更新策略
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see SpaceStrategyModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("修改策略")
	@PatchMapping("")
	public BaseRsp updateStrategy(@RequestBody SpaceStrategyModifyReq req) {
		SpaceStrategy old = spaceStrategyService.getOrException(req.getResourceId());
		if(Boolean.TRUE.equals(old.getBuiltin())){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		spaceStrategyService.updateStrategy(req);
		return new BaseRsp();
	}

	/**
	 * 删除策略
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see IdListReq
	 * @see BaseRsp
	 */
	@ApiOperation("删除策略")
	@DeleteMapping("")
	public BaseRsp deleteStrategy(@RequestBody IdListReq req) {
		spaceStrategyService.deleteStrategy(req.getIds());
		return new BaseRsp();
	}

	/**
	 * 获取详情
	 *
	 * @param resource_id 资源id
	 * @return Base RSP< Space Strategy BO>
	 * @throws
	 * @see String
	 * @see BaseRsp<SpaceStrategyBo>
	 */
	@ApiOperation("查看策略详情")
	@GetMapping("")
	@CallCount(CallType.STRATEGY)
	public BaseRsp<SpaceStrategyBo>
			getDetail(@ApiParam(value = "策略id", name = "resource_id") String resource_id) {
		SpaceStrategyBo res = spaceStrategyService.getDetail(resource_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取路线
	 *
	 * @param strategy_id 策略id
	 * @param frame_id    框架id
	 * @return base rsp< 字符串>
	 * @throws
	 * @see String
	 * @see String
	 * @see BaseRsp<String>
	 */
	@ApiOperation("查看策略路径")
	@GetMapping("/route")
	public BaseRsp<String> getRoute(
			@ApiParam(value = "策略id", name = "strategy_id") String strategy_id, @ApiParam(value = "框架id", name = "frame_id") String frame_id) {
		if (StringUtils.isNotBlank(frame_id)) {
			String res = spaceStrategyService.getFrameRouteById(frame_id);
			return new BaseRsp<>(res);
		}
		SpaceStrategy strategy = spaceStrategyService.getById(strategy_id);
		return new BaseRsp<>(spaceStrategyService.getFrameRouteById(strategy.getFrameId()));
	}

	/**
	 * 导出策略2PDF格式
	 *
	 * @param httpServletResponse HTTP Servlet 响应
	 * @param strategy_ids        策略id列表
	 * @throws
	 * @see HttpServletResponse
	 * @see List<String>
	 */
	@ApiOperation("导出策略")
	@GetMapping("/export")
	public void exportStrategy2PDF(
			HttpServletResponse httpServletResponse,
			@RequestParam List<String> strategy_ids) {
		FileUtil.setResponse(new File(spaceStrategyService.dumpStrategyPath(strategy_ids)), false, httpServletResponse);
	}
}
