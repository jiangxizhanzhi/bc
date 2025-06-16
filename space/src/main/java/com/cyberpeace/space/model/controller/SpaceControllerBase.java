package com.cyberpeace.space.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.model.SpaceBoBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.context.IdListReq;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 空间Controller基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码定义了一个抽象类 `SpaceControllerBase`，用于处理与空间（Space）相关的通用业务逻辑。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **列表查询**：提供分页查询接口，支持根据请求参数动态生成查询条件，并返回结果。 
 * 2. **新增操作**：接收业务对象（BO），将其转换为实体对象（Entity），并保存到数据库中。 
 * 3. **更新操作**：基于资源 ID 更新现有记录，确保不修改内置资源，并检查名称唯一性。 
 * 4. **删除操作**：批量删除指定 ID 的记录，同时禁止删除内置资源。 
 * 5. **详情查询**：通过资源 ID 查询单个记录的详细信息。 
 * 6. **全量查询**：获取所有记录，并按 ID 排序。 
 * 7. **通用方法**：包括名称重复校验、值校验等辅助方法。 
 * ### 关键特性： 
 * - 使用了 MyBatis-Plus 提供的 `IService` 和 `IPage` 进行数据库操作。 
 * - 借助泛型支持多种业务对象（BO）、实体对象（Entity）以及请求参数类型。 
 * - 验证机制：如名称长度验证、名称唯一性验证、内置资源保护等。 
 * - 工具类集成：如 `IDGeneratorUtil` 生成唯一 ID，`PageUtil` 处理分页结果。 
 * ### 设计亮点： 
 * 1. **抽象化设计**：通过抽象类避免重复代码，同时允许子类灵活实现特定逻辑。 
 * 2. **扩展性强**：利用泛型支持不同类型的业务场景。 
 * 3. **模块化验证**：将复杂的业务规则封装在独立的方法中，便于维护和复用。 
 * ### 总结： 
 * 此代码是一个典型的 CRUD 操作框架，适用于需要快速构建空间管理模块的场景。它结合了 MyBatis-Plus 的强大功能与 Spring MVC 的灵活性，提供了清晰的接口划分和良好的扩展性。 
**/
public abstract class SpaceControllerBase<S extends IService<E>, E extends SpaceBaseTable, B extends SpaceBoBase, R extends SpacePageReqBase> {

	@Autowired
	protected S baseService;


	@ApiOperation("列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<B>> page(R req) {
		LambdaQueryWrapper<E> wrapper = req.req2QueryWrapper();
		IPage<E> iPage = baseService.page(req.toPageParam(), wrapper);
		Map<String, IdAndName> relatedMap = getRelatedMap(iPage.getRecords());
		iPage.convert(e -> e.entity2Bo(relatedMap));
		ListRsp<B> res = PageUtil.getListResult(iPage);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody B req) {
		req.setResourceId(IDGeneratorUtil.getID());
		E entity = (E) req.bo2Entity();
		entity.setBuiltin(false);
		this.checkNameDuplication(entity);
		this.checkValue(entity);
		baseService.save(entity);
		return new BaseRsp();
	}

	@ApiOperation("更新")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody B req) {
		if (StringUtils.isBlank(req.getResourceId())) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		E old = baseService.getById(req.getResourceId());
		if(null == old){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if(null != old.getBuiltin() && old.getBuiltin()){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		E entity = (E) req.bo2Entity();
		entity.setBuiltin(false);
		this.checkNameDuplication(entity);
		this.checkValue(entity);
		baseService.updateById(entity);
		return new BaseRsp();
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		checkBeforeDelete(req);
		List<E> eList = baseService.listByIds(req.getIds());
		for (E e : eList) {
			if(null != e.getBuiltin() && e.getBuiltin()){
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
		}
		baseService.removeByIds(req.getIds());
		return new BaseRsp();
	}


	protected void checkBeforeDelete(IdListReq req) {
		return;
	}

	@ApiOperation("详情")
	@GetMapping("")
	public BaseRsp<B>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		E res = baseService.getById(resource_id);
		if (null == res) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		B bo = (B) res.entity2Bo(getRelatedMap(Lists.newArrayList(res)));
		this.detailAddCallTypeNum();

		return new BaseRsp<>(bo);
	}

	@ApiOperation("列出全部")
	@GetMapping("/list_all")
	public BaseRsp<List<B>> listAll() {
		LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(E::getId);
		List<E> eList = baseService.list(wrapper);
		List<B> res = Lists.newArrayList();
		for (E e : eList) {
			res.add((B) e.entity2Bo(getRelatedMap(eList)));
		}
		return new BaseRsp<>(res);
	}

	public boolean checkNameDuplication(E entity) {
		CrCommonValidator.validateNameLen(entity.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(E::getName, entity.getName());
		E one = baseService.getOne(wrapper);
		if (null != one && (StringUtils.isBlank(entity.getResourceId())
				|| !one.getResourceId().equals(entity.getResourceId()))) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return true;
	}

	public void detailAddCallTypeNum(){
	}
	public void checkValue(E entity){
	}

	public void checkPercentageValue(Integer value){
		if(value > 100){
			throw new BusinessException(SpaceError.PERCENTAGE_LARGER_THAN_100);
		}
	}

	protected Map<String, IdAndName> getRelatedMap(List<E> eList) {
		return Maps.newHashMap();
	}

}

