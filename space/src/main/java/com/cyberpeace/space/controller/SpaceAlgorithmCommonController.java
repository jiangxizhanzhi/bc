package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.service.SpaceAlgorithmExpertiseService;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
import com.cyberpeace.space.service.SpaceAlgorithmRoleService;
import com.cyberpeace.space.service.SpaceAlgorithmTeamService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmStatistics;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间算法公共Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **类的功能与作用** 
 * - 类名为 `SpaceAlgorithmCommonController`，表示这是一个用于处理空间算法相关业务的公共控制器。 
 * - 它属于知识库和算法库模块，提供通用的接口功能。 
 * #### 2. **注解解析** 
 * - `@RestController`：声明该类是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/space/algorithm/common")`：定义了该类所有方法的默认路径前缀为 `/space/algorithm/common`。 
 * - `@RequiresAuthentication`：需要用户认证后才能访问该接口，确保只有经过身份验证的用户可以调用这些 API。 
 * - `@Slf4j`：引入日志功能，方便后续调试和问题追踪。 
 * #### 3. **依赖注入** 
 * - 使用 `@Autowired` 注入了多个服务类： 
 * - `SpaceAlgorithmExpertiseService`：专门知识相关的服务。 
 * - `SpaceAlgorithmRoleService`：角色相关的服务。 
 * - `SpaceAlgorithmIndividualService`：个人相关的服务。 
 * - `SpaceAlgorithmTeamService`：团队相关的服务。 
 * #### 4. **方法功能** 
 * - 方法 `getStatistics()`： 
 * - 路径：`/space/algorithm/common/statistics`。 
 * - 功能：获取统计数据，包括专门知识、角色、个人和团队的数量。 
 * - 返回值：`BaseRsp<SpaceAlgorithmStatistics>`，其中 `SpaceAlgorithmStatistics` 包含四个字段（`expertise`, `role`, `individual`, `team`），分别对应四种类型的数据统计结果。 
 * - 操作流程： 
 * 1. 创建 `SpaceAlgorithmStatistics` 对象。 
 * 2. 调用各个服务类的 `count()` 方法，获取对应的统计值。 
 * 3. 将统计结果封装到返回对象中并返回。 
 * #### 5. **设计特点** 
 * - **模块化**：通过注入多个服务类，将具体的业务逻辑分离到服务层，保持控制器的简洁性。 
 * - **安全性**：使用 `@RequiresAuthentication` 确保只有认证用户可以访问。 
 * - **可扩展性**：如果未来需要新增统计维度，只需在服务层添加新的方法即可，无需修改控制器逻辑。 
 * #### 6. **潜在改进点** 
 * - **异常处理**：当前代码未处理可能的异常情况（如服务调用失败或数据库异常），建议增加异常捕获和处理逻辑。 
 * - **日志记录**：可以在关键操作处添加日志记录，便于排查问题。 
 * ### 总结 
 * 这段代码实现了空间算法相关的通用统计功能，具有良好的模块化设计和安全性保障。通过依赖注入和注解简化了代码结构，同时提供了清晰的接口文档支持。 
**/
@Api(tags = "知识库-算法库-通用")
@RestController
@RequestMapping("/space/algorithm/common")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmCommonController {

	/**
	 * 专门知识Service
	 */
	@Autowired
	private SpaceAlgorithmExpertiseService expertiseService;

	/**
	 * 角色Service
	 */
	@Autowired
	private SpaceAlgorithmRoleService roleService;

	/**
	 * 个人Service
	 */
	@Autowired
	private SpaceAlgorithmIndividualService individualService;

	/**
	 * 团队Service
	 */
	@Autowired
	private SpaceAlgorithmTeamService teamService;

	/**
	 * 获取统计学
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("人员能力评估算法统计")
	@GetMapping("/statistics")
	public BaseRsp<SpaceAlgorithmStatistics> getStatistics() {
		SpaceAlgorithmStatistics res = new SpaceAlgorithmStatistics();
		res.setExpertise(expertiseService.count());
		res.setRole(roleService.count());
		res.setIndividual(individualService.count());
		res.setTeam(teamService.count());
		return new BaseRsp(res);
	}
}

