package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.IdReq;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameOrderService;
import com.cyberpeace.space.service.SpaceActionFrameReleaseService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.frame.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * ### 代码分析 
 * #### 概述 
 * 这段代码是一个Spring Boot控制器类，用于管理与“防御行动框架”相关的功能模块。控制器名为`SpaceActionFrameApplicationController`，继承自`SpaceControllerBase`，并实现了多个RESTful API接口。 
 * #### 主要功能模块 
 * 1. **框架搜索 (`/search`)** 
 * - 使用`GET`请求，根据请求体中的参数搜索框架。 
 * - 如果请求体为空，返回一个空列表；否则调用服务层方法`frameService.searchFrame()`进行搜索。 
 * 2. **应用框架 (`/`)** 
 * - 使用`POST`请求，接收`SpaceActionFrameApplicationReq`请求体，调用服务层的`create()`方法完成框架应用。 
 * 3. **列出所有框架应用 (`/list_by_rvb`)** 
 * - 使用`GET`请求，根据请求体中的参数筛选并列出红蓝双方的所有框架应用。 
 * - 将实体列表转换为业务对象列表后返回。 
 * 4. **更新框架应用 (`/`)** 
 * - 使用`PATCH`请求，接收`SpaceActionFrameApplicationBo`请求体，更新指定资源的框架应用信息。 
 * - 在事务中执行更新操作，同时删除与更新框架相关的已发布命令。 
 * 5. **获取框架应用详情 (`/`)** 
 * - 使用`GET`请求，通过资源ID获取特定框架应用的详细信息。 
 * - 调用服务层方法`getApplicationBo()`返回具体业务对象。 
 * 6. **删除框架应用 (`/`)** 
 * - 使用`DELETE`请求，接收`IdListReq`请求体，批量删除指定ID的框架应用。 
 * 7. **查询下发情况 (`/tactics`)** 
 * - 使用`GET`请求，通过框架应用ID查询下发的策略信息。 
 * 8. **中位线下发战法指令 (`/order`)** 
 * - 使用`POST`请求，接收`SpaceActionFrameOrderReq`请求体，创建新的战法指令。 
 * 9. **查询分配到的战法指令 (`/order`)** 
 * - 使用`GET`请求，查询当前用户的战法指令。 
 * 10. **确认完成战法指令 (`/order/complete`)** 
 * - 使用`POST`请求，通过资源ID确认某条战法指令已完成。 
 * 11. **获取红蓝用户列表 (`/users`)** 
 * - 使用`GET`请求，根据红蓝对抗ID和用户组筛选并返回用户列表。 
 * #### 技术亮点 
 * - **事务管理**：使用`TransactionTemplate`确保更新操作的原子性。 
 * - **工具类支持**：如`StreamApiUtils`、`JsonUtil`等工具类简化了数据转换和操作。 
 * - **Swagger注解**：通过`@Api`和`@ApiOperation`提供了清晰的接口文档。 
 * - **异常处理**：在关键逻辑中（如资源ID为空时）抛出`BusinessException`以提示错误。 
 * #### 总结 
 * 该代码实现了一个完整的防御行动框架管理系统，涵盖了框架的创建、更新、查询、删除等核心功能。代码结构清晰，功能模块划分明确，使用了现代Java开发的最佳实践，如依赖注入、事务管理以及工具类的支持。 
**/
@Api(tags = "防御行动框架-框架应用")
@RestController
@RequestMapping("/space/action_frame/application")
@Slf4j
public class SpaceActionFrameApplicationController extends SpaceControllerBase<SpaceActionFrameApplicationService, SpaceActionFrameApplication, SpaceActionFrameApplicationBo, SpaceActionFrameApplicationListReq> {

	/**
	 * 下发Service
	 *
	 * @see SpaceActionFrameReleaseService
	 */
	@Autowired
	private SpaceActionFrameReleaseService releaseService;

	/**
	 * 框架Service
	 *
	 * @see SpaceActionFrameService
	 */
	@Autowired
	private SpaceActionFrameService frameService;

