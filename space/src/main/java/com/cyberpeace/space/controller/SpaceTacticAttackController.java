package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.service.SpaceTacticAttackService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.tactic.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 空间战法攻击Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **功能概述** 
 * 该代码是一个基于Spring Boot的RESTful API控制器类，用于管理**空间战法攻击**相关的业务逻辑。它主要负责处理与攻击战法相关的增删改查操作，包括攻击战法的列表、详情、新增、修改、删除等操作，同时也支持对攻击战法类别的管理。 
 * --- 
 * #### 2. **核心功能模块** 
 * ##### **2.1 攻击战法管理** 
 * - **获取攻击战法列表** 
 * - 方法：`listAttack` 
 * - URL：`/space/tactic/attack/page` 
 * - 功能：根据分页请求参数返回攻击战法的分页列表。 
 * - **新增攻击战法** 
 * - 方法：`addAttack` 
 * - URL：`/space/tactic/attack` 
 * - 功能：接收新增攻击战法的数据并调用服务层完成保存。 
 * - **更新攻击战法** 
 * - 方法：`updateA` 
 * - URL：`/space/tactic/attack` 
 * - 功能：根据请求体中的资源ID更新指定的攻击战法。 
 * - **删除攻击战法** 
 * - 方法：`deleteA` 
 * - URL：`/space/tactic/attack` 
 * - 功能：批量删除指定ID的攻击战法，并检查是否为内置数据（抛出异常）。 
 * - **获取攻击战法详情** 
 * - 方法：`getADetail` 
 * - URL：`/space/tactic/attack/{resource_id}` 
 * - 功能：通过资源ID查询单个攻击战法的详细信息。 
 * ##### **2.2 攻击战法类别管理** 
 * - **获取攻击战法类别列表** 
 * - 方法：`pageC` 
 * - URL：`/space/tactic/attack/category/page` 
 * - 功能：分页查询攻击战法类别列表。 
 * - **新增攻击战法类别** 
 * - 方法：`addC` 
 * - URL：`/space/tactic/attack/category` 
 * - 功能：新增攻击战法类别。 
 * - **更新攻击战法类别** 
 * - 方法：`updateC` 
 * - URL：`/space/tactic/attack/category` 
 * - 功能：更新指定ID的攻击战法类别。 
 * - **删除攻击战法类别** 
 * - 方法：`deleteC` 
 * - URL：`/space/tactic/attack/category` 
 * - 功能：批量删除指定ID的攻击战法类别，并检查是否存在关联数据（抛出异常）。 
 * --- 
 * #### 3. **关键设计点** 
 * - **异常处理** 
 * - 使用了`BusinessException`来统一处理业务逻辑异常，例如尝试修改或删除内置数据时会抛出特定错误码。 
 * - **分层架构** 
 * - 控制器层仅负责接收HTTP请求并调用服务层，具体业务逻辑由`SpaceTacticAttackService`实现。 
 * - **Swagger注解** 
 * - 使用了`io.swagger.annotations.ApiOperation`和`@ApiParam`注解，便于生成API文档，提升可读性和维护性。 
 * - **数据校验** 
 * - 在更新和删除操作中，会对内置数据进行校验，避免误操作。 
 * --- 
 * #### 4. **潜在改进点** 
 * - **输入验证** 
 * - 当前代码对输入参数的校验较为简单，建议在服务层增加更严格的参数校验逻辑。 
 * - **日志记录** 
 * - 增加日志记录功能，方便后续排查问题。 
 * - **性能优化** 
 * - 对于批量操作（如删除），可以考虑异步处理以提高系统性能。 
 * --- 
 * #### 5. **总结** 
 * 该代码实现了攻击战法及其类别的全生命周期管理，结构清晰，职责明确，符合RESTful API的设计规范。通过分层架构和异常处理机制，确保了系统的健壮性和可扩展性。 
**/
@Api(tags = "战法库-攻击战法")
@RestController
@RequestMapping("/space/tactic/attack")
public class SpaceTacticAttackController {

	/**
	 * 攻击Service
	 *
	 * @see SpaceTacticAttackService
	 */
	@Autowired
	private SpaceTacticAttackService attackService;

	/**
	 * 列表攻击
	 *
	 * @param req 请求体
	 * @return base rsp< list rsp<攻击列表 bo>>
	 * @throws
	 * @see SpaceTacticPageReq
	 * @see BaseRsp<ListRsp<AttackListBo>>
	 */
	@ApiOperation("攻击战法列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<AttackListBo>> listAttack(SpaceTacticPageReq req) {
		ListRsp<AttackListBo> res = attackService.pageAttack(req);
		return new BaseRsp(res);
	}

	/**
	 * 新增攻击
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see AttackAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("新增攻击战法")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody AttackAddReq req) {
		attackService.addAttack(req);
		return new BaseRsp();
	}

	/**
	 * 更新一个
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see AttackModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("更新攻击战法")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody AttackModifyReq req) {
		SpaceTacticAttack old=attackService.getById(req.getResourceId());
		if(Boolean.TRUE.equals(old.getBuiltin())){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		attackService.updateAttack(req);
		return new BaseRsp();
	}

	/**
	 * 删除
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see IdListReq
	 * @see BaseRsp
	 */
	@ApiOperation("删除攻击战法")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		List<SpaceTacticAttack> attacks = attackService.listByIds(req.getIds());
		for(SpaceTacticAttack attack:attacks){
			if(Boolean.TRUE.equals(attack.getBuiltin())){
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
		}

		attackService.deleteBatch(req.getIds());
		return new BaseRsp();
	}

	/**
	 * 获取adetail
	 *
	 * @param resource_id 资源id
	 * @return Base RSP< 攻击详情>
	 * @throws
	 * @see String
	 * @see BaseRsp<AttackDetail>
	 */
	@ApiOperation("攻击战法详情")
	@GetMapping("")
	public BaseRsp<AttackDetail>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		AttackDetail detail = attackService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	/**
	 * Pagec
	 *
	 * @param reqBase 请求体基础
	 * @return 基础RSP<列表RSP < 战法攻击类别博>>
	 * @throws
	 * @see PageReqBase
	 * @see BaseRsp<ListRsp<TacticAttackCategoryBo>>
	 */
	@ApiOperation("攻击战法类别列表")
	@GetMapping("/category/page")
	public BaseRsp<ListRsp<TacticAttackCategoryBo>> pageC(PageReqBase reqBase) {
		ListRsp<TacticAttackCategoryBo> rsp = attackService.pageCategory(reqBase);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 添加
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see TacticAttackCategoryAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("新增攻击战法类别")
	@PostMapping("/category")
	public BaseRsp addC(@RequestBody TacticAttackCategoryAddReq req) {
		attackService.addAttackCategory(req);
		return new BaseRsp();
	}

	/**
	 * 更新c
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see TacticAttackCategoryModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("修改攻击战法类别")
	@PatchMapping("/category")
	public BaseRsp updateC(@RequestBody TacticAttackCategoryModifyReq req) {
		attackService.updateAttackCategory(req);
		return new BaseRsp();
	}

	/**
	 * 删除
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see IdListReq
	 * @see BaseRsp
	 */
	@ApiOperation("删除攻击战法类别")
	@DeleteMapping("/category")
	public BaseRsp deleteC(@RequestBody IdListReq req) {
		List <String> resourceIds =attackService.listByCategoryids(req.getIds());
		if (resourceIds!= null && !resourceIds.isEmpty()) {
			throw new BusinessException(ErrorCode.RELATED_DATA_EXIST);
		}
		attackService.deleteCategory(req.getIds());
		return new BaseRsp();
	}
}

