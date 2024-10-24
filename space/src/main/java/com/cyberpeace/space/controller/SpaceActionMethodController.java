package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.SpaceActionTargetService;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodBo;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodPageReq;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "知识库-防御行动框架-作战方法管理")
@RestController
@RequestMapping("/space/action_frame/action_method")
@RequiresAuthentication
public class SpaceActionMethodController extends SpaceActionFrameBaseController<SpaceActionMethodService, SpaceActionMethod, SpaceActionMethodBo, SpaceActionMethodPageReq> {

	@Autowired
	private SpaceActionTargetService targetService;

	@Override
	protected void addIdAndName(Map<String, IdAndName> parentMap,
			List<SpaceActionMethod> entityList) {
		List<String> ids = StreamApiUtils.map(entityList, SpaceActionMethod::getTarget);
		if (!ids.isEmpty()) {
			List<SpaceActionTarget> methods = targetService.listByIds(ids);
			this.createIdAndName(methods, parentMap);
		}
	}
}
