/**
 * 这段代码是一个基于Spring Boot的控制器类，用于处理与“空间行动方法”相关的业务逻辑。以下是代码的主要功能和结构分析： 
 * ### 类的基本信息 
 * - **类名**：`SpaceActionMethodController` 
 * - **继承关系**：继承自`SpaceActionFrameBaseController`，这是一个泛型基类。 
 * - **注解**： 
 * - `@RestController`：表明这是一个RESTful风格的控制器。 
 * - `@RequestMapping`：定义了该控制器的请求路径。 
 * - `@RequiresAuthentication`：表示此接口需要用户认证才能访问。 
 * ### 功能描述 
 * 该控制器主要负责处理与“空间行动方法”相关的CRUD操作，并且包含了特定的方法检查逻辑。 
 * ### 主要方法分析 
 * 1. **构造函数及依赖注入** 
 * - 使用`@Autowired`注解自动装配了`SpaceActionFrameService`服务层对象，用于执行数据库操作。 
 * 2. **checkReference方法** 
 * - **功能**：检查指定的方法ID列表中是否存在任何方法被动作帧引用。 
 * - **流程**： 
 * - 遍历每个方法ID。 
 * - 构造查询条件（使用`LambdaQueryWrapper`），查找包含该方法ID的动作帧记录。 
 * - 如果发现有动作帧引用了该方法，则通过`baseService.getById()`获取方法详情，并将所有引用该方法的动作帧名称拼接成字符串。 
 * - 抛出`BusinessException`异常，携带具体的错误信息（包括方法名称和引用它的动作帧名称）。 
 * - **用途**：确保在尝试删除或修改某个方法之前，该方法未被其他数据引用，从而避免数据完整性问题。 
 * 3. **继承自父类的方法** 
 * - 虽然部分方法被注释掉了（如`getUpperRelated`、`getUpperIds`），但它们可能涉及更复杂的业务逻辑，比如处理父子关系等。 
 * ### 总结 
 * 这个控制器的核心在于实现对“空间行动方法”的安全管理，特别是在删除或修改方法时进行引用检查，以保证系统的数据一致性。同时，它利用了MyBatis-Plus提供的`LambdaQueryWrapper`简化了查询条件的构建过程。 
**/
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

	/**
	 * 框架Service
	 *
	 * @see SpaceActionFrameService
	 */
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