	/**
	 * 次序Service
	 *
	 * @see SpaceActionFrameOrderService
	 */
	@Autowired
	private SpaceActionFrameOrderService orderService;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 搜索框架
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<List<SpaceActionFrameBo>>}
	 */
	@ApiOperation("指挥员搜索框架")
	@GetMapping("/search")
	public BaseRsp<List<SpaceActionFrameBo>> searchFrame(@ApiParam(name = "req") String req) {
	    // 检查请求体是否为空
	    if (StringUtils.isBlank(req)) {
	        // 如果请求体为空，返回一个空列表
	        return new BaseRsp<>(Lists.newArrayList());
	    }
	    // 调用服务层方法进行框架搜索
	    List<SpaceActionFrameBo> res = frameService.searchFrame(req);
	    // 将搜索结果封装到响应对象中并返回
	    return new BaseRsp<>(res);
	}

	/**
	 * 应用框架
	 *
	 * 此方法用于处理指挥员的应用框架请求。它接收一个请求体，执行创建操作，并返回一个基础响应对象。
	 * 主要目的是通过POST请求来应用特定的框架配置或操作。
	 *
	 * @param req 请求体，包含应用框架所需的信息
	 * @return {@code BaseRsp} 返回一个基础响应对象，表示操作的结果
	 */
	@ApiOperation("指挥员应用框架")
	@PostMapping("/")
	public BaseRsp applyFrame(@RequestBody SpaceActionFrameApplicationReq req) {
	    baseService.create(req);
	    return new BaseRsp<>();
	}

	/**
	 * 列出当前红蓝的所有框架应用
<<<<<<< HEAD
	 * @param req
	 * @return {@code BaseRsp }
=======
	 *
	 * @param req 查询请求参数，包含筛选条件
	 * @return 返回一个包含框架应用信息的响应对象
>>>>>>> 514a85088 (feat:添加代码注释)
	 */
	@ApiOperation("列出当前红蓝的所有框架应用")
	@GetMapping("/list_by_rvb")
	public BaseRsp<List<SpaceActionFrameApplicationBo>>
			list(SpaceActionFrameApplicationListReq req) {
		// 将请求参数转换为查询条件
		LambdaQueryWrapper<SpaceActionFrameApplication> wrapper = req.req2QueryWrapper();
		// 根据查询条件获取实体列表
		List<SpaceActionFrameApplication> eList = baseService.list(wrapper);
		// 将实体列表转换为业务对象列表
		List<SpaceActionFrameApplicationBo> res = StreamApiUtils.map(eList, e -> e.entity2Bo(null));
		// 返回包含业务对象列表的响应
		return new BaseRsp<>(res);
	}

	/**
<<<<<<< HEAD
	 * 更新
	 * @param req
	 * @return {@code BaseRsp }
=======
	 * 更新空间行动框架应用信息
	 *
	 * 此方法主要用于更新空间行动框架的应用信息它通过 PATCH 请求接收更新数据，
	 * 并对特定资源进行更新操作如果资源 ID 为空，则抛出异常指示错误
	 *
	 * @param req 包含更新信息的请求对象，包括资源 ID 和框架信息
	 * @return 返回一个基础响应对象，表示更新操作的结果
	 * @throws BusinessException 如果资源 ID 为空，则抛出此异常
>>>>>>> 514a85088 (feat:添加代码注释)
	 */
	@ApiOperation("更新")
	@PatchMapping("")
	@Override
	public BaseRsp updateA(@RequestBody SpaceActionFrameApplicationBo req) {
	    // Check if the resource ID is blank, if so, throw an exception
	    if (StringUtils.isBlank(req.getResourceId())) {
	        throw new BusinessException(ErrorCode.BLANK_ID);
	    }
	    //  can only update frame
	    // Retrieve the existing entity by resource ID
	    SpaceActionFrameApplication entity = baseService.getById(req.getResourceId());
	    // Check for frame tactic duplication
	    req.getFrame().checkTacticDuplication();
	    // Update the frame information of the entity
	    entity.setFrame(JsonUtil.toJsonString(req.getFrame()));
	    // Execute the update operation in a transaction
	    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
	        @Override
	        protected void doInTransactionWithoutResult(TransactionStatus status) {
	            // Update the entity information
	            baseService.updateById(entity);
	            // Delete the released orders associated with the updated frame
	            orderService.deleteReleased(req.getFrame(), req.getResourceId());
	        }
	    });
	    // Return a new base response object, indicating the update was successful
	    return new BaseRsp<>();
	}

	/**
<<<<<<< HEAD
	 * 详情
	 * @param resource_id
	 * @return {@code BaseRsp }
=======
	 * 获取空间行动框架应用的详细信息
	 *
	 * @param resource_id 资源ID，用于标识特定的空间行动框架应用
	 * @return 返回一个包含空间行动框架应用详细信息的BaseRsp对象
>>>>>>> 514a85088 (feat:添加代码注释)
	 */
	@ApiOperation("详情")
	@GetMapping("")
	@Override
	public BaseRsp<SpaceActionFrameApplicationBo> getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
	    // 调用服务层方法获取空间行动框架应用的业务对象
	    SpaceActionFrameApplicationBo bo = releaseService.getApplicationBo(resource_id);
	    // 将获取到的业务对象封装到响应对象中并返回
	    return new BaseRsp<>(bo);
	}

	/**
	 * 删除框架应用
<<<<<<< HEAD
	 * @param req
	 * @return {@code BaseRsp }
=======
	 *
	 * 此方法用于接收一个包含多个ID的请求对象，并将这些ID对应的应用从数据库中删除
	 * 它使用HTTP DELETE请求映射到一个特定的API端点，以便前端或其他服务可以调用它
	 *
	 * @param req 包含要删除的应用ID列表的请求对象 这些ID用于标识哪些应用将被删除
	 * @return 返回一个基础响应对象，表示删除操作是否成功
>>>>>>> 514a85088 (feat:添加代码注释)
	 */
	@ApiOperation("删除框架应用")
	@DeleteMapping("")
	@Override
	public BaseRsp deleteA(@RequestBody IdListReq req) {
	    // 调用基础服务的按ID删除方法，传入从请求中提取的ID列表
	    baseService.deleteByIds(req.getIds());
	    // 创建并返回一个新的基础响应对象，表示删除操作的结果
	    return new BaseRsp();
	}

