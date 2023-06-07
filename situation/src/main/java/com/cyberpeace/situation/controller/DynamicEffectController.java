package com.cyberpeace.situation.controller;

import java.io.File;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.topo.Color;
import com.cyberpeace.situation.constant.topo.DynamicType;
import com.cyberpeace.situation.constant.topo.LineType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DynamicEffectMappingRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.DynamicEffect;
import com.cyberpeace.situation.dao.entity.DynamicEffectsCategory;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.dao.mapper.DynamicEffectMappingMapper;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsCategoryMapper;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsMapper;
import com.cyberpeace.situation.model.IntType;
import com.cyberpeace.situation.service.DynamicEffectMappingService;
import com.cyberpeace.situation.service.DynamicEffectsCategoryService;
import com.cyberpeace.situation.service.DynamicEffectsService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 动效管理
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "动效管理")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation")
public class DynamicEffectController {

	@Autowired
	private DynamicEffectsMapper dynamicEffectsMapper;

	@Autowired
	private DynamicEffectsCategoryMapper dynamicEffectsCategoryMapper;

	@Autowired
	private DynamicEffectsCategoryService dynamicEffectsCategoryService;

	@Autowired
	private DynamicEffectsService dynamicEffectsService;

	@Autowired
	private DynamicEffectMappingService dynamicEffectMappingService;

	@Autowired
	private DynamicEffectMappingMapper dynamicEffectMappingMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 动效类型-新增
	 *
	 * @param dynamicEffectsCategoryReq 动效类型新增请求
	 * @return {@code BaseRsp<DynamicEffectsCategory>}
	 */
	@ApiOperation("动效类型-新增")
	@PostMapping("/dynamic_effect_library/category")
	public BaseRsp<DynamicEffectsCategory> createDynamicEffectsCategory(
			@RequestBody @Validated(Create.class) DynamicEffectsCategoryReq dynamicEffectsCategoryReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dynamicEffectsCategoryReq.getName(), null);

		// 1、构造动效类型对象
		DynamicEffectsCategory dynamicEffectsCategory = new DynamicEffectsCategory();
		dynamicEffectsCategory.setName(dynamicEffectsCategoryReq.getName());
		// dynamicEffectsCategory.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		// 2、插入数据库
		try {
			dynamicEffectsCategoryMapper.insert(dynamicEffectsCategory);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(dynamicEffectsCategory);
	}

