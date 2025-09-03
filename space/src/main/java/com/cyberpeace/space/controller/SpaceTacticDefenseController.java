package com.cyberpeace.space.controller;

import com.cyberpeace.common.annoations.CallCount;
import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.controller.res.SystemTreeRes;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 空间战法防御Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码是一个Spring MVC控制器类，用于处理与“空间战法防御”相关的API请求。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **防御战法管理**：提供了一系列API接口来管理和操作防御战法。 
 * - 列表查询（分页、按类别分页等） 
 * - 单个或批量操作（新增、修改、删除） 
 * - 获取详细信息（单个或分类） 
 * 2. **分类管理**：除了防御战法本身，还提供了对防御战法分类的管理。 
 * 3. **框架获取**：可以获取防御战法的整体框架结构。 
 * ### 关键点分析 
 * - **依赖注入**：通过`@Autowired`注解引入了`SpaceTacticDefenseService`服务层对象，用于实际业务逻辑的执行。 
 * - **异常处理**：使用了自定义的`BusinessException`类来抛出业务逻辑中的异常，并配合`ErrorCode`枚举定义具体的错误码。 
 * - **Swagger文档支持**：通过`@Api`和`@ApiOperation`注解生成接口文档，方便前端开发人员了解接口用途及参数。 
 * - **数据模型**：涉及多个数据传输对象（DTO），如`BaseRsp`, `ListRsp`, `DefenseListBo`等，确保前后端的数据交互格式统一。 
 * - **安全性考虑**：在某些操作中检查了资源是否为内置资源，如果是，则不允许修改或删除。 
 * ### 方法概述 
 * - `/page`: 分页查询防御战法列表。 
 * - `/add`: 新增防御战法。 
 * - `/update`: 更新防御战法信息。 
 * - `/delete`: 删除防御战法。 
 * - `/get`: 获取指定ID的防御战法详情。 
 * - `/category/page`: 分页查询防御战法分类。 
 * - `/category/add`: 新增防御战法分类。 
 * - `/category/update`: 更新防御战法分类。 
 * - `/category/delete`: 删除防御战法分类。 
 * - `/category/get`: 获取指定ID的防御战法分类详情。 
 * - `/category/list`: 获取所有防御战法分类并按阶段分类返回。 
 * - `/list`: 根据分类ID查询该分类下的所有防御战法。 
 * - `/frame`: 获取防御战法的整体框架结构。 
 * ### 总结 
 * 该控制器设计合理，功能模块清晰，符合RESTful API的设计原则。它不仅实现了基本的CRUD操作，还增加了分页、分类管理以及框架获取等功能，增强了系统的灵活性和扩展性。同时，通过异常处理和安全检查保证了系统的健壮性和安全性。 
**/
@Api(tags = "战法库-防御战法")
@RestController
@RequestMapping("/space/tactic/defense")
public class SpaceTacticDefenseController {

	/**
	 * 防御Service
	 *
	 * @see SpaceTacticDefenseService
	 */
	@Autowired
	private SpaceTacticDefenseService defenseService;

	/**
	 * 列表攻击
	 *
	 * @param req 请求体
	 * @return 基本 rsp< list rsp< 防御列表 bo>>
	 * @throws
	 * @see SpaceTacticPageReq
	 * @see BaseRsp<ListRsp<DefenseListBo>>
	 */
	@ApiOperation("防御战法列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<DefenseListBo>> listAttack(SpaceTacticPageReq req) {
		ListRsp<DefenseListBo> res = defenseService.page(req);
		return new BaseRsp(res);
	}