//	/**
//	 * 查询下发情况
//	 *
//	 * @return {@code BaseRsp }
//	 */
//	@ApiOperation("指挥员查询下发情况")
//	@GetMapping("/release")
//	public BaseRsp<List<SpaceActionFrameReleaseBo>> listRelease(@ApiParam(value = "框架应用id", name = "application_id") String application_id) {
//		List<SpaceActionFrameReleaseBo> res = releaseService.listBo(application_id);
//		return new BaseRsp<>(res);
//	}


	/**
	 * 指挥、中位线查看下发情况
	 *
	 * 该接口用于获取特定框架应用的指挥或中位线策略下发情况通过应用ID来查询对应的策略信息
	 * 主要用于监控和管理策略的分发状态
	 *
	 * @return {@code BaseRsp<List<SpaceActionFrameOrderBo>>} 包含策略信息列表的响应对象
	 */
	@ApiOperation("指挥、中位线查看下发情况")
	@GetMapping("/tactics")
	public BaseRsp<List<SpaceActionFrameOrderBo>> getTactics(
	        @ApiParam(value = "框架应用id", name = "application_id") String application_id) {
	    List<SpaceActionFrameOrderBo> res = releaseService.getTactics(application_id);
	    return new BaseRsp<>(res);
	}

//	/**
//	 * 下发步骤
//	 *
//	 * @param req 请求体
//	 * @return {@code BaseRsp }
//	 */
//	@ApiOperation("指挥员下发步骤")
//	@PostMapping("/release")
//	public BaseRsp releaseStep(@RequestBody @Valid SpaceActionFrameReleaseReq req) {
//		releaseService.create(req);
//		return new BaseRsp<>();
//	}


//	/**
//	 * 中位线查询分配到的步骤
//	 *
//	 * @return {@code BaseRsp }
//	 */
//	@ApiOperation("中位线查询分配到的步骤")
//	@GetMapping("/step")
//	public BaseRsp<List<SpaceActionFrameOrderBo>> mySteps(
//			@ApiParam(value = "红蓝对抗id", name = "rvb_id") String rvb_id,
//			@ApiParam(value = "当前用户id, 必传", name = "user_id") String user_id) {
//		List<SpaceActionFrameOrderBo> res = releaseService.getMySteps(rvb_id, user_id);
//		return new BaseRsp<>(res);
//	}

	/**
	 * 中位线下发战法指令
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("下发战法指令")
	@PostMapping("/order")
	public BaseRsp releaseOrder(@RequestBody @Valid SpaceActionFrameOrderReq req) {
		orderService.create(req);
		return new BaseRsp<>();
	}

	/**
	 * 工位线查询分配到的战法指令
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("工位线查询分配到的战法指令")
	@GetMapping("/order")
	public BaseRsp<List<SpaceActionFrameOrderBo>> myOrders(SpaceActionFrameOrderListReq req) {
		List<SpaceActionFrameOrderBo> res = orderService.listMyOrders(req);
		return new BaseRsp<>(res);
	}

	/**
	 * 工位线确认完成战法指令
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("工位线确认完成战法指令")
	@PostMapping("/order/complete")
	public BaseRsp completeOrder(@RequestBody IdReq req) {
		orderService.complete(req.getResourceId());
		return new BaseRsp<>();
	}

	/**
	 * 获取红蓝用户列表
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("获取红蓝用户列表")
	@GetMapping("/users")
	public BaseRsp<List<ApplicationUser>> getUsers(
			@ApiParam(value = "红蓝对抗id", name = "rvb_id") String rvb_id,
			@ApiParam(value = "用户组，指挥1，中位线2，工位线3", name = "group") Integer group) {
		return new BaseRsp<>(baseService.getUsers(rvb_id, group));
	}
}

