package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.SpaceActionService;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.frame.SpaceActionBo;
import com.cyberpeace.space.service.context.frame.SpaceActionPageReq;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import com.cyberpeace.space.service.context.frame.SpaceActionTool;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "知识库-防御行动框架-攻击、防御活动管理")
@RestController
@RequestMapping("/space/action_frame/action")
@RequiresAuthentication
public class SpaceActionController extends SpaceActionFrameBaseController<SpaceActionService, SpaceAction, SpaceActionBo, SpaceActionPageReq> {

	@Autowired
	private SpaceActionMethodService methodService;

	@Autowired
	private ToolService toolService;

	@Autowired
	private SpaceStrategyService strategyService;


	@Autowired
	private SpaceActionRelationService relationService;

	@ApiOperation("详情")
	@GetMapping("")
	@Override
	public BaseRsp<SpaceActionBo>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		SpaceAction e = baseService.getById(resource_id);
		if (null == e) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SpaceActionBo bo = BeanUtils.copy(e, SpaceActionBo.class);
		List<String> toolIds = JsonUtil.toJsonObjectArray(e.getTool(), String.class);
		List<String> strategyIds = JsonUtil.toJsonObjectArray(e.getStrategy(), String.class);
		List<Tool> toolList = toolService.listByIdsInOrder(toolIds);
		bo.setTool(BeanUtils.deepCopyList(toolList, SpaceActionTool.class));
		bo.setStrategy(this.convert(strategyIds));

		List<String> upperIds = relationService.getUpperIdsByLowerId(e.getResourceId());
		List<SpaceActionMethod> methods = this.getUpperRelated(upperIds);
		List<IdAndName> idAndNames = StreamApiUtils.map(methods, m->new IdAndName(m.getResourceId(), m.getName()));
		bo.setMethod(idAndNames);
		return new BaseRsp<>(bo);
	}

	@Override
	protected List<SpaceActionMethod> getUpperRelated(List<String> upperIds) {
		return methodService.listByIds(upperIds);
	}

	@Override
	protected List<String> getUpperIds(SpaceActionBo req) {
		return StreamApiUtils.map(req.getMethod(), IdAndName::getResourceId);
	}


	private List<SpaceActionStrategy> convert(List<String> ids) {
		List<SpaceStrategy> strategyList = strategyService.listByIds(ids);
		Map<String, SpaceActionStrategy> map = Maps.newHashMap();

		for (SpaceStrategy strategy : strategyList) {
			SpaceActionStrategy bo = new SpaceActionStrategy();
			bo.setResourceId(strategy.getResourceId());
			bo.setName(strategy.getName());
			bo.setScript(strategy.getScript());
			String frameRoute = strategyService.getFrameRouteById(strategy.getFrameId());
			bo.setRoute(frameRoute);

			map.put(bo.getResourceId(), bo);
		}
		List<SpaceActionStrategy> res = Lists.newArrayList();
		for (String id : ids) {
			if (map.containsKey(id)) {
				res.add(map.get(id));
			}
		}
		return res;
	}
}
