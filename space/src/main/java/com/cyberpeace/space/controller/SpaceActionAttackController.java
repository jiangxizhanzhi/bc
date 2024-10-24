package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionAttack;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionAttackService;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.context.frame.SpaceActionAttackBo;
import com.cyberpeace.space.service.context.frame.SpaceActionAttackPageReq;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "知识库-防御行动框架-攻击活动管理")
@RestController
@RequestMapping("/space/action_frame/action_attack")
@RequiresAuthentication
public class SpaceActionAttackController extends SpaceActionFrameBaseController<SpaceActionAttackService, SpaceActionAttack, SpaceActionAttackBo, SpaceActionAttackPageReq> {

    @Autowired
    private SpaceActionMethodService methodService;

	@Override
	protected void addIdAndName(Map<String, IdAndName> parentMap,
			List<SpaceActionAttack> entityList) {
		List<String> ids = StreamApiUtils.map(entityList, SpaceActionAttack::getMethod);
		if (!ids.isEmpty()) {
			List<SpaceActionMethod> methods = methodService.listByIds(ids);
			this.createIdAndName(methods, parentMap);
		}
	}
}
