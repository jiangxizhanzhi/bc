package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionTargetService;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetBo;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetPageReq;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(tags = "知识库-防御行动框架-作战目标管理")
@RestController
@RequestMapping("/space/action_frame/action_target")
@RequiresAuthentication
public class SpaceActionTargetController extends SpaceActionFrameBaseController<SpaceActionTargetService, SpaceActionTarget, SpaceActionTargetBo, SpaceActionTargetPageReq> {

    @Autowired
    private SpaceActionFrameService frameService;

    @Override
    protected void addIdAndName(Map<String, IdAndName> parentMap, List<SpaceActionTarget> entityList){
        Set<String> ids = Sets.newHashSet();
        for (SpaceActionTarget target : entityList) {
            ids.addAll(JsonUtil.toJsonObjectArray(target.getFrame(), String.class));
        }
        if(!ids.isEmpty()) {
            List<SpaceActionFrame> methods = frameService.listByIds(ids);
            this.createIdAndName(methods, parentMap);
        }
    }

}
