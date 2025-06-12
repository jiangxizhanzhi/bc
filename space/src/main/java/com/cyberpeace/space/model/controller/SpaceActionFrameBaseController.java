package com.cyberpeace.space.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.dao.entity.SpaceActionRelation;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.context.IdListReq;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 空间行动框架基础Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个抽象类 `SpaceActionFrameBaseController`，用于处理与空间行动框架相关的 CRUD 操作。以下是对代码的主要分析： 
 * ### 类的功能概述 
 * - **抽象类**：这是一个抽象类，提供了通用的方法来处理与空间行动框架相关的操作。 
 * - **泛型参数**： 
 * - `S`：表示服务接口，继承自 `IService<E>`。 
 * - `E`：表示实体类，继承自 `SpaceFrameBaseTable`。 
 * - `B`：表示业务对象类，继承自 `SpaceFrameBoBase`。 
 * - `R`：表示请求类，继承自 `FrameBasePageReq`。 
 * ### 主要方法 
 * 1. **`page` 方法**： 
 * - 提供分页查询功能。 
 * - 根据请求对象 `req` 构建查询条件，并支持按父级 ID 获取子级数据。 
 * - 返回包含分页信息的结果。 
 * 2. **`listAll` 方法**： 
 * - 查询所有记录并返回业务对象列表。 
 * 3. **`addAttack` 方法**： 
 * - 新增记录。 
 * - 自动生成资源 ID。 
 * - 验证名称唯一性。 
 * - 使用事务模板执行插入操作。 
 * 4. **`updateA` 方法**： 
 * - 更新记录。 
 * - 验证资源 ID 是否为空。 
 * - 验证名称唯一性。 
 * - 使用事务模板执行更新操作。 
 * 5. **`deleteA` 方法**： 
 * - 删除记录。 
 * - 检查是否有引用。 
 * - 使用事务模板执行删除操作。 
 * 6. **`getADetail` 方法**： 
 * - 获取单个记录的详细信息。 
 * - 如果记录不存在，抛出异常。 
 * 7. **`checkNameDuplication` 方法**： 
 * - 验证名称是否重复。 
 * 8. **`getRelatedMap` 方法**： 
 * - 获取相关联的数据映射。 
 * 9. **`getUpperRelated` 和 `getUpperIds` 方法**： 
 * - 抽象方法，需要在具体实现中定义。 
 * 10. **`checkReference` 方法**： 
 * - 抽象方法，用于检查是否有引用。 
 * ### 关键技术点 
 * - **MyBatis Plus**：使用了 `LambdaQueryWrapper` 进行条件构造。 
 * - **Guava**：使用了 `Maps` 和 `Lists` 工具类。 
 * - **Spring Transaction**：通过 `TransactionTemplate` 管理事务。 
 * - **Stream API**：利用流式操作处理集合数据。 
 * ### 总结 
 * 该类提供了一个通用的框架来处理与空间行动框架相关的 CRUD 操作，并且通过泛型和抽象方法的设计，使得具体的实现类可以根据需求进行定制化开发。 
**/
public abstract class SpaceActionFrameBaseController<S extends IService<E>, E extends SpaceFrameBaseTable, B extends SpaceFrameBoBase, R extends FrameBasePageReq> {

	@Autowired
	protected S baseService;

	@Autowired
	protected SpaceActionRelationService relationService;

	@Autowired
	protected TransactionTemplate transactionTemplate;

	@ApiOperation("列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<B>> page(R req) {
		LambdaQueryWrapper<E> wrapper = req.req2QueryWrapper();
		wrapper.orderByDesc(E::getUpdateTime);
		List<String> upperIds = req.getUpperIds();
		if (CollectionUtils.isNotEmpty(upperIds)) {
			List<String> ids = relationService.getLowerIdsByUpperIds(upperIds);
			if (ids.isEmpty()) {
				return new BaseRsp<>(PageUtil.getNullResult());
			}
			wrapper.in(E::getResourceId, ids);
		}
		IPage<E> iPage = baseService.page(req.toPageParam(), wrapper);
		Map<String, List<IdAndName>> relatedMap = this.getRelatedMap(iPage.getRecords());
		iPage.convert(e -> e.entity2Bo(relatedMap));
		ListRsp<B> res = PageUtil.getListResult(iPage);
		return new BaseRsp<>(res);
	}


	@ApiOperation("全部")
	@GetMapping("/list_all")
	public BaseRsp<List<B>> listAll() {
		List<E> eList = baseService.list();
		List<B> res = StreamApiUtils.map(eList, e -> (B) e.entity2Bo(Maps.newHashMap()));
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp<E> addAttack(@RequestBody B req) {
		if (StringUtils.isBlank(req.getResourceId())) {
			req.setResourceId(IDGeneratorUtil.getID());
		}
		E entity = (E) req.bo2Entity();
		this.checkNameDuplication(entity);
		this.checkValue(entity);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				baseService.getBaseMapper().insert(entity);
				relationService.createRelation(getUpperIds(req), req.getResourceId());
			}
		});

		return new BaseRsp<>(entity);
	}

	@ApiOperation("更新")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody B req) {
		if (StringUtils.isBlank(req.getResourceId())) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		E entity = (E) req.bo2Entity();
		this.checkNameDuplication(entity);
		this.checkValue(entity);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				baseService.updateById(entity);
				relationService.updateRelation(getUpperIds(req), req.getResourceId());
			}
		});

		return new BaseRsp();
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		this.checkReference(req.getIds());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				baseService.removeByIds(req.getIds());
				relationService.deleteRelationsByIds(req.getIds());
			}
		});
		return new BaseRsp();
	}

	@ApiOperation("详情")
	@GetMapping("")
	public BaseRsp<B>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		E e = baseService.getById(resource_id);
		if(null == e){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		B bo = (B) e.entity2Bo(getRelatedMap(Lists.newArrayList(e)));
		return new BaseRsp<>(bo);
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

	public void checkValue(E entity){
	}

	protected Map<String, List<IdAndName>> getRelatedMap(List<E> eList){
		Map<String, List<IdAndName>> res = Maps.newHashMap();
		if (eList.isEmpty()) {
			return res;
		}
		List<String> lowerIds = StreamApiUtils.map(eList, E::getResourceId);
		LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SpaceActionRelation::getLowerId, lowerIds);
		List<SpaceActionRelation> relations = relationService.list(wrapper);
		if (relations.isEmpty()) {
			return res;
		}
		List<String> upperIds = StreamApiUtils.map(relations, SpaceActionRelation::getUpperId);
		List<? extends SpaceFrameBaseTable> upperList = this.getUpperRelated(upperIds);

		Map<String, IdAndName> upperMap = Maps.newHashMap();
		for (SpaceFrameBaseTable e : upperList) {
			upperMap.put(e.getResourceId(), new IdAndName(e.getResourceId(), e.getName()));
		}
		// 排序，使结果一致
		relations = relations.stream().sorted(Comparator.comparing(SpaceActionRelation::getId)).collect(Collectors.toList());

		for (SpaceActionRelation relation : relations) {
			String lowerId = relation.getLowerId();
			String upperId = relation.getUpperId();
			res.putIfAbsent(lowerId, Lists.newArrayList());
			res.get(lowerId).add(upperMap.get(upperId));
		}
		return res;
	}

	protected List<? extends SpaceFrameBaseTable> getUpperRelated(List<String> upperIds) {
		return Lists.newArrayList();
	}

	protected List<String> getUpperIds(B req){
		return Lists.newArrayList();
	}

	/**
	 * 检查引用
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
	protected void checkReference(List<String> ids) {}

}