	/**
	 * 动效类型-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("动效类型-删除")
	@DeleteMapping("/dynamic_effect_library/category")
	public BaseRsp<Boolean>
			deleteDynamicEffectsCategory(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		// 1、提取待删除的动效类型id
		List<String> resourceIds = batchDeleteReq.getIds();

		// 2、查询待删除的动效类型对象
		List<DynamicEffectsCategory> dynamicEffectsCategory = dynamicEffectsCategoryMapper
				.selectBatchIds(resourceIds);

		// 3、校验动效类型是否存在
		if (dynamicEffectsCategory.isEmpty()
				|| dynamicEffectsCategory.size() != resourceIds.size()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 4、校验动效类型是否被使用
		if (Boolean.TRUE.equals(dynamicEffectsMapper.checkCategory(resourceIds))) {
			throw new BusinessException(SituationErrorCode.DYNAMIC_EFFECTS_CATEGORY_USED);
		}

		// 4、删除动效类型
		dynamicEffectsCategoryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>(true);
	}

	/**
	 * 动效类型-修改
	 *
	 * @param dynamicEffectsCategoryReq 动效类型修改请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("动效类型-修改")
	@PatchMapping("/dynamic_effect_library/category")
	public BaseRsp<Boolean> modifyDynamicEffectsCategory(
			@RequestBody @Validated(Update.class) DynamicEffectsCategoryReq dynamicEffectsCategoryReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dynamicEffectsCategoryReq.getName(), null);

		// 1、提取更新信息
		String resourceId = dynamicEffectsCategoryReq.getResourceId();
		String name = dynamicEffectsCategoryReq.getName();

		// 2、校验类型是否存在
		DynamicEffectsCategory dynamicEffectsCategory = dynamicEffectsCategoryService
				.getCategory(resourceId, true);

		// 3、校验名称是否重复
		if (Boolean.TRUE.equals(dynamicEffectsCategoryMapper.checkCategoryName(resourceId, name))) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 4、更新数据库
		dynamicEffectsCategory.setName(name);
		dynamicEffectsCategoryMapper.updateById(dynamicEffectsCategory);
		return new BaseRsp<>(true);
	}

	/**
	 * 动效类型-列表查询
	 *
	 * @param name 名称
	 * @return {@code BaseRsp<List<DynamicEffectsCategory>>}
	 */
	@ApiOperation("动效类型-列表查询")
	@GetMapping("/dynamic_effect_library/category/list")
	public BaseRsp<List<DynamicEffectsCategory>> listDynamicEffectsCategory(String name) {

		// 1、构造查询条件
		LambdaQueryWrapper<DynamicEffectsCategory> queryWrapper = new LambdaQueryWrapper<>();
		if (null != name) {
			queryWrapper.like(DynamicEffectsCategory::getName, name);
		}

		// 2、设置排序条件
		queryWrapper.orderByDesc(DynamicEffectsCategory::getCreateTime);

		// 3、查询数据库
		List<DynamicEffectsCategory> res = dynamicEffectsCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	/**
	 * 动效类型-单个查询
	 *
	 * @param resourceId 资源ID
	 * @return {@code BaseRsp<DynamicEffectsCategory>}
	 */
	@ApiOperation("动效类型-单个查询")
	@GetMapping("/dynamic_effect_library/category")
	public BaseRsp<DynamicEffectsCategory>
			getDynamicEffectsCategory(@RequestParam("resourceId") @NotBlank String resourceId) {

		return new BaseRsp<>(dynamicEffectsCategoryService.getCategory(resourceId, true));
	}

	/**
	 * 新增动效
	 *
	 * @param dynamicEffectAddReq 动效新增请求
	 * @return {@code BaseRsp<DynamicEffect>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXZY, type = ServiceLogContext.Type.XZ)
	@ApiOperation("动效-新增")
	@PostMapping("/dynamic_effect_library")
	public BaseRsp<DynamicEffect> setDynamicEffectsLibrary(
			@RequestBody @Validated(Create.class) DynamicEffectAddReq dynamicEffectAddReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dynamicEffectAddReq.getName(), null);

		// 1、提取动效信息
		DynamicEffect dynamicEffect = BeanUtils.copy(dynamicEffectAddReq, DynamicEffect.class);

		// 2、设置动效创建人
		// String userId = UserInfoContext.getUserContext().getUserId();
		// dynamicEffect.setCreateUserId(userId);

		// 3、判断动效类型是否存在
		dynamicEffectsCategoryService.getCategory(dynamicEffectAddReq.getCategory(), true);

		// 4、判断动效名称是否重复
		if (dynamicEffectsMapper.checkName(null, dynamicEffect.getName())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 5、解压动效文件并将数据插入数据库
		try {
			// 5.1 解压动效文件并设置动效zip解压文件路径
			String dynamicFile = dynamicEffectAddReq.getSourceNodeDynamicFile();
			dynamicEffect.setDynamicDir(unZipDynamicFile(dynamicFile));

			// 5.2 插入数据库
			dynamicEffectsMapper.insert(dynamicEffect);
		} catch (Exception e) {
			// 如果插入数据失败，则删除解压的动效文件
			dynamicEffectsService.clearDynamicEffects(dynamicEffect, false);

			// 抛出异常
			if (e instanceof BusinessException) {
				throw e;
			} else {
				throw new BusinessException(SituationErrorCode.ADD_DYNAMIC_EFFECT_FAILED);
			}
		}
		return new BaseRsp<>(dynamicEffect);
	}

	/**
	 * 删除动效
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXZY, type = ServiceLogContext.Type.SC)
	@ApiOperation("动效-删除")
	@DeleteMapping("/dynamic_effect_library")
	public BaseRsp
			deleteDynamicEffectsLibrary(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		List<String> resourceIds = batchDeleteReq.getIds();

		// 1、判断动效是否被使用，如果被使用则抛出异常
		if (dynamicEffectMappingMapper.checkEffectsStatus(resourceIds)) {
			throw new BusinessException(SituationErrorCode.DYNAMIC_EFFECT_IN_USE);
		}

		// 2、删除动效数据
		List<DynamicEffect> dynamicEffectsLibraries = dynamicEffectsMapper
				.selectBatchIds(resourceIds);

		// 2.1、如果动效数据不存在，则抛出异常
		if (dynamicEffectsLibraries.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		dynamicEffectsLibraries.forEach(dynamicEffect -> {
			// 2.2、删除动效表数据
			dynamicEffectsMapper.deleteById(dynamicEffect.getResourceId());
			// 2.3、删除动效文件
			dynamicEffectsService.clearDynamicEffects(dynamicEffect, true);
		});

		return new BaseRsp<>();
	}

	/**
	 * 动效-列表查询（全量）
	 *
	 * @param dynamicEffectFilterReq 动效列表查询请求
	 * @return {@code BaseRsp<List<DynamicEffect>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXZY, type = ServiceLogContext.Type.CX)
	@ApiOperation("动效-列表查询")
	@GetMapping("/dynamic_effect_library/list")
	public BaseRsp<List<DynamicEffect>>
			listDynamicEffects(DynamicEffectFilterReq dynamicEffectFilterReq) {

		// 1、构造动效查询条件
		LambdaQueryWrapper<DynamicEffect> queryWrapper = new LambdaQueryWrapper<>();

		// 2、如果动效名称不为空，则模糊查询动效名称
		String name = dynamicEffectFilterReq.getName();
		if (StringUtils.isNotEmpty(name)) {
			queryWrapper.like(DynamicEffect::getName, name);
		}

		// 3、如果动效类型不为空，则设置动效类型查询条件
		if (StringUtils.isNotEmpty(dynamicEffectFilterReq.getCategory())) {
			String category = dynamicEffectFilterReq.getCategory();

			// 判断动效类型是否存在
			dynamicEffectsCategoryService.getCategory(category, true);

			// 设置动效类型查询条件
			queryWrapper.eq(DynamicEffect::getCategory, category);
		}

		// 4、设置排序条件
		queryWrapper.orderByDesc(DynamicEffect::getCreateTime);

		// 5、执行查询
		List<DynamicEffect> res = dynamicEffectsMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	/**
	 * 动效-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DynamicEffect>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXZY, type = ServiceLogContext.Type.CX)
	@ApiOperation("动效-单个查询")
	@GetMapping("/dynamic_effect_library")
	public BaseRsp<DynamicEffect>
			getDynamicEffect(@RequestParam("resourceId") @NotBlank String resourceId) {

		return new BaseRsp<>(dynamicEffectsService.getDynamicEffect(resourceId, true));
	}

	/**
	 * 解压文件
	 *
	 * @param zipPath zip路径
	 * @return {@code String}
	 */
	private String unZipDynamicFile(String zipPath) {

		// 1、设置目标路径
		String destPath = "/cpms/upload/dynamic_effects/";
		File zipFile = new File(zipPath);
		destPath = destPath + zipFile.getName().substring(0, zipFile.getName().indexOf("."));

		// 2、判断文件路径是否存在，如果不存在则抛出未找到动效文件的异常
		if (!zipFile.exists()) {
			throw new BusinessException(SituationErrorCode.DYNAMIC_EFFECT_FILE_NOT_FOUND);
		}

		// 3、解压文件
		ZipUtil.unzipToDest(zipPath, destPath);
		return destPath;
	}

	/**
	 * 获取动效映射属性选项
	 *
	 * @return {@code BaseRsp<OptionsRes>}
	 */
	@ApiOperation("动效-获取颜色、线型选项")
	@GetMapping("/dynamic_effect_library/option")
	public BaseRsp<OptionsRes> getOption() {
		OptionsRes optionsRes = new OptionsRes();

		// 设置线型选项
		optionsRes.setLineTypeList(LineType.getMsgList());

		// 设置颜色选项
		optionsRes.setColorList(Color.getMsgList());
		return new BaseRsp<>(optionsRes);
	}

	/**
	 * 动效映射-新增
	 *
	 * @param dynamicEffectMappingReq 动态映射新增请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXYS, type = ServiceLogContext.Type.XZ)
	@ApiOperation("动效映射-新增")
	@PostMapping("/dynamic/effect/mapping")
	public BaseRsp<String> setDynamicEffectsMapping(
			@RequestBody @Validated(Create.class) DynamicEffectMappingReq dynamicEffectMappingReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dynamicEffectMappingReq.getName(), null);

		return new BaseRsp<>(
				dynamicEffectMappingService.createDynamicEffectsMapping(dynamicEffectMappingReq));
	}

	/**
	 * 动效映射-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXYS, type = ServiceLogContext.Type.SC)
	@ApiOperation("动效映射-删除")
	@DeleteMapping("/dynamic/effect/mapping")
	public BaseRsp<Integer>
			deleteDynamicEffectsMapping(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		return new BaseRsp<>(
				dynamicEffectMappingService.deleteDynamicEffectsMapping(batchDeleteReq));
	}

	/**
	 * 动效映射-修改
	 *
	 * @param dynamicEffectMappingUpdateReq 动态映射修改请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXYS, type = ServiceLogContext.Type.XG)
	@ApiOperation("动效映射-修改")
	@PatchMapping("/dynamic/effect/mapping")
	public BaseRsp<String> patchDynamicEffectsMapping(
			@RequestBody @Validated(Update.class) DynamicEffectMappingUpdateReq dynamicEffectMappingUpdateReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dynamicEffectMappingUpdateReq.getName(), null);

		return new BaseRsp<>(dynamicEffectMappingService
				.patchDynamicEffectsMapping(dynamicEffectMappingUpdateReq));
	}

	/**
	 * 动效映射-列表查询
	 *
	 * @param dynamicEffectMappingFilterReq 动态映射列表查询请求
	 * @return {@code BaseRsp<ListRsp<DynamicEffectsMapping>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXYS, type = ServiceLogContext.Type.CX)
	@ApiOperation("动效映射-列表查询（分页）")
	@GetMapping("/dynamic/effect/mapping/list")
	public BaseRsp<ListRsp<DynamicEffectMappingRes>>
			getDynamicEffectsMapping(DynamicEffectMappingPageReq dynamicEffectMappingFilterReq) {

		return new BaseRsp<>(dynamicEffectMappingService
				.getDynamicEffectsMapping(dynamicEffectMappingFilterReq));
	}

	/**
	 * 动效映射-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DynamicEffectsMapping>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DXYS, type = ServiceLogContext.Type.CX)
	@ApiOperation("动效映射-单个查询")
	@GetMapping("/dynamic/effect/mapping")
	public BaseRsp<DynamicEffectsMapping> getDynamicEffectsMappingDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		return new BaseRsp<>(
				dynamicEffectMappingService.getDynamicEffectsMapping(resourceId, true));
	}

	/**
	 * 动效分类-列表查询
	 *
	 * @return {@code BaseRsp<List<IntType>>}
	 */
	@ApiOperation("动效分类-列表查询")
	@GetMapping("/dynamic/effect/type")
	public BaseRsp<List<IntType>> getDynamicEffectType() {

		return new BaseRsp<>(DynamicType.getList());
	}

}