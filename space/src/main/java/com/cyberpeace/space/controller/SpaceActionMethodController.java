package com.cyberpeace.space.controller;

import com.cyberpeace.space.dao.entity.SpaceActionMethod;
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

@Api(tags = "知识库-防御行动框架-作战方法管理")
@RestController
@RequestMapping("/space/action_frame/action_method")
@RequiresAuthentication
public class SpaceActionMethodController extends SpaceActionFrameBaseController<SpaceActionMethodService, SpaceActionMethod, SpaceActionMethodBo, SpaceActionMethodPageReq> {

	@Autowired
	private SpaceActionTargetService targetService;

//	@Override
//	protected List<? extends SpaceFrameBaseTable> getUpperRelated(List<String> upperIds) {
//		return targetService.listByIds(upperIds);
//	}

//	@Override
//	protected List<String> getUpperIds(SpaceActionMethodBo req) {
////		return StreamApiUtils.map(req.getTarget(), IdAndName::getResourceId);
//		return Lists.newArrayList();
//	}
}