	/**
	 * 新增攻击
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see DefenseAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("新增防御战法")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody DefenseAddReq req) {
		defenseService.add(req, false, IDGeneratorUtil.getID());
		return new BaseRsp();
	}

	@ApiOperation("新增临时防御战法")
	@PostMapping("/tmp")
	public BaseRsp addTmpAttack(@RequestBody DefenseTmpAddReq req) {
		defenseService.tmpAdd(req);
		return new BaseRsp();
	}

	/**
	 * 更新一个
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see DefenseModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("更新防御战法")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody DefenseModifyReq req) {
		SpaceTacticDefense old=defenseService.getById(req.getResourceId());
		if(Boolean.TRUE.equals(old.getBuiltin())){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		defenseService.updateDefense(req);
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
	@ApiOperation("删除防御战法")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		List<SpaceTacticDefense> defenses=defenseService.listByIds(req.getIds());
		for(SpaceTacticAttack defense:defenses){
			if (Boolean.TRUE.equals(defense.getBuiltin())) {
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
		}
		defenseService.deleteBatch(req.getIds());
		return new BaseRsp();
	}

	/**
	 * 获取adetail
	 *
	 * @param resource_id 资源id
	 * @return Base RSP< 防御细节>
	 * @throws
	 * @see String
	 * @see BaseRsp<DefenseDetail>
	 */
	@ApiOperation("获取防御战法详情")
	@GetMapping("")
	@CallCount(CallType.TACTIC)
	public BaseRsp<DefenseDetail>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		DefenseDetail detail = defenseService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	/**
	 * Pagec
	 *
	 * @param reqBase 请求体基础
	 * @return 基础RSP<列表RSP < 战法防御类别博>>
	 * @throws
	 * @see PageReqBase
	 * @see BaseRsp<ListRsp<TacticDefenseCategoryBo>>
	 */
	@ApiOperation("防御战法战术分页")
	@GetMapping("/category/page")
	public BaseRsp<ListRsp<TacticDefenseCategoryBo>> pageC(PageReqBase reqBase) {
		ListRsp<TacticDefenseCategoryBo> rsp = defenseService.pageCategory(reqBase);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 添加
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see TacticDefenseCategoryAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("新增防御战法战术")
	@PostMapping("/category")
	public BaseRsp addC(@RequestBody TacticDefenseCategoryAddReq req) {
		defenseService.addDefenseCategory(req);
		return new BaseRsp();
	}

	/**
	 * 更新c
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see TacticDefenseCategoryModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("更新防御战法战术")
	@PatchMapping("/category")
	public BaseRsp updateC(@RequestBody TacticDefenseCategoryModifyReq req) {
		defenseService.updateDefenseCategory(req);
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
	@ApiOperation("删除防御战法战术")
	@DeleteMapping("/category")
	public BaseRsp deleteC(@RequestBody IdListReq req) {
		List <String> resourceIds =defenseService.listByCategoryids(req.getIds());
		if (resourceIds!= null && !resourceIds.isEmpty()) {
			throw new BusinessException(ErrorCode.RELATED_DATA_EXIST);
		}
		defenseService.deleteCategory(req.getIds());
		return new BaseRsp();
	}

	/**
	 * 获取cdetail
	 *
	 * @param resource_id 资源id
	 * @return Base RSP< 防御类别 BO>
	 * @throws
	 * @see String
	 * @see BaseRsp<DefenseCategoryBo>
	 */
	@ApiOperation("防御战法战术详情")
	@GetMapping("/category")
	public BaseRsp<DefenseCategoryBo>
			getCDetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
        DefenseCategoryBo bo = defenseService.getCategoryDetail(resource_id);
        return new BaseRsp<>(bo);
	}

	/**
	 * 列表防御战法战术全部
	 *
	 * @return {@code BaseRsp<List<StagedDefenseCategory>> }
	 */
	@ApiOperation("防御战法战术全部")
	@GetMapping("/category/list")
	public BaseRsp<List<StagedDefenseCategory>> listC() {
		List<TacticDefenseCategoryBo> bos = defenseService.listCategory();
		List<StagedDefenseCategory> rsp = Lists.newArrayList();

		StagedDefenseCategory pre = new StagedDefenseCategory();
		pre.setStage(SpaceCommonConstant.TacticStage.PRE);
		pre.setCategories(StreamApiUtils.filter(bos,
				bo -> bo.getStage().equals(SpaceCommonConstant.TacticStage.PRE)));
		rsp.add(pre);

		StagedDefenseCategory in = new StagedDefenseCategory();
		in.setStage(SpaceCommonConstant.TacticStage.IN);
		in.setCategories(StreamApiUtils.filter(bos,
				bo -> bo.getStage().equals(SpaceCommonConstant.TacticStage.IN)));
		rsp.add(in);

		StagedDefenseCategory post = new StagedDefenseCategory();
		post.setStage(SpaceCommonConstant.TacticStage.POST);
		post.setCategories(StreamApiUtils.filter(bos,
				bo -> bo.getStage().equals(SpaceCommonConstant.TacticStage.POST)));
		rsp.add(post);

		return new BaseRsp<>(rsp);
	}

	/**
	 * 按类别列出
	 *
	 * @param category_id 类别id
	 * @return {@code BaseRsp<List<DefenseDetail>> }
	 */
	@ApiOperation("根据战术获取战法")
	@GetMapping("/list")
	public BaseRsp<List<DefenseDetail>>
			listByCategory(@ApiParam(value = "战术id", name = "category_id") String category_id) {
		List<DefenseDetail> res = defenseService.listByCategoryId(category_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取防御框架
	 *
	 * @return 基础RSP<列表 < 空间框架级别>>
	 * @throws
	 * @see BaseRsp<List<SpaceFrameLevel>>
	 */
	@ApiOperation("获取防御战法框架")
	@GetMapping("/frame")
	public BaseRsp<List<SpaceFrameLevel>> getDefenseFrame() {
		SpaceFrameLevel level = defenseService.getFrame();
		return new BaseRsp<>(level.getChildren());
	}

	@ApiOperation("获取所有战法")
	@GetMapping("/tree")
	public BaseRsp<List<SystemTreeRes>> getTree(@ApiParam(value = "战法id", name = "system_id") @RequestParam(value = "system_id", required = false) String systemId) {
		return new BaseRsp<>(defenseService.tree(systemId));
	}
}

