package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodBo;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodPageReq;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "知识库-防御行动框架-作战方法管理")
@RestController
@RequestMapping("/space/action_frame/action_method")
@RequiresAuthentication
public class SpaceActionMethodController extends SpaceActionFrameBaseController<SpaceActionMethodService, SpaceActionMethod, SpaceActionMethodBo, SpaceActionMethodPageReq> {

	@Autowired
	private SpaceActionFrameService frameService;

	@Override
	protected void checkReference(List<String> ids) {
		for (String methodId : ids) {
			LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
			wrapper.like(SpaceActionFrame::getFrame, "\"" + methodId + "\"");
			List<SpaceActionFrame> frameList = frameService.list(wrapper);
			if (!frameList.isEmpty()) {
				SpaceActionMethod method = baseService.getById(methodId);
				if (method != null) {
					String frameNames = String.join(",", StreamApiUtils.map(frameList, SpaceActionFrame::getName));
					throw new BusinessException(SpaceError.METHOD_REFERENCE_EXIST, method.getName(), frameNames);
				}
			}
		}
	}
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
