package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DbToolRes;
import com.cyberpeace.situation.dao.entity.SituationDbTool;
import com.cyberpeace.situation.dao.entity.SituationDbToolCategory;
import com.cyberpeace.situation.dao.mapper.SituationDbToolCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationDbToolMapper;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库管理-管理工具
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "数据库管理-管理工具")
@RestController
@Slf4j
@RequestMapping("/situation/db/tool")
public class DbManageToolController {

	@Autowired
	private SituationDbToolMapper situationDbToolMapper;

	@Autowired
	private SituationDbToolCategoryMapper situationDbToolCategoryMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 管理工具类型-列表查询
	 *
	 * @param pageReqBase 分页请求
	 * @return {@code BaseRsp<List<SituationDbToolCategory>>}
	 */
	@ApiOperation("管理工具类型-列表查询")
	@GetMapping("/category/list")
	public BaseRsp<List<SituationDbToolCategory>>
			getSituationDbToolCategory(PageReqBase pageReqBase) {
		LambdaQueryWrapper<SituationDbToolCategory> queryWrapper = new LambdaQueryWrapper<>();
		String name = pageReqBase.getPattern();
		if (StringUtils.isNotEmpty(name)) {
			queryWrapper.like(SituationDbToolCategory::getName, name);
		}
		List<SituationDbToolCategory> res = situationDbToolCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	/**
	 * 管理工具类型-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationDbToolCategory>}
	 */
	@ApiOperation("管理工具类型-单个查询")
	@GetMapping("/category/")
	public BaseRsp<SituationDbToolCategory> getSituationDbToolCategoryDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {
		SituationDbToolCategory situationDbToolCategory = situationDbToolCategoryMapper
				.selectById(resourceId);
		if (null == situationDbToolCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(situationDbToolCategory);
	}

	/**
	 * 管理工具类型-新增
	 *
	 * @param req 类型新增请求
	 * @return {@code BaseRsp<SituationDbToolCategory>}
	 */
	@ApiOperation("管理工具类型-新增")
	@PostMapping("/category/")
	public BaseRsp<SituationDbToolCategory>
			addSituationDbToolCategory(@RequestBody @Validated DynamicEffectsCategoryReq req) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(req.getName(), null);

		SituationDbToolCategory situationDbToolCategory = new SituationDbToolCategory();
		situationDbToolCategory.setName(req.getName());
		situationDbToolCategoryMapper.insert(situationDbToolCategory);
		return new BaseRsp<>(situationDbToolCategory);
	}

	/**
	 * 管理工具类型-批量删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("管理工具类型-删除")
	@DeleteMapping("/category/")
	public BaseRsp
			deleteSituationDbToolCategory(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<SituationDbToolCategory> dbToolCategories = situationDbToolCategoryMapper
				.selectBatchIds(resourceIds);
		if (dbToolCategories.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		situationDbToolCategoryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	/**
	 * 管理工具类型-修改
	 *
	 * @param req 类型修改请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("管理工具类型-修改")
	@PatchMapping("/category/")
	public BaseRsp
			updateSituationDbToolCategory(@RequestBody @Validated DynamicEffectsCategoryReq req) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(req.getName(), null);

		// 获取管理工具类型
		String resourceId = req.getResourceId();
		SituationDbToolCategory situationDbToolCategory = situationDbToolCategoryMapper
				.selectById(resourceId);
		if (null == situationDbToolCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 判断名称是否重复
		if (!Objects.equals(situationDbToolCategory.getName(), req.getName())) {
			LambdaQueryWrapper<SituationDbToolCategory> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(SituationDbToolCategory::getName, req.getName());
			SituationDbToolCategory toolCategory = situationDbToolCategoryMapper
					.selectOne(queryWrapper);
			if (null != toolCategory) {
				throw new BusinessException(ErrorCode.NAME_DUPLICATE);
			}
		}
		SituationDbToolCategory category = new SituationDbToolCategory();
		BeanUtils.copyProperties(req, category);
		situationDbToolCategoryMapper.updateById(category);
		return new BaseRsp<>();
	}

	/**
	 * 管理工具-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationDbTool>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.GLGJ, type = ServiceLogContext.Type.CX)
	@ApiOperation("管理工具-单个查询")
	@GetMapping("")
	public BaseRsp<SituationDbTool>
			getSituationDbToolDetail(@RequestParam("resourceId") @NotBlank String resourceId) {
		SituationDbTool situationDbTool = situationDbToolMapper.selectById(resourceId);
		if (null == situationDbTool) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(situationDbTool);
	}

	/**
	 * 管理工具-新增
	 *
	 * @param dbToolAddReq 管理工具新增请求
	 * @return {@code BaseRsp<SituationDbTool>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.GLGJ, type = ServiceLogContext.Type.XZ)
	@ApiOperation("管理工具-新增")
	@PostMapping("")
	public BaseRsp<SituationDbTool> setSituationDbTool(
			@RequestBody @Validated(Create.class) SituationDbToolAddReq dbToolAddReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dbToolAddReq.getName(),
				dbToolAddReq.getDescription());

		// 提取工具信息
		SituationDbTool situationDbTool = BeanUtils.copy(dbToolAddReq, SituationDbTool.class);
		situationDbTool.setToolType(dbToolAddReq.getToolCategory());
		String type = situationDbTool.getToolType();
		if (StringUtils.isNotEmpty(type)) {
			// 判断工具类型是否存在
			SituationDbToolCategory situationDbToolCategory = situationDbToolCategoryMapper
					.selectById(type);
			if (null == situationDbToolCategory) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
		}

		// 设置创建用户
		String userId = UserInfoContext.getUserContext().getUserId();
		situationDbTool.setCreateUserId(userId);

		// 插入数据并判断名称是否重复
		try {
			situationDbToolMapper.insert(situationDbTool);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(situationDbTool);
	}

	/**
	 * 管理工具-列表查询
	 *
	 * @param req 管理工具列表查询请求
	 * @return {@code BaseRsp<ListRsp<DbToolRes>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.GLGJ, type = ServiceLogContext.Type.CX)
	@ApiOperation("管理工具-列表查询")
	@GetMapping("list")
	public BaseRsp<ListRsp<DbToolRes>> getSituationDbTool(SituationDbToolFilterReq req) {

		// 查询工具列表
		Page page = req.toPageParam();
		IPage<DbToolRes> dbToolResIPage = situationDbToolMapper.selectPage(page, req);
		ListRsp<DbToolRes> res = PageUtil.getListResult(dbToolResIPage);

		// 获取工具类型
		List<SituationDbToolCategory> dbToolCategories = situationDbToolCategoryMapper
				.selectList(null);
		Map<String, String> categoryMap = StreamApiUtils.toMap(dbToolCategories,
				SituationDbToolCategory::getResourceId, toolCategory -> toolCategory.getName());

		// 组装工具类型
		res.getList().forEach(tool -> {
			String toolCategory = categoryMap.get(tool.getToolType());
			toolCategory = StringUtils.isEmpty(toolCategory) ? "" : toolCategory;
			tool.setToolType(toolCategory);
		});

		return new BaseRsp<>(res);
	}

	/**
	 * 管理工具-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.GLGJ, type = ServiceLogContext.Type.SC)
	@ApiOperation("管理工具-删除")
	@DeleteMapping("")
	public BaseRsp deleteSituationDbTool(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<SituationDbTool> twoDimensionalComponents = situationDbToolMapper
				.selectBatchIds(resourceIds);
		if (twoDimensionalComponents.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		situationDbToolMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	/**
	 * 管理工具-修改
	 *
	 * @param situationDbToolUpdateReq 管理工具修改请求
	 * @return {@code BaseRsp}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.GLGJ, type = ServiceLogContext.Type.XG)
	@ApiOperation("管理工具-修改")
	@PatchMapping("")
	public BaseRsp patchSituationDbTool(
			@RequestBody @Validated(Update.class) SituationDbToolUpdateReq situationDbToolUpdateReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationDbToolUpdateReq.getName(),
				situationDbToolUpdateReq.getDescription());

		// 判断工具是否存在
		SituationDbTool patchObj = BeanUtils.copy(situationDbToolUpdateReq, SituationDbTool.class);
		SituationDbTool situationDbTool = situationDbToolMapper
				.selectById(situationDbToolUpdateReq.getResourceId());
		if (null == situationDbTool) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 处理工具更新数据
		LambdaQueryWrapper<SituationDbTool> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SituationDbTool::getName, situationDbToolUpdateReq.getName());
		SituationDbTool one = situationDbToolMapper.selectOne(queryWrapper);
		if (null != one
				&& !Objects.equals(one.getResourceId(), situationDbToolUpdateReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		situationDbToolMapper.updateById(patchObj);
		return new BaseRsp<>();
	}
}