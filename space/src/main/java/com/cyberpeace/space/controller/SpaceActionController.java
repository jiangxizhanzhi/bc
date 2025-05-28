package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.SpaceActionService;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.frame.SpaceActionBo;
import com.cyberpeace.space.service.context.frame.SpaceActionOfflineTool;
import com.cyberpeace.space.service.context.frame.SpaceActionPageReq;
import com.cyberpeace.space.service.context.frame.SpaceActionTool;
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

/**
 * 空间操作控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "防御行动框架-攻击、防御活动管理")
@RestController
@RequestMapping("/space/action_frame/action")
@RequiresAuthentication
public class SpaceActionController extends SpaceActionFrameBaseController<SpaceActionService, SpaceAction, SpaceActionBo, SpaceActionPageReq> {

	/**
	 * 方法Service
	 */
	@Autowired
	private SpaceActionMethodService methodService;

	/**
	 * 工具Service
	 */
	@Autowired
	private ToolService toolService;

	/**
	 * 策略Service
	 */
	@Autowired
	private SpaceStrategyService strategyService;

	@Autowired
	private SpaceActionFrameService frameService;

	/**
	 * 获取资源详情
	 *
	 * 此方法用于根据资源ID获取详细的资源信息，包括工具列表、策略、离线工具等关联信息
	 * 它首先通过资源ID从数据库中获取基本信息，然后根据关联的工具和策略ID获取详细的工具和策略信息，
	 * 最后将这些信息封装到SpaceActionBo对象中返回
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<SpaceActionBo> }
	 */
	@ApiOperation("详情")
	@GetMapping("")
	@Override
	public BaseRsp<SpaceActionBo>
	getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
	    // 根据资源ID获取基本信息
	    SpaceAction e = baseService.getById(resource_id);
	    if (null == e) {
	        throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
	    }
	    // 将基本信息转换为BO对象
	    SpaceActionBo bo = BeanUtils.copy(e, SpaceActionBo.class);
	    // 解析并获取关联的工具ID列表
	    List<String> toolIds = JsonUtil.toJsonObjectArray(e.getTool(), String.class);
	    List<String> strategyIds = JsonUtil.toJsonObjectArray(e.getStrategy(), String.class);
	    // 根据工具ID列表获取详细的工具信息列表
	    List<Tool> toolList = toolService.listByIdsInOrder(toolIds);
	    bo.setTool(BeanUtils.deepCopyList(toolList, SpaceActionTool.class));
	    // 根据策略ID列表获取策略信息
	    bo.setStrategy(strategyService.convert2ActionStrategy(strategyIds));
	    // 解析并设置离线工具信息
	    bo.setOfflineTool(JsonUtil.toJsonObjectArray(e.getOfflineTool(), SpaceActionOfflineTool.class));

	    // 获取上级资源ID列表
	    List<String> upperIds = relationService.getUpperIdsByLowerId(e.getResourceId());
	    // 根据上级资源ID列表获取关联的资源方法信息
	    List<SpaceActionMethod> methods = this.getUpperRelated(upperIds);
	    // 将资源方法信息转换为简单的ID和名称对象列表
	    List<IdAndName> idAndNames = StreamApiUtils.map(methods, m->new IdAndName(m.getResourceId(), m.getName()));
	    bo.setMethod(idAndNames);
	    // 解析并设置平台信息
	    bo.setPlatform(JsonUtil.toJsonObjectArray(e.getPlatform(), String.class));
	    // 返回封装了详细信息的BO对象
	    return new BaseRsp<>(bo);
	}

	/**
	 * 获取上相关
	 *
	 * @param upperIds 上id列表
	 * @return {@code List<SpaceActionMethod> }
	 */
	@Override
	protected List<SpaceActionMethod> getUpperRelated(List<String> upperIds) {
	    // 当上id列表为空时，直接返回空列表
	    if (upperIds.isEmpty()) {
	        return Lists.newArrayList();
	    }
	    // 调用methodService的listByIds方法，根据上id列表获取对应的SpaceActionMethod列表
	    return methodService.listByIds(upperIds);
	}

	/**
	 * 获取上id列表
	 *
	 * 通过请求体中的方法信息，提取出所有资源的ID，并进行去重处理
	 * 使用了StreamApiUtils工具类中的两个静态方法：distinct和map
	 * 这是为了确保返回的列表中每个ID都是唯一的，并且每个ID都对应于请求方法中的一个资源
	 *
	 * @param req 请求体，包含了需要提取资源ID的方法信息
	 * @return {@code List<String> } 去重后的资源ID列表
	 */
	@Override
	protected List<String> getUpperIds(SpaceActionBo req) {
	    // 使用Stream API映射方法中的每个元素到其资源ID，并进行去重处理
	    return StreamApiUtils.distinct(StreamApiUtils.map(req.getMethod(), IdAndName::getResourceId));
	}

	/**
	 * 检查操作是否被框架引用
	 * @param ids id列表
	 * 检查给定ID列表中的每个ID是否作为方法ID在SpaceActionFrame表中被引用
	 * 如果找到引用，则抛出异常指示该方法ID对应的操作被一个或多个框架引用
	 * 此方法用于确保在进行删除或修改操作前，相关数据的完整性不会因引用关系而受到破坏
	 *
	 * @param ids 方法ID列表，用于检查是否被SpaceActionFrame表中的记录引用
	 */
	@Override
	protected void checkReference(List<String> ids) {
	    // 遍历每个方法ID
	    for (String methodId : ids) {
	        // 创建查询条件，用于查找包含当前方法ID的SpaceActionFrame记录
	        LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
	        wrapper.like(SpaceActionFrame::getFrame, "\"" + methodId + "\"");

	        // 执行查询，获取所有包含当前方法ID的SpaceActionFrame记录
	        List<SpaceActionFrame> frameList = frameService.list(wrapper);

	        // 如果查询结果不为空，说明当前方法ID被至少一个SpaceActionFrame记录引用
	        if (!frameList.isEmpty()) {
	            // 根据方法ID获取对应的SpaceAction记录
	            SpaceAction action = baseService.getById(methodId);

	            // 如果SpaceAction记录存在，说明方法ID有效且被引用
	            if (action != null) {
	                // 将所有引用该方法ID的SpaceActionFrame记录的名称拼接成字符串
	                String frameNames = String.join(",", StreamApiUtils.map(frameList, SpaceActionFrame::getName));

	                // 抛出异常，指示该方法ID对应的操作被一个或多个框架引用
	                throw new BusinessException(SpaceError.ACTION_REFERENCE_EXIST, action.getName(), frameNames);
	            }
	        }
	    }
	}
}
