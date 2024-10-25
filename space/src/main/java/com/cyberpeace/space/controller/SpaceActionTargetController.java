package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameTargetRelationMapper;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionTargetService;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetBo;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetPageReq;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "知识库-防御行动框架-作战目标管理")
@RestController
@RequestMapping("/space/action_frame/action_target")
@RequiresAuthentication
public class SpaceActionTargetController extends SpaceActionFrameBaseController<SpaceActionTargetService, SpaceActionTarget, SpaceActionTargetBo, SpaceActionTargetPageReq> {

    @Autowired
    private SpaceActionFrameService frameService;

    @Autowired
    private SpaceActionFrameTargetRelationMapper frameTargetRelationMapper;


    @Override
    protected List<? extends SpaceFrameBaseTable> getUpperRelated(List<String> upperIds) {
        return frameService.listByIds(upperIds);
    }

    @Override
    protected List<String> getUpperIds(SpaceActionTargetBo req) {
        return StreamApiUtils.map(req.getFrame(), IdAndName::getResourceId);
    }
}
