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

/**
 * 空间行动方法Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceActionFrameBaseController
 * @since 1.0.0
 */
@Api(tags = "知识库-防御行动框架-作战方法管理")
@RestController
@RequestMapping("/space/action_frame/action_method")
@RequiresAuthentication
public class SpaceActionMethodController extends SpaceActionFrameBaseController<SpaceActionMethodService, SpaceActionMethod, SpaceActionMethodBo, SpaceActionMethodPageReq> {

	@Autowired
	private SpaceActionFrameService frameService;

	/**
	 * 检查给定方法ID列表中的每个方法是否被动作帧引用
	 * 如果方法被一个或多个动作帧引用，则抛出异常，指示这些方法无法被删除或修改
	 *
	 * @param ids 方法ID列表，用于检查引用
	 * @throws BusinessException 当方法被动作帧引用时抛出，包含引用存在的错误信息
	 */
	@Override
	protected void checkReference(List<String> ids) {
	    // 遍历每个方法ID
	    for (String methodId : ids) {
	        // 创建查询包装器，用于搜索包含方法ID的动作帧
	        LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
	        wrapper.like(SpaceActionFrame::getFrame, "\"" + methodId + "\"");

	        // 执行查询，获取包含当前方法ID的所有动作帧列表
	        List<SpaceActionFrame> frameList = frameService.list(wrapper);

	        // 如果找到引用该方法的动作帧
	        if (!frameList.isEmpty()) {
	            // 根据方法ID获取方法详细信息
	            SpaceActionMethod method = baseService.getById(methodId);

	            // 如果方法存在
	            if (method != null) {
	                // 将引用该方法的所有动作帧名称拼接成字符串
	                String frameNames = String.join(",", StreamApiUtils.map(frameList, SpaceActionFrame::getName));

	                // 抛出异常，指示方法被引用，无法进行删除或修改操作
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
